package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.entities.Document;
import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import at.fhtw.swkom.paperless.services.mappers.DocumentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public void init() {
        // Initialization logic (if required)
    }

    @Override
    public void store(Document documentEntity) {
        documentRepository.save(documentEntity);
    }

    @Override
    public List<DocumentDTO> loadAll() {
        // Use the mapper to convert entities to DTOs
        return documentRepository.findAll()
                .stream()
                .map(documentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentDTO load(Integer id) {
        // Find entity and map it to DTO
        return documentRepository.findById(id)
                .map(documentMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Document with ID " + id + " not found"));
    }

    @Override
    public void delete(Integer id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Document with ID " + id + " not found for deletion");
        }
        documentRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, DocumentDTO documentDTO) {
        // Find the existing entity
        Document existingDocument = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with ID " + id + " not found for update"));

        // Use the mapper to update the entity
        Document updatedDocument = documentMapper.dtoToEntity(documentDTO);
        updatedDocument.setId(existingDocument.getId()); // Ensure ID consistency

        documentRepository.save(updatedDocument);
    }
}
