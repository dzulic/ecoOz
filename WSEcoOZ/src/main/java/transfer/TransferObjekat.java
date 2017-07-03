/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class TransferObjekat {

    private Object objekat;
    private HttpStatus httpStatus;
    private Exception exception;

    public Object getObjekat() {
        return objekat;
    }

    public void setObjekat(Object objekat) {
        this.objekat = objekat;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public TransferObjekat(Object objekat, HttpStatus httpStatus) {
        this.objekat = objekat;
        this.httpStatus = httpStatus;
    }

}
