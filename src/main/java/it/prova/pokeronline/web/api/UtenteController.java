package it.prova.pokeronline.web.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.UtenteAbilitaDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.dto.UtenteInsertDTO;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.UtenteNonEliminabileException;
import it.prova.pokeronline.web.api.exception.UtenteNotFoundExcepition;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private TavoloService tavoloService;

	// questa mi serve solo per capire se solo ADMIN vi ha accesso
	@GetMapping("/testSoloAdmin")
	public String test() {
		return "OK";
	}

	@GetMapping(value = "/userInfo")
	public ResponseEntity<UtenteInfoJWTResponseDTO> getUserInfo() {

		// se sono qui significa che sono autenticato quindi devo estrarre le info dal
		// contesto
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		List<String> ruoli = utenteLoggato.getRuoli().stream().map(item -> item.getCodice())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UtenteInfoJWTResponseDTO(utenteLoggato.getNome(), utenteLoggato.getCognome(),
				utenteLoggato.getUsername(), ruoli));
	}

	@GetMapping
	public List<UtenteDTO> findAll() {
		return UtenteDTO.buildDTOListFromModelList(utenteService.listAllUtenti());
	}

	@GetMapping("/{username}")
	public UtenteDTO findById(@PathVariable(required = true) String username) {
		return UtenteDTO.buildUtenteDTOFromModel(utenteService.findByUsername(username));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UtenteDTO insert(@Valid @RequestBody UtenteInsertDTO utenteInsertDTO) {
		return UtenteDTO.buildUtenteDTOFromModel(utenteService.inserisciNuovo(utenteInsertDTO.buildDTOFromModel()));
	}

	@PutMapping
	public UtenteDTO update(@Valid @RequestBody UtenteDTO utenteDTO) {
		Utente utente = utenteService.caricaSingoloUtente(utenteDTO.getId());
		if (utente == null) {
			throw new UtenteNotFoundExcepition("Elemento non trovato");
		}
		return UtenteDTO.buildUtenteDTOFromModel(utenteDTO.buildUtenteModel(false));
	}

	@DeleteMapping("/{username}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) String username) {
		Utente utente = utenteService.findByUsername(username);
		if (utente.getStato() == StatoUtente.ATTIVO) {
			throw new UtenteNonEliminabileException("Utente non eliminabile");
		}
		utenteService.rimuovi(utente.getId());
	}

	@PutMapping("/cambioStato/{username}")
	public void cambioStato(@PathVariable(required = true) String username) {
		utenteService.changeUserAbilitation(username);
	}
	
	@PostMapping("/abilita")
	public void abilita(@Valid @RequestBody UtenteAbilitaDTO utenteAbilitaDTO) {
		utenteService.abilita(utenteAbilitaDTO.buildModelFromDto());
	}
	
	@GetMapping("/tavolo")
	public List<Tavolo> findAllTavoli(){
		return tavoloService.findAll();
	}
	
	@GetMapping("/tavolo/{id}")
	public Tavolo findById(@PathVariable(required = true) Long id){
		return tavoloService.findById(id);
	}
	
	@DeleteMapping("/tavolo/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true)Long id) {
		tavoloService.delete(id);
	}
}
