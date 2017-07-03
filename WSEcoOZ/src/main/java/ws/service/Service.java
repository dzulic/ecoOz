/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.service;

import so.PretraziZahteve;
import so.PretraziIzvestaje;
import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import domen.Zaduzenja;
import domen.Zahtev;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.Constants;
import so.IzmeniZahtev;
import so.KreirajIzvestaj;
import so.KreirajKorisnika;
import so.KreirajZahtev;
import so.NadjiKorisnika;
import so.NadjiSluzbu;
import so.ObrisiZahtev;
import so.PretraziZaduzenja;
import so.PrikaziIzvestaj;
import so.PrikaziZaduzenje;
import so.PrikaziZahtev;
import so.SOAbstract;
import so.ZapamtiIzvestaj;
import so.ZapamtiZahtev;
import transfer.TransferObjekat;
import ws.db.AbstractDataBase;

/**
 *
 * @author ciricj
 */
@org.springframework.stereotype.Service
@Component
public class Service extends AbstractService {

    @Autowired
    AbstractDataBase db;

    @Override
    public TransferObjekat create(OpstiDomenskiObjekat odo, String type) {
        SOAbstract operacija = null;
        String ID = "";
        switch (type) {
            case (Constants.KORISNIK): {
                operacija = new KreirajKorisnika((Korisnik) odo);
                db.dataBaseOperation(operacija);
                ID = ((Korisnik) operacija.getObjekat()).getBrLicne() + "";
                break;
            }
            case (Constants.ZAHTEV): {
                operacija = new KreirajZahtev((Zahtev) odo);
                db.dataBaseOperation(operacija);
                ID = ((Zahtev) operacija.getObjekat()).getZahtevID() + "";
                break;
            }
            case (Constants.IZVESTAJ): {
                operacija = new KreirajIzvestaj((Izvestaj) odo);
                db.dataBaseOperation(operacija);
                ID = ((Izvestaj) operacija.getObjekat()).getID() + "";
                break;
            }

        }
        return new TransferObjekat(ID, operacija.getHttpStatus());
    }

    //TODO
    @Override
    public TransferObjekat list(OpstiDomenskiObjekat odo, String type) {
        SOAbstract operacija = null;

        switch (type) {
            case (Constants.ZADUZENJE): {
                operacija = new PretraziZaduzenja(odo);
                break;
            }
            case (Constants.IZVESTAJ): {
                operacija = new PretraziIzvestaje(odo);
                break;
            }
            case (Constants.STAVKA_ZAHTEVA): {
                operacija = new PretraziZahteve(odo);
                break;
            }
            case (Constants.ZAHTEV): {
                operacija = new PrikaziZahtev(odo);
                break;
            }

        }
        db.dataBaseOperation(operacija);
        return new TransferObjekat(operacija.getListaObjekata(), operacija.getHttpStatus());
    }

    @Override
    public TransferObjekat get(OpstiDomenskiObjekat odo, String type
    ) {
        SOAbstract operacija = null;
        switch (type) {
            case (Constants.KORISNIK): {
                operacija = new NadjiKorisnika((Korisnik) odo);
                break;
            }
            case (Constants.SLUZBA): {
                operacija = new NadjiSluzbu((SluzbaTransporta) odo);
                break;
            }
            case(Constants.ZADUZENJE):{
             operacija= new PrikaziZaduzenje((Zaduzenja) odo);
             break;
            }
            case (Constants.IZVESTAJ):{
                operacija= new PrikaziIzvestaj((Izvestaj)odo);
                break;
            }
        }
        db.dataBaseOperation(operacija);
        return new TransferObjekat(operacija.getObjekat(), operacija.getHttpStatus());

    }

    @Override
    public TransferObjekat save(List<OpstiDomenskiObjekat> odo, String type) {
        SOAbstract operacija = null;
        switch (type) {
            case (Constants.IZVESTAJ): {
                operacija = new ZapamtiIzvestaj(odo);
                break;
            }
            case (Constants.STAVKA_ZAHTEVA): {
                operacija = new ZapamtiZahtev(odo);
                break;
            }
        }
        db.dataBaseOperation(operacija);
        return new TransferObjekat(null, operacija.getHttpStatus());
    }

    @Override
    public TransferObjekat delete(List<OpstiDomenskiObjekat> odo) {
        SOAbstract operacija = new ObrisiZahtev(odo);
        db.dataBaseOperation(operacija);
        return new TransferObjekat(null, operacija.getHttpStatus());
    }

    @Override
    public TransferObjekat update(List<OpstiDomenskiObjekat> obj) {
        SOAbstract operacija = new IzmeniZahtev(obj);
        db.dataBaseOperation(operacija);
        return new TransferObjekat(operacija.getObjekat(), operacija.getHttpStatus());
    }
}
