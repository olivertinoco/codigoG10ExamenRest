package multi.modulo.sunat.infrastructure.client.mapper;

import multi.modulo.sunat.domine.model.EmpresaDto;
import multi.modulo.sunat.infrastructure.client.record.EmpresaRecord;

public class EmpresaDtoMapper {

    public static EmpresaDto toDto(EmpresaRecord record, Integer id){
        EmpresaDto dto = new EmpresaDto();

        if(id != null){
            dto.setId(id);
        }else{
            dto.setId(record.getId());
        }

        dto.setRazonSocial(record.getRazonSocial());
        dto.setTipoDocumento(record.getTipoDocumento());
        dto.setNumeroDocumento(record.getNumeroDocumento());
        dto.setEstado(record.getEstado());
        dto.setCondicion(record.getCondicion());
        dto.setDireccion(record.getDireccion());
        dto.setUbigeo(record.getUbigeo());
        dto.setDistrito(record.getDistrito());
        dto.setProvincia(record.getProvincia());
        dto.setDepartamento(record.getDepartamento());
        dto.setEsAgenteRetencion(record.getEsAgenteRetencion());
        dto.setEsBuenContribuyente(record.getEsBuenContribuyente());
        dto.setActividadEconomica(record.getActividadEconomica());
        dto.setNumeroTrabajadores(record.getNumeroTrabajadores());
        dto.setTipoFacturacion(record.getTipoFacturacion());
        dto.setTipoContabilidad(record.getTipoContabilidad());

        return dto;
    }
}
