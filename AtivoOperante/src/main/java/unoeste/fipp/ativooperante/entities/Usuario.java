package unoeste.fipp.ativooperante.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;

    @NotBlank
    @Column(name = "usu_cpf")
    private String cpf;

    @NotBlank
    @Email
    @Column(name = "usu_email")
    private String email;

    @NotBlank
    @Column(name = "usu_senha")
    private String senha;

    @Column(name = "usu_nivel")
    private int nivel;

    public Usuario() {
        this(0L, "", "", "", 0);
    }

    public Usuario(Long id, String cpf, String email, String senha, int nivel) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Usuario(String cpf, String email, String senha, int nivel) {
        this(0L, cpf, email, senha, nivel);
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
