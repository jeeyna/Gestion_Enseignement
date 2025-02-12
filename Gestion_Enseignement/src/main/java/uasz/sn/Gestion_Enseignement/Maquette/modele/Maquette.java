package uasz.sn.Gestion_Enseignement.Maquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Maquette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false ,referencedColumnName = "id")
    private Classe classe;

    private int semestre;

    @ManyToMany
    @JoinTable(
            name = "maquette_ue", // Nom de la table d'association
            joinColumns = @JoinColumn(name = "maquette_id"),
            inverseJoinColumns = @JoinColumn(name = "ue_id")
    )
    private List<UE> ues; // Une maquette contient plusieurs UE, et une UE peut être dans plusieurs maquettes


}