package at.fhtw.swkom.paperless.services.exception;

import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import at.fhtw.swkom.paperless.services.DocumentServiceImpl;
import at.fhtw.swkom.paperless.services.mappers.DocumentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ExceptionTests {
    private DocumentServiceImpl documentService;

    @BeforeEach
    void setUp() {
        DocumentRepository documentRepository = mock(DocumentRepository.class);
        DocumentMapper documentMapper = mock(DocumentMapper.class);
        documentService = new DocumentServiceImpl(documentRepository, documentMapper);
    }

    @Test
    void testStoreThrowsStorageExceptionForNullDocument() {
        // Act & Assert
        StorageException exception = assertThrows(StorageException.class, () -> documentService.store(null));

        assertEquals("No documentEntity found!", exception.getMessage());
    }
}
