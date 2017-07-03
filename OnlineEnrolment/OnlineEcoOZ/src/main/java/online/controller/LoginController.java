/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import exceptions.CustomException;
import static online.controller.EnrolmentController.korisnik;
import static online.controller.EnrolmentController.logged;
import static online.controller.EnrolmentController.message;
import static online.controller.EnrolmentController.sluzba;
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
@RequestMapping("/login")
public class LoginController extends EnrolmentController {

    @RequestMapping(path = "/korisnik", method = RequestMethod.GET)
    public String loginKorisnik(@ModelAttribute("korisnik") Korisnik newObject) throws CustomException {
        message = "";
        try {
            OpstiDomenskiObjekat exe = service.get(newObject, Constants.KORISNIK);
            if (exe != null || ((Korisnik) exe).getBrLicne() == 0) {
                korisnik = (Korisnik) exe;
                modelAndView.addObject("korisnik", korisnik);
                logged = "user";
                modelAndView.addObject("logged", logged);
                message = "Uspešno ste ulogovani";
                modelAndView.addObject("message", message);
            }
        } catch (Exception e) {
            throw new CustomException("Korisnik sa tim podacima nije pronadjen");
        }
        return "redirect:/";

    }

    @RequestMapping(path = "/sluzba", method = RequestMethod.GET)
    public String loginSluzba(@ModelAttribute("sluzba") SluzbaTransporta newObject) throws CustomException {
        OpstiDomenskiObjekat exe = service.get(newObject, Constants.SLUZBA);
        message = "";
        sluzba = (SluzbaTransporta) exe;
        if (sluzba != null) {
            modelAndView.addObject("sluzba", sluzba);
            logged = "sluzba";
            message = "Uspešno ste ulogovani";
            modelAndView.addObject("message", message);
            modelAndView.addObject("logged", logged);
            return "redirect:/";
        } else {
            throw new CustomException("Sluzba sa tim podacima nije pronadjena");
        }
    }
}
