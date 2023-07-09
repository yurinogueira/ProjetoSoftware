package br.dev.yurinogueira.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotEmpty(message = "O 'Nome' deve ser informado.")
    private String nome;
    @Column
    @NotNull(message = "O 'CPF' deve ser informado.")
    private String cpf;
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("nome asc")
    private List<Funcao> funcoes;

    public void addFuncao(Funcao funcao) {
        if (this.funcoes == null) {
            this.funcoes = new ArrayList<>();
        }
        this.funcoes.add(funcao);
    }

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

}
