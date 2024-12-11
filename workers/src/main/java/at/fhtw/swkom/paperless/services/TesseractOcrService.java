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
    public void processMessage(String message, String storagePath) throws JsonProcessingException {
        log.info("Received Message: " + message);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new JsonNullableModule());

        // fetch document data
        Document document = mapper.readValue( message, Document.class);
        log.debug("Received Document: " + document);
        log.debug("Received Document storage path: " + storagePath);

        // fetch document file
        var doc = storageService.download(storagePath);
        if ( doc==null || doc.length == 0)
            throw new StorageFileNotFoundException(storagePath);

        // do OCR recognition
        try (InputStream inputStream = new ByteArrayInputStream(doc)) {
            File tempFile = createTempFile(storagePath, inputStream);
            String result = doOCR(tempFile);
            log.info(result);

            document.setContent(result);

        } catch (TesseractException | IOException e) {
            log.error(e.getMessage());
        }

        String documentString = mapper.writeValueAsString(document);
        rabbit.convertAndSend( RabbitMQConfig.OCR_QUEUE_NAME, documentString);
    }

    public String doOCR(File tempFile) throws TesseractException {
        var tesseract = new Tesseract();
        tesseract.setDatapath(tesseractData);
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
