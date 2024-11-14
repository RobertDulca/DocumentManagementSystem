package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.entity.DocumentEntity;
import at.fhtw.swkom.paperless.persistence.repository.DocumentRepository;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import at.fhtw.swkom.paperless.services.exception.StorageException;
import at.fhtw.swkom.paperless.services.mapper.DocumentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public void init() {

    }

    @Override
    public void store(String document, MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("File is empty");
        }

        String fileName = file.getOriginalFilename();
        String filePath = "path/to/your/storage/location/" + fileName;

        try {
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Datei.", e);
        }

        DocumentEntity documentEntity = new DocumentEntity();

        documentRepository.save(documentEntity);
    }

    @Override
    public List<DocumentDTO> loadAll() {
        return documentRepository.findAll().stream().map(entity -> documentMapper.INSTANCE.entityToDto(entity)).collect(Collectors.toList());
    }

    @Override
    public DocumentDTO load(Integer id) {
        return documentRepository.findById(id).map(entity -> documentMapper.INSTANCE.entityToDto(entity)).orElseThrow(() -> new EntityNotFoundException("Document with id " + id + " not found"));
    }

    @Override
    public void delete(Integer id) {
        documentRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, DocumentDTO documentDTO) {

    }
}
