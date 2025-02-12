package uasz.sn.Gestion_Enseignement.Utilisateur.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Etudiant extends Utilisateur {
    private String matricule;
    private String telephone;
    private String date_naissance;
}
