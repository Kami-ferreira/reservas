package br.edu.positivo.reservas.exception;

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem){
        super(mensagem);
    }
}
