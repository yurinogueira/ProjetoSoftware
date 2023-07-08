package br.dev.yurinogueira.controller;

import br.dev.yurinogueira.model.Funcao;
import br.dev.yurinogueira.service.FuncaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("funcoes/")
public class FuncaoController {

    private final FuncaoService funcaoService;

    public FuncaoController(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    @PutMapping
    public Funcao atualizarFuncao(@RequestBody Funcao funcao) {
        return funcaoService.atualizarFuncao(funcao);
    }

    @PostMapping
    public Funcao cadastrarFuncao(@RequestBody Funcao funcao) {
        return funcaoService.cadastrarFuncao(funcao);
    }

    @DeleteMapping("{idFuncao}")
    public void removerFuncao(@PathVariable("idFuncao") Long id) {
        funcaoService.removerFuncao(id);
    }

    @GetMapping
    public List<Funcao> recuperarFuncoes() {
        return funcaoService.recuperarFuncoes();
    }

    @GetMapping("{idFuncao}")
    private Funcao recuperarFuncaoPorId(@PathVariable("idFuncao") Long id) {
        return funcaoService.recuperarFuncoesPorId(id);
    }
}
