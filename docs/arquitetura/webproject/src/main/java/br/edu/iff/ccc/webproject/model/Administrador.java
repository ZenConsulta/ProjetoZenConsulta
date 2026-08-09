package br.edu.iff.ccc.webproject.model;

/**
 * Entidade Administrador. Acesso amplo ao sistema (RF07, RF08).
 */
public class Administrador {

    private Long id;
    private String nome;
    private String email;

    public Administrador() {
    }

    public Administrador(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
