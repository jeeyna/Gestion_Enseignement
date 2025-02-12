package uasz.sn.Gestion_Enseignement.Utilisateur.dtoRestController;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Utilisateur.dto.PermanentDTO;
import uasz.sn.Gestion_Enseignement.Utilisateur.mapper.PermanentMapper;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.PermanentService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/apiDTO")
public class PermanentDTORestController {

    @Autowired
    private PermanentService permanentService;

    private PermanentMapper permanentMapper = Mappers.getMapper(PermanentMapper.class);

    @PostMapping(path = "/permanent")
    public ResponseEntity<PermanentDTO> ajouter(@RequestBody PermanentDTO permanentDTO) {
        Permanent permanent = permanentMapper.dtoToPermanent(permanentDTO);
        permanentService.ajouter(permanent);
        return ResponseEntity.status(HttpStatus.CREATED).body(permanentDTO);
    }

    @GetMapping(path = "/permanent/{id}")
    public ResponseEntity<PermanentDTO> rechercher(@PathVariable Long id) {
        Permanent permanent = permanentService.rechercher(id);
        PermanentDTO permanentDTO = permanentMapper.permanentToDTO(permanent);
        return ResponseEntity.ok(permanentDTO);
    }
    @PutMapping(path = "/permanent")
    public ResponseEntity<PermanentDTO> modifier(@RequestBody PermanentDTO permanentDTO) {
        Permanent permanent = permanentService.rechercher(permanentDTO.getId());
        permanent.setMatricule(permanent.getMatricule());
        permanent.setNom(permanentDTO.getNom());
        permanent.setPrenom(permanentDTO.getPrenom());
        permanent.setMatricule(permanentDTO.getMatricule());
        permanent.setGrade(permanentDTO.getGrade());
        permanentService.modifier(permanent);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(permanentDTO);
    }

    @DeleteMapping(path = "/permanent/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        permanentService.supprimer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = "/permanents")
    public ResponseEntity<List<PermanentDTO>> lister() {
        List<PermanentDTO> productList = permanentMapper.permanentToDTOs(permanentService.lister());
        return ResponseEntity.ok(productList);
    }
}
