package uasz.sn.Gestion_Enseignement.Notification.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Notification.Modele.Notification;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByDestinataire(Enseignant destinataire);

    @Query("select count(n) from Notification n where n.destinataire= :destinataire and n.lu=false")
    Long nombreNotificationNonLu(@Param("destinataire")Enseignant destinataire);
}
