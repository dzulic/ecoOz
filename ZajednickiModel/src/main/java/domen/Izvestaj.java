/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import java.util.ArrayList;
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

/**
 *
 * @author Julija
 */
@JsonIgnoreProperties
@Entity
@Table(name = "izvestaj")
public class Izvestaj implements OpstiDomenskiObjekat {

    @Id
    public int id;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date datum;
    @ManyToOne
    @JoinColumn
    SluzbaTransporta sluzba;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "izvestaj", cascade = CascadeType.ALL)
    public List<StavkaIzvestaja> listaStavki;

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Izvestaj() {
    }

    public Izvestaj(String json) {
        Gson g = new Gson();
        Izvestaj i = g.fromJson(json, Izvestaj.class);
        this.id = i.getID();
        this.datum = i.getDatum();
        this.sluzba = i.getSluzba();
        this.listaStavki = i.getListaStavki();
    }

    public Izvestaj(int ID, Date datum, SluzbaTransporta sluzba) {
        this.id = ID;
        this.datum = datum;
        this.sluzba = sluzba;
        listaStavki = new ArrayList<>();
    }

    public SluzbaTransporta getSluzba() {
        return sluzba;
    }

    public void setSluzba(SluzbaTransporta sluzba) {
        this.sluzba = sluzba;
    }

    public List<StavkaIzvestaja> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(List<StavkaIzvestaja> listaStavki) {
        this.listaStavki = listaStavki;
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
        final Izvestaj other = (Izvestaj) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.sluzba != other.sluzba) {
            return false;
        }
        return Objects.equals(this.datum, other.datum);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.datum);
        hash = 79 * hash + Objects.hashCode(this.sluzba);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
