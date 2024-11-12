package at.fhtw.swkom.paperless.services.mapper;

import at.fhtw.swkom.paperless.persistance.entity.DocumentEntity;
import at.fhtw.swkom.paperless.services.dto.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentEntity dtoToEntity(DocumentDTO dto);

    DocumentDTO entityToDto(DocumentEntity entity);

}
