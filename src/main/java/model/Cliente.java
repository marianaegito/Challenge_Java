package model;

/**
 * Cliente: especializacao de Usuario enriquecida com dados reais da base
 * de transcricoes (UF, CNAE, segmento, faixa de faturamento e NPS) -
 * diferencial 6.1.
 */
public class Cliente extends Usuario {
    private String nomeEmpresaCliente;
    private String historicoCliente;
    private String uf;
    private String cnae;
    private String segmento;
    private String faixaFaturamento;
    private Integer notaNps;

    public Cliente(int idUsuario, String nomeUsuario, String nomeEmpresaCliente) {
        super(idUsuario, nomeUsuario);
        this.nomeEmpresaCliente = nomeEmpresaCliente;
    }

    public Cliente(int idUsuario, String nomeUsuario, String nomeEmpresaCliente,
                   String uf, String cnae, String segmento,
                   String faixaFaturamento, Integer notaNps) {
        super(idUsuario, nomeUsuario);
        this.nomeEmpresaCliente = nomeEmpresaCliente;
        this.uf = uf;
        this.cnae = cnae;
        this.segmento = segmento;
        this.faixaFaturamento = faixaFaturamento;
        setNotaNps(notaNps);
    }

    @Override
    public String getTipoUsuario() { return "CLIENTE"; }

    public String getNomeEmpresaCliente() { return nomeEmpresaCliente; }
    public void setNomeEmpresaCliente(String v) { this.nomeEmpresaCliente = v; }

    public String getHistoricoCliente() { return historicoCliente; }
    public void setHistoricoCliente(String v) { this.historicoCliente = v; }

    public String getUf() { return uf; }
    public void setUf(String v) { this.uf = v; }

    public String getCnae() { return cnae; }
    public void setCnae(String v) { this.cnae = v; }

    public String getSegmento() { return segmento; }
    public void setSegmento(String v) { this.segmento = v; }

    public String getFaixaFaturamento() { return faixaFaturamento; }
    public void setFaixaFaturamento(String v) { this.faixaFaturamento = v; }

    public Integer getNotaNps() { return notaNps; }
    public void setNotaNps(Integer notaNps) {
        if (notaNps != null && (notaNps < 0 || notaNps > 100)) {
            throw new IllegalArgumentException("NPS deve estar entre 0 e 100.");
        }
        this.notaNps = notaNps;
    }

    public void participarReuniao() {
        System.out.println(getNomeUsuario() + " (" + nomeEmpresaCliente + ") entrou na reuniao.");
    }

    public String comprarProduto() { return "Produto comprado!"; }

    public void solicitarProposta() {
        System.out.println(getNomeUsuario() + " solicitou uma proposta comercial.");
    }

    public void avaliarVendedor(Vendedor vendedor, double nota) {
        vendedor.setAvaliacaoVendedor(nota);
        System.out.println("Vendedor " + vendedor.getNomeUsuario() + " avaliado com nota " + nota + ".");
    }
}
