package model;

import java.time.LocalDate;

/** Armazena e processa os dados textuais gerados durante a reuniao. */
public class Transcricao {
    private int idTranscricao;
    private String origem;
    private LocalDate dataTranscricao;
    private StringBuilder transcricaoInfo;
    private boolean finalizada;

    public Transcricao(int idTranscricao, String origem, LocalDate dataTranscricao, StringBuilder transcricaoInfo) {
        this.idTranscricao = idTranscricao;
        this.origem = origem;
        this.dataTranscricao = dataTranscricao;
        this.transcricaoInfo = transcricaoInfo == null ? new StringBuilder() : transcricaoInfo;
    }

    public int getIdTranscricao() { return idTranscricao; }
    public void setIdTranscricao(int v) { this.idTranscricao = v; }

    public String getOrigem() { return origem; }
    public void setOrigem(String v) { this.origem = v; }

    public LocalDate getDataTranscricao() { return dataTranscricao; }
    public void setDataTranscricao(LocalDate v) { this.dataTranscricao = v; }

    public StringBuilder getTranscricaoInfo() { return transcricaoInfo; }
    public void setTranscricaoInfo(StringBuilder v) { this.transcricaoInfo = v; }

    public boolean isFinalizada() { return finalizada; }

    /** Inicia a captura da transcricao. */
    public void iniciarTranscricao() {
        this.finalizada = false;
        if (transcricaoInfo == null) transcricaoInfo = new StringBuilder();
    }

    /** Adiciona uma fala ao corpo da transcricao. */
    public void registrarFala(String autor, String mensagem) {
        transcricaoInfo.append(autor).append(": ").append(mensagem).append("\n");
    }

    /** Encerra a captura da transcricao. */
    public void finalizarTranscricao() { this.finalizada = true; }

    public StringBuilder analisarResumo() { return transcricaoInfo; }
}
