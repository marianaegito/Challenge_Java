package repository;

import model.Transcricao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Persistencia em memoria das transcricoes (diferencial 6.3 - camada repository). */
public class TranscricaoRepository {
    private final List<Transcricao> dados = new ArrayList<>();

    public Transcricao salvar(Transcricao t) { dados.add(t); return t; }

    public Optional<Transcricao> buscarPorId(int id) {
        return dados.stream().filter(t -> t.getIdTranscricao() == id).findFirst();
    }

    public List<Transcricao> listar() { return new ArrayList<>(dados); }
    public int total() { return dados.size(); }
}
