package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.config.RabbitMQConfig;
import at.fhtw.swkom.paperless.entities.Document;
import at.fhtw.swkom.paperless.exception.StorageFileNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;

@Component
@Slf4j
public class TesseractOcrService implements OcrService{
    private final RabbitTemplate rabbit;

    private final FileStorage storageService;

    private final String tesseractData;

    @Autowired
    public TesseractOcrService(RabbitTemplate rabbit, FileStorage storageService, @Value("${tesseract.data}") String tessData) {
        this.rabbit = rabbit;
        this.storageService = storageService;
        this.tesseractData = tessData;
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.OCR_QUEUE_NAME)
    public void processMessage(org.springframework.amqp.core.Message message) throws JsonProcessingException {
        log.info("Received Message: {}", message);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new JsonNullableModule());

        try {
            // Deserialize the body to Document
            String body = new String(message.getBody());
            Document document = mapper.readValue(body, Document.class);

            // Retrieve the storagePath from message headers
            String storagePath = message.getMessageProperties().getHeader("storagePath");
            if (storagePath == null || storagePath.isEmpty()) {
                throw new IllegalArgumentException("Storage path is missing in message headers");
            }

            log.debug("Received Document: {}", document);
            log.debug("Storage Path: {}", storagePath);

            // Fetch the file from MinIO
            byte[] fileBytes = storageService.download(storagePath);
            if (fileBytes == null || fileBytes.length == 0) {
                throw new StorageFileNotFoundException(storagePath);
            }

            // Perform OCR on the file
            try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
                File tempFile = createTempFile(storagePath, inputStream);
                String result = doOCR(tempFile);
                log.info("OCR Result: {}", result);

                // Update document content
                document.setContent(result);

                // Send updated document back to RabbitMQ
                String updatedMessage = mapper.writeValueAsString(document);
                rabbit.convertAndSend(RabbitMQConfig.OCR_QUEUE_NAME, updatedMessage);
                log.info("Updated document sent to RabbitMQ");

            } catch (TesseractException | IOException e) {
                log.error("Error during OCR process", e);
            }

        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }

    public String doOCR(File tempFile) throws TesseractException {
        var tesseract = new Tesseract();
        tesseract.setDatapath(tesseractData); // Injected path from application.properties
        tesseract.setLanguage("eng");
        return tesseract.doOCR(tempFile);
    }

    private static File createTempFile(String storagePath, InputStream is) throws IOException {
        File tempFile = File.createTempFile(StringUtils.getFilename(storagePath), "." + StringUtils.getFilenameExtension(storagePath));
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }
}
