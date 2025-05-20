package multi.modulo.sunat.application.controller;

import lombok.NoArgsConstructor;
import multi.modulo.sunat.domine.model.EmpresaDto;
import multi.modulo.sunat.domine.ports.in.EmpresaService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    @GetMapping("/listarAll")
    public List<EmpresaDto> listAll (){
        return empresaService.listAll(null);
    }

    @GetMapping("/listarPorRuc/{ruc}")
    public List<EmpresaDto> listarRuc(@PathVariable String ruc){
        return empresaService.listAll(ruc);
    }

    @PostMapping("/nuevo/feign/{ruc}")
    public EmpresaDto crearFeign(@PathVariable String ruc){
        return empresaService.create(ruc, 1);
    }

    @PostMapping("/nuevo/RestTempl/{ruc}")
    public EmpresaDto crearRestTemplate(@PathVariable String ruc){
        return empresaService.create(ruc, 2);
    }

    @PostMapping("/nuevo/retrofit/{ruc}")
    public EmpresaDto crearRetrofit(@PathVariable String ruc){
        return empresaService.create(ruc, 3);
    }

    @PutMapping("/actualiza/{id}")
    public EmpresaDto actualizar(
            @PathVariable Integer id,
            @RequestBody EmpresaDto dto
    ) {
        dto.setId(id);
        return empresaService.update(dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id){
        empresaService.delete(id);
    }

}

