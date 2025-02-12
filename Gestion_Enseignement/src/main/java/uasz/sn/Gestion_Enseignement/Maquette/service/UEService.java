package uasz.sn.Gestion_Enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.UERepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UEService {
    @Autowired
    private UERepository ueRepository;

    public void ajouterUE(UE ue) {ueRepository.save(ue);}

    public List<UE> afficherToutUE() {
        return ueRepository.findAll();
    }

    public UE afficherUE(Long id) {
        return ueRepository.getById(id);
    }

    public void modifierUE(UE ue) {
        UE ueUpdate = ueRepository.getById(ue.getId());
        ueUpdate.setCode(ue.getCode());
        ueUpdate.setLibelle(ue.getLibelle());
        ueUpdate.setCredit(ue.getCredit());
        ueUpdate.setCoefficient(ue.getCoefficient());
        ueUpdate.setDescription(ue.getDescription());
        ueRepository.save(ueUpdate);
    }

    public void supprimerUE(UE ue) {
        ueRepository.delete(ue);
    }

    public List<EC> afficherLesEC(UE ue ) {
        return ueRepository.findByUE(ue);
    }

    public List<EC> getEcsByID(Long ueId) {
        return ueRepository.findEcsByUeId(ueId);
    }

    public void activer (Long id){
        UE ue = ueRepository.findById(id).get();
        if (ue.isActive()) {ue.setActive(false);}
        else {ue.setActive(true);}
        ueRepository.save(ue);
    }
    public void archiver(Long id){
        UE ue= ueRepository.findById(id).get();
        if (ue.isArchive()) {ue.setArchive(false);}
        else {ue.setArchive(true);}
        ueRepository.save(ue);
    }

    public UE ajouterEcUe(Long ueId, EC ec) {
        if (ueId == null) {
            throw new IllegalArgumentException("L'identifiant de l'UE (ueId) ne peut pas être nul.");
        }

        Optional<UE> optionalUe = ueRepository.findById(ueId);
        if (optionalUe.isEmpty()) {
            throw new IllegalArgumentException("UE avec l'ID " + ueId + " introuvable.");
        }

        UE ue = optionalUe.get();
        ec.setUe(ue);
        ue.getEcs().add(ec);
        return ueRepository.save(ue);
    }

}
