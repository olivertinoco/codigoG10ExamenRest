package multi.modulo.sunat.application;

import org.jooq.DSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import static org.jooq.impl.DSL.constraint;

@Component
public class CreaTablaSunat {

    private final DSLContext dsl;

    public CreaTablaSunat(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Async
    public void crearTablaSunatAsync() {
        dsl.createTableIfNotExists("sunat")
                .column("id", SQLDataType.INTEGER.identity(true)) // SERIAL
                .column("razonSocial", SQLDataType.VARCHAR(400))
                .column("tipoDocumento", SQLDataType.VARCHAR(10))
                .column("numeroDocumento", SQLDataType.VARCHAR(20))
                .column("estado", SQLDataType.VARCHAR(20))
                .column("condicion", SQLDataType.VARCHAR(20))
                .column("direccion", SQLDataType.VARCHAR(400))
                .column("ubigeo", SQLDataType.VARCHAR(10))
                .column("distrito", SQLDataType.VARCHAR(300))
                .column("provincia", SQLDataType.VARCHAR(300))
                .column("departamento", SQLDataType.VARCHAR(300))
                .column("EsAgenteRetencion", SQLDataType.BOOLEAN)
                .column("EsBuenContribuyente", SQLDataType.BOOLEAN)
                .column("actividadEconomica", SQLDataType.CLOB) // TEXT
                .column("numeroTrabajadores", SQLDataType.VARCHAR(10))
                .column("tipoFacturacion", SQLDataType.VARCHAR(100))
                .column("tipoContabilidad", SQLDataType.VARCHAR(100))
                .constraints(
                        constraint("pk_sunat").primaryKey("id"),
                        constraint("uk_numero_documento_sunat").unique("numeroDocumento")
                )
                .execute();
    }
}
