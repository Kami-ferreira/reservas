package br.edu.positivo.reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.positivo.reservas.entity.Bloco;
import br.edu.positivo.reservas.exception.RecursoNaoEncontradoException;
import br.edu.positivo.reservas.repository.BlocoRepository;

@Service 
public class BlocoService {
    private final BlocoRepository blocoRepository;

    public BlocoService(BlocoRepository blocoRepository){
        this.blocoRepository = blocoRepository;
    }

    public List<Bloco> listar(){
        return blocoRepository.findAll();
    }

    public Bloco buscarPorId(Long id){
        return blocoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Bloco não encontrado: "+id) );
    }

    public Bloco criar(Bloco bloco){
        return blocoRepository.save(bloco);
    }

    public Bloco atualizar(Long id, Bloco dados){
        Bloco existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        return blocoRepository.save(existente);
    }

    public void excluir(Long id){
        buscarPorId(id);
        blocoRepository.deleteById(id);
    }

}
