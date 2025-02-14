package uasz.sn.Gestion_Enseignement.Repartition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Repartition;

@Repository
public interface RepartitionRepository extends JpaRepository <Repartition, Long> {
    @Query("select r from Repartition r where r.enseignement.ec = :ec")
    Repartition findByEC(@Param("ec") EC ec);
}
