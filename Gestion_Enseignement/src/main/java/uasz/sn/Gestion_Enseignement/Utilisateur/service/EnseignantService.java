package uasz.sn.Gestion_Enseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;

import java.util.List;

@Service
@Transactional
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ChoixRepository choixRepository;



    public Enseignant ajouter(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public List<Enseignant> lister() {
        return enseignantRepository.findAll();
    }

    public Enseignant rechercher(Long id) {return enseignantRepository.findById(id).get();}

    public Enseignant modifier(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public void supprimer(Long id) {
        enseignantRepository.deleteById(id);
    }

    public void activer (Long id){
        Enseignant enseignant = enseignantRepository.findById(id).get();
        if (enseignant.isActive()) {enseignant.setActive(false);}
        else {enseignant.setActive(true);}
        enseignantRepository.save(enseignant);
    }

    public void archiver(Long id){
        Enseignant enseignant = enseignantRepository.findById(id).get();
        if (enseignant.isArchive()) {enseignant.setArchive(false);}
        else {enseignant.setArchive(true);}
        enseignantRepository.save(enseignant);
    }
    public Enseignant findByUsername(String username) {
        return enseignantRepository.findByUsername(username);  // Assurez-vous d'avoir cette méthode dans votre repository
    }
}
