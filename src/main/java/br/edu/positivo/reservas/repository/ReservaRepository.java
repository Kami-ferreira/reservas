package br.edu.positivo.reservas.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.positivo.reservas.entity.Reserva;
import br.edu.positivo.reservas.entity.StatusReserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsBySalaIdAndStatusAndInicioLessThanAndFimGreaterThan(
            Long salaId, StatusReserva status, LocalDateTime fim, LocalDateTime inicio);

    boolean existsByUsuarioIdAndStatusAndInicioLessThanAndFimGreaterThan(
            Long usuarioId, StatusReserva status, LocalDateTime fim, LocalDateTime inicio);
}