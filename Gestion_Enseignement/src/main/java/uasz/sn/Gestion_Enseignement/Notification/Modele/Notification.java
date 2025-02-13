package uasz.sn.Gestion_Enseignement.Notification.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private boolean lu = false;
    private Date dateNotification= new Date();

    @ManyToOne
    @JoinColumn(name = "destinataire_id", nullable = false)
    private Enseignant destinataire;

}
