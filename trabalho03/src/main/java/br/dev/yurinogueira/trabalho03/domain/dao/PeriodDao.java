package br.dev.yurinogueira.trabalho03.domain.dao;

import br.dev.yurinogueira.trabalho03.domain.entity.Period;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.exception.EntityException;

import java.util.List;

public interface PeriodDao {

    Long create(Period period) throws EntityException;

    Period read(Long id) throws EntityException;

    void update(Period period) throws EntityException, ObsoleteEntityException;

    void delete(Period period) throws EntityException;

    List<Period> list(Long personId);

    List<Period> list(Long roleId, Long personId);

    List<Period> list();

}
