package br.dev.yurinogueira.trabalho03.domain.service.impl;

import br.dev.yurinogueira.trabalho03.domain.dao.PeriodDao;
import br.dev.yurinogueira.trabalho03.domain.entity.Period;
import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.domain.service.PeriodService;
import br.dev.yurinogueira.trabalho03.domain.service.RoleService;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.factory.DaoFactory;
import br.dev.yurinogueira.trabalho03.factory.EntityFactory;

import java.util.List;

public class PeriodServiceImpl implements PeriodService {

    private static PeriodService periodService;

    private final RoleService roleService;
    private final PeriodDao periodDao;

    private PeriodServiceImpl() {
        this.periodDao = DaoFactory.getDao(PeriodDao.class);
        this.roleService = RoleServiceImpl.getInstance();
    }

    public static PeriodService getInstance() {
        if (periodService == null) {
            periodService = new PeriodServiceImpl();
        }

        return periodService;
    }

    @Override
    public List<Period> list() {
        return periodDao.list();
    }

    @Override
    public List<Period> list(Long personId) {
        return periodDao.list(personId);
    }

    @Override
    public List<Period> list(Long roleId, Long personId) {
        return periodDao.list(roleId, personId);
    }

    @Override
    public Period create(Period period) throws EntityException {
        Long personId = period.getPerson() != null ? period.getPerson().getId() : null;
        if (personId == null || personId == 0) {
            throw new EntityException("O ID da pessoa não pode ser nulo!");
        }

        EntityFactory.beginTransaction();

        Long roleId = period.getRole() != null ? period.getRole().getId() : null;
        if (roleId == null || roleId == 0) {
            Role role = period.getRole();
            Role roleDatabase = roleService.create(role);
            period.setRole(roleDatabase);
        }
        else {
            List<Period> periods = list(roleId, personId);
            for (Period p : periods) {
                if (p.getDateOut() == null) {
                    throw new EntityException(
                            "Já existe um período aberto para essa pessoa e função! ID: " + p.getId()
                    );
                }
            }
        }

        Long id = periodDao.create(period);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        period.setId(id);

        return period;
    }

    @Override
    public Period read(Long id) throws EntityException {
        return periodDao.read(id);
    }

    @Override
    public Period update(Period period) throws EntityException {
        EntityFactory.beginTransaction();

        try {
            periodDao.update(period);
        }
        catch (EntityException exception) {
            EntityFactory.rollbackTransaction();
            throw new EntityException(exception.getMessage());
        }
        catch (ObsoleteEntityException exception) {
            EntityFactory.rollbackTransaction();
            throw new EntityException(
                    "Operação não efetuada, os dados que você tentou salvar foram modificados por outro usuário!"
            );
        }

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        return period;
    }

    @Override
    public void delete(Period period) throws EntityException {
        EntityFactory.beginTransaction();

        periodDao.delete(period);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();
    }
}
