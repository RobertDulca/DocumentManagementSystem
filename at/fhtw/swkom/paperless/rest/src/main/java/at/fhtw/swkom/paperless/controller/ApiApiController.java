package at.fhtw.swkom.paperless.controller;

import at.fhtw.swkom.paperless.services.dto.Document;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-16T19:41:45.793122307Z[Etc/UTC]", comments = "Generator version: 7.10.0-SNAPSHOT")
@Controller
@RequestMapping("${openapi.paperlessRESTServer.base-path:}")
public class ApiApiController implements ApiApi {

    private final NativeWebRequest request;

    @Autowired
    public ApiApiController(NativeWebRequest request) {
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
    public ResponseEntity<Document> getDocument(Integer id) {
        Document document = new Document();
        document.setId(id);
        document.setTitle("Hardcoded Document Name");
        document.setAuthor("Peter");

        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Document>> getDocuments() {
        List<Document> documents = new ArrayList<>();
        Document doc1 = new Document();
        doc1.setId(1);
        doc1.setTitle("Document 1");
        doc1.setAuthor("Peter 1");

        Document doc2 = new Document();
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
}
