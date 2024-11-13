package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.entity.DocumentEntity;
import at.fhtw.swkom.paperless.persistence.repository.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    public DocumentEntity save(DocumentEntity documentEntity) {
        return documentRepository.save(documentEntity);
    }
}
