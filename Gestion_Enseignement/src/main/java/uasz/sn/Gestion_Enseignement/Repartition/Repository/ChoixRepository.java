package uasz.sn.Gestion_Enseignement.Repartition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.List;

@Repository
public interface ChoixRepository extends JpaRepository<Choix, Long> {
    boolean existsByEnseignantAndEnseignement(Enseignant enseignant, Enseignement enseignement);

}
