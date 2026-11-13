package br.edu.positivo.reservas.exception;

import java.time.LocalDateTime;

public record ErroResposta(int status, String mensagem, LocalDateTime momento) {
} 