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

import br.edu.positivo.reservas.entity.Sala;
import br.edu.positivo.reservas.service.SalaService;

@RestController 
@RequestMapping("/salas")
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService){
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> listar() {
        return salaService.listar();
    }

    @GetMapping("/{id}")
    public Sala buscarPorId(@PathVariable Long id){
        return salaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sala criar(@RequestBody Sala sala){
        return salaService.criar(sala);
    }

    @PutMapping("/{id}")
    public  Sala atualizar(@PathVariable Long id, @RequestBody Sala dados){
        return salaService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        salaService.excluir(id);
    }

}
