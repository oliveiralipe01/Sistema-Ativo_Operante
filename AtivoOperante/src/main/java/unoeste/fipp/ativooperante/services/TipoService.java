package unoeste.fipp.ativooperante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Tipo;
import unoeste.fipp.ativooperante.repositories.TipoRepository;

import java.util.List;

@Service
public class TipoService {

    @Autowired
    private TipoRepository repository;

    public Tipo cadastrar(Tipo tipo) {
        return repository.save(tipo);
    }

    public List<Tipo> listarTodos() {
        return repository.findAll();
    }
}
