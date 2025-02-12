package uasz.sn.Gestion_Enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;

import java.util.List;

public interface UERepository extends JpaRepository<UE, Long> {
    @Query("SELECT e from EC e where e.ue = ?1")
    List<EC> findByUE(UE ue);

    @Query("SELECT e FROM EC e WHERE e.ue.id = :ueId")
    List<EC> findEcsByUeId(@Param("ueId") Long ueId);


}
