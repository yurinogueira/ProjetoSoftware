package dao.impl;

import anotacao.Executar;
import dao.DaoGenerico;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import jakarta.persistence.*;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JPADaoGeneric<T, Pk> implements DaoGenerico<T, Pk> {

    public EntityManager em;

    private final Class<T> tipo;

    public JPADaoGeneric(Class<T> tipo) {
        this.tipo = tipo;
    }

    @Executar
    @Override
    public T inclui(T obj) {
        try {
            em.persist(obj);
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }

        return obj;
    }

    @Executar
    @Override
    public void altera(T obj) {
        try {
            em.merge(obj);
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Executar
    @Override
    public void exclui(T obj) {
        try {
            em.remove(obj);
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Executar
    @Override
    public T getPorId(Pk id) throws ObjetoNaoEncontradoException {
        T objeto;

        try {
            objeto = em.find(tipo, id);
            if (objeto == null) {
                throw new ObjetoNaoEncontradoException();
            }
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }

        return objeto;
    }

    @Executar
    @Override
    public T getPorIdComLock(Pk id) throws ObjetoNaoEncontradoException {
        T objeto;

        try {
            objeto = em.find(tipo, id, LockModeType.PESSIMISTIC_WRITE);
            if (objeto == null) {
                throw new ObjetoNaoEncontradoException();
            }
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }

        return objeto;
    }

    public final List<T> buscaLista(Method metodo, Object[] argumentos) {
        try {
            String nomeDaBusca = tipo.getSimpleName() + "." + metodo.getName();
            TypedQuery<T> namedQuery = em.createNamedQuery(nomeDaBusca, tipo);

            if (argumentos != null) {
                for (int i = 0; i < argumentos.length; i++) {
                    Object arg = argumentos[i];
                    namedQuery.setParameter(i + 1, arg);
                }
            }
            return namedQuery.getResultList();
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

}
