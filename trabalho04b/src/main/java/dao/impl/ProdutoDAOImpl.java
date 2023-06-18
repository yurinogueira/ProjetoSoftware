package dao.impl;

import java.util.List;


import anotacao.PersistenceContext;
import dao.ProdutoDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import modelo.Produto;

public class ProdutoDAOImpl implements ProdutoDAO {

	@PersistenceContext
	protected EntityManager em;

	public long inclui(Produto umProduto) {
		try {
			em.persist(umProduto);

			return umProduto.getId();
		}
		catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public void altera(Produto umProduto) throws ObjetoNaoEncontradoException {
		try {
			Produto produto = em.find(Produto.class, umProduto.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (produto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			em.merge(umProduto);
		}
		catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(Long id) throws ObjetoNaoEncontradoException {
		try {
			Produto produto = em.find(Produto.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (produto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			em.remove(produto);
		}
		catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProduto(Long id) throws ObjetoNaoEncontradoException {
		try {
			Produto umProduto = em.find(Produto.class, id);

			if (umProduto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoComLock(long id) throws ObjetoNaoEncontradoException {
		try {
			Produto umProduto = em.find(Produto.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (umProduto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public List<Produto> recuperaProdutos() {
		try {
			return em.createQuery("select p from Produto p order by p.id asc", Produto.class).getResultList();
		}
		catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}
}