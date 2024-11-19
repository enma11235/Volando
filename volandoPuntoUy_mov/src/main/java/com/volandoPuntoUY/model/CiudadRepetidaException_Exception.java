
package com.volandoPuntoUY.model;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.0
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "CiudadRepetidaException", targetNamespace = "http://servidor/")
public class CiudadRepetidaException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CiudadRepetidaException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public CiudadRepetidaException_Exception(String message, CiudadRepetidaException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public CiudadRepetidaException_Exception(String message, CiudadRepetidaException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.volandoPuntoUY.model.CiudadRepetidaException
     */
    public CiudadRepetidaException getFaultInfo() {
        return faultInfo;
    }

}
