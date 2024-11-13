package at.fhtw.swkom.paperless.persistance.repository;

import at.fhtw.swkom.paperless.persistance.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {
    DocumentEntity findByTitle(String title);
}

