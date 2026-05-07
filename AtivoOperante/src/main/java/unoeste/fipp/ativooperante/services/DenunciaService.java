package unoeste.fipp.ativooperante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Denuncia;
import unoeste.fipp.ativooperante.repositories.DenunciaRepository;

import java.util.List;

@Service
public class DenunciaService {

    @Autowired
    private DenunciaRepository repository;

    public Denuncia cadastrar(Denuncia denuncia) {
        return repository.save(denuncia);
    }

    public List<Denuncia> listarTodas() {
        return repository.findAll();
    }

    public List<Denuncia> listarPorUsuario(Long idUsuario) {
        return repository.findByUsuarioId(idUsuario);
    }

    public Denuncia buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
