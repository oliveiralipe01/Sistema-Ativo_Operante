package unoeste.fipp.ativooperante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Orgao;
import unoeste.fipp.ativooperante.repositories.OrgaoRepository;

import java.util.List;

@Service
public class OrgaoService
{
    @Autowired
    OrgaoRepository orgaoRepository;

    public List<Orgao> listarTodos(){
        return orgaoRepository.findAll();
    }
}
