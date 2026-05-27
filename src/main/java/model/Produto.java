package model;

/** Produto ou servico oferecido durante a reuniao. */
public class Produto {
    private int idProduto;
    private String nomeProduto;
    private double preco;
    private String descricaoProduto;

    public Produto(int idProduto, String nomeProduto, double preco, String descricaoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        setPreco(preco);
        this.descricaoProduto = descricaoProduto;
    }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int v) { this.idProduto = v; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String v) { this.nomeProduto = v; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) {
        if (preco < 0) throw new IllegalArgumentException("O preco nao pode ser negativo.");
        this.preco = preco;
    }

    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String v) { this.descricaoProduto = v; }

    /** Calcula o preco total para uma quantidade (funcionalidade implementada). */
    public double calcularPreco(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva.");
        return preco * quantidade;
    }

    public double aplicarDesconto(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Nao e possivel dar um desconto maior do que 100%!");
        }
        preco = preco - (preco * percentual / 100);
        return preco;
    }

    /** Retorna os detalhes reais do produto. */
    public String obterDetalhes() {
        return "Produto #" + idProduto + " - " + nomeProduto
                + " | R$ " + String.format("%.2f", preco)
                + " | " + descricaoProduto;
    }
}
