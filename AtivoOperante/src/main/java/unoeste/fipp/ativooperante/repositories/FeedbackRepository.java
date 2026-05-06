package unoeste.fipp.ativooperante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unoeste.fipp.ativooperante.entities.Denuncia;
import unoeste.fipp.ativooperante.entities.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long>
{

}

