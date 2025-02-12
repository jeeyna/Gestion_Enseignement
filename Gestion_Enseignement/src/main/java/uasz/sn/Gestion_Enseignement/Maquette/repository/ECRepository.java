package uasz.sn.Gestion_Enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;

import java.util.Collection;
import java.util.List;

public interface ECRepository extends JpaRepository<EC, Long> {
    List<EC> findByUe(UE ue);
}
