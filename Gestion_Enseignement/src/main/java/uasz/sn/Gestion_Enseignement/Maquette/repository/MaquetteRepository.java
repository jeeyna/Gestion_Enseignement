package uasz.sn.Gestion_Enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Maquette;

import java.util.List;

@Repository
public interface MaquetteRepository extends JpaRepository<Maquette, Long> {

    List<Maquette> findByClasse(Classe classe);


}
