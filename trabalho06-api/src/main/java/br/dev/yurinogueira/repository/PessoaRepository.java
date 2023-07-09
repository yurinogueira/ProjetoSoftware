package br.dev.yurinogueira.repository;

import br.dev.yurinogueira.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.id IN (SELECT f.pessoa.id FROM Funcao f WHERE f.id = :id)")
    List<Pessoa> findByFuncaoId(Long id);

}