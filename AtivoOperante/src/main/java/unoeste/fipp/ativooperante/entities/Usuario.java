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

    @NotNull
    @Column(name = "usu_cpf")
    private Long cpf;

    @NotBlank
    @Email
    @Column(name = "usu_email")
    private String email;

    @NotNull
    @Column(name = "usu_senha")
    private Long senha;

    @Column(name = "usu_nivel")
    private int nivel;

    public Usuario() {
        this(0L, 0L, "", 0L, 0);
    }

    public Usuario(Long id, Long cpf, String email, Long senha, int nivel) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Usuario(Long cpf, String email, Long senha, int nivel) {
        this(0L, cpf, email, senha, nivel);
    }

    public Long getId() {
        return id;
    }

    public Long getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public Long getSenha() {
        return senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(Long senha) {
        this.senha = senha;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
