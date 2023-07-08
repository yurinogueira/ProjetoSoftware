package br.dev.yurinogueira.service;

import br.dev.yurinogueira.model.Funcao;
import br.dev.yurinogueira.model.Pessoa;
import br.dev.yurinogueira.exception.EntidadeNaoEncontradaException;
import br.dev.yurinogueira.repository.FuncaoRepository;
import br.dev.yurinogueira.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final FuncaoRepository funcaoRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, FuncaoRepository funcaoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.funcaoRepository = funcaoRepository;
    }

    public List<Pessoa> recuperarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa recuperarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Pessoa de ID " + id + " não encontrado."));
    }

    public Pessoa atualizarPessoa(Pessoa pessoa) {
        Pessoa umPessoa = recuperarPessoaPorId(pessoa.getId());
        List<Funcao> newFuncoes = pessoa.getFuncoes();
        List<Funcao> oldFuncoes = umPessoa.getFuncoes();

        for (Funcao funcao : newFuncoes) {
            if (!oldFuncoes.contains(funcao)) {
                funcaoRepository.findById(funcao.getId()).orElseThrow(()-> new EntidadeNaoEncontradaException(
                        "Função de ID " + funcao.getId() + " não encontrada."
                ));
            }
        }

        return pessoaRepository.save(pessoa);
    }

    public void removerPessoa(Long id) {
        recuperarPessoaPorId(id);
        pessoaRepository.deleteById(id);
    }

    public List<Pessoa> recuperarPessoasPorFuncao(Long id) {
        return pessoaRepository.findByFuncaoId(id);
    }
}
