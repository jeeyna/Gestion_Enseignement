package uasz.sn.Gestion_Enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.repository.ECRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.UERepository;

import java.util.List;

@Service
@Transactional
public class ECservice {
    @Autowired
    private ECRepository ecRepository;

    public void ajouterEC(EC ec) {ecRepository.save(ec);}

    public List<EC> afficherToutEC() {
        return ecRepository.findAll();
    }

    public EC afficherEC(Long id) {
        return ecRepository.getById(id);
    }

    public void modifierEC(EC ec) {
        EC ecUpdate = ecRepository.getById(ec.getId());
        ecUpdate.setCode(ec.getCode());
        ecUpdate.setLibelle(ec.getLibelle());
        ecUpdate.setCm(ec.getCm());
        ecUpdate.setTd(ec.getTd());
        ecUpdate.setTp(ec.getTp());
        ecUpdate.setTpe(ec.getTpe());
        ecUpdate.setCoefficient(ec.getCoefficient());
        ecUpdate.setDescription(ec.getDescription());
        ecUpdate.setUe(ec.getUe());
        ecRepository.save(ecUpdate);
    }
    public void supprimerEC(EC ec) { ecRepository.delete(ec);}

    public void activerEC (Long id){
        EC ec = ecRepository.findById(id).get();
        if (ec.isActive()) {ec.setActive(false);}
        else {ec.setActive(true);}
        ecRepository.save(ec);
    }
    public void archiverEC(Long id){
        EC ec= ecRepository.findById(id).get();
        if (ec.isArchive()) {ec.setArchive(false);}
        else {ec.setArchive(true);}
        ecRepository.save(ec);
    }

}
