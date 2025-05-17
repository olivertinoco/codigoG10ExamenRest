package multi.modulo.sunat.domine.usecase;

import lombok.RequiredArgsConstructor;
import multi.modulo.sunat.domine.model.EmpresaDto;
import multi.modulo.sunat.domine.ports.in.EmpresaService;
import multi.modulo.sunat.domine.ports.out.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;

    @Override
    public List<EmpresaDto> listAll(String ruc){
        return repository.findByNumeroDocumento(ruc);
    }

    @Override
    public EmpresaDto create(String ruc, int pivot){
        return repository.save(ruc, pivot);
    }

    @Override
    public EmpresaDto update(EmpresaDto dto){
        return repository.update(dto);
    }

    @Override
    public void delete(Integer id){
        repository.deleteById(id);
    }
}
