package dao;

import excecao.ObjetoNaoEncontradoException;

public interface DaoGenerico<T, Pk> {

    T inclui(T obj);

    void altera(T obj);

    void exclui(T obj);

    T getPorId(Pk id) throws ObjetoNaoEncontradoException;

    T getPorIdComLock(Pk id) throws ObjetoNaoEncontradoException;

}
