/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "zaduzenja")
public class Zaduzenja implements OpstiDomenskiObjekat {

    @Id
    private int zaduzenjaID;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    @JoinColumn
    @ManyToOne
    private SluzbaTransporta sluzba;
    @JoinColumn
    @ManyToOne
    Zahtev zahtev;
    public boolean checked;
    @Column
    public boolean stanje;

    public boolean isStanje() {
        return stanje;
    }

    public void setStanje(boolean stanje) {
        this.stanje = stanje;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getZaduzenjaID() {
        return zaduzenjaID;
    }

    public void setZaduzenjaID(int zaduzenjaID) {
        this.zaduzenjaID = zaduzenjaID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Zahtev getZahtev() {
        return zahtev;
    }

    public void setZahtev(Zahtev zahtev) {
        this.zahtev = zahtev;
    }

    public Zaduzenja() {
    }

    public Zaduzenja(int zaduzenjaID, Date datum, SluzbaTransporta sluzba) {
        this.zaduzenjaID = zaduzenjaID;
        this.datum = datum;
        this.sluzba = sluzba;
    }

    public Zaduzenja(String json) {
        Gson g = new Gson();
        Zaduzenja z = g.fromJson(json, Zaduzenja.class);
        this.zaduzenjaID = z.getZaduzenjaID();
        this.datum = z.getDatum();
        this.sluzba = z.getSluzba();
        this.zahtev = z.getZahtev();
    }

    public Zaduzenja(int zaduzenjaID, Date datum, SluzbaTransporta sluzba, Zahtev zahtev) {
        this.zaduzenjaID = zaduzenjaID;
        this.datum = datum;
        this.sluzba = sluzba;
        this.zahtev = zahtev;
    }

    public SluzbaTransporta getSluzba() {
        return sluzba;
    }

    public void setSluzba(SluzbaTransporta sluzba) {
        this.sluzba = sluzba;
    }

}
