package unoeste.fipp.ativooperante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Tipo;
import unoeste.fipp.ativooperante.repositories.TipoRepository;

import java.util.List;

@Service

public class TipoService {
    @Autowired
    TipoRepository tipoRepository;

    public List<Tipo> findAll() {
        List<Tipo> tipos = tipoRepository.findAll();

        return tipos;
    }

    public Tipo findById(Long id) {
        Tipo tipo = tipoRepository.findById(id).orElse(null);

        return tipo;
    }

    public Tipo save(Tipo tipo) {
        Tipo tipoSave = tipoRepository.save(tipo);

        return tipoSave;
    }

    public boolean delete(Long id) {
        try {
            if (findById(id) != null) {
                tipoRepository.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

