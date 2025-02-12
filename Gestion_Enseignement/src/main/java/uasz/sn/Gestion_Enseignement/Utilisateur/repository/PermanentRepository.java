package uasz.sn.Gestion_Enseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;

import java.util.Optional;

public interface PermanentRepository extends JpaRepository<Permanent, Long> {
    @Query("Select p from Permanent p where p.matricule=?1")
    Optional findByMatricule(String matricule);
}
