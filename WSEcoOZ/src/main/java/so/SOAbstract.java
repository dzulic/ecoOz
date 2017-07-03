/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import java.util.List;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public abstract class SOAbstract {

    protected OpstiDomenskiObjekat objekat;
    protected List<OpstiDomenskiObjekat> listaObjekata;
    protected Session session;
    protected HttpStatus httpStatus;

    public abstract void execute();

    public OpstiDomenskiObjekat getObjekat() {
        return objekat;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<OpstiDomenskiObjekat> getListaObjekata() {
        return listaObjekata;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
