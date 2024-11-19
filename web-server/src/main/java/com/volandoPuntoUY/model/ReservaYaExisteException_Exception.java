
package com.volandoPuntoUY.model;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.0
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "ReservaYaExisteException", targetNamespace = "http://servidor/")
public class ReservaYaExisteException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ReservaYaExisteException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public ReservaYaExisteException_Exception(String message, ReservaYaExisteException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public ReservaYaExisteException_Exception(String message, ReservaYaExisteException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.volandoPuntoUY.model.ReservaYaExisteException
     */
    public ReservaYaExisteException getFaultInfo() {
        return faultInfo;
    }

}