package uasz.sn.Gestion_Enseignement.Utilisateur.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.PermanentService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")

public class PermanentRestController {

    @Autowired
    private PermanentService permanentService;

    @GetMapping(path = "/permanents")
    public List Lister_Permanent(){
        return permanentService.lister();
    }

    @PostMapping(path = "/permanent")
    public Permanent ajouter_Permanent(@RequestBody Permanent permanent){
        return permanentService.ajouter(permanent);
    }

    @GetMapping(path = "/permanent/{id}")
    public Permanent rechercher_Permanent(@PathVariable Long id){
        return permanentService.rechercher(id);
    }

    @PutMapping(path = "/permanent")
    public Permanent modifier_Permanent(@RequestBody Permanent permanent){
        return permanentService.modifier(permanent);
    }

    @DeleteMapping(path = "/permanent/{id}")
    public String supprimer_Permanent(@PathVariable Long id){
        permanentService.supprimer(id);
        return "Enseignant supprimé avec succés";
    }

}
