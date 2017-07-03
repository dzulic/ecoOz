/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.service;

import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import domen.Zaduzenja;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import util.Constants;

/**
 *
 * @author ciricj
 */
@Component
@Service
public class ApplicationService extends AbstractService {

    final String uri = "http://localhost:8080/ws/exchange/";
    Logger logger = Logger.getLogger(ApplicationService.class);

    @Override
    public String create(OpstiDomenskiObjekat odo, String type) {
        initializeRest();
        ResponseEntity<String> result = null;
        Map<String, String> vars = new HashMap<>();
        vars.put("type", type);
        OpstiDomenskiObjekat forObject = null;
        String body = restTemplate.postForEntity(uri + "create/{type}", getRequestFromType(odo, type), String.class, vars).getBody();
        return body;
    }

    @Override
    public void save(List<OpstiDomenskiObjekat> odo, String type) {
        initializeRest();
        HttpEntity request = new HttpEntity<>(odo, headers);
        String body = restTemplate.postForEntity(uri + "save/{type}", request, String.class, type).getBody();
    }

    @Override
    public OpstiDomenskiObjekat get(OpstiDomenskiObjekat odo, String type) throws ResourceAccessException, HttpClientErrorException, NullPointerException {
        initializeRest();
        Map<String, String> vars = new HashMap<>();
        switch (type) {
            case (Constants.KORISNIK): {
                vars.put("param1", ((Korisnik) odo).getUser());
                vars.put("param2", ((Korisnik) odo).getPass());
                break;
            }
            case (Constants.SLUZBA): {
                vars.put("param1", ((SluzbaTransporta) odo).getPIB() + "");
                vars.put("param2", ((SluzbaTransporta) odo).getSifra());
                break;
            }
            case (Constants.ZADUZENJE): {
                vars.put("param1", ((Zaduzenja) odo).getZaduzenjaID() + "");
                vars.put("param2", "empty");
                break;
            }
            case (Constants.IZVESTAJ): {
                vars.put("param1", ((Izvestaj) odo).getID() + "");
                vars.put("param2", "empty");
                break;
            }
        }
        vars.put("type", type);
        String sufix = "get/{param1}/{param2}/{type}";
        ResponseEntity<OpstiDomenskiObjekat> obj = restTemplate.getForEntity(uri + sufix, Constants.returnClassForType(type), vars);
        httpMessage = obj.getStatusCode() + "";
        alertException(obj.getStatusCode());
        return obj.getBody();
    }

    @Override
    public List<LinkedHashMap> list(OpstiDomenskiObjekat odo, String type) throws ResourceAccessException {
        initializeRest();
        ResponseEntity<Object> postForEntity = restTemplate.postForEntity(uri + "list/setObj/{type}", getRequestFromType(odo, type), null, type);
        return restTemplate.getForObject(uri + "list/{type}", List.class, type);
    }

    @Override
    public void delete(List<OpstiDomenskiObjekat> odo, String type) throws ResourceAccessException {
        initializeRest();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(odo, headers);
        //da li je String dobra povratnas
        ResponseEntity<String> re = restTemplate.postForEntity(uri + "delete/{type}", request, String.class, type);
    }

    @Override
    public void update(List<OpstiDomenskiObjekat> odo, String type) throws ResourceAccessException, NullPointerException {
        initializeRest();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(odo, headers);
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(uri + "update/{type}", request, String.class, type);
        alertException(postForEntity.getStatusCode());
    }

    private void alertException(HttpStatus statusCode) {
        if (statusCode.equals(HttpStatus.NOT_FOUND)) {
            httpMessage = "Nije pronadjen";
            throw new NullPointerException();
        }
        if (statusCode.equals(HttpStatus.OK)) {
        }

    }

}
