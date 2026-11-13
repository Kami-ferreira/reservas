package br.edu.positivo.reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.positivo.reservas.entity.Aluno;
import br.edu.positivo.reservas.exception.RecursoNaoEncontradoException;
import br.edu.positivo.reservas.repository.AlunoRepository;

@Service 
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listar(){
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id){
        return alunoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: "+id) );
    }

    public Aluno criar(Aluno aluno){
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno dados){
        Aluno existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setRgm(dados.getRgm());
        return alunoRepository.save(existente);
    }

    public void excluir(Long id){
        buscarPorId(id);
        alunoRepository.deleteById(id);
    }

}
