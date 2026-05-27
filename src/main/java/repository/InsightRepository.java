package repository;

import model.Insight;
import java.util.ArrayList;
import java.util.List;

/** Persistencia em memoria dos insights gerados (diferencial 6.3). */
public class InsightRepository {
    private final List<Insight> dados = new ArrayList<>();

    public Insight salvar(Insight i) { dados.add(i); return i; }
    public List<Insight> listar() { return new ArrayList<>(dados); }

    /** Lista apenas os clientes em risco - apoio a retencao. */
    public List<Insight> listarEmRisco() {
        List<Insight> risco = new ArrayList<>();
        for (Insight i : dados) if (i.isRiscoChurn()) risco.add(i);
        return risco;
    }
}
