package br.dev.yurinogueira.trabalho03.cli;

import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.domain.service.RoleService;
import br.dev.yurinogueira.trabalho03.domain.service.impl.RoleServiceImpl;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class RoleCli {

    private static final Logger logger = LoggerFactory.getLogger(RoleCli.class);

    private final Scanner scanner;
    private final RoleService roleService;

    public RoleCli(Scanner scanner) {
        this.scanner = scanner;
        this.roleService = RoleServiceImpl.getInstance();
    }

    private Role getRole() {
        Long roleId = scanner.nextLong();
        Role role = null;

        try {
            role = roleService.read(roleId);
        }
        catch (EntityException e) {
            System.out.println("\n" + e.getMessage());
        }

        return role;
    }

    private void createRole() {
        Role role = new Role();

        System.out.print("Informe o nome da Função: ");
        String name = scanner.next();
        System.out.print("Informe a descrição da Função: ");
        String description = scanner.next();
        System.out.print("Informe as horas de trabalho da Função: ");
        Integer hours = scanner.nextInt();

        role.setName(name);
        role.setDescription(description);
        role.setHours(hours);

        try {
            roleService.create(role);
        }
        catch (EntityException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private void updateRole() {
        System.out.println("Informe o ID da Função que deseja alterar: ");
        Role role = getRole();
        if (role == null) {
            System.out.println("Função não encontrada!");
            return;
        }

        System.out.println(role);

        System.out.println("\nO que você deseja alterar?\n");
        System.out.println("1. Nome");
        System.out.println("2. Descrição");
        System.out.println("3. Horas de trabalho");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                System.out.print("Informe o novo nome da Função: ");
                String name = scanner.next();
                role.setName(name);

                try {
                    role = roleService.update(role);
                    System.out.println("Nome alterado com sucesso! ID: " + role.getId());
                }
                catch (EntityException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Informe a nova descrição da Função: ");
                String description = scanner.next();
                role.setDescription(description);

                try {
                    role = roleService.update(role);
                    System.out.println("Descrição alterada com sucesso! ID: " + role.getId());
                }
                catch (EntityException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("Informe as novas horas de trabalho da Função: ");
                Integer hours = scanner.nextInt();
                role.setHours(hours);

                try {
                    role = roleService.update(role);
                    System.out.println("Horas de trabalho alteradas com sucesso! ID: " + role.getId());
                }
                catch (EntityException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            default -> System.out.println("Comando inválido!");
        }
    }

    private void deleteRole() {
        System.out.println("Informe o ID da Função que deseja remover: ");
        Role role = getRole();
        if (role == null) {
            System.out.println("Função não encontrada!");
            return;
        }

        System.out.println(role);
        System.out.print("\nConfirme a remoção da Função (s/n): ");

        String resp = scanner.next();

        if (resp.toLowerCase().startsWith("s")) {
            try {
                roleService.delete(role);
                System.out.println("Função removida com sucesso!");
            }
            catch (EntityException e) {
                System.out.println("\n" + e.getMessage());
            }
        }
    }

    private void listRole() {
        System.out.println("Listando todas as Funções...");
        roleService.list().forEach(System.out::println);
    }

    private void retrieveRole() {
        System.out.println("Informe o ID da Função que deseja recuperar: ");
        Role role = getRole();
        if (role == null) {
            System.out.println("Função não encontrada!");
            return;
        }

        System.out.println(role);
    }

    public void run() {
        System.out.println("\nO que você deseja fazer?\n");
        System.out.println("1. Cadastrar um cargo");
        System.out.println("2. Alterar um cargo");
        System.out.println("3. Remover um cargo");
        System.out.println("4. Listar todos os cargos");
        System.out.println("5. Recuperar um cargo");
        System.out.println("6. Voltar ao menu principal");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> {
                createRole();
                run();
            }
            case 2 -> {
                updateRole();
                run();
            }
            case 3 -> {
                deleteRole();
                run();
            }
            case 4 -> {
                listRole();
                run();
            }
            case 5 -> {
                retrieveRole();
                run();
            }
            case 6 -> {
                System.out.println("Voltando ao menu principal...");
                logger.info("Carregando menu principal...");
            }
            default -> {
                System.out.println("Comando inválido!");
                run();
            }
        }
    }

}
