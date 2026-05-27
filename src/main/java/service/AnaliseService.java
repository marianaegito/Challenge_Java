package service;

import model.Insight;
import model.Transcricao;
import model.enums.Sentimento;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servico que transforma o texto bruto de uma transcricao em inteligencia
 * acionavel (diferencial 6.2), atendendo aos tres processamentos exigidos
 * pelo desafio TOTVS: identificacao de oportunidades, sinalizacao de
 * retencao e mapeamento do ecossistema de produtos.
 */
public class AnaliseService {

    private static final String[] PRODUTOS_TOTVS = {
        "Protheus", "RM", "Fluig", "Datasul", "TOTVS"
    };
    private static final String[] CONCORRENTES = { "Senior", "SAP", "Oracle", "Sankhya" };
    private static final String[] GATILHOS_COMPRA = {
        "demo", "gostei", "quero", "comprar", "integrar", "proposta", "contratar", "upsell", "modulo"
    };
    private static final String[] SINAIS_CHURN = {
        "insatisfeito", "sofrendo", "problema", "cancelar", "concorrente",
        "caro", "reclamacao", "reclamando", "frustrado", "avaliando"
    };
    private static final String[] POSITIVOS = { "gostei", "otimo", "excelente", "satisfeito", "bom", "ajudou" };
    private static final String[] NEGATIVOS = { "ruim", "problema", "sofrendo", "frustrado", "insatisfeito", "caro" };

    private int sequenciaInsight = 1;

    public Insight analisar(Transcricao transcricao) {
        String texto = transcricao.analisarResumo().toString();
        String low = normalizar(texto);

        Insight insight = new Insight(sequenciaInsight++, transcricao);

        // 1. Mapeamento de ecossistema (produtos TOTVS + concorrentes)
        List<String> achados = new ArrayList<>();
        for (String p : PRODUTOS_TOTVS) if (low.contains(p.toLowerCase())) achados.add(p);
        for (String c : CONCORRENTES) if (low.contains(c.toLowerCase())) achados.add(c + " (concorrente)");
        insight.setProdutosCitados(String.join(", ", achados));

        // 2. Identificacao de oportunidades (gatilhos de compra)
        int gatilhos = contarOcorrencias(low, GATILHOS_COMPRA);
        insight.setNivelOportunidade(Math.min(100, gatilhos * 20));

        // 3. Sinalizacao de retencao (risco de churn)
        int sinais = contarOcorrencias(low, SINAIS_CHURN);
        boolean concorrenteCitado = achados.stream().anyMatch(a -> a.contains("concorrente"));
        insight.setRiscoChurn(sinais >= 1 || concorrenteCitado);

        // 4. Sentimento
        int pos = contarOcorrencias(low, POSITIVOS);
        int neg = contarOcorrencias(low, NEGATIVOS);
        insight.setSentimento(classificarSentimento(pos, neg));

        // 5. Persona (interlocutor)
        insight.setPersona(detectarPersona(low));

        // 6. Budget mencionado (ex.: "R$ 50 mil", "50.000")
        insight.setOrcamentoEstimado(extrairOrcamento(low));

        return insight;
    }

    private Sentimento classificarSentimento(int pos, int neg) {
        if (pos > 0 && neg > 0) return Sentimento.MISTO;
        if (pos > neg) return Sentimento.POSITIVO;
        if (neg > pos) return Sentimento.NEGATIVO;
        return Sentimento.NEUTRO;
    }

    private String detectarPersona(String low) {
        if (contemPalavra(low, "cfo") || contemPalavra(low, "financeiro")) return "Decisor Financeiro (CFO)";
        if (contemPalavra(low, "diretor") || contemPalavra(low, "ceo")) return "Alta Lideranca";
        if (contemPalavra(low, "rh") || contemPalavra(low, "folha")) return "Gestor de RH";
        if (contemPalavra(low, "gestor") || contemPalavra(low, "gerente")) return "Gestor Operacional";
        if (contemPalavra(low, "ti") || contemPalavra(low, "tecnico")) return "Decisor Tecnico (TI)";
        return "Nao identificada";
    }

    /** Verifica a presenca do termo como palavra inteira (evita falsos positivos). */
    private boolean contemPalavra(String texto, String termo) {
        return Pattern.compile("\\b" + Pattern.quote(termo) + "\\b").matcher(texto).find();
    }

    private double extrairOrcamento(String low) {
        // padrao "50 mil" / "r$ 50 mil"
        Matcher mMil = Pattern.compile("(\\d+[\\.,]?\\d*)\\s*mil").matcher(low);
        if (mMil.find()) {
            double base = Double.parseDouble(mMil.group(1).replace(".", "").replace(",", "."));
            return base * 1000;
        }
        // padrao "50000" / "50.000,00"
        Matcher mNum = Pattern.compile("r\\$\\s*([\\d\\.]+,?\\d*)").matcher(low);
        if (mNum.find()) {
            String n = mNum.group(1).replace(".", "").replace(",", ".");
            try { return Double.parseDouble(n); } catch (NumberFormatException e) { return 0; }
        }
        return 0;
    }

    private int contarOcorrencias(String texto, String[] termos) {
        int total = 0;
        for (String termo : termos) {
            int idx = 0;
            while ((idx = texto.indexOf(termo.toLowerCase(), idx)) != -1) { total++; idx += termo.length(); }
        }
        return total;
    }

    private String normalizar(String s) {
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
