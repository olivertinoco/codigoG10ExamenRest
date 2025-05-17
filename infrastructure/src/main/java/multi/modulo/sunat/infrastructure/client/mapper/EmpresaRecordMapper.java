package multi.modulo.sunat.infrastructure.client.mapper;

import multi.modulo.sunat.infrastructure.client.adapter.EmpresaTable;
import multi.modulo.sunat.infrastructure.client.dto.EmpresaBuild;
import multi.modulo.sunat.infrastructure.client.record.EmpresaRecord;
import org.jooq.DSLContext;

public class EmpresaRecordMapper {
    public static EmpresaRecord fromDto(DSLContext dsl, EmpresaTable e, EmpresaBuild sunat){
        EmpresaRecord record = dsl.newRecord(e);

        record.setId(sunat.getId());
        record.setRazonSocial(sunat.getRazonSocial());
        record.setTipoDocumento(sunat.getTipoDocumento());
        record.setNumeroDocumento(sunat.getNumeroDocumento());
        record.setEstado(sunat.getEstado());
        record.setCondicion(sunat.getCondicion());
        record.setDireccion(sunat.getDireccion());
        record.setUbigeo(sunat.getUbigeo());
        record.setDistrito(sunat.getDistrito());
        record.setProvincia(sunat.getProvincia());
        record.setDepartamento(sunat.getDepartamento());
        record.setEsAgenteRetencion(sunat.getEsAgenteRetencion());
        record.setEsBuenContribuyente(sunat.getEsBuenContribuyente());
        record.setActividadEconomica(sunat.getActividadEconomica());
        record.setNumeroTrabajadores(sunat.getNumeroTrabajadores());
        record.setTipoFacturacion(sunat.getTipoFacturacion());
        record.setTipoContabilidad(sunat.getTipoContabilidad());

        return  record;
    }
}
