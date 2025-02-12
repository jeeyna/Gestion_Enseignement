package uasz.sn.Gestion_Enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Formation;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
List<Classe> findByFormation(Formation formation);

    List<Classe> findByArchiveTrue();

    List<Classe> findByArchiveFalse();
}
