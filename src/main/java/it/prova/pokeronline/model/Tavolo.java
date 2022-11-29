package it.prova.pokeronline.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {
//	Tavolo (sarebbe la partita) coi campi esperienzaMin (cioè il minimo
//			dell’esperienzaAccumulata che gli utenti devono possedere per poter giocare a quel
//			tavolo), cifraMinima (il minimo valore di denaro che si deve possedere per giocare a quel
//			tavolo), denominazione ...., data creazione.....
//			Set di Utente (i giocatori) ed un Utente utenteCreazione che è colui che ha creato il tavolo.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienzaMin")
	private Integer esperienzaMin;
	@Column(name = "cifraMinima")
	private Integer cifraMinima;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dataCreazione")
	private LocalDate dataCreazione;

	@OneToMany
	@JoinColumn(name = "giocatori_id")
	private Set<Utente> giocatori = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utenteCreazione;

	public Tavolo() {
		super();
	}

	public Tavolo(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, LocalDate dataCreazione,
			Set<Utente> giocatori, Utente utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.giocatori = giocatori;
		this.utenteCreazione = utenteCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(Integer esperienzaMin) {
		this.esperienzaMin = esperienzaMin;
	}

	public Integer getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Integer cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

}
