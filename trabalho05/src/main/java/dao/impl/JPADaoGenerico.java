package dao.impl;

import dao.DaoGenerico;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import jakarta.persistence.*;

import java.lang.reflect.Method;
import java.util.List;

public class JPADaoGenerico<T, Pk> implements DaoGenerico<T, Pk> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> tipo;

    public JPADaoGenerico(Class<T> tipo) {
        this.tipo = tipo;
    }

    @Override
    public final T inclui(T obj) {
        try {
            em.persist(obj);
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }

        return obj;
    }

    @Override
    public final void altera(T obj) {
        try {
            em.merge(obj);
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final void exclui(T obj) {
        try {
            if (em.contains(obj)) {
                em.remove(obj);
            }
            else {
                obj = em.merge(obj);
                em.remove(obj);
            }
        }
        catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final T getPorId(Pk id) throws ObjetoNaoEncontradoException {
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

    @Override
    public final T getPorIdComLock(Pk id) throws ObjetoNaoEncontradoException {
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

    public final T busca(Method metodo, Object[] argumentos) throws ObjetoNaoEncontradoException {
        try {
            return getQuery(metodo, argumentos).getSingleResult();
        } catch (NoResultException e) {
            throw new ObjetoNaoEncontradoException();
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    public final List<T> buscaLista(Method metodo, Object[] argumentos) {
        return getQuery(metodo, argumentos).getResultList();
    }

    private TypedQuery<T> getQuery(Method metodo, Object[] argumentos) throws InfraestruturaException {
        try {
            String nomeDaBusca = tipo.getSimpleName() + "." + metodo.getName();
            TypedQuery<T> namedQuery = em.createNamedQuery(nomeDaBusca, tipo);

            if (argumentos != null) {
                for (int i = 0; i < argumentos.length; i++) {
                    Object arg = argumentos[i];
                    namedQuery.setParameter(i + 1, arg);
                }
            }
            return namedQuery;
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JPADaoGenerico<?, ?> that = (JPADaoGenerico<?, ?>) o;

        return tipo.equals(that.tipo);
    }

    @Override
    public final int hashCode() {
        return tipo.hashCode();
    }
}
