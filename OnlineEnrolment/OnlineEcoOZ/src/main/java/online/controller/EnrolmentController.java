/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import domen.Izvestaj;
import domen.Korisnik;
import domen.SluzbaTransporta;
import domen.StavkaIzvestaja;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import domen.Zahtev;
import exceptions.CustomException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import online.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;
import utils.Wrapper;

/**
 *
 * @author ciricj
 */
@RequestMapping("/")
@Controller
public class EnrolmentController {

    @Autowired
    public AbstractService service;

    public EnrolmentController() {
        super();
        zahtev = new Zahtev();
        izvestaj = new Izvestaj();
    }
    public String id = "";

    public static String message = "";
    public ModelAndView modelAndView = new ModelAndView("index");

    public String fragment = "";
    public static Zahtev zahtev;
    public static Zahtev pomocniZahtev;
    public static Zahtev zaduzenjeZahtev;
    public static Zahtev noviZahtev;
    public static Korisnik korisnik;
    public static Korisnik noviKorisnik;
    public static SluzbaTransporta sluzba;
    public static List<Izvestaj> stavkeIzv;
    public static List<StavkaIzvestaja> pomocneStavkeIzv;
    public static List<Zaduzenja> listaZaduzenja;
    public static List<Zahtev> listaZahteva;
    public static List<StavkaZahteva> stavkeZah;
    public static List<StavkaZahteva> pomocneStavkeZah;
    public static Wrapper wrapper;

    public List<Izvestaj> listaIzvestaja;

    public StavkaZahteva stavkaZah;
    public static Zaduzenja zaduzenje;
    public static Zaduzenja zaduzenjeSve;
    public static Izvestaj izvestaj;
    public StavkaIzvestaja stavkaIzvestaja;
    public List<Korisnik> listaKorisnika;
    public List<SluzbaTransporta> listaSluzbe;
    public static String logged;

    @ModelAttribute("fragment")
    public String returnFrag() {
        return fragment;
    }

    @ModelAttribute("wrapper")
    public Wrapper returnWrapp() {
        return wrapper;
    }

    @ModelAttribute("materijali")
    public List<String> returnMaterijali() {
        return new ArrayList<>(Arrays.asList("select", "staklo", "papir", "plastika", "ostalo"));
    }

    @ModelAttribute("logged")
    public String returnlogged() {
        return logged;
    }

    @ModelAttribute("message")
    public String returnMessage() {
        return message;
    }

    //atributi
    @ModelAttribute("listica")
    public List<Korisnik> returnListica() {
        return listaKorisnika;
    }

    @ModelAttribute("listaZahteva")
    public List<Zahtev> returnZahteve() {
        if (listaZahteva == null) {
            listaZahteva = new ArrayList<>();
        }
        return listaZahteva;
    }

    @ModelAttribute("stavkeZah")
    public List<StavkaZahteva> returnStavkeZahteva() {
        if (stavkeZah == null) {
            stavkeZah = new ArrayList<>();
        }
        return stavkeZah;
    }

    @ModelAttribute("pomocneStavkeZah")
    public List<StavkaZahteva> returnTempStavkeZahteva() {
        return pomocneStavkeZah;
    }

    @ModelAttribute("listaSluzba")
    public List<SluzbaTransporta> returnListaSluzbi() {
        return listaSluzbe;
    }

    @ModelAttribute("listaZaduzenja")
    public List<Zaduzenja> returnListaZaduzenja() {
        if (listaZaduzenja == null) {
            listaZaduzenja = new ArrayList<>();
        }
        return listaZaduzenja;
    }

    @ModelAttribute("listaStavkeIzvestaja")
    public List<Izvestaj> returnStavkeIzvestaja() {
        if (stavkeIzv == null) {
            stavkeIzv = new ArrayList<>();
        }
        return stavkeIzv;
    }

    @ModelAttribute("korisnik")
    public Korisnik returnKorisnik() {
        if (korisnik == null) {
            korisnik = new Korisnik();
        }
        return korisnik;
    }

    @ModelAttribute("noviKorisnik")
    public Korisnik returnNoviKorisnik() {
        if (noviKorisnik == null) {
            noviKorisnik = new Korisnik();
        }
        return noviKorisnik;
    }

    @ModelAttribute("stavkaZah")
    public StavkaZahteva returnStavkaZahteva() {
        return stavkaZah;
    }

    @ModelAttribute("stavkaIzv")
    public StavkaIzvestaja returnStavkaIzvestaja() {
        return stavkaIzvestaja;
    }

    @ModelAttribute("pomocneStavkeIzv")
    public List<StavkaIzvestaja> returnPomocneStavkeIzvestaja() {
        return pomocneStavkeIzv;
    }

    @ModelAttribute("izvestaj")
    public Izvestaj returnIzvestaj() {
        if (izvestaj != null) {
            return izvestaj;
        } else {
            return new Izvestaj();
        }
    }

    @ModelAttribute("sluzba")
    public SluzbaTransporta returnSluzba() {
        if (sluzba != null) {
            return sluzba;
        } else {
            return new SluzbaTransporta();
        }
    }

