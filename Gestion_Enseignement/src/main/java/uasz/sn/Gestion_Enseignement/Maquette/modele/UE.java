package uasz.sn.Gestion_Enseignement.Maquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String code;
    private int credit;
    private int coefficient;
    private String description;
    private Date dateCreation;
    private boolean active;
    private boolean archive;
    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToMany(mappedBy = "ues", cascade = CascadeType.ALL)
    private Collection<Maquette> maquettes = new ArrayList<>();

    @OneToMany(mappedBy = "ue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<EC> ecs= new ArrayList<>();


}
