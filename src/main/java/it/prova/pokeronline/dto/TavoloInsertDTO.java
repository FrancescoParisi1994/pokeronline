package it.prova.pokeronline.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloInsertDTO {

	private Long id;
	@NotNull(message = "{}")
	private Integer esperienzaMin;
	@NotNull(message = "{}")
	private Integer cifraMinima;
	@NotBlank(message = "{}")
	private String denominazione;

	private Set<Utente> giocatori = new HashSet<>();

	public TavoloInsertDTO() {
		super();
	}

	public TavoloInsertDTO(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione,
			Set<Utente> giocatori) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.giocatori = giocatori;
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

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public Tavolo buildModelFromDTO() {
		Tavolo result = new Tavolo(this.id, this.esperienzaMin, this.cifraMinima, this.denominazione, this.giocatori);
		return result;
	}

}
