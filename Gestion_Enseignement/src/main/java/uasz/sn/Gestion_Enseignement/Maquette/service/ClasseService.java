package uasz.sn.Gestion_Enseignement.Maquette.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.Maquette.repository.ClasseRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.MaquetteRepository;

@Service
@Transactional
@Slf4j
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private MaquetteRepository maquetteRepository;

    // Ajouter une nouvelle classe
    public Classe ajouterClasse(Classe classe) {
        // Enregistrer la Classe en base avant d'ajouter les Maquettes
        classeRepository.save(classe);

        if (classe.getNiveau() == 1) {
            maquetteRepository.save(new Maquette(null, classe, 1, null));
            maquetteRepository.save(new Maquette(null, classe, 2, null));
        } else if (classe.getNiveau() == 2) {
            maquetteRepository.save(new Maquette(null, classe, 3, null));
            maquetteRepository.save(new Maquette(null, classe, 4, null));
        } else if (classe.getNiveau() == 3) {
            maquetteRepository.save(new Maquette(null, classe, 5, null));
            maquetteRepository.save(new Maquette(null, classe, 6, null));
        }

        return classeRepository.save(classe);
    }

    // Modifier une classe existante
    public Classe modifierClasse(Long id, Classe classeDetails) {
        Optional<Classe> classeOptional = classeRepository.findById(id);
        if (classeOptional.isPresent()) {
            Classe classe = classeOptional.get();
            classe.setNom(classeDetails.getNom());
            classe.setNiveau(classeDetails.getNiveau());
            return classeRepository.save(classe);
        }
        return null;
    }


    // Archiver (supprimer) une classe par ID
    public void archiverClasse(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable avec l'id : " + id));
        classe.setArchive(true);
        classeRepository.save(classe);
    }


    // Afficher toutes les classes
    public List<Classe> afficherToutesLesClasses() {
        return classeRepository.findAll();
    }

    // Afficher toutes les classes non archivées
    public List<Classe> afficherClassesNonArchivees() {
        return classeRepository.findByArchiveFalse();
    }

    // Afficher toutes les classes archivées
    public List<Classe> afficherClassesArchivees() {
        return classeRepository.findByArchiveTrue();
    }

    public Classe touverParId (Long id){
        return classeRepository.findById(id).get();
    }

    public List<Classe> listerClassesNonArchivesFormation(Formation f) {
        List<Classe> classes = classeRepository.findByFormation(f);
        List<Classe> classesNonArchives = new ArrayList<>();

        for (Classe classe : classes) {
            if(!classe.isArchive())
                classesNonArchives.add(classe);
        }

        return classesNonArchives;
    }




}

