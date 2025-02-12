package uasz.sn.Gestion_Enseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Enseignant extends Utilisateur {
    private String specialite;
    private boolean archive;

}
