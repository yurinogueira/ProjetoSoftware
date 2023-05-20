package br.dev.yurinogueira.trabalho02;

import br.dev.yurinogueira.trabalho02.domain.service.PersonService;
import br.dev.yurinogueira.trabalho02.domain.service.impl.PersonServiceImpl;
import br.dev.yurinogueira.trabalho02.domain.entity.Person;
import br.dev.yurinogueira.trabalho02.exception.PersonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static Person getPerson(Scanner scanner, PersonService personService) {
        Long personId = scanner.nextLong();
        Person person = null;

        try {
            person = personService.read(personId);
        }
        catch (PersonException e) {
            System.out.println("\n" + e.getMessage());
        }

        return person;
    }

    private static void createPerson(Scanner scanner, PersonService personService) {
        StringBuilder personName = new StringBuilder();
        Person person = new Person();

        System.out.print("Informe o nome da pessoa: ");
        personName.append(scanner.next()).append(" ");
        System.out.print("Informe o segundo nome da pessoa: ");
        personName.append(scanner.next());
        person.setName(personName.toString());

        do {
            if (person.getCpf() != null) {
                System.out.println("CPF inválido, tente novamente");
            }

            System.out.print("Informe o CPF da pessoa sem mascara (00011122233): ");
            String cpf = scanner.next();
            person.setCpf(cpf);
        } while (!person.isValidCpf());

        try {
            person = personService.create(person);
            System.out.println("Pessoa criada com sucesso! ID: " + person.getId());
        } catch (PersonException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private static void updatePerson(Scanner scanner, PersonService personService) {
        System.out.print("Informe o ID da pessoa que você deseja alterar: ");
        Person person = getPerson(scanner, personService);
        if (person == null) {
            return;
        }

        System.out.println(person);

        System.out.println("\nO que você deseja alterar?\n");
        System.out.println("1. Nome");
        System.out.println("2. CPF");

        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                StringBuilder personName = new StringBuilder();
                System.out.print("Informe o novo nome da pessoa: ");
                personName.append(scanner.next()).append(" ");
                System.out.print("Informe o novo segundo nome da pessoa: ");
                personName.append(scanner.next());

                person.setName(personName.toString());
                try {
                    person = personService.update(person);
                    System.out.println("Nome alterado com sucesso! ID: " + person.getId());
                }
                catch (PersonException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Informe o CPF da pessoa sem mascara (00011122233): ");
                String cpf = scanner.next();


                person.setCpf(cpf);
                try {
                    person = personService.update(person);
                    System.out.println("CPF alterado com sucesso! ID: " + person.getId());
                }
                catch (PersonException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void deletePerson(Scanner scanner, PersonService personService) {
        System.out.print("Informe o ID da pessoa que você deseja apagar: ");
        Person person = getPerson(scanner, personService);
        if (person == null) {
            return;
        }

        System.out.println(person);
        System.out.println("Confirme a remoção da pessoa (s/n): ");

        String resp = scanner.next();

        if (resp.toLowerCase().startsWith("s")) {
            try {
                personService.delete(person);
                System.out.println("Pessoa deletada com sucesso! ID: " + person.getId());
            } catch (PersonException e) {
                System.out.println("\n" + e.getMessage());
            }
        }
    }

    private static void listPerson(PersonService personService) {
        List<Person> personList = personService.list();

        for (Person person : personList) {
            System.out.println(person);
        }
    }

    private static void retrievePerson(Scanner scanner, PersonService personService) {
        System.out.print("Informe o ID da pessoa que você acessar: ");
        Person person = getPerson(scanner, personService);
        if (person == null) {
            return;
        }

        System.out.println(person);
    }

    public static void main(String[] args) {
        logger.info("Mensagem de log emitida utilizando o LOG4J");

        Scanner scanner = new Scanner(System.in);
        PersonService personService = new PersonServiceImpl();

        boolean running = true;
        while (running) {
            System.out.println("\nO que você deseja fazer?\n");
            System.out.println("1. Cadastrar uma pessoa");
            System.out.println("2. Alterar uma pessoa");
            System.out.println("3. Remover uma pessoa");
            System.out.println("4. Listar todas as pessoas");
            System.out.println("5. Recuperar uma pessoa");
            System.out.println("6. Encerrar");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> createPerson(scanner, personService);
                case 2 -> updatePerson(scanner, personService);
                case 3 -> deletePerson(scanner, personService);
                case 4 -> listPerson(personService);
                case 5 -> retrievePerson(scanner, personService);
                case 6 -> running = false;
                default -> System.out.println("Comando inválido!");
            }
        }

        scanner.close();
    }

}