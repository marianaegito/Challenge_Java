package model;

import model.enums.FormatoReuniao;
import model.enums.StatusReuniao;
import model.enums.TipoReuniao;

/** Entidade central que conecta clientes, vendedores, produtos e transcricoes. */
public class Reuniao {
    private int idReuniao;
    private TipoReuniao tipoReuniao;
    private StatusReuniao statusReuniao;
    private double duracaoReuniao;
    private FormatoReuniao formatoReuniao;

    public Reuniao(int idReuniao, TipoReuniao tipoReuniao, StatusReuniao statusReuniao, double duracaoReuniao) {
        this.idReuniao = idReuniao;
        this.tipoReuniao = tipoReuniao;
        this.statusReuniao = statusReuniao;
        this.duracaoReuniao = duracaoReuniao;
    }

    public int getIdReuniao() { return idReuniao; }
    public void setIdReuniao(int v) { this.idReuniao = v; }

    public TipoReuniao getTipoReuniao() { return tipoReuniao; }
    public void setTipoReuniao(TipoReuniao v) { this.tipoReuniao = v; }

    public StatusReuniao getStatusReuniao() { return statusReuniao; }
    public void setStatusReuniao(StatusReuniao v) { this.statusReuniao = v; }

    public double getDuracaoReuniao() { return duracaoReuniao; }
    public void setDuracaoReuniao(double v) { this.duracaoReuniao = v; }

    public FormatoReuniao getFormatoReuniao() { return formatoReuniao; }
    public void setFormatoReuniao(FormatoReuniao v) { this.formatoReuniao = v; }

    public boolean isAtiva() { return statusReuniao == StatusReuniao.ATIVA; }

    public boolean iniciarReuniao() {
        if (statusReuniao != StatusReuniao.ATIVA) {
            statusReuniao = StatusReuniao.ATIVA;
            return true;
        }
        return false;
    }

    public boolean finalizarReuniao() {
        if (statusReuniao == StatusReuniao.ATIVA) {
            statusReuniao = StatusReuniao.FINALIZADA;
            return true;
        }
        return false;
    }
}
