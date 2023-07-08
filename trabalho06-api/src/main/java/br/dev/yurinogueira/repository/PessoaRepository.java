package br.dev.yurinogueira.repository;

import br.dev.yurinogueira.model.Pessoa;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.id IN (SELECT f.pessoa.id FROM Funcao f WHERE f.id = :id)")
    List<Pessoa> findByFuncaoId(Long id);

}