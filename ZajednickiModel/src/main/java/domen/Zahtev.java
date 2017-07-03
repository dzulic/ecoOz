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
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Julija
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "zahtev")
public class Zahtev implements OpstiDomenskiObjekat {

    @Id
    public int zahtevID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    @Temporal(TemporalType.DATE)
    public Date datum;
    @ManyToOne
    @JoinColumn
    public Korisnik korisnik;
    @Column
    double ukupno = 0;
    @OneToMany(mappedBy = "zahtev")
    @JsonIgnore
    List<StavkaZahteva> listaStavki;

    public int getZahtevID() {
        return zahtevID;
    }

    public void setZahtevID(int zahtevID) {
        this.zahtevID = zahtevID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Zahtev(int zahtevID, Date datum, Korisnik korisnik, double ukupno) {
        this.zahtevID = zahtevID;
        this.ukupno = ukupno;
        this.datum = datum;
        this.korisnik = korisnik;
    }

    public Zahtev() {
    }

    public Zahtev(String json) {
        Gson g = new Gson();
        Zahtev z = g.fromJson(json, Zahtev.class);
        this.zahtevID = z.getZahtevID();
        this.datum = z.getDatum();
        this.korisnik = z.getKorisnik();
        this.listaStavki = z.getListaStavki();
        this.ukupno = z.getUkupno();

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.zahtevID;
        hash = 17 * hash + Objects.hashCode(this.datum);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zahtev other = (Zahtev) obj;
        if (this.zahtevID != other.zahtevID) {
            return false;
        }
        if (!Objects.equals(this.datum, other.datum)) {
            return false;
        }
        return true;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<StavkaZahteva> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(List<StavkaZahteva> listaStavki) {
        this.listaStavki = listaStavki;
    }

    @Override
    public String toString() {
        return "Zahtev{" + "zahtevID=" + zahtevID + ", datum=" + datum + ", korisnik=" + korisnik + ", listaStavki=" + listaStavki + '}';
    }

    public double getUkupno() {
        return ukupno;
    }

    public void setUkupno(double ukupno) {
        this.ukupno = ukupno;
    }

}
