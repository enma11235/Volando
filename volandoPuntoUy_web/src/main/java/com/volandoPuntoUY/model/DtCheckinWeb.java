
package com.volandoPuntoUY.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtCheckinWeb complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtCheckinWeb">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="fechaEmbarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="horaEmbarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCheckinWeb", propOrder = {
    "fechaEmbarque",
    "horaEmbarque"
})
public class DtCheckinWeb {

    protected String fechaEmbarque;
    protected String horaEmbarque;

    /**
     * Obtiene el valor de la propiedad fechaEmbarque.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaEmbarque() {
        return fechaEmbarque;
    }

    /**
     * Define el valor de la propiedad fechaEmbarque.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaEmbarque(String value) {
        this.fechaEmbarque = value;
    }

    /**
     * Obtiene el valor de la propiedad horaEmbarque.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraEmbarque() {
        return horaEmbarque;
    }

    /**
     * Define el valor de la propiedad horaEmbarque.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraEmbarque(String value) {
        this.horaEmbarque = value;
    }

}
