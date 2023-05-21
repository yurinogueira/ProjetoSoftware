package br.dev.yurinogueira.trabalho03.domain.service;

import br.dev.yurinogueira.trabalho03.domain.entity.Period;
import br.dev.yurinogueira.trabalho03.exception.EntityException;

import java.util.List;

public interface PeriodService {

    List<Period> list();

    List<Period> list(Long personId);

    List<Period> list(Long roleId, Long personId);

    Period create(Period period) throws EntityException;

    Period read(Long id) throws EntityException;

    Period update(Period period) throws EntityException;

    void delete(Period period) throws EntityException;

}
