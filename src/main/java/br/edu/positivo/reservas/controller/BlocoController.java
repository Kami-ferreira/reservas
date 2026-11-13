package br.edu.positivo.reservas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import br.edu.positivo.reservas.entity.Bloco;
import br.edu.positivo.reservas.service.BlocoService;

@RestController 
@RequestMapping("/blocos")
public class BlocoController {
    private final BlocoService blocoService;

    public BlocoController(BlocoService blocoService){
        this.blocoService = blocoService;
    }

    @GetMapping
    public List<Bloco> listar() {
        return blocoService.listar();
    }

    @GetMapping("/{id}")
    public Bloco buscarPorId(@PathVariable Long id){
        return blocoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bloco criar(@RequestBody Bloco bloco){
        return blocoService.criar(bloco);
    }

    @PutMapping("/{id}")
    public  Bloco atualizar(@PathVariable Long id, @RequestBody Bloco dados){
        return blocoService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        blocoService.excluir(id);
    }

}
