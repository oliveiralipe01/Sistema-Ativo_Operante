package unoeste.fipp.ativooperante.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "orgaos")
public class Orgao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long id;

    @NotBlank
    @Column(name = "org_nome")
    private String nome;

    public Orgao() {
        this(0L, "");
    }

    public Orgao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Orgao(String nome) {
        this(0L, nome);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}