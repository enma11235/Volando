
package com.volandoPuntoUY.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtVueloWeb complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtVueloWeb">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="cantEjecutivoDisponible" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="cantTuristaDisponible" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="duracion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="imagen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="maxEjecutivo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="maxTurista" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="reservas" type="{http://servidor/}dtReservaWeb" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtVueloWeb", propOrder = {
    "cantEjecutivoDisponible",
    "cantTuristaDisponible",
    "duracion",
    "fecha",
    "fechaAlta",
    "imagen",
    "maxEjecutivo",
    "maxTurista",
    "nombre",
    "reservas"
})
public class DtVueloWeb {

    protected int cantEjecutivoDisponible;
    protected int cantTuristaDisponible;
    protected String duracion;
    protected String fecha;
    protected String fechaAlta;
    protected String imagen;
    protected int maxEjecutivo;
    protected int maxTurista;
    protected String nombre;
    @XmlElement(nillable = true)
    protected List<DtReservaWeb> reservas;

    /**
     * Obtiene el valor de la propiedad cantEjecutivoDisponible.
     * 
     */
    public int getCantEjecutivoDisponible() {
        return cantEjecutivoDisponible;
    }

    /**
     * Define el valor de la propiedad cantEjecutivoDisponible.
     * 
     */
    public void setCantEjecutivoDisponible(int value) {
        this.cantEjecutivoDisponible = value;
    }

    /**
     * Obtiene el valor de la propiedad cantTuristaDisponible.
     * 
     */
    public int getCantTuristaDisponible() {
        return cantTuristaDisponible;
    }

    /**
     * Define el valor de la propiedad cantTuristaDisponible.
     * 
     */
    public void setCantTuristaDisponible(int value) {
        this.cantTuristaDisponible = value;
    }

    /**
     * Obtiene el valor de la propiedad duracion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Define el valor de la propiedad duracion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuracion(String value) {
        this.duracion = value;
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
     * Obtiene el valor de la propiedad fechaAlta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Define el valor de la propiedad fechaAlta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAlta(String value) {
        this.fechaAlta = value;
    }

    /**
     * Obtiene el valor de la propiedad imagen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Define el valor de la propiedad imagen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagen(String value) {
        this.imagen = value;
    }

    /**
     * Obtiene el valor de la propiedad maxEjecutivo.
     * 
     */
    public int getMaxEjecutivo() {
        return maxEjecutivo;
    }

    /**
     * Define el valor de la propiedad maxEjecutivo.
     * 
     */
    public void setMaxEjecutivo(int value) {
        this.maxEjecutivo = value;
    }

    /**
     * Obtiene el valor de la propiedad maxTurista.
     * 
     */
    public int getMaxTurista() {
        return maxTurista;
    }

    /**
     * Define el valor de la propiedad maxTurista.
     * 
     */
    public void setMaxTurista(int value) {
        this.maxTurista = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the reservas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the reservas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtReservaWeb }
     * 
     * 
     * @return
     *     The value of the reservas property.
     */
    public List<DtReservaWeb> getReservas() {
        if (reservas == null) {
            reservas = new ArrayList<>();
        }
        return this.reservas;
    }

}
