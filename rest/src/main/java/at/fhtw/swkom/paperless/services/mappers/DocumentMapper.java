package at.fhtw.swkom.paperless.services.mappers;

import at.fhtw.swkom.paperless.persistence.entities.Document;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    Document dtoToEntity(DocumentDTO dto);

    DocumentDTO entityToDto(Document entity);
    List<DocumentDTO> entityListToDtoList(List<Document> entities);

}
