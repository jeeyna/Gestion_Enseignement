package uasz.sn.Gestion_Enseignement.Maquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String code;
    private int cm;
    private int td;
    private int tp;
    private int tpe;
    private int credit;
    private int coefficient;
    private String description;
    private boolean active;
    private boolean archive;
    @ManyToOne
    private UE ue;
}
