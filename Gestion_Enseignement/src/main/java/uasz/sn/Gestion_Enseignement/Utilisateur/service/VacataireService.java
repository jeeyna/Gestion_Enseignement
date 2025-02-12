package uasz.sn.Gestion_Enseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.VacataireRepository;

import java.util.List;

@Service
@Transactional
public class VacataireService {
    @Autowired
    private VacataireRepository vacataireRepository;
    public Vacataire ajouter(Vacataire vacataire) {return vacataireRepository.save(vacataire);}
    public List<Vacataire> lister() {return vacataireRepository.findAll();}
    public Vacataire rechercher(Long id){return vacataireRepository.findById(id).get();}
    public Vacataire modifier (Vacataire vacataire) {return vacataireRepository.save(vacataire);}
    public void supprimer (Long id){vacataireRepository.deleteById(id);}

}
