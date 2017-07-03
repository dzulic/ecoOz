/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Julija
 */
@Entity
@Table(name = "sluzba_transporta")
public class SluzbaTransporta implements OpstiDomenskiObjekat {

    @Column
    @JsonProperty
    private String naziv;
    @Column
    @JsonProperty
    private String sifra;
    @Column
    @JsonProperty
    private String PTT;
    @JsonProperty
    @Id
    private int PIB;

    public SluzbaTransporta() {
    }

    public SluzbaTransporta(String json) {
        Gson g = new Gson();
        SluzbaTransporta s = g.fromJson(json, SluzbaTransporta.class);
        this.PIB = Integer.parseInt(g.fromJson(json, JsonObject.class).get("PIB").toString());
        this.naziv = s.getNaziv();
        this.PTT = s.getPTT();
        this.sifra = s.getSifra();
    }

    public SluzbaTransporta(String naziv, String sifra, String PTT, int PIB) {
        this.naziv = naziv;
        this.sifra = sifra;
        this.PTT = PTT;
        this.PIB = PIB;
    }

    public int getPIB() {
        return PIB;
    }

    public void setPIB(int PIB) {
        this.PIB = PIB;
    }

    public void setPIB(Integer PIB) {
        this.PIB = PIB;
    }

    public void setPIB(String PIB) {
        this.PIB = Integer.parseInt(PIB);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPTT() {
        return PTT;
    }

    public void setPTT(String PTT) {
        this.PTT = PTT;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.PIB ^ (this.PIB >>> 32));
        hash = 53 * hash + Objects.hashCode(this.naziv);
        hash = 53 * hash + Objects.hashCode(this.PTT);
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
        final SluzbaTransporta other = (SluzbaTransporta) obj;
        if (this.PIB != other.PIB) {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        if (!Objects.equals(this.PTT, other.PTT)) {
            return false;
        }
        return true;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String pass) {
        this.sifra = pass;
    }

    @Override
    public String toString() {
        return "SluzbaTransporta{naziv='" + naziv + "', PTT='" + PTT + "', sifra=" + sifra + "', PIB=" + PIB + "}";
    }

}
