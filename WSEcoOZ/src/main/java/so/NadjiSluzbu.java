/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.transform.Transformers;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class NadjiSluzbu extends SOAbstract {

    Logger logger = Logger.getLogger(NadjiSluzbu.class);
    private SluzbaTransporta sluzba;

    public NadjiSluzbu(SluzbaTransporta sluzbaTransporta) {
        sluzba = sluzbaTransporta;
    }

    @Override
    public void execute() {
        if (sluzba != null) {
                List list = session.createNativeQuery(
                        "select * from sluzba_transporta where PIB='" + sluzba.getPIB() + "' AND sifra='" + sluzba.getSifra() + "'").setResultTransformer(Transformers.aliasToBean(SluzbaTransporta.class)).list();
                objekat = (OpstiDomenskiObjekat) list.get(0);
                httpStatus = HttpStatus.OK;
            
        }
    }
}
