package at.fhtw.swkom.paperless.controller;

import at.fhtw.swkom.paperless.persistence.entities.Document;
import at.fhtw.swkom.paperless.services.DocumentService;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import at.fhtw.swkom.paperless.services.echo.EchoService;
import jakarta.annotation.Generated;
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
    private final EchoService echoService;

    @Autowired
    public DocumentController(NativeWebRequest request, DocumentService documentService, EchoService echoService) {
        this.request = request;
        this.documentService = documentService;
        this.echoService = echoService;
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
        if (document == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            documentService.store(documentEntity);
            echoService.processMessage(document, messageCount);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
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
