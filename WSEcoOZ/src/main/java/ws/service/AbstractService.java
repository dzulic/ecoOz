/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.service;

import domen.OpstiDomenskiObjekat;
import java.util.List;
import org.springframework.stereotype.Component;
import transfer.TransferObjekat;

/**
 *
 * @author ciricj
 */
@org.springframework.stereotype.Service
@Component
public abstract class AbstractService {

    public abstract TransferObjekat create(OpstiDomenskiObjekat odo, String type);

    public abstract TransferObjekat list(OpstiDomenskiObjekat odo, String type);

    public abstract TransferObjekat get(OpstiDomenskiObjekat odo, String type);

    public abstract TransferObjekat delete(OpstiDomenskiObjekat odo);

    public abstract TransferObjekat update(List<OpstiDomenskiObjekat> obj);

    public abstract TransferObjekat save(List<OpstiDomenskiObjekat> odo, String type);
}
