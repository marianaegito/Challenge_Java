package model.enums;

/** Tipos de reuniao comercial suportados pelo sistema (diferencial 6.4). */
public enum TipoReuniao {
    VENDAS("Vendas"),
    SUPORTE("Suporte"),
    RENOVACAO("Renovacao"),
    PROSPECCAO("Prospeccao");

    private final String descricao;
    TipoReuniao(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
