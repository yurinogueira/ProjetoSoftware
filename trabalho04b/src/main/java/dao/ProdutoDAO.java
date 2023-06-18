package dao;

import java.util.List;

import excecao.ObjetoNaoEncontradoException;
import modelo.Produto;

public interface ProdutoDAO {

	long inclui(Produto umProduto);

	void altera(Produto umProduto) throws ObjetoNaoEncontradoException;

	void exclui(Long id) throws ObjetoNaoEncontradoException;

	Produto recuperaUmProduto(Long numero) throws ObjetoNaoEncontradoException;

	List<Produto> recuperaProdutos();

}