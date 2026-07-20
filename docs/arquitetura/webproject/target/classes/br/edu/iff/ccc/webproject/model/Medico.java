package br.edu.iff.ccc.webproject.model;

public class Medico {
    private String id;
    private String nome;
    private String especialidade;
    private String local;
    private String nota;

    public Medico(String id, String nome, String especialidade, String local, String nota) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.local = local;
        this.nota = nota;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public String getLocal() { return local; }
    public String getNota() { return nota; }
}
