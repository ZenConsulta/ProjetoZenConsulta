package br.edu.iff.ccc.webproject.model;

public class Profissional {

    private Long id;
    private String nome;
    private String especialidade;
    private String email;

    public Profissional() {
    }

    public Profissional(Long id, String nome, String especialidade, String email) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
