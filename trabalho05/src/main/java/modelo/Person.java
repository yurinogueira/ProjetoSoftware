package modelo;

import br.com.caelum.stella.validation.CPFValidator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@NamedQueries({
        @NamedQuery(name = "Person.recuperaListaDePersons", query = "SELECT p FROM Person p ORDER BY p.id"),
        @NamedQuery(name = "Person.recuperaUmaPessoaERoles", query = "SELECT p FROM Person p LEFT OUTER JOIN FETCH p.roles WHERE p.id = ?1"),
})
public class Person {

    private static final CPFValidator CPF_VALIDATOR = new CPFValidator();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "person")
    @OrderBy
    private List<Role> roles;

    @Version
    private Integer version;

    public String getCpfMask() {
        return getCpf().substring(0, 3) + "." +
                getCpf().substring(3, 6) + "." +
                getCpf().substring(6, 9) + "-" +
                getCpf().substring(9, 11);
    }

    @Transient
    public boolean isValidCpf() {
        try {
            CPF_VALIDATOR.assertValid(getCpf());
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "\nID = " + getId() +
                "\nNome = " + getName() +
                "\nCPF = " + getCpfMask() +
                "\nVersão = " + getVersion();
    }

}
