package br.edu.positivo.reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.positivo.reservas.entity.Professor;
import br.edu.positivo.reservas.exception.RecursoNaoEncontradoException;
import br.edu.positivo.reservas.repository.ProfessorRepository;

@Service 
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    public List<Professor> listar(){
        return professorRepository.findAll();
    }

    public Professor buscarPorId(Long id){
        return professorRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Professor não encontrado: "+id) );
    }

    public Professor criar(Professor professor){
        return professorRepository.save(professor);
    }

    public Professor atualizar(Long id, Professor dados){
        Professor existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setCpf(dados.getCpf());
        return professorRepository.save(existente);
    }

    public void excluir(Long id){
        buscarPorId(id);
        professorRepository.deleteById(id);
    }

}
