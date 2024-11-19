
package com.volandoPuntoUY.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtReservaWeb complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtReservaWeb">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="cantEquipaje" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="cantPasajeros" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="embarque" type="{http://servidor/}dtCheckinWeb" minOccurs="0"/>
 *         <element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nicknameCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nomVuelo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="pasajes" type="{http://servidor/}dtPasaje" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="tipoAsiento" type="{http://servidor/}tipoAsiento" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtReservaWeb", propOrder = {
    "cantEquipaje",
    "cantPasajeros",
    "costo",
    "embarque",
    "fecha",
    "nicknameCliente",
    "nomVuelo",
    "pasajes",
    "tipoAsiento"
})
public class DtReservaWeb {

    protected int cantEquipaje;
    protected int cantPasajeros;
    protected float costo;
    protected DtCheckinWeb embarque;
    protected String fecha;
    protected String nicknameCliente;
    protected String nomVuelo;
    @XmlElement(nillable = true)
    protected List<DtPasaje> pasajes;
    @XmlSchemaType(name = "string")
    protected TipoAsiento tipoAsiento;

    /**
     * Obtiene el valor de la propiedad cantEquipaje.
     * 
     */
    public int getCantEquipaje() {
        return cantEquipaje;
    }

    /**
     * Define el valor de la propiedad cantEquipaje.
     * 
     */
    public void setCantEquipaje(int value) {
        this.cantEquipaje = value;
    }

    /**
     * Obtiene el valor de la propiedad cantPasajeros.
     * 
     */
    public int getCantPasajeros() {
        return cantPasajeros;
    }

    /**
     * Define el valor de la propiedad cantPasajeros.
     * 
     */
    public void setCantPasajeros(int value) {
        this.cantPasajeros = value;
    }

    /**
     * Obtiene el valor de la propiedad costo.
     * 
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Define el valor de la propiedad costo.
     * 
     */
    public void setCosto(float value) {
        this.costo = value;
    }

    /**
     * Obtiene el valor de la propiedad embarque.
     * 
     * @return
     *     possible object is
     *     {@link DtCheckinWeb }
     *     
     */
    public DtCheckinWeb getEmbarque() {
        return embarque;
    }

    /**
     * Define el valor de la propiedad embarque.
     * 
     * @param value
     *     allowed object is
     *     {@link DtCheckinWeb }
     *     
     */
    public void setEmbarque(DtCheckinWeb value) {
        this.embarque = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad nicknameCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNicknameCliente() {
        return nicknameCliente;
    }

    /**
     * Define el valor de la propiedad nicknameCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNicknameCliente(String value) {
        this.nicknameCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad nomVuelo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomVuelo() {
        return nomVuelo;
    }

    /**
     * Define el valor de la propiedad nomVuelo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomVuelo(String value) {
        this.nomVuelo = value;
    }

    /**
     * Gets the value of the pasajes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the pasajes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPasajes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtPasaje }
     * 
     * 
     * @return
     *     The value of the pasajes property.
     */
    public List<DtPasaje> getPasajes() {
        if (pasajes == null) {
            pasajes = new ArrayList<>();
        }
        return this.pasajes;
    }

    /**
     * Obtiene el valor de la propiedad tipoAsiento.
     * 
     * @return
     *     possible object is
     *     {@link TipoAsiento }
     *     
     */
    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    /**
     * Define el valor de la propiedad tipoAsiento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAsiento }
     *     
     */
    public void setTipoAsiento(TipoAsiento value) {
        this.tipoAsiento = value;
    }

}
