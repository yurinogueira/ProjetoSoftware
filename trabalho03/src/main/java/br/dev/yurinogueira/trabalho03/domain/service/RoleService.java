package br.dev.yurinogueira.trabalho03.domain.service;

import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.exception.EntityException;

import java.util.List;

public interface RoleService {

    List<Role> list();

    Role create(Role role) throws EntityException;

    Role read(Long id) throws EntityException;

    Role update(Role role) throws EntityException;

    void delete(Role role) throws EntityException;

}
