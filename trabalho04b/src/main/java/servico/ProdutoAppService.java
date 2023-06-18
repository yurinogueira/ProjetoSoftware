package servico;

import java.util.List;

import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;

public interface ProdutoAppService {

	long inclui(Produto umProduto);

	void altera(Produto umProduto) throws ProdutoNaoEncontradoException;

	void exclui(Long numero) throws ProdutoNaoEncontradoException;

	Produto recuperaUmProduto(Long numero) throws ProdutoNaoEncontradoException;

	List<Produto> recuperaProdutos();

}