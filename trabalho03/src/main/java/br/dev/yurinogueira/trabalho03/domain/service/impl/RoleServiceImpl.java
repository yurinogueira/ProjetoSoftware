package br.dev.yurinogueira.trabalho03.domain.service.impl;

import br.dev.yurinogueira.trabalho03.domain.dao.RoleDao;
import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.domain.service.RoleService;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.factory.DaoFactory;
import br.dev.yurinogueira.trabalho03.factory.EntityFactory;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private static RoleService roleService;
    private final RoleDao roleDao;

    private RoleServiceImpl() {
        this.roleDao = DaoFactory.getDao(RoleDao.class);
    }

    public static RoleService getInstance() {
        if (roleService == null) {
            roleService = new RoleServiceImpl();
        }

        return roleService;
    }

    @Override
    public List<Role> list() {
        return roleDao.list();
    }

    @Override
    public Role create(Role role) throws EntityException {
        EntityFactory.beginTransaction();

        Long id = roleDao.create(role);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        role.setId(id);

        return role;
    }

    @Override
    public Role read(Long id) throws EntityException {
        return roleDao.read(id);
    }

    @Override
    public Role update(Role role) throws EntityException {
        EntityFactory.beginTransaction();

        try {
            List<Role> roleWithName = roleDao.list(role.getName());
            for (Role r : roleWithName) {
                if (!r.getId().equals(role.getId())) {
                    throw new EntityException("Já existe uma Função cadastrada com esse nome!");
                }
            }

            roleDao.update(role);
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

        return role;
    }

    @Override
    public void delete(Role role) throws EntityException {
        EntityFactory.beginTransaction();

        roleDao.delete(role);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();
    }
}
