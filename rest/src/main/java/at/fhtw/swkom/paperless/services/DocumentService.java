package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.entity.DocumentEntity;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    void init();

    void store(DocumentEntity documentEntity);

    List<DocumentDTO> loadAll();

    DocumentDTO load(Integer id);

    void delete(Integer id);

    void update(Integer id, DocumentDTO documentDTO);
}
