package br.edu.positivo.reservas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.positivo.reservas.dto.ReservaRequest;
import br.edu.positivo.reservas.entity.*;
import br.edu.positivo.reservas.exception.RegraNegocioException;
import br.edu.positivo.reservas.repository.BlocoRepository;
import br.edu.positivo.reservas.repository.SalaRepository;
import br.edu.positivo.reservas.repository.UsuarioRepository;
import br.edu.positivo.reservas.service.ReservaService;

@SpringBootTest
@Transactional
class ReservaServiceTest {

    @Autowired private ReservaService reservaService;
    @Autowired private BlocoRepository blocoRepository;
    @Autowired private SalaRepository salaRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    private Sala salaEstudo;
    private Sala laboratorio;
    private Aluno aluno;
    private Professor professor;
    private LocalDateTime semanaQueVem;

    @BeforeEach
    void preparar() {
        Bloco bloco = new Bloco();
        bloco.setNome("Bloco Teste");
        bloco = blocoRepository.save(bloco);

        salaEstudo = criarSala("Sala Teste", 8, TipoSala.ESTUDO, bloco);
        laboratorio = criarSala("Lab Teste", 30, TipoSala.LABORATORIO, bloco);

        aluno = new Aluno();
        aluno.setNome("Aluno Teste");
        aluno.setEmail("aluno@teste.com");
        aluno.setRgm("11111111");
        aluno = usuarioRepository.save(aluno);

        professor = new Professor();
        professor.setNome("Professor Teste");
        professor.setEmail("professor@teste.com");
        professor.setCpf("22222222222");
        professor = usuarioRepository.save(professor);

        semanaQueVem = LocalDate.now().plusDays(7).atTime(10, 0);
    }

    private Sala criarSala(String nome, int capacidade, TipoSala tipo, Bloco bloco) {
        Sala sala = new Sala();
        sala.setNome(nome);
        sala.setAndar(1);
        sala.setCapacidade(capacidade);
        sala.setTipo(tipo);
        sala.setStatus(StatusSala.ATIVA);
        sala.setBloco(bloco);
        return salaRepository.save(sala);
    }

    @Test
    void deveCriarReservaValidaComStatusAtiva() {
        // Arrange
        ReservaRequest dados = new ReservaRequest(
                salaEstudo.getId(), aluno.getId(),
                semanaQueVem, semanaQueVem.plusHours(2), 5);

        // Act
        Reserva reserva = reservaService.criar(dados);

        // Assert
        assertNotNull(reserva.getId());
        assertEquals(StatusReserva.ATIVA, reserva.getStatus());
    }

    @Test
    void naoDevePermitirAlunoReservarLaboratorio() {
        // Arrange
        ReservaRequest dados = new ReservaRequest(
                laboratorio.getId(), aluno.getId(),
                semanaQueVem, semanaQueVem.plusHours(2), 5);

        // Act + Assert
        RegraNegocioException erro = assertThrows(
                RegraNegocioException.class,
                () -> reservaService.criar(dados));

        assertEquals("Apenas professores podem reservar laboratórios.", erro.getMessage());
    }

    @Test
    void naoDevePermitirConflitoDeHorarioNaMesmaSala() {
        // Arrange: o aluno já reservou a sala das 10h às 12h
        reservaService.criar(new ReservaRequest(
                salaEstudo.getId(), aluno.getId(),
                semanaQueVem, semanaQueVem.plusHours(2), 5));

        // o professor tenta das 11h às 13h, na mesma sala
        ReservaRequest conflitante = new ReservaRequest(
                salaEstudo.getId(), professor.getId(),
                semanaQueVem.plusHours(1), semanaQueVem.plusHours(3), 3);

        // Act + Assert
        RegraNegocioException erro = assertThrows(
                RegraNegocioException.class,
                () -> reservaService.criar(conflitante));

        assertEquals("A sala já está reservada nesse horário.", erro.getMessage());
    }

    @Test
    void deveCancelarReserva() {
        // Arrange: criar uma reserva válida (copiem do teste 1)
        Reserva reserva = reservaService.criar(new ReservaRequest(
                salaEstudo.getId(), aluno.getId(),
                semanaQueVem, semanaQueVem.plusHours(2), 5));

        // Act: cancelar a reserva usando o id dela
        Reserva cancelada = reservaService.cancelar(reserva.getId());

        // Assert: o status deve ser CANCELADA
        assertEquals(StatusReserva.CANCELADA, cancelada.getStatus());
    }
}