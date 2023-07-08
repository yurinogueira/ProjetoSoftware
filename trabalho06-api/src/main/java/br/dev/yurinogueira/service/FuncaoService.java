package br.dev.yurinogueira.service;

import br.dev.yurinogueira.model.Funcao;
import br.dev.yurinogueira.exception.EntidadeNaoEncontradaException;
import br.dev.yurinogueira.repository.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncaoService {

    private final FuncaoRepository funcaoRepository;

    @Autowired
    public FuncaoService(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
    }

    public Funcao atualizarFuncao(Funcao funcao) {
        return funcaoRepository.save(funcao);
    }

    public Funcao cadastrarFuncao(Funcao funcao) {
        return funcaoRepository.save(funcao);
    }

    public void removerFuncao(Long id) {
        recuperarFuncoesPorId(id);
        funcaoRepository.deleteById(id);
    }

    public List<Funcao> recuperarFuncoes() {
        return funcaoRepository.findAll(Sort.by("id"));
    }

    public Funcao recuperarFuncoesPorId(Long id) {
        return funcaoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Função de ID " + id + " não encontrada.")
        );
    }
}
