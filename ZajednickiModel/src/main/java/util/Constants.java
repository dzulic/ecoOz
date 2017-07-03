/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import domen.StavkaIzvestaja;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import domen.Zahtev;

/**
 *
 * @author Julija
 */
public class Constants {

    public static final String CREATE = "create";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    public static final String GET = "get";
    public static final String GETALL = "getall";

    public static final String STATUS_OK = "OK";
    public static final String STATUS_CONFLICT = "CONFLICT";
    public static final String STATUS_ERROR = "ERROR";

    public static final String ERROR = "ERROR";

    public static final String KORISNIK = "Korisnik";
    public static final String ZAHTEV = "Zahtev";
    public static final String IZVESTAJ = "Izvestaj";
    public static final String ZADUZENJE = "Zaduzenje";
    public static final String STAVKA_ZAHTEVA = "StavkaZahteva";
    public static final String STAVKA_IZVESTAJA = "StavkaIzvestaja";
    public static final String SLUZBA = "SluzbaTransporta";

    public static Class returnClassForType(String type) {
        switch (type) {
            case Constants.KORISNIK: {
                return Korisnik.class;
            }
            case Constants.IZVESTAJ: {
                return Izvestaj.class;
            }
            case Constants.ZAHTEV: {
                return Zahtev.class;
            }
            case Constants.ZADUZENJE: {
                return Zaduzenja.class;
            }
            case Constants.SLUZBA: {
                return SluzbaTransporta.class;
            }
            case Constants.STAVKA_ZAHTEVA: {
                return StavkaZahteva.class;
            }
            case Constants.STAVKA_IZVESTAJA: {
                return StavkaIzvestaja.class;
            }
            default:
                return OpstiDomenskiObjekat.class;
        }
    }
}
