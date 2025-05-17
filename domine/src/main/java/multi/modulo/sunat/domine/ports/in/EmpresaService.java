package multi.modulo.sunat.domine.ports.in;

import multi.modulo.sunat.domine.model.EmpresaDto;

import java.util.List;

public interface EmpresaService {
    List<EmpresaDto> listAll(String ruc);
    EmpresaDto create(String ruc, int pivot);
    EmpresaDto update(EmpresaDto dto);
    void delete(Integer id);
}
