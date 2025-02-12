package uasz.sn.Gestion_Enseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}
