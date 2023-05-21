package br.dev.yurinogueira.trabalho03.domain.dao;

import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.exception.EntityException;

import java.util.List;

public interface RoleDao {

    Long create(Role role) throws EntityException;

    Role read(Long id) throws EntityException;

    void update(Role role) throws EntityException, ObsoleteEntityException;

    void delete(Role role) throws EntityException;

    List<Role> list(String name);

    List<Role> list();

}
