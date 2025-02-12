package uasz.sn.Gestion_Enseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;

public interface VacataireRepository extends JpaRepository<Vacataire, Long> {
}
