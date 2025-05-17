package multi.modulo.sunat.infrastructure.client.mapper;

import multi.modulo.sunat.domine.model.EmpresaDto;
import multi.modulo.sunat.infrastructure.client.dto.EmpresaEntity;
import multi.modulo.sunat.infrastructure.client.record.EmpresaRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    EmpresaDto toDto(EmpresaEntity entity);
    EmpresaDto toDto(EmpresaRecord record);
}
