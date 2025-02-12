package uasz.sn.Gestion_Enseignement.Maquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private boolean archive;

@OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
private Collection<Classe> classes= new ArrayList<>();

}