package br.dev.yurinogueira.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {
    private String model;
    public EntidadeNaoEncontradaException(String model, String message) {
        super(message);
        this.model = model;
    }
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
    public String getModel() {
        return model;
    }
}