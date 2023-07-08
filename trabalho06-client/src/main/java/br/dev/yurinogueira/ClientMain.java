package br.dev.yurinogueira;


import br.dev.yurinogueira.exception.EntidadeNaoEncontradaException;
import br.dev.yurinogueira.exception.ErrorHandler;
import br.dev.yurinogueira.exception.ViolacaoDeConstraintException;
import br.dev.yurinogueira.modelo.Funcao;
import br.dev.yurinogueira.modelo.Pessoa;
import corejava.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ClientMain {

    private static final Logger logger = LoggerFactory.getLogger(ClientMain.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        restTemplate.setErrorHandler(new ErrorHandler());

        logger.info("Iniciando a execução da aplicação cliente.");

        long id;
        String nome;
        String cpf;
        String description;
        int hours;
        Pessoa pessoa;
        Funcao funcao;

        boolean continua = true;
        while (continua) {
            System.out.println("\nO que você deseja fazer?");
            System.out.println("\n1. Cadastrar uma pessoa");
            System.out.println("2. Alterar uma pessoa");
            System.out.println("3. Remover uma pessoa");
            System.out.println("4. Recuperar uma pessoa");
            System.out.println("5. Listar todos as pessoas");
            System.out.println("6. Listar todos as pessoas por funções");
            System.out.println("7. Cadastrar uma função");
            System.out.println("8. Alterar uma função");
            System.out.println("9. Remover uma função");
            System.out.println("10. Recuperar uma função");
            System.out.println("11. Listar todos as funções");
            System.out.println("12. Sair");


            int opcao = Console.readInt("\nDigite um número entre 1 e 12:");

            switch (opcao) {
                case 1 -> {
                    nome = Console.readLine("\nInforme o nome da pessoa: ");
                    cpf = Console.readLine("Informe o CPF: ");
                    List<Funcao> funcoes = new ArrayList<>();

                    String add = Console.readLine("Deseja adicionar funções? (s/n): ");
                    while (add.equals("s")) {
                        id = Console.readInt("\nInforme o ID da função: ");
                        try {
                            ResponseEntity<Funcao> res = restTemplate.exchange(
                                    "http://localhost:8080/funcoes/{id}",
                                    HttpMethod.GET,
                                    null,
                                    Funcao.class,
                                    id
                            );
                            funcao = res.getBody();
                            funcoes.add(funcao);
                        }
                        catch (EntidadeNaoEncontradaException e) {
                            System.out.println("\nFunção não encontrada!");
                            break;
                        }
                        add = Console.readLine("\nDeseja continuar adicionando mais funções? (s/n): ");
                    }

                    pessoa = new Pessoa(nome, cpf, funcoes);

                    try {

                        ResponseEntity<Pessoa> res = restTemplate.postForEntity(
                                "http://localhost:8080/pessoas/",
                                pessoa,
                                Pessoa.class
                        );
                        pessoa = res.getBody();
                        if (pessoa == null) {
                            System.out.println("\nErro ao cadastrar a pessoa!");
                            break;
                        }

                        System.out.println("\nPessoa com id " + pessoa.getId() + " cadastrado com sucesso!");
                        System.out.println(pessoa);
                    } catch (ViolacaoDeConstraintException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 2 -> {
                    try {
                        pessoa = recuperarObjeto(
                                "Informe o id da pessoa que você deseja alterar: ",
                                "http://localhost:8080/pessoas/{id}",
                                Pessoa.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(pessoa);

                    System.out.println("\nO que você deseja alterar?");
                    System.out.println("\n1. Nome");
                    System.out.println("2. Funções");

                    int opcaoAlteracao = Console.readInt("\nDigite o número 1 ou 2:");

                    if (opcaoAlteracao == 1) {
                        nome = Console.readLine("Digite o novo nome: ");
                        pessoa.setNome(nome);
                    } else if (opcaoAlteracao == 2) {
                        List<Funcao> funcoes = new ArrayList<>();

                        String add = Console.readLine("Deseja adicionar funções? (s/n): ");
                        while (add.equals("s")) {
                            id = Console.readInt("\nInforme o ID da função: ");
                            try {
                                ResponseEntity<Funcao> res = restTemplate.exchange(
                                        "http://localhost:8080/funcoes/{id}",
                                        HttpMethod.GET,
                                        null,
                                        Funcao.class,
                                        id
                                );
                                funcao = res.getBody();
                                funcoes.add(funcao);
                            }
                            catch (EntidadeNaoEncontradaException e) {
                                System.out.println("\nFunção não encontrada!");
                                break;
                            }
                            add = Console.readLine("\nDeseja continuar adicionando mais funções? (s/n): ");
                        }
                        pessoa.setFuncoes(funcoes);
                    } else {
                        System.out.println("\nOpção inválida");
                        break;
                    }

                    try {
                        restTemplate.put("http://localhost:8080/pessoas/", pessoa);

                        System.out.println("\nPessoa de ID " + pessoa.getId() + " alterada com sucesso!");
                        System.out.println(pessoa);
                    } catch (ViolacaoDeConstraintException | EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        pessoa = recuperarObjeto(
                                "Informe o ID da pessoa que você deseja remover: ",
                                "http://localhost:8080/pessoas/{id}",
                                Pessoa.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(pessoa);

                    String resp = Console.readLine("\nConfirma a remoção da pessoa?");

                    if (resp.equals("s")) {
                        try {
                            restTemplate.delete("http://localhost:8080/pessoas/{id}", pessoa.getId());

                            System.out.println("\nPessoa de ID " + pessoa.getId() + " removida com sucesso!");
                        } catch (EntidadeNaoEncontradaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Pessoa não removida.");
                    }
                }
                case 4 -> {
                    try {
                        pessoa = recuperarObjeto(
                                "Informe o ID da pessoa que você deseja recuperar: ",
                                "http://localhost:8080/pessoas/{id}", Pessoa.class);
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(pessoa);
                }
                case 5 -> {
                    ResponseEntity<Pessoa[]> res = restTemplate.exchange(
                            "http://localhost:8080/pessoas/",
                            HttpMethod.GET,
                            null,
                            Pessoa[].class
                    );
                    Pessoa[] pessoas = res.getBody();

                    if (pessoas != null) {
                        for (Pessoa umPessoa : pessoas) {
                            System.out.println(umPessoa);
                        }
                    }
                }
                case 6 -> {
                    id = Console.readInt("\nInforme o ID da função: ");

                    ResponseEntity<Pessoa[]> res = restTemplate.exchange(
                            "http://localhost:8080/funcoes/{id}",
                            HttpMethod.GET,
                            null,
                            Pessoa[].class,
                            id
                    );
                    Pessoa[] pessoas = res.getBody();

                    if (pessoas == null || pessoas.length == 0) {
                        System.out.println("\nNenhuma pessoa foi encontrada com está função.");
                        break;
                    }

                    for (Pessoa umPessoa : pessoas) {
                        System.out.println(umPessoa);
                    }
                }
                case 7 -> {
                    nome = Console.readLine("\nInforme o nome da função: ");
                    description = Console.readLine("Informe a descrição: ");
                    hours = Console.readInt("Informe o número de horas: ");

                    funcao = new Funcao(nome, description, hours);

                    try {
                        ResponseEntity<Funcao> res = restTemplate.postForEntity(
                                "http://localhost:8080/funcoes/",
                                funcao,
                                Funcao.class
                        );
                        funcao = res.getBody();
                        if (funcao == null) {
                            System.out.println("\nErro ao cadastrar a função!");
                            break;
                        }

                        System.out.println("\nFunção com id " + funcao.getId() + " cadastrado com sucesso!");
                        System.out.println(funcao);
                    } catch (ViolacaoDeConstraintException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 8 -> {
                    try {
                        funcao = recuperarObjeto(
                                "Informe o id da função que você deseja alterar: ",
                                "http://localhost:8080/funcoes/{id}",
                                Funcao.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(funcao);

                    System.out.println("\nO que você deseja alterar?");
                    System.out.println("\n1. Nome");
                    System.out.println("2. description");
                    System.out.println("3. hours");

                    int opcaoAlteracao = Console.readInt("\nDigite o número 1 ou 3:");

                    if (opcaoAlteracao == 1) {
                        nome = Console.readLine("Digite o novo nome: ");
                        funcao.setNome(nome);
                    } else if (opcaoAlteracao == 2) {
                        description = Console.readLine("Digite a nova description: ");
                        funcao.setDescription(description);
                    } else if (opcaoAlteracao == 3) {
                        hours = Console.readInt("Digite o novo número de horas: ");
                        funcao.setHours(hours);
                    } else {
                        System.out.println("\nOpção inválida");
                        break;
                    }

                    try {
                        restTemplate.put("http://localhost:8080/funcoes/", funcao);

                        System.out.println("\nFunção de ID " + funcao.getId() + " alterada com sucesso!");
                        System.out.println(funcao);
                    } catch (ViolacaoDeConstraintException | EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 9 -> {
                    try {
                        funcao = recuperarObjeto(
                                "Informe o ID da função que você deseja remover: ",
                                "http://localhost:8080/funcoes/{id}",
                                Funcao.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(funcao);

                    String resp = Console.readLine("\nConfirma a remoção da função?");

                    if (resp.equals("s")) {
                        try {
                            restTemplate.delete("http://localhost:8080/funcoes/{id}", funcao.getId());

                            System.out.println("\nFunção de ID " + funcao.getId() + " removida com sucesso!");
                        } catch (EntidadeNaoEncontradaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Função não removida.");
                    }
                }
                case 10 -> {
                    try {
                        funcao = recuperarObjeto(
                                "Informe o ID da função que você deseja recuperar: ",
                                "http://localhost:8080/funcoes/{id}", Funcao.class);
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(funcao);
                }
                case 11 -> {
                    ResponseEntity<Funcao[]> res = restTemplate.exchange(
                            "http://localhost:8080/funcoes/",
                            HttpMethod.GET,
                            null,
                            Funcao[].class
                    );
                    Funcao[] funcoes = res.getBody();

                    if (funcoes != null) {
                        for (Funcao umFuncao : funcoes) {
                            System.out.println(umFuncao);
                        }
                    }
                }
                case 12 -> continua = false;
                default -> System.out.println("\nOpção inválida!");
            }
        }
    }

    private static <T> T recuperarObjeto(String msg, String url, Class<T> classe) {
        int id = Console.readInt('\n' + msg);
        return restTemplate.getForObject(url, classe, id);
    }
}
