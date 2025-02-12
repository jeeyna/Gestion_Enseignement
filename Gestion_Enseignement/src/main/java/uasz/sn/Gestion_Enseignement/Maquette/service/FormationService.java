package uasz.sn.Gestion_Enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.repository.ClasseRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.ECRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.FormationRepository;

import java.text.Normalizer;
import java.util.List;

@Service
@Transactional
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ClasseRepository classeRepository;

    public Formation ajouter(Formation formation) {
        return formationRepository.save(formation);
    }

    public List<Formation> lister() {return formationRepository.findAll();
    }

    public Formation afficherFormation(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable avec l'id : " + id));
    }


    public Formation rechercher(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable avec l'id : " + id));
    }

    @Transactional
    public Formation modifier(Formation formation) {
        Formation formationUpdate = formationRepository.getById(formation.getId());
        formationUpdate.setNom(formation.getNom());
        return formationRepository.save(formationUpdate);
    }

    @Transactional
    public void archiver(Long id){
        Formation formation = formationRepository.findById(id).get();
        if (formation.isArchive()) {
            formation.setArchive(false);
        }
        else {
            formation.setArchive(true);
        }
        formationRepository.save(formation);

    }

    public List<Classe> afficherLesClasses(Long formationId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable avec l'id : " + formationId));
        return classeRepository.findByFormation(formation);
    }

    @Transactional
    public Formation ajouterClasseFormation(Long formationId, Classe classe) {
        // Récupérer la formation par son ID
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable avec l'id : " + formationId));

        classe.setFormation(formation);
        classeRepository.save(classe);

        formation.getClasses().add(classe);
        return formationRepository.save(formation);
    }
}
