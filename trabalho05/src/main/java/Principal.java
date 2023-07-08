import corejava.Console;
import excecao.PersonNaoEncontradoException;
import modelo.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import servico.PersonAppService;


public class Principal {

    public static void main(String[] args) {
        long id;
        String cpf;
        String name;
        Person umPerson;

        ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
        PersonAppService personAppService = (PersonAppService) fabrica.getBean("personAppService");

        boolean continua = true;
        while (continua) {
            System.out.println("\nO que você deseja fazer?");
            System.out.println("\n1. Cadastrar uma pessoa");
            System.out.println("2. Alterar uma pessoa");
            System.out.println("3. Remover uma pessoa");
            System.out.println("4. Listar pessoas");
            System.out.println("5. Recuperar uma pessoa");
            System.out.println("6. Recuperar uma pessoa e Roles");
            System.out.println("7. Sair");

            int opcao = Console.readInt("\nDigite um número entre 1 e 7:");

            switch (opcao) {
                case 1 -> {
                    name = Console.readLine("\nInforme o nome da pessoa: ");
                    cpf = Console.readLine("Informe o CPF da pessoa: ");

                    umPerson = new Person();
                    umPerson.setName(name);
                    umPerson.setCpf(cpf);

                    id = personAppService.inclui(umPerson);

                    System.out.println("\nProduto número " + id + " incluído com sucesso!");
                }
                case 2 -> {
                    id = Console.readInt("\nDigite o id da pessoa que você deseja alterar: ");

                    try {
                        umPerson = personAppService.recuperaUmPerson(id);
                    } catch (PersonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(umPerson);

                    System.out.println("\nO que você deseja alterar?");
                    System.out.println("\n1. Nome");
                    System.out.println("2. CPF");

                    boolean esperandoOpcao = true;
                    while (esperandoOpcao) {
                        int opcaoAlteracao = Console.readInt("\nDigite um número de 1 a 2:");

                        switch (opcaoAlteracao) {
                            case 1 -> {
                                esperandoOpcao = false;
                                name = Console.readLine("Digite o novo nome: ");
                                umPerson.setName(name);

                                try {
                                    personAppService.altera(umPerson);
                                    System.out.println("Alteração de nome efetuada com sucesso!");
                                } catch (PersonNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            case 2 -> {
                                cpf = Console.readLine("Digite o novo CPF: ");
                                umPerson.setCpf(cpf);

                                if (!umPerson.isValidCpf()) {
                                    System.out.println("\nCPF inválido!");
                                    continue;
                                }

                                esperandoOpcao = false;
                                try {
                                    personAppService.altera(umPerson);
                                    System.out.println("\nAlteração de descrição efetuada com sucesso!");
                                } catch (PersonNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            default -> System.out.println("Opção inválida!");
                        }
                    }
                }
                case 3 -> {
                    id = Console.readInt("\nDigite o id da pessoa que você deseja remover: ");

                    try {
                        umPerson = personAppService.recuperaUmPerson(id);
                    } catch (PersonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(umPerson);

                    String resp = Console.readLine("Confirma a remoção da pessoa? (s/n)");

                    if (resp.equals("s")) {
                        try {
                            personAppService.exclui(umPerson);
                            System.out.println("\nPessoa removida com sucesso!");
                        } catch (PersonNaoEncontradoException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("\nPessoa não removido.");
                    }

                }
                case 4 -> personAppService.recuperaPersons().forEach(System.out::println);
                case 5 -> {
                    id = Console.readInt("\nDigite o id da pessoa que você quer recuperar: ");

                    try {
                        umPerson = personAppService.recuperaUmPerson(id);
                        System.out.println(umPerson);
                    } catch (PersonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 6 -> {
                    id = Console.readInt("\nDigite o id da pessoa que você quer recuperar: ");

                    try {
                        umPerson = personAppService.recuperaUmPersonERoles(id);
                        System.out.println("Pessoa:");
                        System.out.println(umPerson);
                        System.out.println("Roles:");
                        umPerson.getRoles().forEach(System.out::println);
                    } catch (PersonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 7 -> continua = false;
                default -> System.out.println("\nOpção inválida!");
            }
        }

    }

}
