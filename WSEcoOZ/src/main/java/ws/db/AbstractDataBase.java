/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.db;

import org.springframework.stereotype.Component;
import so.SOAbstract;

/**
 *
 * @author ciricj
 */
@Component
public abstract class AbstractDataBase {

    public boolean isCreated = false;

    public boolean dataBaseOperation(SOAbstract operation) {
        return isCreated;
    }
;
}
