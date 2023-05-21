package br.dev.yurinogueira.trabalho03.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "role_period")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Period {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "roleId")
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "personId")
    private Person person;

    @Column(name = "dateIn")
    private Timestamp dateIn;
    @Column(name = "dateOut")
    private Timestamp dateOut;
    @Version
    private Integer version;

    public String getDateInFormatted() {
        return dateFormat.format(dateIn);
    }

    public String getDateOutFormatted() {
        return dateFormat.format(dateOut);
    }

    @Override
    public String toString() {
        return "\nRolePeriod Id = " + getId() +
               "\nRole Id = " + getRole().getId() +
               "\nPerson Id = " + getPerson().getId() +
               "\nData Inicial = " + getDateInFormatted() +
               "\nData Final = " + getDateOutFormatted() +
               "\nVersão = " + getVersion();
    }

}
