package uasz.sn.Gestion_Enseignement.Notification.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Notification.Modele.Notification;
import uasz.sn.Gestion_Enseignement.Notification.Repository.NotificationRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void envoyerNotification(Notification notification) {
        notificationRepository.save(notification);
    }
    public List<Notification> findByDestinataire(Enseignant destinataire) {
        return notificationRepository.findByDestinataire(destinataire);
    }

    public Long nombreNotificationNonLu(Enseignant destinataire) {
        return notificationRepository.nombreNotificationNonLu(destinataire);
    }
    public void lireNotification(Long id){
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Notification introuvable")
        );

        notification.setLu(true);
        notificationRepository.save(notification);

    }


}

