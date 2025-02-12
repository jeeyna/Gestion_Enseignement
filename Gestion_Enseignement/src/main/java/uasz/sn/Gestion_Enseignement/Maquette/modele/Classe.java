package uasz.sn.Gestion_Enseignement.Maquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int niveau;
    private boolean archive;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "classe")
    private List<Maquette> maquettes = new ArrayList<>();

}

