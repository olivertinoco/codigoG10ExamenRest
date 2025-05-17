package multi.modulo.sunat.infrastructure.client.repository;

import multi.modulo.sunat.domine.model.EmpresaDto;
import multi.modulo.sunat.domine.ports.out.repository.EmpresaRepository;

import multi.modulo.sunat.infrastructure.client.dto.EmpresaBuild;
import multi.modulo.sunat.infrastructure.client.dto.SunatResponse;
import multi.modulo.sunat.infrastructure.client.mapper.EmpresaDtoMapper;
import multi.modulo.sunat.infrastructure.client.mapper.EmpresaRecordMapper;
import multi.modulo.sunat.infrastructure.client.record.EmpresaRecord;
import multi.modulo.sunat.infrastructure.client.adapter.EmpresaTable;
import multi.modulo.sunat.infrastructure.config.SunatApiSelector;
import multi.modulo.sunat.infrastructure.config.SunatClientType;

import org.jooq.*;
import org.jooq.impl.DSL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import retrofit2.Response;


@Repository
@RequiredArgsConstructor
@Log4j2
public class EmpresaRepositoryImpl implements EmpresaRepository {

    private final DSLContext dsl;
    private final SunatApiSelector apiSelector;
    private static final EmpresaTable p = EmpresaTable.EMPRESA;

    @Value("${token.api}")
    private String token;

    @Override
    public List<EmpresaDto> findByNumeroDocumento(String nroRuc) {
        return dsl.select(
                p.id,
                p.razonSocial,
                p.tipoDocumento,
                p.numeroDocumento,
                p.estado,
                p.condicion,
                p.direccion,
                p.ubigeo,
                p.distrito,
                p.provincia,
                p.departamento,
                p.esAgenteRetencion,
                p.esBuenContribuyente,
                p.actividadEconomica,
                p.numeroTrabajadores,
                p.tipoFacturacion,
                p.tipoContabilidad
                )
                .from(p)
                .where(
                        nroRuc != null
                                ? p.numeroDocumento.eq(nroRuc)
                                : DSL.noCondition()
                )
                .orderBy(p.id)
                .limit(10)
                .fetchInto(EmpresaDto.class);
    }

    @Override
    public EmpresaDto save(String ruc, int pivot) {
        try {
            SunatResponse data;
            SunatClientType tipoCliente = determinarTipoDeCliente(pivot);

            if (tipoCliente == SunatClientType.FEIGN || tipoCliente == SunatClientType.RESTTEMPLATE) {

                ResponseEntity<SunatResponse> response = (tipoCliente == SunatClientType.FEIGN) ?
                        apiSelector.getDatosPorRucFeign(ruc, token) :
                        apiSelector.getDatosPorRucRestTemplate(ruc, token);
                data = (response.getStatusCode().is2xxSuccessful() && response.hasBody())
                        ? response.getBody()
                        : null;
            } else if (tipoCliente == SunatClientType.RETROFIT) {

                Response<SunatResponse> response = apiSelector.getDatosPorRucRetrofit(ruc, token);
                data = (response.isSuccessful() && Objects.nonNull(response.body()))
                        ? response.body()
                        : null;
            } else {
                throw new IllegalArgumentException("Tipo de cliente RENIEC no soportado: " + tipoCliente);
            }

            if(data != null) {
                EmpresaBuild empresa = EmpresaBuild.builder()
                        .razonSocial(data.getRazonSocial())
                        .tipoDocumento(data.getTipoDocumento())
                        .numeroDocumento(data.getNumeroDocumento())
                        .estado(data.getEstado())
                        .condicion(data.getCondicion())
                        .direccion(data.getDireccion())
                        .ubigeo(data.getUbigeo())
                        .distrito(data.getDistrito())
                        .provincia(data.getUbigeo())
                        .departamento(data.getDepartamento())
                        .esAgenteRetencion(data.getEsAgenteRetencion())
                        .esBuenContribuyente(data.getEsBuenContribuyente())
                        .actividadEconomica(data.getActividadEconomica())
                        .numeroTrabajadores(data.getNumeroTrabajadores())
                        .tipoFacturacion(data.getTipoFacturacion())
                        .tipoContabilidad(data.getTipoContabilidad())
                        .build();

                EmpresaRecord record =
                        EmpresaRecordMapper.fromDto(dsl, p, empresa);

                Integer id =
                        dsl.insertInto(p)
                                .set(p.razonSocial, record.getRazonSocial())
                                .set(p.tipoDocumento, record.getTipoDocumento())
                                .set(p.numeroDocumento, record.getNumeroDocumento())
                                .set(p.estado, record.getEstado())
                                .set(p.condicion, record.getCondicion())
                                .set(p.direccion, record.getDireccion())
                                .set(p.ubigeo, record.getUbigeo())
                                .set(p.distrito, record.getDistrito())
                                .set(p.provincia, record.getProvincia())
                                .set(p.departamento, record.getDepartamento())
                                .set(p.esAgenteRetencion, record.getEsAgenteRetencion())
                                .set(p.esBuenContribuyente, record.getEsBuenContribuyente())
                                .set(p.actividadEconomica, record.getActividadEconomica())
                                .set(p.numeroTrabajadores, record.getNumeroTrabajadores())
                                .set(p.tipoFacturacion, record.getTipoFacturacion())
                                .set(p.tipoContabilidad, record.getTipoContabilidad())
                                .returning(p.id)
                                .fetchOne()
                                .getValue(p.id);

                log.info("ID GENERADO {}", id);

                return EmpresaDtoMapper.toDto(record, id);

            } else {
                throw new RuntimeException("RENIEC devolvió una respuesta vacía o inválida para DNI: " + ruc);
            }
        }catch (IOException e) {
            throw new RuntimeException("Fallo en Retrofit al obtener datos de RENIEC", e);
        } catch (Exception e) {
            throw new RuntimeException("Fallo en llamada a RENIEC", e);
        }
    }

