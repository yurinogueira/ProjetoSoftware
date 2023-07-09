package br.dev.yurinogueira.modelo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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

    public Pessoa(String nome, String cpf, List<Funcao> funcoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.funcoes = funcoes;
    }

}
