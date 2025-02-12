package uasz.sn.Gestion_Enseignement.Repartition.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateChoix;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "enseignement_id", nullable = false)
    private Enseignement enseignement;

    @Enumerated(EnumType.STRING)
    private Statut statut;
}
