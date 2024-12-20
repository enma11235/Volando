
package com.volandoPuntoUY.model;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.0
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "RutaDeVueloRepetidaException", targetNamespace = "http://servidor/")
public class RutaDeVueloRepetidaException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private RutaDeVueloRepetidaException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public RutaDeVueloRepetidaException_Exception(String message, RutaDeVueloRepetidaException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public RutaDeVueloRepetidaException_Exception(String message, RutaDeVueloRepetidaException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.volandoPuntoUY.model.RutaDeVueloRepetidaException
     */
    public RutaDeVueloRepetidaException getFaultInfo() {
        return faultInfo;
    }

}
