package uasz.sn.Gestion_Enseignement.Repartition.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enseignement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sceance typeSeance;

    @ManyToOne
    @JoinColumn(name = "ec_id", nullable = false)
    private EC ec;

    @ManyToOne
    @JoinColumn(name = "maquette_id", nullable = false)
    private Maquette maquette;

    @ManyToOne
    @JoinColumn(name = "chef_id", nullable = false)
    private Enseignant chefDepartement;

    private boolean disponible = true;

    @OneToMany(mappedBy = "enseignement", cascade = CascadeType.ALL)
    private List<Choix> choix = new ArrayList<>();
}
