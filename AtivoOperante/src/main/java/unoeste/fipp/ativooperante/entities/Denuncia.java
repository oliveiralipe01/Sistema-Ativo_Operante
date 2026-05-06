package unoeste.fipp.ativooperante.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "denuncia")
public class Denuncia
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "den_titulo")
    private String titulo;

    @Column(name = "den_texto")
    private String texto;

    @Column(name = "den_urgencia")
    private int urgencia;

    @JoinColumn(name = "org_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Orgao orgao;

    @JoinColumn(name = "tip_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipo tipo;

    @JoinColumn(name = "usu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Column(name = "den_data")
    private LocalDate data;


    public Denuncia() {
        this(0L, "", "", 0, null, null, null, null);
    }

    public Denuncia(Long id, String titulo, String texto, int urgencia, Orgao orgao, Tipo tipo, Usuario usuario, LocalDate data) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.urgencia = urgencia;
        this.orgao = orgao;
        this.tipo = tipo;
        this.usuario = usuario;
        this.data = data;
    }

    public Denuncia(String titulo, String texto, int urgencia, Orgao orgao, Tipo tipo, Usuario usuario, LocalDate data) {
        this(0L, titulo, texto, urgencia, orgao, tipo, usuario, data);
    }
}
