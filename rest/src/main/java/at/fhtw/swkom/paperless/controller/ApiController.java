package at.fhtw.swkom.paperless.controller;

import at.fhtw.swkom.paperless.services.dto.DocumentDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-17T08:44:06.510922473Z[Etc/UTC]", comments = "Generator version: 7.10.0-SNAPSHOT")
@Controller
@RequestMapping("${openapi.paperlessRESTServer.base-path:}")
public class ApiController implements ApiApi {

    private final NativeWebRequest request;

    @Autowired
    public ApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> deleteDocument(Integer id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DocumentDTO> getDocument(Integer id) {
        DocumentDTO document = new DocumentDTO();
        document.setId(id);
        document.setTitle("Hardcoded Document Name");
        document.setAuthor("Peter");

        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        List<DocumentDTO> documents = new ArrayList<>();
        DocumentDTO doc1 = new DocumentDTO();
        doc1.setId(1);
        doc1.setTitle("Document 1");
        doc1.setAuthor("Peter 1");

        DocumentDTO doc2 = new DocumentDTO();
        doc2.setId(2);
        doc2.setTitle("Document 2");
        doc2.setAuthor("Peter 2");

        documents.add(doc1);
        documents.add(doc2);

        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postDocument(String document, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateDocument(Integer id) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
