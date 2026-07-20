java
package br.edu.iff.ccc.webproject.dto;

public class MedicoDTO {
    private String nome;
    private String especialidade;
    private String local;
    private String nota;

    public MedicoDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
}
