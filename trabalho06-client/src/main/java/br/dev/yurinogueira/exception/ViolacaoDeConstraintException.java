package br.dev.yurinogueira.exception;

public class ViolacaoDeConstraintException extends RuntimeException {
    public ViolacaoDeConstraintException(String msg) {
        super(msg);
    }
}
