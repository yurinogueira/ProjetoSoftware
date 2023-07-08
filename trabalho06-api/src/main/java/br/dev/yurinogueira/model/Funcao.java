package br.dev.yurinogueira.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String description;
    @Column
    private Integer hours;

    @JoinColumn(name = "pessoa_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;

    public Funcao(String nome, String description, Integer hours) {
        this.nome = nome;
        this.description = description;
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "\nID = " + getId() +
                "\nNome = " + getNome() +
                "\nDescription = " + getDescription();
    }

}
