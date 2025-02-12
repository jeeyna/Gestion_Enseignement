package uasz.sn.Gestion_Enseignement.Repartition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.List;

public interface EnseignementRepository extends JpaRepository<Enseignement, Long> {
    void deleteByEcId(Long id);
    List<Enseignement> findByChefDepartement(Enseignant chefDepartement);
    List<Enseignement> findByDisponible(Boolean disponible);
}

