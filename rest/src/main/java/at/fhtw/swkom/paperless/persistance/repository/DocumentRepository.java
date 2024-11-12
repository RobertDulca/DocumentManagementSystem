package at.fhtw.swkom.paperless.persistance.repository;

import at.fhtw.swkom.paperless.persistance.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {
    DocumentEntity findByTitle(String title);
}

