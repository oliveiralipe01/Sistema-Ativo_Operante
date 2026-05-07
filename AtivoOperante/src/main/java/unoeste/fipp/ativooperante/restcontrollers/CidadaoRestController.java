package unoeste.fipp.ativooperante.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.ativooperante.entities.Denuncia;
import unoeste.fipp.ativooperante.entities.Feedback;
import unoeste.fipp.ativooperante.entities.Orgao;
import unoeste.fipp.ativooperante.entities.Tipo;
import unoeste.fipp.ativooperante.repositories.FeedbackRepository;
import unoeste.fipp.ativooperante.services.DenunciaService;
import unoeste.fipp.ativooperante.services.OrgaoService;
import unoeste.fipp.ativooperante.services.TipoService;

import java.util.List;

@RestController
@RequestMapping("/apis/cidadao")
public class CidadaoRestController {

    @Autowired
    private DenunciaService denunciaService;

    @Autowired
    private OrgaoService orgaoService;

    @Autowired
    private TipoService tipoService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @PostMapping("/denuncia")
    public Denuncia cadastrar(@Valid @RequestBody Denuncia denuncia) {
        return denunciaService.cadastrar(denuncia);
    }

    @GetMapping("/denuncias/{idUsuario}")
    public List<Denuncia> listarPorUsuario(@PathVariable Long idUsuario) {
        return denunciaService.listarPorUsuario(idUsuario);
    }

    @GetMapping("/orgaos")
    public List<Orgao> listarOrgaos() {
        return orgaoService.findAll();
    }

    @GetMapping("/tipos")
    public List<Tipo> listarTipos() {
        return tipoService.findAll();
    }

    @GetMapping("/feedbacks")
    public List<Feedback> listarFeedbacks() {
        return feedbackRepository.findAll();
    }
}