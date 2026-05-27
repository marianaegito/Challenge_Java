package model;

import model.enums.Sentimento;

/**
 * Inteligencia acionavel extraida de uma transcricao (diferencial 6.2).
 * Transforma o "ruido" da conversa em dados estruturados para o comercial.
 */
public class Insight {
    private int idInsight;
    private Transcricao transcricao;     // atributo de referencia
    private Sentimento sentimento;
    private int nivelOportunidade;       // 0 a 100
    private boolean riscoChurn;
    private String produtosCitados;
    private String persona;
    private double orcamentoEstimado;

    public Insight(int idInsight, Transcricao transcricao) {
        this.idInsight = idInsight;
        this.transcricao = transcricao;
        this.sentimento = Sentimento.NEUTRO;
    }

    public int getIdInsight() { return idInsight; }
    public void setIdInsight(int v) { this.idInsight = v; }

    public Transcricao getTranscricao() { return transcricao; }
    public void setTranscricao(Transcricao v) { this.transcricao = v; }

    public Sentimento getSentimento() { return sentimento; }
    public void setSentimento(Sentimento v) { this.sentimento = v; }

    public int getNivelOportunidade() { return nivelOportunidade; }
    public void setNivelOportunidade(int nivel) {
        if (nivel < 0 || nivel > 100) {
            throw new IllegalArgumentException("Nivel de oportunidade deve estar entre 0 e 100.");
        }
        this.nivelOportunidade = nivel;
    }

    public boolean isRiscoChurn() { return riscoChurn; }
    public void setRiscoChurn(boolean v) { this.riscoChurn = v; }

    public String getProdutosCitados() { return produtosCitados; }
    public void setProdutosCitados(String v) { this.produtosCitados = v; }

    public String getPersona() { return persona; }
    public void setPersona(String v) { this.persona = v; }

    public double getOrcamentoEstimado() { return orcamentoEstimado; }
    public void setOrcamentoEstimado(double v) { this.orcamentoEstimado = v; }

    public String resumo() {
        return  "===== INSIGHT DA REUNIAO #" + idInsight + " =====\n" +
                "Sentimento.........: " + sentimento + "\n" +
                "Oportunidade.......: " + nivelOportunidade + "/100\n" +
                "Risco de churn.....: " + (riscoChurn ? "SIM (atencao!)" : "nao") + "\n" +
                "Produtos citados...: " + (produtosCitados == null || produtosCitados.isBlank() ? "-" : produtosCitados) + "\n" +
                "Persona............: " + (persona == null ? "-" : persona) + "\n" +
                "Orcamento estimado.: R$ " + String.format("%.2f", orcamentoEstimado);
    }
}
