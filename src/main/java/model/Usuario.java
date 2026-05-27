package model;

import java.util.Scanner;

/**
 * Classe base do sistema. Tornada abstrata para evidenciar heranca e
 * polimorfismo: cada subtipo define seu papel via getTipoUsuario().
 */
public abstract class Usuario {
    private int idUsuario;
    private String nomeUsuario;

    public Usuario(int idUsuario, String nomeUsuario) {
        this.idUsuario = idUsuario;
        setNomeUsuario(nomeUsuario);
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) {
        if (nomeUsuario == null || nomeUsuario.isBlank()) {
            throw new IllegalArgumentException("O nome do usuario nao pode ser vazio.");
        }
        this.nomeUsuario = nomeUsuario;
    }

    /** Metodo polimorfico: cada subclasse informa seu papel. */
    public abstract String getTipoUsuario();

    public String enviarMensagem(String nome) {
        System.out.println(nome + ", insira sua mensagem:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /** Sobrecarga utilizada na demonstracao automatizada (sem console). */
    public String enviarMensagem(String nome, String texto) {
        return texto;
    }

    @Override
    public String toString() {
        return getTipoUsuario() + " [id=" + idUsuario + ", nome=" + nomeUsuario + "]";
    }
}
