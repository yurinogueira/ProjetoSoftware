package br.dev.yurinogueira.controller;

import br.dev.yurinogueira.model.Pessoa;
import br.dev.yurinogueira.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="pessoas/")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<Pessoa> recuperarPessoas() {
        return pessoaService.recuperarPessoas();
    }

    @PostMapping
    public Pessoa cadastrarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.cadastrarPessoa(pessoa);
    }

    @GetMapping("{idPessoa}")
    public Pessoa recuperarPessoaPorId(@PathVariable("idPessoa") Long id) {
        return pessoaService.recuperarPessoaPorId(id);
    }

    @PutMapping
    public Pessoa atualizarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.atualizarPessoa(pessoa);
    }

    @DeleteMapping("{idPessoa}")
    public void removerPessoa(@PathVariable("idPessoa") Long id) {
        pessoaService.removerPessoa(id);
    }

    @GetMapping("funcao/{idFuncao}")
    public List<Pessoa> recuperarPessoasPorCategoria(@PathVariable("idFuncao") Long id) {
        return pessoaService.recuperarPessoasPorFuncao(id);
    }

}