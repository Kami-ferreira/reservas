package br.edu.positivo.reservas.dto;

import java.time.LocalDateTime;

public record ReservaRequest(
        Long salaId,
        Long usuarioId,
        LocalDateTime inicio,
        LocalDateTime fim,
        Integer quantidadePessoas) {
}