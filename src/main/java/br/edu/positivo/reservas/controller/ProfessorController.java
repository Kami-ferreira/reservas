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

import br.edu.positivo.reservas.entity.Professor;
import br.edu.positivo.reservas.service.ProfessorService;

@RestController 
@RequestMapping("/professores")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService){
        this.professorService = professorService;
    }

    @GetMapping
    public List<Professor> listar() {
        return professorService.listar();
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id){
        return professorService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor criar(@RequestBody Professor professor){
        return professorService.criar(professor);
    }

    @PutMapping("/{id}")
    public  Professor atualizar(@PathVariable Long id, @RequestBody Professor dados){
        return professorService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        professorService.excluir(id);
    }

}
