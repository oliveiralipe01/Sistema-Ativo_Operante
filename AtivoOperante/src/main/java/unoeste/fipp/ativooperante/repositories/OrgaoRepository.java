package unoeste.fipp.ativooperante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unoeste.fipp.ativooperante.entities.Orgao;

import java.util.List;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao,Long> {
    List<Orgao> findAll();
}
