package uasz.sn.Gestion_Enseignement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Maquette.modele.*;
import uasz.sn.Gestion_Enseignement.Maquette.repository.*;
import uasz.sn.Gestion_Enseignement.Maquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Maquette.service.FormationService;
import uasz.sn.Gestion_Enseignement.Maquette.service.MaquetteService;
import uasz.sn.Gestion_Enseignement.Maquette.service.UEService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GestionEnseignementApplication implements CommandLineRunner {
	@Autowired
	private  UtilisateurService utilisateurService;

	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private  EnseignantService enseignantService;

	@Autowired
	private FormationService formationService;
    @Autowired
    private MaquetteService maquetteService;
    @Autowired
    private ClasseService classeService;
	@Autowired
	private UEService ueService;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private MaquetteRepository maquetteRepository;

	@Autowired
	private UERepository ueRepository;

	@Autowired
	private ECRepository ecRepository;


	public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }




	public static void main(String[] args) {
		SpringApplication.run(GestionEnseignementApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
//		Role permanent = utilisateurService.ajouter_Role(new Role("Permanent"));
//		Role vacataire = utilisateurService.ajouter_Role(new Role("Vacataire"));
//		String password = passwordEncoder().encode("Passer123");
//
//		Permanent user_1 = new Permanent();
//		user_1.setNom("DIOP");
//		user_1.setPrenom("Ibrahima");
//		user_1.setUsername("idiop@uasz.sn");
//		user_1.setPassword(password);
//		user_1.setDateCreation(new Date());
//		user_1.setActive(true);
//		user_1.setSpecialite("Web Semantique");
//		user_1.setMatricule("ID2024");
//		user_1.setGrade("Professeur");
//		enseignantService.ajouter(user_1);
//		utilisateurService.ajouter_UtilisateurRoles(user_1, permanent);
//
//		Vacataire user_2 = new Vacataire();
//		user_2.setNom("MALACK");
//		user_2.setPrenom("Camir");
//		user_2.setUsername("cmalack@uasz.sn");
//		user_2.setPassword(password);
//		user_2.setDateCreation(new Date());
//		user_2.setActive(true);
//		user_2.setSpecialite("Ingénierie de Connaissances");
//		user_2.setNiveau("Doctorant");
//		enseignantService.ajouter(user_2);
//		utilisateurService.ajouter_UtilisateurRoles(user_2, vacataire);
//
//		Role chefDepartement = utilisateurService.ajouter_Role(new Role("ChefDepartement"));
//		Permanent user = new Permanent();
//		user.setNom("DIAGNE");
//		user.setPrenom("Serigne");
//		user.setUsername("sdiagne@uasz.sn");
//		user.setPassword(password);
//		user.setDateCreation(new Date());
//		user.setActive(true);
//		user.setSpecialite("Base de donnees");
//		user.setMatricule("SD2024");
//		user.setGrade("Professeur");
//		enseignantService.ajouter(user);
//		utilisateurService.ajouter_UtilisateurRoles(user, chefDepartement);
	}
}

