/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import domen.Izvestaj;
import domen.Korisnik;
import domen.Zahtev;
import exceptions.CustomException;
import java.util.Date;
import javax.validation.Valid;
import static online.controller.EnrolmentController.izvestaj;
import static online.controller.EnrolmentController.korisnik;
import static online.controller.EnrolmentController.message;
import static online.controller.EnrolmentController.noviKorisnik;
import static online.controller.EnrolmentController.sluzba;
import static online.controller.EnrolmentController.zahtev;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.Constants;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/novi")
public class KreiraјController extends EnrolmentController {

    @RequestMapping(path = "/korisnik", method = RequestMethod.POST)
    public String kreirajNovogKorisnika(@Valid
            @ModelAttribute("noviKorisnik") Korisnik newObject) throws CustomException {
        message = "";
        try {
            String create = service.create(newObject, Constants.KORISNIK);
            if (null != create) {
                message = "Sistem je kreirao nalog";
            }
            modelAndView.addObject("message", message);
            noviKorisnik = null;
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da kreira nalog");
        }
    }

    //2
    @RequestMapping(path = "/zahtev", method = RequestMethod.POST)
    public String kreirajNoviZahtev(@Valid
            @ModelAttribute("zahtev") Zahtev newObject) throws CustomException {
        message = "";
        newObject.setKorisnik(korisnik);
        String id;
        Date datum = new Date();
        if (newObject.getDatum().before(datum)) {
            throw new CustomException("Sistem ne moze da kreira zahtev. Datum ne moze biti pre danasnjeg datuma");
        }
        try {
            id = service.create(newObject, Constants.ZAHTEV);
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da kreira zahtev");
        }
        if (id != null && !"".equals(id)) {
            zahtev.setZahtevID(Integer.parseInt(id));
            message = "Sistem je kreirao zahtev";
        }
        modelAndView.addObject("zahtev", zahtev);
        return "redirect:/";
    }

    //3
    @RequestMapping(path = "/izvestaj", method = RequestMethod.POST)
    public String kreirajNoviIzvestaj(@Valid
            @ModelAttribute("izvestaj") Izvestaj newObject) throws CustomException {
        message = "";
        izvestaj = new Izvestaj();
        Date datum = new Date();
        newObject.setSluzba(sluzba);
        if (newObject.getDatum().before(datum)) {
            throw new CustomException("Datum ne sme biti pre danasnjeg");
        }
        try {
            String id = service.create(newObject, Constants.IZVESTAJ);
            izvestaj = newObject;
            if (id != null && !"".equals(id)) {
                izvestaj.setID(Integer.parseInt(id));
            }
            modelAndView.addObject("message", message);
            return "redirect:/get/zaduzenja/selected";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da kreira izvestaj");
        }
    }

 
}
