package unoeste.fipp.ativooperante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Orgao;
import unoeste.fipp.ativooperante.repositories.OrgaoRepository;
import unoeste.fipp.ativooperante.repositories.TipoRepository;

import java.util.List;

@Service
public class OrgaoService
{
    @Autowired
    OrgaoRepository orgaoRepository;

    public List<Orgao> findAll()
    {
        List<Orgao> orgaos = orgaoRepository.findAll();

        return orgaos;
    }

    public Orgao findById(Long id)
    {
        Orgao orgaoId = orgaoRepository.findById(id).orElse(null);
        return orgaoId;
    }

    public Orgao save(Orgao orgao)
    {
        try {
            Orgao orgaoSave = orgaoRepository.save(orgao);
            return orgaoSave;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(Long id)
    {
        if(findById(id) != null)
        {
            orgaoRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

}
