package br.dev.yurinogueira.trabalho03.cli;

import br.dev.yurinogueira.trabalho03.domain.entity.Period;
import br.dev.yurinogueira.trabalho03.domain.entity.Person;
import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.domain.service.PeriodService;
import br.dev.yurinogueira.trabalho03.domain.service.PersonService;
import br.dev.yurinogueira.trabalho03.domain.service.RoleService;
import br.dev.yurinogueira.trabalho03.domain.service.impl.PeriodServiceImpl;
import br.dev.yurinogueira.trabalho03.domain.service.impl.PersonServiceImpl;
import br.dev.yurinogueira.trabalho03.domain.service.impl.RoleServiceImpl;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Scanner;

public class PeriodCli {

    private static final Logger logger = LoggerFactory.getLogger(PeriodCli.class);

    private final Scanner scanner;
    private final PeriodService periodService;
    private final RoleService roleService;
    private final PersonService personService;

    public PeriodCli(Scanner scanner) {
        this.scanner = scanner;
        this.periodService = PeriodServiceImpl.getInstance();
        this.roleService = RoleServiceImpl.getInstance();
        this.personService = PersonServiceImpl.getInstance();
    }

    private Person getPerson() {
        Long personId = scanner.nextLong();
        Person person = null;

        try {
            person = personService.read(personId);
        }
        catch (EntityException e) {
            System.out.println("\n" + e.getMessage());
        }

        return person;
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

    private Period getPeriod() {
        System.out.print("Informe o ID do período: ");
        Long periodId = scanner.nextLong();

        Period period = null;
        try {
            period = periodService.read(periodId);
        }
        catch (EntityException e) {
            System.out.println("\n" + e.getMessage());
        }

        return period;
    }

    private void createPeriod() {
        Period period = new Period();

        System.out.println("\nEscolha uma opção:\n");
        System.out.println("1. Cadastrar um período com um novo cargo");
        System.out.println("2. Cadastrar um período com um cargo existente");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                Role role = new Role();
                System.out.print("Informe o nome do cargo: ");
                String name = scanner.next();
                System.out.print("Informe a descrição do cargo: ");
                String description = scanner.next();
                System.out.print("Informe as horas de trabalho do cargo: ");
                Integer hours = scanner.nextInt();

                role.setName(name);
                role.setDescription(description);
                role.setHours(hours);

                period.setRole(role);
            }
            case 2 -> {
                System.out.print("Informe o ID do cargo: ");
                Role role = getRole();

                if (role == null) {
                    System.out.println("Cargo não encontrado!");
                    createPeriod();
                    return;
                }

                period.setRole(role);
            }
            default -> {
                System.out.println("Comando inválido!");
                createPeriod();
                return;
            }
        }


        System.out.print("Informe o ID da pessoa: ");
        Person person = getPerson();

        if (person == null) {
            System.out.println("Pessoa não encontrada!");
            return;
        }

        period.setPerson(person);

        System.out.print("Informe a data de início do período: (yyyy-mm-dd) ");
        String startDateHour = scanner.next();
        String startDate = startDateHour + " 00:00:00";
        System.out.print("Informe a data de término do período: (yyyy-mm-dd) ");
        String endDateHour = scanner.next();
        String endDate = endDateHour + " 00:00:00";

        period.setDateIn(Timestamp.valueOf(startDate));
        period.setDateOut(Timestamp.valueOf(endDate));

        try {
            periodService.create(period);
            System.out.println("\nPeríodo cadastrado com sucesso!");
        }
        catch (EntityException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private void updatePeriod() {
        Period period = getPeriod();
        if (period == null) {
            System.out.println("Período não encontrado!");
            return;
        }

        System.out.println(period);

        System.out.println("\nO que você deseja alterar?\n");
        System.out.println("1. Alterar a data de início");
        System.out.println("2. Alterar a data de término");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                System.out.print("Informe a nova data de início do período: (yyyy-mm-dd) ");
                String startDateHour = scanner.next();
                String startDate = startDateHour + " 00:00:00";
                period.setDateIn(Timestamp.valueOf(startDate));

                try {
                    period = periodService.update(period);
                    System.out.println("Data de início alterada com sucesso!");
                    System.out.println(period);
                }
                catch (EntityException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Informe a nova data de término do período: (yyyy-mm-dd) ");
                String endDateHour = scanner.next();
                String endDate = endDateHour + " 00:00:00";
                period.setDateOut(Timestamp.valueOf(endDate));

                try {
                    period = periodService.update(period);
                    System.out.println("Data de término alterada com sucesso!");
                    System.out.println(period);
                }
                catch (EntityException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            default -> System.out.println("Comando inválido!");
        }
    }

    private void deletePeriod() {
        Period period = getPeriod();
        if (period == null) {
            System.out.println("Período não encontrado!");
            return;
        }

        System.out.println(period);
        System.out.print("\nConfirme a remoção do Período (s/n): ");

        String resp = scanner.next();

        if (resp.toLowerCase().startsWith("s")) {
            try {
                periodService.delete(period);
                System.out.println("Período removido com sucesso!");
            }
            catch (EntityException e) {
                System.out.println("\n" + e.getMessage());
            }
        }
    }

    private void listPeriod() {
        System.out.println("Listando todos os Períodos...");
        periodService.list().forEach(System.out::println);
    }

    private void listPeriodFromPerson() {
        System.out.print("Informe o ID da pessoa: ");
        Person person = getPerson();

        if (person == null) {
            System.out.println("Pessoa não encontrada!");
            return;
        }

        System.out.println("Listando todos os Períodos da pessoa " + person.getName() + "...");
        periodService.list(person.getId()).forEach(System.out::println);
    }

    private void retrievePeriod() {
        Period period = getPeriod();
        if (period == null) {
            System.out.println("Período não encontrado!");
            return;
        }

        System.out.println(period);
    }

    public void run() {
        System.out.println("\nO que você deseja fazer?\n");
        System.out.println("1. Cadastrar um período");
        System.out.println("2. Alterar um período");
        System.out.println("3. Remover um período");
        System.out.println("4. Listar todos os período");
        System.out.println("5. Listar todos os período de uma pessoa");
        System.out.println("6. Recuperar um período");
        System.out.println("7. Voltar ao menu principal");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> {
                createPeriod();
                run();
            }
            case 2 -> {
                updatePeriod();
                run();
            }
            case 3 -> {
                deletePeriod();
                run();
            }
            case 4 -> {
                listPeriod();
                run();
            }
            case 5 -> {
                listPeriodFromPerson();
                run();
            }
            case 6 -> {
                retrievePeriod();
                run();
            }
            case 7 -> {
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
