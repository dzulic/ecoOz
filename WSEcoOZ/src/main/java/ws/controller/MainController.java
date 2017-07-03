/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import domen.Zaduzenja;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import transfer.TransferObjekat;
import util.Constants;
import ws.service.AbstractService;

/**
 *
 * @author ciricj
 */
@RestController
public class MainController {

    @Autowired
    AbstractService service;
    OpstiDomenskiObjekat prenosObj;
    Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(value = "/ws/exchange/create/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createData(@RequestBody String odo, @PathVariable("type") String type) {
        TransferObjekat to = null;
        try {
            to = service.create((OpstiDomenskiObjekat) new ObjectMapper().reader().forType(Constants.returnClassForType(type)).readValue(odo), type);
        } catch (IOException ex) {
            logger.debug(ex, ex);
        }
        return new ResponseEntity<>((String) to.getObjekat(), to.getHttpStatus());
    }

    @RequestMapping(value = "/ws/exchange/save/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveData(@RequestBody String odo, @PathVariable("type") String type) {
        TransferObjekat to = null;
        ObjectMapper mapper = new ObjectMapper();
        Class<?> clz = Constants.returnClassForType(type);
        JavaType t = mapper.getTypeFactory().constructCollectionType(List.class, clz);
        try {
            to = service.save((List<OpstiDomenskiObjekat>) mapper.readValue(odo, t), type);
        } catch (IOException ex) {
            logger.debug(ex, ex);
        }
        return new ResponseEntity<>((String) to.getObjekat(), to.getHttpStatus());
    }

    @RequestMapping(value = "/ws/exchange/delete/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteData(@RequestBody String odo, @PathVariable String type) {
        ObjectMapper mapper = new ObjectMapper();
        TransferObjekat to = null;
        Class<?> clz = Constants.returnClassForType(type);
        JavaType t = mapper.getTypeFactory().constructCollectionType(List.class, clz);
        try {
            to = service.delete((List<OpstiDomenskiObjekat>) mapper.readValue(odo, t));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<String>(to.getHttpStatus());

    }

    @RequestMapping(value = "/ws/exchange/update/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateData(@RequestBody String odo, @PathVariable String type) {
        TransferObjekat to = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            Class<?> clz = Constants.returnClassForType(type);
            JavaType t = mapper.getTypeFactory().constructCollectionType(List.class, clz);
            to = service.update((List<OpstiDomenskiObjekat>) mapper.readValue(odo, t));
        } catch (IOException ex) {
            logger.debug(ex, ex);
        }
        return new ResponseEntity<>((String) to.getObjekat(), to.getHttpStatus());
    }

    @RequestMapping(value = "/ws/exchange/get/{param1}/{param2}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpstiDomenskiObjekat> getData(@PathVariable String param1, @PathVariable String param2, @PathVariable String type) {
        TransferObjekat to = null;
        OpstiDomenskiObjekat pomocni = null;
        switch (type) {
            case (Constants.KORISNIK): {
                Korisnik k = new Korisnik();
                k.setUser(param1);
                k.setPass(param2);
                to = service.get(k, type);
                pomocni = new Korisnik();
                break;
            }
            case (Constants.SLUZBA): {
                SluzbaTransporta sluzba = new SluzbaTransporta();
                sluzba.setPIB(Integer.parseInt(param1));
                sluzba.setSifra(param2);
                to = service.get(sluzba, type);
                pomocni = new SluzbaTransporta();
                break;
            }
            case (Constants.ZADUZENJE): {
                Zaduzenja zaduzenja = new Zaduzenja();
                zaduzenja.setZaduzenjaID(Integer.parseInt(param1));
                to = service.get(zaduzenja, type);
                pomocni = new Zaduzenja();
                break;
            }
            case (Constants.IZVESTAJ): {
                Izvestaj izvestaj = new Izvestaj();
                izvestaj.setID(Integer.parseInt(param1));
                to = service.get(izvestaj, type);
                pomocni = new Izvestaj();
                break;
            }
        }
        if (to != null) {
            if (to.getObjekat() == null) {
                return new ResponseEntity<>((OpstiDomenskiObjekat) pomocni, to.getHttpStatus());
            }
            return new ResponseEntity<>((OpstiDomenskiObjekat) to.getObjekat(), to.getHttpStatus());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @RequestMapping(value = "/ws/exchange/list/setObj/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getObjForList(@RequestBody String odo, @PathVariable("type") String type) {
        boolean created = false;
        try {
            prenosObj = new ObjectMapper().reader().forType(Constants.returnClassForType(type)).readValue(odo);
        } catch (IOException ex) {
            logger.debug(ex, ex);
        }

    }

    @RequestMapping(value = "/ws/exchange/list/{type}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<OpstiDomenskiObjekat>> getAllData(@PathVariable("type") String type) {
        TransferObjekat list = service.list(prenosObj, type);
        return new ResponseEntity<>((List<OpstiDomenskiObjekat>) list.getObjekat(), list.getHttpStatus());

    }

}
