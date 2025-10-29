import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import locadora.Cliente;
import locadora.DVD;
import locadora.FitaVHS;
import locadora.GerenciadorLocadora;
import locadora.Midia;
import locadora.Streaming;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorLocadora gerenciador = new GerenciadorLocadora();

        gerenciador.cadastrarCliente(new Cliente("Luke Skywalker", "luke@jedi.com"));
        gerenciador.cadastrarCliente(new Cliente("Leia Organa", "leia@rebels.com"));
        gerenciador.cadastrarMidia(new DVD("Star Wars: Uma Nova Esperança", 12.00, true));
        gerenciador.cadastrarMidia(new FitaVHS("A Volta do Jedi", 10.00));
        gerenciador.cadastrarMidia(new Streaming("The Mandalorian", 8.00, "Disney+"));

        int opcao = 0;
        do {
            exibirMenu();
            try {
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarMidia(scanner, gerenciador);
                        break;
                    case 2:
                        gerenciador.listarAcervo();
                        break;
                    case 3:
                        realizarLocacao(scanner, gerenciador);
                        break;
                    case 4:
                        devolverMidia(scanner, gerenciador);
                        break;
                    case 5:
                        gerenciador.listarLocacoes();
                        break;
                    case 6:
                        System.out.printf("Total Arrecadado até o momento: R$%.2f\n", gerenciador.calcularTotalArrecadado());
                        break;
                    case 7:
                        System.out.println("Saindo do Sistema RetroFlix. Até logo!");
                        break;
                    case 8:
                        cadastrarCliente(scanner, gerenciador);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine();
                opcao = 0;
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
            System.out.println("\n------------------------------------");
        } while (opcao != 7);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n====================================");
        System.out.println("          MENU RETROFLIX         ");
        System.out.println("====================================");
        System.out.println("1. Cadastrar nova Mídia (DVD/VHS/Streaming)");
        System.out.println("2. Listar todas as Mídias cadastradas");
        System.out.println("3. Realizar uma Locação");
        System.out.println("4. Devolver uma Mídia");
        System.out.println("5. Listar Histórico de Locações");
        System.out.println("6. Mostrar Total Arrecadado");
        System.out.println("8. Cadastrar Cliente");
        System.out.println("7. Sair do sistema");
    }

    private static void cadastrarMidia(Scanner scanner, GerenciadorLocadora gerenciador) {
        System.out.println("\n--- Cadastro de Mídia ---");
        System.out.println("1. DVD");
        System.out.println("2. Fita VHS");
        System.out.println("3. Streaming");
        System.out.print("Escolha o tipo de mídia: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Título do Filme: ");
        String titulo = scanner.nextLine();
        System.out.print("Preço Base: R$");
        double precoBase = scanner.nextDouble();
        scanner.nextLine();

        Midia novaMidia = null;

        switch (tipo) {
            case 1:
                System.out.print("Possui extras (true/false)? ");
                boolean extras = scanner.nextBoolean();
                scanner.nextLine();
                novaMidia = new DVD(titulo, precoBase, extras);
                break;
            case 2:
                novaMidia = new FitaVHS(titulo, precoBase);
                break;
            case 3:
                System.out.print("Plataforma (Netflix, Prime Video, etc.): ");
                String plataforma = scanner.nextLine();
                novaMidia = new Streaming(titulo, precoBase, plataforma);
                break;
            default:
                System.out.println("Tipo de mídia inválido.");
                return;
        }

        if (novaMidia != null) {
            gerenciador.cadastrarMidia(novaMidia);
        }
    }
    
    private static void cadastrarCliente(Scanner scanner, GerenciadorLocadora gerenciador) {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Email do Cliente: ");
        String email = scanner.nextLine();
        
        gerenciador.cadastrarCliente(new Cliente(nome, email));
    }


    private static void realizarLocacao(Scanner scanner, GerenciadorLocadora gerenciador) {
        List<Midia> acervo = gerenciador.getAcervoCompleto();
        List<Cliente> clientes = gerenciador.getClientes();
        
        if (acervo.isEmpty() || clientes.isEmpty()) {
            System.out.println("Não é possível alugar. Verifique se há mídias no acervo e clientes cadastrados.");
            return;
        }
        
        System.out.println("\n--- MÍDIAS DISPONÍVEIS ---");
        for (int i = 0; i < acervo.size(); i++) {
            Midia midia = acervo.get(i);
            if (midia.isDisponivel()) {
                System.out.println((i + 1) + ". " + midia.getTituloFilme() 
                    + " (Preço por dia: R$" + String.format("%.2f", midia.calcularPreco()) + ")");
            }
        }
        System.out.print("Selecione o número da Mídia a ser alugada: ");
        int midiaIndex = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\n--- CLIENTES CADASTRADOS ---");
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId() + " - " + cliente.getNome());
        }
        System.out.print("Informe o ID do Cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Informe o número de dias de locação: ");
        int dias = scanner.nextInt();
        scanner.nextLine();

        gerenciador.realizarLocacao(midiaIndex, clienteId, dias);
    }
    
    private static void devolverMidia(Scanner scanner, GerenciadorLocadora gerenciador) {
        List<Midia> acervo = gerenciador.getAcervoCompleto();
        
        if (acervo.isEmpty()) {
            System.out.println("O acervo está vazio.");
            return;
        }
        
        System.out.println("\n--- MÍDIAS ATUALMENTE ALUGADAS ---");
        boolean alugadaEncontrada = false;
        for (int i = 0; i < acervo.size(); i++) {
            if (!acervo.get(i).isDisponivel()) {
                System.out.println((i + 1) + ". " + acervo.get(i).getTituloFilme());
                alugadaEncontrada = true;
            }
        }
        
        if (!alugadaEncontrada) {
            System.out.println("Nenhuma mídia está atualmente alugada.");
            return;
        }

        System.out.print("Selecione o número da Mídia a ser devolvida: ");
        int midiaIndex = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Midia midia = acervo.get(midiaIndex - 1);
            if (midia instanceof FitaVHS) {
                System.out.print("A Fita VHS foi devolvida rebobinada (true/false)? ");
                boolean rebobinada = scanner.nextBoolean();
                ((FitaVHS) midia).setRebobinada(rebobinada);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Índice de mídia inválido.");
            return;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para rebobinada. Tente novamente.");
            scanner.nextLine();
            return;
        }

        gerenciador.devolverMidia(midiaIndex);
    }
}