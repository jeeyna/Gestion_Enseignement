package uasz.sn.Gestion_Enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Formation;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Long> {
    @Query("SELECT c from Classe c where c.formation=?1")
    List<Classe> findByFormation(Formation formation);
}
