/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Julija
 */
@JsonIgnoreProperties
@Entity
@Table(name = "stavka_izvestaja")
public class StavkaIzvestaja implements OpstiDomenskiObjekat {

    @Id
    private int redniBroj;
    @Column
    private double kolicina;
    @Column
    String materijal;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    @ManyToOne
    @JoinColumn
    Korisnik korisnik;
    @ManyToOne
    @JsonIgnore
    private Izvestaj izvestaj;

    public Izvestaj getIzvestaj() {
        return izvestaj;
    }

    public void setIzvestaj(Izvestaj izvestaj) {
        this.izvestaj = izvestaj;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getMaterijal() {
        return materijal;
    }

    public void setMaterijal(String materijal) {
        this.materijal = materijal;
    }

    public StavkaIzvestaja() {
    }

    public StavkaIzvestaja(Izvestaj izvestaj, int redniBroj, Date datum, double kolicina, String materijal, Korisnik korisnik) {
        this.izvestaj = izvestaj;
        this.redniBroj = redniBroj;
        this.kolicina = kolicina;
        this.korisnik = korisnik;
        this.datum = datum;
        this.materijal = materijal;
    }

    public StavkaIzvestaja(String json) {
        Gson g = new Gson();
        StavkaIzvestaja si = g.fromJson(json, StavkaIzvestaja.class);
        this.izvestaj = si.getIzvestaj();
        this.redniBroj = si.getRedniBroj();
        this.kolicina = si.getKolicina();
        this.korisnik = si.getKorisnik();
        this.materijal = si.getMaterijal();
        this.datum = si.getDatum();
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
