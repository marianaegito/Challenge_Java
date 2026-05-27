package model.enums;

/**
 * Status de uma reuniao. Substitui o antigo boolean/CHAR(1),
 * reforcando a integridade e a legibilidade (diferencial 6.4).
 */
public enum StatusReuniao {
    ATIVA('A', "Ativa"),
    FINALIZADA('F', "Finalizada");

    private final char codigo;
    private final String descricao;

    StatusReuniao(char codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public char getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static StatusReuniao porCodigo(char codigo) {
        for (StatusReuniao s : values()) {
            if (s.codigo == Character.toUpperCase(codigo)) return s;
        }
        throw new IllegalArgumentException("Status invalido: " + codigo);
    }
}