    @Override
    public EmpresaDto update(EmpresaDto dto) {
        EmpresaBuild original = dsl
                .select()
                .from(p)
                .where(p.id.eq(dto.getId()))
                .fetchOneInto(EmpresaBuild.class);

        if (original != null) {
            EmpresaBuild actualizado = original.toBuilder()
                    .id(dto.getId())
                    .razonSocial(dto.getRazonSocial() != null ? dto.getRazonSocial() : original.getRazonSocial())
                    .tipoDocumento(dto.getTipoDocumento() != null ? dto.getTipoDocumento() : original.getTipoDocumento())
                    .numeroDocumento(dto.getNumeroDocumento() != null ? dto.getNumeroDocumento() : original.getNumeroDocumento())
                    .estado(dto.getEstado() != null ? dto.getEstado() : original.getEstado())
                    .condicion(dto.getCondicion() != null ? dto.getCondicion() : original.getCondicion())
                    .direccion(dto.getDireccion() != null ? dto.getDireccion() : original.getDireccion())
                    .ubigeo(dto.getUbigeo() != null ? dto.getUbigeo() : original.getUbigeo())
                    .distrito(dto.getDistrito() != null ? dto.getDistrito() : original.getDistrito())
                    .provincia(dto.getProvincia() != null ? dto.getProvincia() : original.getProvincia())
                    .departamento(dto.getDepartamento() != null ? dto.getDepartamento() : original.getDepartamento())
                    .esAgenteRetencion(dto.getEsAgenteRetencion() != null ? dto.getEsAgenteRetencion() : original.getEsAgenteRetencion())
                    .esBuenContribuyente(dto.getEsBuenContribuyente() != null ? dto.getEsBuenContribuyente() : original.getEsBuenContribuyente())
                    .actividadEconomica(dto.getActividadEconomica() != null ? dto.getActividadEconomica() : original.getActividadEconomica())
                    .numeroTrabajadores(dto.getNumeroTrabajadores() != null ? dto.getNumeroTrabajadores() : original.getNumeroTrabajadores())
                    .tipoFacturacion(dto.getTipoFacturacion() != null ? dto.getTipoFacturacion() : original.getTipoFacturacion())
                    .tipoContabilidad(dto.getTipoContabilidad() != null ? dto.getTipoContabilidad() : original.getTipoContabilidad())
                    .build();

            EmpresaRecord record =
                    EmpresaRecordMapper.fromDto(dsl, p, actualizado);

            Query updateQuery =
                    dsl.update(p)
                            .set(p.razonSocial, record.getRazonSocial())
                            .set(p.tipoDocumento, record.getTipoDocumento())
                            .set(p.numeroDocumento, record.getNumeroDocumento())
                            .set(p.estado, record.getEstado())
                            .set(p.condicion, record.getCondicion())
                            .set(p.direccion, record.getDireccion())
                            .set(p.ubigeo, record.getUbigeo())
                            .set(p.distrito, record.getDistrito())
                            .set(p.provincia, record.getProvincia())
                            .set(p.departamento, record.getDepartamento())
                            .set(p.esAgenteRetencion, record.getEsAgenteRetencion())
                            .set(p.esBuenContribuyente, record.getEsBuenContribuyente())
                            .set(p.actividadEconomica, record.getActividadEconomica())
                            .set(p.numeroTrabajadores, record.getNumeroTrabajadores())
                            .set(p.tipoFacturacion, record.getTipoFacturacion())
                            .set(p.tipoContabilidad, record.getTipoContabilidad())
                            .where(p.id.eq(record.getId()));

            log.info("DATO ACTUALIZAR");
            log.debug(Arrays.toString(updateQuery.getBindValues().toArray()));

            updateQuery.execute();
            return EmpresaDtoMapper.toDto(record, null);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        dsl.deleteFrom(p)
                .where(p.id.eq(id))
                .execute();
    }

    private SunatClientType determinarTipoDeCliente(int pivot) {
        switch (pivot) {
            case 1:
                return SunatClientType.FEIGN;
            case 2:
                return SunatClientType.RESTTEMPLATE;
            case 3:
                return SunatClientType.RETROFIT;
            default:
                throw new IllegalArgumentException("Valor de pivot no válido: " + pivot);
        }
    }
}
