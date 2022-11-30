package it.prova.pokeronline.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {
//	Utente (che è anche il giocatore) coi campi classici (nome, cognome, username, password,
//			data registrazione, stato, esperienzaAccumulata (indica una specie di punteggio attribuito
//			dal sistema per cercare di livellare le abilità dei giocatori e far quindi disputare partite alla
//			pari), creditoAccumulato (in pratica il credito accumulato in termini di soldi
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username",unique = true)
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "registrazione")
	private LocalDate registrazione;
	@Enumerated(EnumType.STRING)
	private StatoUtente stato;
	@Column(name = "esperienzaAccumulata")
	private Integer esperienzaAccumulata;
	@Column(name = "creditoAccumulato")
	private Integer creditoAccumulato;

	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "ID"))
	private Set<Ruolo> ruoli = new HashSet<>(0);

	public Utente() {
		super();
	}

	public Utente(String username) {
		super();
		this.username = username;
	}

	public Utente(String username, String password, String nome, String cognome, LocalDate registrazione,
			Integer esperienzaAccumulata, Integer creditoAccumulato) {
		super();
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.registrazione = registrazione;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
	}

	public Utente(String username, String password, String nome, String cognome, Integer esperienzaAccumulata,
			Integer creditoAccumulato) {
		super();
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
	}

	public Utente(Long id, String username, String password, String nome, String cognome, LocalDate registrazione,
			StatoUtente stato, Integer esperienzaAccumulata, Integer creditoAccumulato, Set<Ruolo> ruoli) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.registrazione = registrazione;
		this.stato = stato;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
		this.ruoli = ruoli;
	}

	public Utente(String username, String nome, String cognome, LocalDate registrazione, StatoUtente stato,
			Integer esperienzaAccumulata, Integer creditoAccumulato) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.registrazione = registrazione;
		this.stato = stato;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
	}

	public Utente(String username, String nome, String cognome, LocalDate registrazione, StatoUtente stato,
			Integer esperienzaAccumulata, Integer creditoAccumulato, Set<Ruolo> ruoli) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.registrazione = registrazione;
		this.stato = stato;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
		this.ruoli = ruoli;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getRegistrazione() {
		return registrazione;
	}

	public void setRegistrazione(LocalDate registrazione) {
		this.registrazione = registrazione;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public Integer getEsperienzaAccumulata() {
		return esperienzaAccumulata;
	}

	public void setEsperienzaAccumulata(Integer esperienzaAccumulata) {
		this.esperienzaAccumulata = esperienzaAccumulata;
	}

	public Integer getCreditoAccumulato() {
		return creditoAccumulato;
	}

	public void setCreditoAccumulato(Integer creditoAccumulato) {
		this.creditoAccumulato = creditoAccumulato;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public boolean isAdmin() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_ADMIN))
				return true;
		}
		return false;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}
}
