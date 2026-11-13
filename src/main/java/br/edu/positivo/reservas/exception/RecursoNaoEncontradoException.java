package br.edu.positivo.reservas.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
