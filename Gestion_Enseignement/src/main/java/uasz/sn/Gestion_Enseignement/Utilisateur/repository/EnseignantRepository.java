package uasz.sn.Gestion_Enseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Enseignant findByUsername(String username);
}
