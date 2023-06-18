package servico.impl;

import java.util.List;

import anotacao.Autowired;
import anotacao.Transactional;
import dao.ProdutoDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;
import servico.ProdutoAppService;

public class ProdutoAppServiceImpl implements ProdutoAppService {

	@Autowired
	protected ProdutoDAO produtoDAO;

	@Transactional
	public long inclui(Produto umProduto) {
		return produtoDAO.inclui(umProduto);
	}

	@Transactional(rollbackFor = { ProdutoNaoEncontradoException.class })
	public void altera(Produto umProduto) throws ProdutoNaoEncontradoException {
		try {
			produtoDAO.altera(umProduto);
		}
		catch (ObjetoNaoEncontradoException e) {
			throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
	}

	@Transactional
	public void exclui(Long numero) throws ProdutoNaoEncontradoException {
		try {
			produtoDAO.exclui(numero);
		}
		catch (ObjetoNaoEncontradoException e) {
			throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
	}

	public Produto recuperaUmProduto(Long numero) throws ProdutoNaoEncontradoException {
		try {
			return produtoDAO.recuperaUmProduto(numero);
		}
		catch (ObjetoNaoEncontradoException e) {
			throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
	}

	public List<Produto> recuperaProdutos() {
		return produtoDAO.recuperaProdutos();
	}

}