import excecao.InfraestruturaException;
import modelo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servico.PersonAppService;
import servico.controle.FabricaDeServico;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersonAppService personAppService = FabricaDeServico.getServico(PersonAppService.class);

        PersonCli personCli = new PersonCli(scanner, personAppService);

        chooseCli(scanner, personCli);

        scanner.close();
    }

    private static void chooseCli(Scanner scanner, PersonCli personCli) {

        System.out.println("\nBem vindo ao sistema de Gerência de Pessoas e Funções!\n");
        System.out.println("Escolha o que você deseja gerênciar:");
        System.out.println("1. Pessoas");
        System.out.println("2. Encerrar");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();

        try {
            switch (option) {
                case 1 -> {
                    personCli.runPerson();
                    chooseCli(scanner, personCli);
                }
                case 2 -> System.out.println("Até mais!");
                default -> {
                    System.out.println("Opção inválida!");
                    chooseCli(scanner, personCli);
                }
            }
        }
        catch (InfraestruturaException e) {
            System.out.println("Ocorreu um problema no sistema!");
        }

    }

    public static class PersonCli {

        private static final Logger logger = LoggerFactory.getLogger(PersonCli.class);

        private final Scanner scanner;
        private final PersonAppService personService;

        public PersonCli(Scanner scanner, PersonAppService personService) {
            this.scanner = scanner;
            this.personService = personService;
        }

        private Person getPerson() {
            Long personId = scanner.nextLong();
            Person person = null;

            try {
                person = personService.recuperaUmPerson(personId);
            }
            catch (Exception e) {
                System.out.println("\n" + e.getMessage());
            }

            return person;
        }

        private void createPerson() {
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
                long personId = personService.inclui(person);
                System.out.println("Pessoa criada com sucesso! ID: " + personId);
            } catch (Exception e) {
                System.out.println("\n" + e.getMessage());
            }
        }

        private void updatePerson() {
            System.out.print("Informe o ID da pessoa que você deseja alterar: ");
            Person person = getPerson();
            if (person == null) {
                System.out.println("Pessoa não encontrada!");
                return;
            }

            System.out.println(person);

            System.out.println("\nO que você deseja alterar?\n");
            System.out.println("1. Nome");
            System.out.println("2. CPF");

            System.out.print("\nOpção: ");
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
                        long id = personService.inclui(person);
                        System.out.println("Nome alterado com sucesso! ID: " + id);
                    }
                    catch (Exception e) {
                        System.out.println("\n" + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.print("Informe o CPF da pessoa sem mascara (00011122233): ");
                    String cpf = scanner.next();
                    person.setCpf(cpf);

                    try {
                        long personId = personService.inclui(person);
                        System.out.println("CPF alterado com sucesso! ID: " + personId);
                    }
                    catch (Exception e) {
                        System.out.println("\n" + e.getMessage());
                    }
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        private void deletePerson() {
            System.out.print("Informe o ID da pessoa que você deseja apagar: ");
            Person person = getPerson();
            if (person == null) {
                System.out.println("Pessoa não encontrada!");
                return;
            }

            System.out.println(person);
            System.out.print("\nConfirme a remoção da pessoa (s/n): ");

            String resp = scanner.next();

            if (resp.toLowerCase().startsWith("s")) {
                try {
                    personService.exclui(person);
                    System.out.println("Pessoa deletada com sucesso! ID: " + person.getId());
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
        }

        private void listPerson() {
            System.out.println("Listando todas as pessoas...");
            personService.recuperaPersons().forEach(System.out::println);
        }

        private void retrievePerson() {
            System.out.print("Informe o ID da pessoa que você acessar: ");
            Person person = getPerson();
            if (person == null) {
                System.out.println("Pessoa não encontrada!");
                return;
            }

            System.out.println(person);
        }

        public void runPerson() {
            System.out.println("\nO que você deseja fazer?\n");
            System.out.println("1. Cadastrar uma pessoa");
            System.out.println("2. Alterar uma pessoa");
            System.out.println("3. Remover uma pessoa");
            System.out.println("4. Listar todas as pessoas");
            System.out.println("5. Recuperar uma pessoa");
            System.out.println("6. Voltar ao menu principal");

            System.out.print("\nOpção: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    createPerson();
                    runPerson();
                }
                case 2 -> {
                    updatePerson();
                    runPerson();
                }
                case 3 -> {
                    deletePerson();
                    runPerson();
                }
                case 4 -> {
                    listPerson();
                    runPerson();
                }
                case 5 -> {
                    retrievePerson();
                    runPerson();
                }
                case 6 -> {
                    System.out.println("Voltando ao menu principal...");
                    logger.info("Carregando menu principal...");
                }
                default -> {
                    System.out.println("Comando inválido!");
                    runPerson();
                }
            }
        }
    }

}
