package br.edu.positivo.reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.positivo.reservas.entity.Bloco;
import br.edu.positivo.reservas.entity.Sala;
import br.edu.positivo.reservas.exception.RecursoNaoEncontradoException;
import br.edu.positivo.reservas.repository.SalaRepository;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final BlocoService blocoService;

    public SalaService(SalaRepository salaRepository, BlocoService blocoService) {
        this.salaRepository = salaRepository;
        this.blocoService = blocoService;
    }

    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala não encontrada: " + id));
    }

    public Sala criar(Sala sala) {
        Bloco bloco = blocoService.buscarPorId(sala.getBloco().getId());
        sala.setBloco(bloco);
        return salaRepository.save(sala);
    }

    public Sala atualizar(Long id, Sala dados) {
        Sala existente = buscarPorId(id);

        existente.setNome(dados.getNome());
        existente.setAndar(dados.getAndar());
        existente.setCapacidade(dados.getCapacidade());
        existente.setTipo(dados.getTipo());
        existente.setStatus(dados.getStatus());

        return salaRepository.save(existente);
    }

    public void excluir(Long id) {
        buscarPorId(id);
        salaRepository.deleteById(id);
    }
}