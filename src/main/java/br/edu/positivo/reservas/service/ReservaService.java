package br.edu.positivo.reservas.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.positivo.reservas.dto.ReservaRequest;
import br.edu.positivo.reservas.entity.Professor;
import br.edu.positivo.reservas.entity.Reserva;
import br.edu.positivo.reservas.entity.Sala;
import br.edu.positivo.reservas.entity.StatusReserva;
import br.edu.positivo.reservas.entity.StatusSala;
import br.edu.positivo.reservas.entity.TipoSala;
import br.edu.positivo.reservas.entity.Usuario;
import br.edu.positivo.reservas.exception.RecursoNaoEncontradoException;
import br.edu.positivo.reservas.exception.RegraNegocioException;
import br.edu.positivo.reservas.repository.ReservaRepository;
import br.edu.positivo.reservas.repository.UsuarioRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaService salaService;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRepository usuarioRepository,
                          SalaService salaService) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaService = salaService;
    }

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada: " + id));
    }

    public Reserva criar(ReservaRequest dados) {
        // Buscar a sala e o usuário (se não existirem, dá 404)
        Sala sala = salaService.buscarPorId(dados.salaId());
        Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + dados.usuarioId()));

        // Regras 1, 2 e 3: horário
        validarHorario(dados.inicio(), dados.fim());

        // Regra 4: sala em manutenção
        if (sala.getStatus() == StatusSala.EM_MANUTENCAO) {
            throw new RegraNegocioException("A sala está em manutenção.");
        }

        //  Regra 5: capacidade
        if (dados.quantidadePessoas() > sala.getCapacidade()) {
            throw new RegraNegocioException("A quantidade de pessoas excede a capacidade da sala.");
        }

        // Regra 6: só professor reserva laboratório
        if (sala.getTipo() == TipoSala.LABORATORIO && !(usuario instanceof Professor)) {
            throw new RegraNegocioException("Apenas professores podem reservar laboratórios.");
        }

        // Regra 7: conflito de horário da sala
        if (reservaRepository.existsBySalaIdAndStatusAndInicioLessThanAndFimGreaterThan(
                sala.getId(), StatusReserva.ATIVA, dados.fim(), dados.inicio())) {
            throw new RegraNegocioException("A sala já está reservada nesse horário.");
        }

        //  Regra 8: conflito de horário do usuário
        if (reservaRepository.existsByUsuarioIdAndStatusAndInicioLessThanAndFimGreaterThan(
                usuario.getId(), StatusReserva.ATIVA, dados.fim(), dados.inicio())) {
            throw new RegraNegocioException("O usuário já possui uma reserva nesse horário.");
        }

        // Tudo certo: montar e salvar a reserva
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setInicio(dados.inicio());
        reserva.setFim(dados.fim());
        reserva.setQuantidadePessoas(dados.quantidadePessoas());
        reserva.setStatus(StatusReserva.ATIVA);
        return reservaRepository.save(reserva);
    }

    public Reserva cancelar(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new RegraNegocioException("A reserva já está cancelada.");
        }
        reserva.setStatus(StatusReserva.CANCELADA);
        return reservaRepository.save(reserva);
    }

    private void validarHorario(LocalDateTime inicio, LocalDateTime fim) {
        if (!fim.isAfter(inicio)) {
            throw new RegraNegocioException("O horário de fim deve ser depois do início.");
        }
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("Não é possível reservar no passado.");
        }
        if (!inicio.toLocalDate().equals(fim.toLocalDate())) {
            throw new RegraNegocioException("A reserva deve começar e terminar no mesmo dia.");
        }
        if (inicio.toLocalTime().isBefore(LocalTime.of(8, 0))
                || fim.toLocalTime().isAfter(LocalTime.of(21, 0))) {
            throw new RegraNegocioException("Reservas só são permitidas entre 8h e 21h.");
        }
    }
}