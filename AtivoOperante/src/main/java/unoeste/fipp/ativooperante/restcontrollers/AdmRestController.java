package unoeste.fipp.ativooperante.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.ativooperante.entities.*;
import unoeste.fipp.ativooperante.services.DenunciaService;
import unoeste.fipp.ativooperante.services.OrgaoService;
import unoeste.fipp.ativooperante.services.TipoService;

import java.util.List;

@RestController
@RequestMapping("apis/adm")
public class AdmRestController
{
    //endpoint:
    //CRUD tipo de problema e orgão competente
    //Listar denuncias
    //Excluir Denuncia
    //Registrar feedback em denúncia

    @Autowired
    OrgaoService orgaoService;

    @Autowired
    TipoService tipoService;

    @Autowired
    DenunciaService denunciaService;



    @GetMapping("/allOrgaos")
    public List<Orgao> findAllO()
    {
        return orgaoService.findAll();
    }

    @PostMapping("/saveOrgao")
    public ResponseEntity<Object> saveOrgao(@RequestBody Orgao orgao)
    {
        Orgao saveO = orgaoService.save(orgao);
        if(saveO == null)
            return ResponseEntity.badRequest().body(new Erro("Erro ao inserir o orgão"));
        else
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/alterOrgao")
    public ResponseEntity<Object> alterOrgao(@RequestBody Orgao orgao)
    {
        Orgao saveO = orgaoService.save(orgao);
        if(saveO == null)
            return ResponseEntity.badRequest().body(new Erro("Erro ao alterar o orgão"));
        else
            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/orgao/{id}")
    public ResponseEntity<Object> deleteOrgao(@PathVariable Long id)
    {
        boolean delete = orgaoService.delete(id);

        if(delete != true)
        {
            return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o orgão"));
        }
        else
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/allTipos")
    public List<Tipo> findAll()
    {
        return tipoService.findAll();
    }

    @PostMapping("/saveTipo")
    public ResponseEntity<Object> saveTipo(@RequestBody Tipo tipo)
    {
        Tipo saveO = tipoService.save(tipo);
        if(saveO == null)
            return ResponseEntity.badRequest().body(new Erro("Erro ao inserir o tipo"));
        else
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/alterTipo")
    public ResponseEntity<Object> alterTipo(@RequestBody Tipo tipo)
    {
        Tipo saveO = tipoService.save(tipo);
        if(saveO == null)
            return ResponseEntity.badRequest().body(new Erro("Erro ao alterar o tipo"));
        else
            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tipo/{id}")
    public ResponseEntity<Object> deleteTipo(@PathVariable Long id)
    {
        boolean delete = tipoService.delete(id);

        if(delete != true)
        {
            return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o tipo"));
        }
        else
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/denuncias")
    public List<Denuncia> listarDenuncias()
    {
        return denunciaService.findAll();
    }

    @GetMapping("/denunciaId")
    public ResponseEntity<Object> findDenuncia(@RequestBody Long id)
    {
        Denuncia denuncia;
        denuncia = denunciaService.findOne(id);
        if(denuncia == null)
            return ResponseEntity.badRequest().body(new Erro("Erro ao encontrar a denúncia"));
        else
            return ResponseEntity.ok(denuncia);
    }

    @PostMapping("/feedback")
    public ResponseEntity<Object> adicionarFeedback(@RequestBody Feedback feedback)
    {
        Feedback save = denunciaService.adicionarFeedback(feedback);

        if(save == null)
        {
            return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar o feedback"));
        }
        else
            return ResponseEntity.noContent().build();
    }

}
