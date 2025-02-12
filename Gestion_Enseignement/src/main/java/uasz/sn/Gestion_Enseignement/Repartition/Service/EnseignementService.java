package uasz.sn.Gestion_Enseignement.Repartition.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.List;
@Service
public class EnseignementService {
    @Autowired
    private EnseignementRepository enseignementRepository;

    public List<Enseignement> trouverChefDepartement(Enseignant chefDepartement) {
        return enseignementRepository.findByChefDepartement(chefDepartement);
    }

    public List<Enseignement> listerEnseignementsDisponibles() {
        return enseignementRepository.findByDisponible(true);
    }

    public Enseignement trouverEnseignementParId(long id) {
        return enseignementRepository.findById(id).orElse(null);
    }
}
