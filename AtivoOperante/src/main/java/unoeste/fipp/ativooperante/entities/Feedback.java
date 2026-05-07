package unoeste.fipp.ativooperante.entities;

import jakarta.persistence.*;
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private Long id;
    @Column(name = "fee_texto")
    private String texto;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "den_id")
    private Denuncia denuncia;
    public Feedback() {
        this(0L, "", null);
    }
    public Feedback(Long id, String texto, Denuncia denuncia) {
        this.id = id;
        this.texto = texto;
        this.denuncia = denuncia;
    }
    public Feedback(String texto, Denuncia denuncia) {
        this(0L, texto, denuncia);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }
}
