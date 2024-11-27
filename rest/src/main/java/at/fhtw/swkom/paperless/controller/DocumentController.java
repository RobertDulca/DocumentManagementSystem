package at.fhtw.swkom.paperless.controller;

import at.fhtw.swkom.paperless.config.RabbitMQConfig;
import at.fhtw.swkom.paperless.persistence.entities.Document;
import at.fhtw.swkom.paperless.services.DocumentService;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import at.fhtw.swkom.paperless.services.echo.EchoService;
import jakarta.annotation.Generated;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-17T08:44:06.510922473Z[Etc/UTC]", comments = "Generator version: 7.10.0-SNAPSHOT")
@RestController
@RequestMapping("${openapi.paperlessRESTServer.base-path:}")
public class DocumentController implements ApiApi {

    private final NativeWebRequest request;
    private final DocumentService documentService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DocumentController(NativeWebRequest request, DocumentService documentService, RabbitTemplate rabbitTemplate) {
        this.request = request;
        this.documentService = documentService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> deleteDocument(Integer id) {
        documentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DocumentDTO> getDocument(Integer id) {
        DocumentDTO document = documentService.load(id);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        List<DocumentDTO> documents = documentService.loadAll();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postDocument(String document, MultipartFile file) {
        Document documentEntity = new Document(document);
        int messageCount = 1;

        if (document == null || file == null || file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            // Save the document to the database
            documentService.store(documentEntity);

            // Create a RabbitMQ message with headers
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader(RabbitMQConfig.ECHO_MESSAGE_COUNT_PROPERTY_NAME, messageCount);
            Message message = MessageBuilder
                    .withBody(document.getBytes())
                    .andProperties(messageProperties)
                    .build();

            // Send the message to the RabbitMQ queue
            rabbitTemplate.send(RabbitMQConfig.ECHO_IN_QUEUE_NAME, message);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> updateDocument(Integer id) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentService.update(id, documentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
