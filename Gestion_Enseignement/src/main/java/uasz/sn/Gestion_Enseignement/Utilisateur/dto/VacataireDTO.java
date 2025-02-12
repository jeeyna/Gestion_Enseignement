package uasz.sn.Gestion_Enseignement.Utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacataireDTO {
    private Long id;
    private String username;
    private String nom;
    private String prenom;
    private String niveau;
}
