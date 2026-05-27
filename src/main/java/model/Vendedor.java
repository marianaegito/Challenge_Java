package model;

import model.enums.StatusReuniao;
import model.enums.TipoReuniao;

/** Vendedor: especializacao de Usuario com comportamentos implementados. */
public class Vendedor extends Usuario {
    private double avaliacaoVendedor;
    private String equipe;
    private String emailVendedor;

    public Vendedor(int idUsuario, String nomeUsuario, double avaliacaoVendedor,
                    String equipe, String emailVendedor) {
        super(idUsuario, nomeUsuario);
        setAvaliacaoVendedor(avaliacaoVendedor);
        this.equipe = equipe;
        setEmailVendedor(emailVendedor);
    }

    @Override
    public String getTipoUsuario() { return "VENDEDOR"; }

    public double getAvaliacaoVendedor() { return avaliacaoVendedor; }
    public void setAvaliacaoVendedor(double nota) {
        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("Avaliacao deve estar entre 0 e 5.");
        }
        this.avaliacaoVendedor = nota;
    }

    public String getEquipe() { return equipe; }
    public void setEquipe(String v) { this.equipe = v; }

    public String getEmailVendedor() { return emailVendedor; }
    public void setEmailVendedor(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail do vendedor invalido.");
        }
        this.emailVendedor = email;
    }

    /** Funcionalidade real: cria e agenda uma reuniao de vendas. */
    public Reuniao solicitarReuniao(int idReuniao, TipoReuniao tipo) {
        Reuniao reuniao = new Reuniao(idReuniao, tipo, StatusReuniao.FINALIZADA, 0.0);
        System.out.println(getNomeUsuario() + " agendou uma reuniao do tipo " + tipo.getDescricao() + ".");
        return reuniao;
    }

    /** Funcionalidade real: registra a venda de um produto. */
    public String venderProduto(Produto produto) {
        return "Venda registrada: " + produto.getNomeProduto()
                + " por R$ " + String.format("%.2f", produto.getPreco());
    }
}
