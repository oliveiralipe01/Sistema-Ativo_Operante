package unoeste.fipp.ativooperante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unoeste.fipp.ativooperante.entities.Orgao;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao,Long> {
}
