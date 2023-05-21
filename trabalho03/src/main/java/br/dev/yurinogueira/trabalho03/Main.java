package br.dev.yurinogueira.trabalho03;

import br.dev.yurinogueira.trabalho03.cli.PeriodCli;
import br.dev.yurinogueira.trabalho03.cli.PersonCli;
import br.dev.yurinogueira.trabalho03.cli.RoleCli;
import br.dev.yurinogueira.trabalho03.exception.InfraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static void chooseCli(Scanner scanner, PersonCli personCli, RoleCli roleCli, PeriodCli periodCli) {

        System.out.println("\nBem vindo ao sistema de Gerência de Pessoas e Funções!\n");
        System.out.println("Escolha o que você deseja gerênciar:");
        System.out.println("1. Pessoas");
        System.out.println("2. Funções");
        System.out.println("3. Períodos (O período em que uma pessoa exerceu uma função)");
        System.out.println("4. Encerrar");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();

        try {
            switch (option) {
                case 1 -> {
                    personCli.run();
                    chooseCli(scanner, personCli, roleCli, periodCli);
                }
                case 2 -> {
                    roleCli.run();
                    chooseCli(scanner, personCli, roleCli, periodCli);
                }
                case 3 -> {
                    periodCli.run();
                    chooseCli(scanner, personCli, roleCli, periodCli);
                }
                case 4 -> {
                    System.out.println("Até mais!");
                    logger.info("Desligando o sistema...");
                }
                default -> {
                    System.out.println("Opção inválida!");
                    chooseCli(scanner, personCli, roleCli, periodCli);
                }
            }
        }
        catch (InfraException e) {
            logger.error("Ocorreu um problema no banco de dados do sistema!");
            System.out.println("Ocorreu um problema no sistema!");
        }

    }

    public static void main(String[] args) {
        logger.info("Carregando o sistema...");

        Scanner scanner = new Scanner(System.in);
        PersonCli personCli = new PersonCli(scanner);
        RoleCli roleCli = new RoleCli(scanner);
        PeriodCli periodCli = new PeriodCli(scanner);

        chooseCli(scanner, personCli, roleCli, periodCli);

        scanner.close();
    }

}