    @ModelAttribute("zaduzenje")
    public Zaduzenja returnZaduzenje() {
        if (zaduzenje == null) {
            zaduzenje = new Zaduzenja();
        }
        return zaduzenje;
    }

    @ModelAttribute("zaduzenjeSve")
    public Zaduzenja returnZaduzenjeSve() {
        return zaduzenjeSve;
    }

    @ModelAttribute("zahtev")
    public Zahtev returnZahtev() {
        if (zahtev == null) {
            zahtev = new Zahtev();
        }
        return zahtev;
    }

    @ModelAttribute("zaduzenjeZahtev")
    public Zahtev zaduzenjeZahtev() {
        return zaduzenjeZahtev;
    }

    @ModelAttribute("noviZahtev")
    public Zahtev noviZahtevZahtev() {
        return noviZahtev;
    }

    @ModelAttribute("pomocniZahtev")
    public Zahtev returnNadjenZahtev() {
        return pomocniZahtev;
    }

    @RequestMapping({"/"})
    public String showIndex() {
        return "index";
    }

    @RequestMapping({"/kreirajNalog"})
    public String showkreiraj() {
        return "kreirajNalog";
    }

    @RequestMapping("/{page}")
    public ModelAndView redirectPage(@PathVariable() String page) {
        fragment = page;
        modelAndView.addObject("fragment", page);
        return modelAndView;
    }

    @ModelAttribute("minDate")
    public String returnMinDate() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        return sf.format(new Date());
    }

    @ModelAttribute("maxDate")
    public String returnMaxDate() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 30);  // number of days to add
        return sf.format(c.getTime());
    }

    @RequestMapping("/reload")
    public String reload() {
        fragment = "";
        modelAndView.addObject("fragment", fragment);
        zahtev = new Zahtev();
        izvestaj = new Izvestaj();
        if (listaZahteva != null) {
            listaZahteva = null;
        }
        stavkeIzv = null;
        if (pomocniZahtev != null) {
            pomocniZahtev = null;
        }
        if (pomocneStavkeZah != null) {
            pomocneStavkeZah = null;
        }
        if (pomocneStavkeIzv != null) {
            pomocneStavkeIzv = null;
        }
        if (zaduzenje != null) {
            zaduzenje = null;
        }
        if (zaduzenjeSve != null) {
            zaduzenjeSve = null;
        }
        if (noviZahtev != null) {
            noviZahtev = null;
        }

        message = "";
        modelAndView.addObject("izvestaj", izvestaj);
        modelAndView.addObject("zahtev", zahtev);
        modelAndView.addObject("message", message);

        return "redirect:/";
    }

    @RequestMapping("/refresh")
    public String refresh() {
        message = "";
        if (pomocniZahtev != null) {
            pomocniZahtev = null;
        }
        if (stavkeIzv != null) {
            stavkeIzv = null;
        }
        zaduzenjeZahtev = null;
        return "redirect:/";

    }

    @RequestMapping("/deleteRowZahtev")
    public String deleteRowZahtev(String s) {

        if (zahtev.getListaStavki() != null && s != null) {
            zahtev.getListaStavki().remove(Integer.parseInt(s));
        }
        modelAndView.addObject("zahtev", zahtev);
        return "redirect:/";

    }

    @RequestMapping("/deleteRowIzvestaj")
    public String deleteRowIzvestaj(String s) {
        if (izvestaj.getListaStavki() != null && s != null) {
            izvestaj.getListaStavki().remove(Integer.parseInt(s));
        }
        modelAndView.addObject("izvestaj", izvestaj);
        return "redirect:/";

    }

    @RequestMapping("create/backKreirajNalog")
    public String redirectBack() {
        return "redirect:keirajNalog";
    }

    @RequestMapping(value = "/logout")
    public String processLogout() {
        message = "";
        if (korisnik != null) {
            korisnik = new Korisnik();
            modelAndView.addObject("korisnik", korisnik);
        } else if (sluzba != null) {
            sluzba = new SluzbaTransporta();
            modelAndView.addObject("sluzba", sluzba);
        }
        logged = "";
        return "redirect:/";
    }

    @RequestMapping(value = "/redirect")
    public String reloadReturn() {
        return "redirect:/";
    }

    @ExceptionHandler({ResourceAccessException.class})
    public String systemError() {
        if (service.getHttpMessage() != null) {
            message = service.getHttpMessage();
        } else {
            message = "Doslo je do sistemske greske";
        }
        return "redirect:/";
    }

    @ExceptionHandler({CustomException.class})
    public String nullError(CustomException exc) {
        if (exc.getMessage() != null) {
            message = exc.getMessage();
        } else {
            message = service.getHttpMessage();
        }
        return "redirect:/";
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public String httpClientError() {
        if (service.getHttpMessage() != null) {
            message = service.getHttpMessage();
        } else {
            message = "Doslo je do greske";
        }
        return "redirect:/";
    }

}
