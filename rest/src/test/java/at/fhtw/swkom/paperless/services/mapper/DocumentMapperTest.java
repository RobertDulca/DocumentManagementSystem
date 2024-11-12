package at.fhtw.swkom.paperless.services.mapper;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DocumentMapperTest {

    /*@Test
    void dtoToEntity() {
        // Arrange
        var dtoDocument = new DocumentDTO();
        dtoDocument.setTitle( JsonNullable.of("title"));
        dtoDocument.setContent( JsonNullable.of( "content") );

        var dtoCorrespondent = new at.fhtw.swkom.paperless.services.dto.Correspondent();
        dtoCorrespondent.setId(101L);
        dtoCorrespondent.setName( JsonNullable.of("John Doe"));
        dtoDocument.setCorrespondent( JsonNullable.of(dtoCorrespondent.getId().intValue()) );

        var dtoDocumentType = new at.fhtw.swkom.paperless.services.dto.DocumentType();
        dtoDocumentType.setId(202L);
        dtoDocumentType.setName( JsonNullable.of("Scriptum") );
        dtoDocument.setDocumentType( JsonNullable.of(dtoDocumentType.getId().intValue()));

        var dtoDocTag = new at.fhtw.swkom.paperless.services.dto.DocTag();
        dtoDocTag.setId(1001L);
        dtoDocTag.setName( JsonNullable.of("REDTag") );
        dtoDocument.setTags( JsonNullable.of( List.of( dtoDocTag.getId().intValue() ) ) );

        // Act
        var result = DocumentMapper.INSTANCE.dtoToEntity(dtoDocument);

        // Assert
        assertNotNull( result );
        assertEquals( dtoDocument.getTitle().get(), result.getTitle() );
        assertEquals( dtoDocument.getContent().get(), result.getContent() );

        assertNotNull( result.getCorrespondent() );
        assertEquals( dtoCorrespondent.getId(), result.getCorrespondent().getId() );

        assertNotNull( result.getDocumentType() );
        assertEquals( dtoDocumentType.getId(), result.getDocumentType().getId() );

        assertNotNull( result.getTags() );
        assertEquals( 1, result.getTags().size() );
        assertEquals( dtoDocTag.getId(), result.getTags().get(0).getId() );
    }

    @Test
    void entityToDto() {
        // Arrange
        var entityCorrespondent = Correspondent.builder()
                .id( 101 )
                .build();
        var entityDocumentType = DocumentType.builder()
                .id( 202 )
                .build();
        var entityDocument = Document.builder()
                .id( 1 )
                .title( "title")
                .content( "content" )
                .correspondent( entityCorrespondent )
                .documentType( entityDocumentType )
                .build();

        // Act
        var result = DocumentMapper.INSTANCE.entityToDto(entityDocument);

        // Assert
        assertNotNull( result );
        assertEquals( entityDocument.getTitle(), result.getTitle().get() );
        assertEquals( entityDocument.getContent(), result.getContent().get() );

        assertNotNull( result.getCorrespondent() );
        assertEquals( entityCorrespondent.getId(), result.getCorrespondent().get().longValue() );

        assertNotNull( result.getDocumentType() );
        assertEquals( entityDocumentType.getId(), result.getDocumentType().get().longValue() );
    }*/

}
