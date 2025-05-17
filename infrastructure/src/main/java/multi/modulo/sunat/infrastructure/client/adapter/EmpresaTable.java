package multi.modulo.sunat.infrastructure.client.adapter;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.TableField;
import org.jooq.UniqueKey;

import static org.jooq.impl.Internal.createUniqueKey;
import multi.modulo.sunat.infrastructure.client.record.EmpresaRecord;

public class EmpresaTable extends TableImpl<EmpresaRecord> {

    public static final EmpresaTable EMPRESA = new EmpresaTable();

    public final TableField<EmpresaRecord, Integer> id =
            createField(DSL.name("id"), SQLDataType.INTEGER.identity(true).nullable(false), this);

    public final TableField<EmpresaRecord, String> razonSocial =
            createField(DSL.name("razonSocial"), SQLDataType.VARCHAR(400).nullable(true), this);

    public final TableField<EmpresaRecord, String> tipoDocumento =
            createField(DSL.name("tipoDocumento"), SQLDataType.VARCHAR(10).nullable(true), this);

    public final TableField<EmpresaRecord, String> numeroDocumento =
            createField(DSL.name("numeroDocumento"), SQLDataType.VARCHAR(20).nullable(true), this);

    public final TableField<EmpresaRecord, String> estado =
            createField(DSL.name("estado"), SQLDataType.VARCHAR(20).nullable(true), this);

    public final TableField<EmpresaRecord, String> condicion =
            createField(DSL.name("condicion"), SQLDataType.VARCHAR(20).nullable(true), this);

    public final TableField<EmpresaRecord, String> direccion =
            createField(DSL.name("direccion"), SQLDataType.VARCHAR(400).nullable(true), this);

    public final TableField<EmpresaRecord, String> ubigeo =
            createField(DSL.name("ubigeo"), SQLDataType.VARCHAR(10).nullable(true), this);

    public final TableField<EmpresaRecord, String> distrito =
            createField(DSL.name("distrito"), SQLDataType.VARCHAR(300).nullable(true), this);

    public final TableField<EmpresaRecord, String> provincia =
            createField(DSL.name("provincia"), SQLDataType.VARCHAR(300).nullable(true), this);

    public final TableField<EmpresaRecord, String> departamento =
            createField(DSL.name("departamento"), SQLDataType.VARCHAR(300).nullable(true), this);

    public final TableField<EmpresaRecord, Boolean> esAgenteRetencion =
            createField(DSL.name("EsAgenteRetencion"), SQLDataType.BOOLEAN.nullable(true), this);

    public final TableField<EmpresaRecord, Boolean> esBuenContribuyente =
            createField(DSL.name("EsBuenContribuyente"), SQLDataType.BOOLEAN.nullable(true), this);

    public final TableField<EmpresaRecord, String> actividadEconomica =
            createField(DSL.name("actividadEconomica"), SQLDataType.CLOB.nullable(true), this);

    public final TableField<EmpresaRecord, String> numeroTrabajadores =
            createField(DSL.name("numeroTrabajadores"), SQLDataType.VARCHAR(10).nullable(true), this);

    public final TableField<EmpresaRecord, String> tipoFacturacion =
            createField(DSL.name("tipoFacturacion"), SQLDataType.VARCHAR(100).nullable(true), this);

    public final TableField<EmpresaRecord, String> tipoContabilidad =
            createField(DSL.name("tipoContabilidad"), SQLDataType.VARCHAR(100).nullable(true), this);

    public EmpresaTable() {
        super(DSL.name("sunat"));
    }

    public final UniqueKey<EmpresaRecord> PK_EMPRESA =
            createUniqueKey(this, DSL.name("pk_empresa"), id);

    public final UniqueKey<EmpresaRecord> UK_NUMERO_DOCUMENTO =
            createUniqueKey(this, numeroDocumento);

    @Override
    public UniqueKey<EmpresaRecord> getPrimaryKey() {
        return PK_EMPRESA;
    }

    @Override
    public Class<? extends EmpresaRecord> getRecordType() {
        return EmpresaRecord.class;
    }
}
