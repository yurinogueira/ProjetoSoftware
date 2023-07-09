package br.dev.yurinogueira.modelo;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;

    public Funcao(String nome, String description, Integer hours) {
        this.nome = nome;
        this.description = description;
        this.hours = hours;
    }

}