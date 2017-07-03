/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Izvestaj;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class KreirajIzvestaj extends SOAbstract {

    Izvestaj izvestaj;
    Boolean isCreated;

    public KreirajIzvestaj(Izvestaj izvestaj) {
        this.izvestaj = izvestaj;
    }

    @Override
    public void execute() {
        if (izvestaj != null) {
            if (izvestaj.getID() == 0) {
                List list = session.createNativeQuery("SELECT ID FROM izvestaj").list();
                int max = 1;
                for (Object object : list) {
                    if (max < Integer.parseInt(object.toString())) {
                        max = Integer.parseInt(object.toString());
                    }
                }
                izvestaj.setID(max + 1);
                session.save(izvestaj);
            }
            objekat = izvestaj;
            isCreated = true;
            httpStatus = HttpStatus.OK;
        }
    }

}
