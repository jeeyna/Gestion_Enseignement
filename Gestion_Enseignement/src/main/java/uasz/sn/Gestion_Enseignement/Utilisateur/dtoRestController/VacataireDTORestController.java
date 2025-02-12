package uasz.sn.Gestion_Enseignement.Utilisateur.dtoRestController;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Utilisateur.dto.VacataireDTO;
import uasz.sn.Gestion_Enseignement.Utilisateur.mapper.VacataireMapper;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.VacataireService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/apiDTO")
public class VacataireDTORestController {

    @Autowired
    private VacataireService vacataireService;

    private VacataireMapper vacataireMapper = Mappers.getMapper(VacataireMapper.class);

    @PostMapping(path = "/vacataire")
    public ResponseEntity<VacataireDTO> ajouter(@RequestBody VacataireDTO vacataireDTO) {
        Vacataire vacataire = vacataireMapper.dtoToVacataire(vacataireDTO);
        vacataireService.ajouter(vacataire);
        return ResponseEntity.status(HttpStatus.CREATED).body(vacataireDTO);
    }

    @GetMapping(path = "/vacataire/{id}")
    public ResponseEntity<VacataireDTO> rechercher(@PathVariable Long id) {
        Vacataire vacataire = vacataireService.rechercher(id);
        VacataireDTO vacataireDTO = vacataireMapper.vacataireToDTO(vacataire);
        return ResponseEntity.ok(vacataireDTO);
    }

    @PutMapping(path = "/vacataire/{id}")
    public ResponseEntity<VacataireDTO> modifier(@PathVariable Long id, @RequestBody VacataireDTO vacataireDTO) {
        Vacataire vacataire = vacataireService.rechercher(id);
        vacataire.setUsername(vacataireDTO.getUsername());
        vacataire.setNom(vacataireDTO.getNom());
        vacataire.setPrenom(vacataireDTO.getPrenom());
        vacataire.setNiveau(vacataireDTO.getNiveau());
        vacataireService.modifier(vacataire);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(vacataireDTO);
    }

    @DeleteMapping(path = "/vacataire/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        vacataireService.supprimer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = "/vacataires")
    public ResponseEntity<List<VacataireDTO>> lister() {
        List<VacataireDTO> productList = vacataireMapper.vacataireToDTOs(vacataireService.lister());
        return ResponseEntity.ok(productList);
    }
}
