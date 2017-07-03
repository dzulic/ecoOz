/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.google.gson.Gson;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Julija
 */
@Entity
@Table(name = "stavka_zahteva")
public class StavkaZahteva implements OpstiDomenskiObjekat {

    @ManyToOne
    @JoinColumn
    public Zahtev zahtev;
    @Id
    public int redniBroj;
    @Column
    public double kolicina;
    @Column
    public String materijal;

    public Zahtev getZahtev() {
        return zahtev;
    }

    public void setZahtev(Zahtev zahtev) {
        this.zahtev = zahtev;
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

    public String getMaterijal() {
        return materijal;
    }

    public void setMaterijal(String materijal) {
        this.materijal = materijal;
    }

    public StavkaZahteva(Zahtev zahtev, int redniBroj, double kolicina, String materijal) {
        this.zahtev = zahtev;
        this.redniBroj = redniBroj;
        this.kolicina = kolicina;
        this.materijal = materijal;
    }

    public StavkaZahteva(String json) {
        Gson g = new Gson();
        StavkaZahteva sz = g.fromJson(json, StavkaZahteva.class);
        this.zahtev = sz.getZahtev();
        this.redniBroj = sz.getRedniBroj();
        this.kolicina = sz.getKolicina();
        this.materijal = sz.getMaterijal();
    }

    public StavkaZahteva() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.zahtev);
        hash = 89 * hash + this.redniBroj;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.kolicina) ^ (Double.doubleToLongBits(this.kolicina) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.materijal);
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
        final StavkaZahteva other = (StavkaZahteva) obj;
        if (this.redniBroj != other.redniBroj) {
            return false;
        }
        if (Double.doubleToLongBits(this.kolicina) != Double.doubleToLongBits(other.kolicina)) {
            return false;
        }
        if (!Objects.equals(this.materijal, other.materijal)) {
            return false;
        }
        if (!Objects.equals(this.zahtev, other.zahtev)) {
            return false;
        }
        return true;
    }

}
