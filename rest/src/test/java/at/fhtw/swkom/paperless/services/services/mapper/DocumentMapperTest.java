package at.fhtw.swkom.paperless.services.services.mapper;

import at.fhtw.swkom.paperless.persistence.entities.Document;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import at.fhtw.swkom.paperless.services.mappers.DocumentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DocumentMapperTest {

   @Test
    void dtoToEntity() {
        // Arrange
        var dtoDocument = new DocumentDTO();
        dtoDocument.setTitle("title");

        // Act
        var result = DocumentMapper.INSTANCE.dtoToEntity(dtoDocument);

        // Assert
        assertNotNull( result );
        assertEquals( dtoDocument.getTitle(), result.getTitle() );
    }

    @Test
    void entityToDto() {
        // Arrange
        var entityDocument = Document.builder()
                .id( 1 )
                .title( "title")
                .build();

        // Act
        var result = DocumentMapper.INSTANCE.entityToDto(entityDocument);

        // Assert
        assertNotNull( result );
        assertEquals( entityDocument.getTitle(), result.getTitle());
    }
}
