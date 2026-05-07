package unoeste.fipp.ativooperante.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Denuncia;
import unoeste.fipp.ativooperante.entities.Feedback;
import unoeste.fipp.ativooperante.repositories.DenunciaRepository;
import unoeste.fipp.ativooperante.repositories.FeedbackRepository;

import java.util.List;

@Service
public class DenunciaService
{
    //save
    //delete
    //findOne
    //findAll(filtro)

    @Autowired
    DenunciaRepository denunciaRepository;
    @Autowired
    FeedbackRepository feedbackRepository;

    public List<Denuncia> findAll()
    {
        List<Denuncia> denuncias = denunciaRepository.findAll();

        return denuncias;
    }

    public Denuncia findOne(Long id)
    {
        Denuncia denuncia = denunciaRepository.findById(id).orElse(null);

        return denuncia;
    }

    @Transactional
    public boolean delete(Long id)
    {
        if(findOne(id) != null)
        {
            Feedback feedback = feedbackRepository.findByDenunciaId(id);
            if( feedback != null)
                feedbackRepository.delete(feedback);

            denunciaRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }


    public Feedback adicionarFeedback(Feedback feedback)
    {
        Denuncia denuncia = feedback.getDenuncia();
        Feedback save;
        if(feedbackRepository.findByDenunciaId(denuncia.getId()) == null)
        {
            save = feedbackRepository.save(feedback);
            return save;
        }
        else
            return null;
    }
}
