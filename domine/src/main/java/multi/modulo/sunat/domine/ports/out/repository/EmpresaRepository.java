package multi.modulo.sunat.domine.ports.out.repository;

import multi.modulo.sunat.domine.model.EmpresaDto;
import java.util.List;

public interface EmpresaRepository {
    List<EmpresaDto> findByNumeroDocumento(String nroRuc);
    EmpresaDto save(String ruc, int pivot);
    EmpresaDto update(EmpresaDto dto);
    void deleteById(Integer id);
}
