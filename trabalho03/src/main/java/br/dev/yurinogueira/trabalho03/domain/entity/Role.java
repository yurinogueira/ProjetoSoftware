package br.dev.yurinogueira.trabalho03.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "hours")
    private Integer hours;

    @Version
    private Integer version;

    @Override
    public String toString() {
        return "\nID = " + getId() +
               "\nNome = " + getName() +
               "\nDescription = " + getDescription() +
               "\nVersão = " + getVersion();
    }

}
