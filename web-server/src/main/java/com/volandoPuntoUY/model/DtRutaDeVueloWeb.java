
package com.volandoPuntoUY.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtRutaDeVueloWeb complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtRutaDeVueloWeb">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="aerolinea" type="{http://servidor/}dtAerolinea" minOccurs="0"/>
 *         <element name="categorias" type="{http://servidor/}dtCategoria" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="ciudadDestino" type="{http://servidor/}dtCiudadWeb" minOccurs="0"/>
 *         <element name="ciudadOrigen" type="{http://servidor/}dtCiudadWeb" minOccurs="0"/>
 *         <element name="costoEjecutivo" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="costoEquipajeExtra" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="costoTurista" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcionCorta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="estado" type="{http://servidor/}estadoRuta" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="hora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="imagen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="video" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="visitas" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtRutaDeVueloWeb", propOrder = {
    "aerolinea",
    "categorias",
    "ciudadDestino",
    "ciudadOrigen",
    "costoEjecutivo",
    "costoEquipajeExtra",
    "costoTurista",
    "descripcion",
    "descripcionCorta",
    "estado",
    "fechaAlta",
    "hora",
    "imagen",
    "nombre",
    "video",
    "visitas"
})
public class DtRutaDeVueloWeb {

    protected DtAerolinea aerolinea;
    @XmlElement(nillable = true)
    protected List<DtCategoria> categorias;
    protected DtCiudadWeb ciudadDestino;
    protected DtCiudadWeb ciudadOrigen;
    protected Float costoEjecutivo;
    protected Float costoEquipajeExtra;
    protected Float costoTurista;
    protected String descripcion;
    protected String descripcionCorta;
    @XmlSchemaType(name = "string")
    protected EstadoRuta estado;
    protected String fechaAlta;
    protected String hora;
    protected String imagen;
    protected String nombre;
    protected String video;
    protected int visitas;

    /**
     * Obtiene el valor de la propiedad aerolinea.
     * 
     * @return
     *     possible object is
     *     {@link DtAerolinea }
     *     
     */
    public DtAerolinea getAerolinea() {
        return aerolinea;
    }

    /**
     * Define el valor de la propiedad aerolinea.
     * 
     * @param value
     *     allowed object is
     *     {@link DtAerolinea }
     *     
     */
    public void setAerolinea(DtAerolinea value) {
        this.aerolinea = value;
    }

    /**
     * Gets the value of the categorias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the categorias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategorias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtCategoria }
     * 
     * 
     * @return
     *     The value of the categorias property.
     */
    public List<DtCategoria> getCategorias() {
        if (categorias == null) {
            categorias = new ArrayList<>();
        }
        return this.categorias;
    }

    /**
     * Obtiene el valor de la propiedad ciudadDestino.
     * 
     * @return
     *     possible object is
     *     {@link DtCiudadWeb }
     *     
     */
    public DtCiudadWeb getCiudadDestino() {
        return ciudadDestino;
    }

    /**
     * Define el valor de la propiedad ciudadDestino.
     * 
     * @param value
     *     allowed object is
     *     {@link DtCiudadWeb }
     *     
     */
    public void setCiudadDestino(DtCiudadWeb value) {
        this.ciudadDestino = value;
    }

    /**
     * Obtiene el valor de la propiedad ciudadOrigen.
     * 
     * @return
     *     possible object is
     *     {@link DtCiudadWeb }
     *     
     */
    public DtCiudadWeb getCiudadOrigen() {
        return ciudadOrigen;
    }

    /**
     * Define el valor de la propiedad ciudadOrigen.
     * 
     * @param value
     *     allowed object is
     *     {@link DtCiudadWeb }
     *     
     */
    public void setCiudadOrigen(DtCiudadWeb value) {
        this.ciudadOrigen = value;
    }

    /**
     * Obtiene el valor de la propiedad costoEjecutivo.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCostoEjecutivo() {
        return costoEjecutivo;
    }

    /**
     * Define el valor de la propiedad costoEjecutivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCostoEjecutivo(Float value) {
        this.costoEjecutivo = value;
    }

    /**
     * Obtiene el valor de la propiedad costoEquipajeExtra.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    /**
     * Define el valor de la propiedad costoEquipajeExtra.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCostoEquipajeExtra(Float value) {
        this.costoEquipajeExtra = value;
    }

    /**
     * Obtiene el valor de la propiedad costoTurista.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCostoTurista() {
        return costoTurista;
    }

    /**
     * Define el valor de la propiedad costoTurista.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCostoTurista(Float value) {
        this.costoTurista = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionCorta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    /**
     * Define el valor de la propiedad descripcionCorta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionCorta(String value) {
        this.descripcionCorta = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoRuta }
     *     
     */
    public EstadoRuta getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoRuta }
     *     
     */
    public void setEstado(EstadoRuta value) {
        this.estado = value;
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
     * Obtiene el valor de la propiedad hora.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHora() {
        return hora;
    }

    /**
     * Define el valor de la propiedad hora.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHora(String value) {
        this.hora = value;
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
     * Obtiene el valor de la propiedad video.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideo() {
        return video;
    }

    /**
     * Define el valor de la propiedad video.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideo(String value) {
        this.video = value;
    }

    /**
     * Obtiene el valor de la propiedad visitas.
     * 
     */
    public int getVisitas() {
        return visitas;
    }

    /**
     * Define el valor de la propiedad visitas.
     * 
     */
    public void setVisitas(int value) {
        this.visitas = value;
    }

}
