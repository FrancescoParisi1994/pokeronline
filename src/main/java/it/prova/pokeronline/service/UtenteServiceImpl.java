package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.utente.UtenteRepository;
import it.prova.pokeronline.web.api.exception.UtenteNonEliminabileException;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Utente> listAllUtenti() {
		List<Utente> utentes = (List<Utente>) repository.findAllRuoli();
		for (Utente utente : utentes) {
			if (utente.getRuoli().isEmpty()) {
				throw new RuntimeException("Ruolo inesistente Service");
			}
		}
		return utentes;
	}

	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Transactional
	public Utente aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		return repository.save(utenteReloaded);
	}

	@Transactional
	public Utente inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setRegistrazione(LocalDate.now());
		return repository.save(utenteInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		Utente utente = caricaSingoloUtente(idToRemove);
		if (utente.getStato() == StatoUtente.ATTIVO) {
			throw new UtenteNonEliminabileException("Questo utente non puo' essere ancora eliminato");
		}
		repository.deleteById(idToRemove);

	}

	public List<Utente> findByExample(Utente example) {
		// TODO Da implementare
		return listAllUtenti();
	}

	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public void changeUserAbilitation(Long id) {
		Utente utenteInstance = caricaSingoloUtente(id);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		repository.save(utenteInstance);
	}
	
	@Transactional
	public void changeUserAbilitation(String username) {
		Utente utenteInstance = findByUsername(username);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");
		
		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		repository.save(utenteInstance);
	}

	@Transactional(readOnly = true)
	public Utente findByUsername(String username) {
		Utente utente = repository.findByUsername(username).orElse(null);
		return utente;
	}

	@Transactional
	public void abilita(Utente utente) {
		Utente utenteDaAbilitare = findByUsername(utente.getUsername());
		if (utenteDaAbilitare == null) {
			throw new RuntimeException("Elemento non trovato.");
		}
		utenteDaAbilitare.setRuoli(utente.getRuoli());
		utenteDaAbilitare.setStato(StatoUtente.ATTIVO);
		repository.save(utenteDaAbilitare);
	}
}
