/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Julija
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "korisnik")
public class Korisnik implements OpstiDomenskiObjekat {

    @Id
    private long brLicne;
    @Column
    private String ime;
    @Column
    private String prezime;
    @Column
    private String user;
    @Column
    private String pass;
    @Column
    private String email;
    @Column(name = "ulica")
    private String ulica;
    @Column(name = "broj")
    private String brKuce;
    @Column
    private String grad;

    public Korisnik() {
    }

    public Korisnik(String json) {
        Gson g = new Gson();
        Korisnik k = g.fromJson(json, Korisnik.class);
        this.ime = k.getIme();
        this.prezime = k.getPrezime();
        this.user = k.getUser();
        this.pass = k.getPass();
        this.email = k.getEmail();
        this.brKuce = k.getBrKuce();
        this.ulica = k.getUlica();
        this.grad = k.getGrad();
        this.brLicne = k.getBrLicne();

    }

    public Korisnik(String ime, String prezime, String user, String pass, String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.user = user;
        this.pass = pass;
        this.email = email;
    }

    public Korisnik(String ime, String prezime, String user, String pass, long brLicne, String email, String ulica, String brKuce, String grad) {
        this.ime = ime;
        this.prezime = prezime;
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.brKuce = brKuce;
        this.ulica = ulica;
        this.grad = grad;
        this.brLicne = brLicne;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBrKuce() {
        return brKuce;
    }

    public void setBrKuce(String brKuce) {
        this.brKuce = brKuce;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.ime);
        hash = 29 * hash + Objects.hashCode(this.prezime);
        hash = 29 * hash + Objects.hashCode(this.user);
        hash = 29 * hash + Objects.hashCode(this.pass);
        hash = 29 * hash + Objects.hashCode(this.brLicne);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.ulica);
        hash = 29 * hash + Objects.hashCode(this.brKuce);
        hash = 29 * hash + Objects.hashCode(this.grad);
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
        final Korisnik other = (Korisnik) obj;
        if (!Objects.equals(this.brLicne, other.brLicne)) {
            return false;
        }
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.pass, other.pass)) {
            return false;
        }

        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.ulica, other.ulica)) {
            return false;
        }
        if (!Objects.equals(this.brKuce, other.brKuce)) {
            return false;
        }
        return true;
    }

    public long getBrLicne() {
        return brLicne;
    }

    public void setBrLicne(long brLicne) {
        this.brLicne = brLicne;
    }

    @Override
    public String toString() {
        String val = "Value{"
                + " ime="
                + ime
                + "  prezime=" + prezime
                + " user=" + user
                + " pass=" + pass
                + " brLicne="
                + brLicne + "email="
                + email
                + "ulica=" + ulica
                + "brKuce=" + brKuce + "}";

        return val;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

}
