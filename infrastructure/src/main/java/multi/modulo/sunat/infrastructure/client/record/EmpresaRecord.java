package multi.modulo.sunat.infrastructure.client.record;

import lombok.Getter;
import lombok.Setter;
import org.jooq.impl.UpdatableRecordImpl;
import multi.modulo.sunat.infrastructure.client.adapter.EmpresaTable;

@Getter
@Setter
public class EmpresaRecord extends UpdatableRecordImpl<EmpresaRecord> {

    public EmpresaRecord() {
        super(EmpresaTable.EMPRESA);
    }

    private Integer id;
    private String razonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String distrito;
    private String provincia;
    private String departamento;
    private Boolean esAgenteRetencion;
    private Boolean esBuenContribuyente;
    private String actividadEconomica;
    private String numeroTrabajadores;
    private String tipoFacturacion;
    private String tipoContabilidad;
}
