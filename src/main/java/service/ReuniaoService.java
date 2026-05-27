package service;

import model.*;
import model.enums.FormatoReuniao;
import model.enums.StatusReuniao;
import model.enums.TipoReuniao;
import repository.InsightRepository;
import repository.TranscricaoRepository;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Orquestra a simulacao interativa de uma reuniao comercial.
 * Ao encerrar a conversa, gera automaticamente um Insight (AnaliseService)
 * e persiste transcricao e insight nas respectivas camadas de repositorio.
 */
public class ReuniaoService {

    private final TranscricaoRepository transcricaoRepository = new TranscricaoRepository();
    private final InsightRepository insightRepository = new InsightRepository();
    private final AnaliseService analiseService = new AnaliseService();

    public void iniciarSimulacao() {
        Scanner scanner = new Scanner(System.in);

        Reuniao reuniao = new Reuniao(1, TipoReuniao.VENDAS, StatusReuniao.FINALIZADA, 0.0);
        reuniao.setFormatoReuniao(FormatoReuniao.VIDEO);
        Vendedor vendedor = new Vendedor(1, "Carlos", 5, "Vendas", "carlos@gmail.com");
        Cliente cliente = new Cliente(2, "Pedro", "Synergy", "SP", "6201-5/01",
                "Tecnologia", "Acima de 50 milhoes", 65);
        Produto produto = new Produto(1, "SistemaRH", 200.00,
                "Um sistema completo de RH customizado para sua empresa");

        System.out.println("Seja bem-vindo ao sistema ATLAS COMMUNICATION!");
        System.out.println("Digite 1 para Vendedor ou 2 para Cliente:");
        int profissao = lerInteiro(scanner);

        if (profissao == 1) {
            executarFluxoVendedor(scanner, reuniao, vendedor, cliente, produto);
        } else if (profissao == 2) {
            executarFluxoCliente(scanner, reuniao, vendedor, cliente, produto);
        } else {
            System.out.println("Insira uma opcao valida.");
        }
    }

    private void executarFluxoVendedor(Scanner scanner, Reuniao reuniao, Vendedor vendedor,
                                       Cliente cliente, Produto produto) {
        System.out.println("Bem-vindo, " + vendedor.getNomeUsuario() + "!");
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("1 - Iniciar reuniao | 2 - Exibir transcricoes | "
                    + "3 - Aplicar desconto | 4 - Sair");
            opcao = lerInteiro(scanner);
            if (opcao == 1) {
                conduzirReuniao(scanner, reuniao, vendedor, cliente);
            } else if (opcao == 2) {
                exibirTranscricoes(scanner);
            } else if (opcao == 3) {
                aplicarDesconto(scanner, produto);
            } else if (opcao != 4) {
                System.out.println("Opcao invalida.");
            }
        }
    }

    private void executarFluxoCliente(Scanner scanner, Reuniao reuniao, Vendedor vendedor,
                                      Cliente cliente, Produto produto) {
        System.out.println("Bem-vindo, " + cliente.getNomeUsuario() + "!");
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("1 - Iniciar reuniao | 2 - Exibir produto | "
                    + "3 - Comprar produto | 4 - Sair");
            opcao = lerInteiro(scanner);
            if (opcao == 1) {
                conduzirReuniao(scanner, reuniao, vendedor, cliente);
            } else if (opcao == 2) {
                System.out.println(produto.obterDetalhes());
            } else if (opcao == 3) {
                System.out.println(produto.obterDetalhes());
                System.out.println("Comprar produto? s/n");
                String r = scanner.nextLine().toLowerCase();
                System.out.println(r.equals("s") ? cliente.comprarProduto() : "Compra cancelada!");
            } else if (opcao != 4) {
                System.out.println("Opcao invalida.");
            }
        }
    }

    private void conduzirReuniao(Scanner scanner, Reuniao reuniao, Vendedor vendedor, Cliente cliente) {
        if (reuniao.isAtiva()) { System.out.println("Reuniao ja esta acontecendo!"); return; }
        reuniao.iniciarReuniao();
        Transcricao transcricao = new Transcricao(
                transcricaoRepository.total(), vendedor.getEquipe(), LocalDate.now(), new StringBuilder());
        transcricao.iniciarTranscricao();

        boolean ativa = true;
        while (ativa) {
            System.out.println("Quem fala? 1 - Vendedor | 2 - Cliente | 3 - Encerrar");
            String quem = scanner.nextLine();
            switch (quem) {
                case "1":
                    transcricao.registrarFala(vendedor.getNomeUsuario(),
                            vendedor.enviarMensagem(vendedor.getNomeUsuario()));
                    break;
                case "2":
                    transcricao.registrarFala(cliente.getNomeUsuario(),
                            cliente.enviarMensagem(cliente.getNomeUsuario()));
                    break;
                case "3":
                    ativa = false;
                    reuniao.finalizarReuniao();
                    transcricao.finalizarTranscricao();
                    transcricaoRepository.salvar(transcricao);
                    Insight insight = analiseService.analisar(transcricao);
                    insightRepository.salvar(insight);
                    System.out.println("Transcricao gravada (ID " + transcricao.getIdTranscricao() + ").");
                    System.out.println(insight.resumo());
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    private void exibirTranscricoes(Scanner scanner) {
        System.out.println("Informe o ID da transcricao:");
        int id = lerInteiro(scanner);
        transcricaoRepository.buscarPorId(id).ifPresentOrElse(
                t -> System.out.println(t.analisarResumo()),
                () -> System.out.println("Transcricao nao encontrada."));
    }

    private void aplicarDesconto(Scanner scanner, Produto produto) {
        System.out.println(produto.obterDetalhes());
        System.out.println("Percentual de desconto:");
        double desc = scanner.nextDouble();
        scanner.nextLine();
        try {
            System.out.println("Novo preco: R$ " + String.format("%.2f", produto.aplicarDesconto(desc)));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) { scanner.next(); System.out.println("Digite um numero valido:"); }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}
