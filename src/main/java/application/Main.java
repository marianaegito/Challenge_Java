package application;

import model.*;
import model.enums.FormatoReuniao;
import model.enums.StatusReuniao;
import model.enums.TipoReuniao;
import repository.InsightRepository;
import repository.TranscricaoRepository;
import service.AnaliseService;
import service.ReuniaoService;

import java.time.LocalDate;

/**
 * Classe principal. Por padrao executa uma DEMONSTRACAO automatizada que
 * instancia e testa todas as classes, exercita heranca/polimorfismo e roda
 * o motor de inteligencia (AnaliseService) sobre transcricoes de exemplo.
 *
 * Para a simulacao interativa (Scanner), execute:  java application.Main sim
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("sim")) {
            new ReuniaoService().iniciarSimulacao();
        } else {
            executarDemonstracao();
        }
    }

    private static void executarDemonstracao() {
        System.out.println("================ ATLAS COMMUNICATION - DEMONSTRACAO ================\n");

        // --- Instanciacao das entidades ---
        Vendedor vendedor = new Vendedor(1, "Carlos", 4.5, "Vendas Corporativas", "carlos@totvs.com");
        Cliente cliente = new Cliente(2, "Joao", "Synergy", "SP", "6201-5/01",
                "Industria", "Acima de 50 milhoes", 72);
        cliente.setHistoricoCliente("Cliente desde 2021, base Protheus.");
        Produto folha = new Produto(10, "TOTVS RM - Folha de Pagamento", 50000.00,
                "Modulo de folha integrado ao Protheus.");

        // --- Polimorfismo: mesma chamada, comportamento por subtipo ---
        Usuario[] usuarios = { vendedor, cliente };
        System.out.println(">> Polimorfismo (getTipoUsuario):");
        for (Usuario u : usuarios) System.out.println("   " + u);
        System.out.println();

        // --- Funcionalidades reais ---
        System.out.println(">> Funcionalidades:");
        System.out.println("   " + folha.obterDetalhes());
        System.out.println("   Total (3 licencas): R$ " + String.format("%.2f", folha.calcularPreco(3)));
        System.out.println("   " + vendedor.venderProduto(folha));
        cliente.avaliarVendedor(vendedor, 5.0);
        Reuniao reuniao = vendedor.solicitarReuniao(100, TipoReuniao.VENDAS);
        reuniao.setFormatoReuniao(FormatoReuniao.VIDEO);
        reuniao.iniciarReuniao();
        System.out.println("   Reuniao ativa? " + reuniao.isAtiva()
                + " | status=" + reuniao.getStatusReuniao().getDescricao());
        System.out.println();

        // --- Motor de inteligencia sobre transcricoes de exemplo ---
        TranscricaoRepository transcricaoRepo = new TranscricaoRepository();
        InsightRepository insightRepo = new InsightRepository();
        AnaliseService analise = new AnaliseService();

        String[] exemplos = {
            "Bom dia, Joao. Entao, o nosso Protheus esta atendendo o backoffice, mas o time de RH "
                + "esta sofrendo muito com a folha manual. O pessoal viu uma demo da Senior e gostou, "
                + "mas eu prefiro consolidar tudo na TOTVS se o RM for realmente integrado. Esse valor "
                + "de R$ 50 mil que conversamos eu nao mencionei ao meu CFO ainda.",
            "O Fluig esta atendendo bem, equipe satisfeita. Queremos contratar o modulo de proposta "
                + "e integrar com o Protheus. Pode mandar a proposta para o nosso gestor."
        };

        System.out.println(">> Inteligencia extraida das transcricoes:\n");
        for (int i = 0; i < exemplos.length; i++) {
            Transcricao t = new Transcricao(i, "VIDEO", LocalDate.now(), new StringBuilder(exemplos[i]));
            transcricaoRepo.salvar(t);
            Insight insight = analise.analisar(t);
            insightRepo.salvar(insight);
            System.out.println(insight.resumo());
            System.out.println();
        }

        // --- Saida estrategica: clientes em risco de churn ---
        System.out.println(">> Clientes sinalizados para RETENCAO: "
                + insightRepo.listarEmRisco().size() + " de " + transcricaoRepo.total() + " reunioes.");
        System.out.println("\nDica: execute 'java application.Main sim' para a simulacao interativa.");
    }
}
