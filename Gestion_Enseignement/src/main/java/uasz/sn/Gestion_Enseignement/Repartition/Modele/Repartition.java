package uasz.sn.Gestion_Enseignement.Repartition.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repartition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private Enseignant responsableTd;

    @ManyToOne
    private Enseignant responsableTp;

    @ManyToOne
    private Enseignement enseignement;
}
