package modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.Util;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "NOME")
	private String nome;
	@Column(name = "LANCE_MINIMO")
	private double lanceMinimo;
	@Column(name = "DATA_CADASTRO")
	private Date dataCadastro;
	@Column(name = "DATA_VENDA")
	private Date dataVenda;

	public Produto(String nome, double lanceMinimo, Date dataCadastro) {
		this.nome = nome;
		this.lanceMinimo = lanceMinimo;
		this.dataCadastro = dataCadastro;
	}

	@Transient
	public String getDataCadastroMasc() {
		return Util.dateToStr(dataCadastro);
	}

}
