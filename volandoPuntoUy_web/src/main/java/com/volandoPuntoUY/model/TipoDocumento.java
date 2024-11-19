
package com.volandoPuntoUY.model;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tipoDocumento.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>{@code
 * <simpleType name="tipoDocumento">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Pasaporte"/>
 *     <enumeration value="CedulaIdentidad"/>
 *     <enumeration value="Extranjero"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "tipoDocumento")
@XmlEnum
public enum TipoDocumento {

    @XmlEnumValue("Pasaporte")
    PASAPORTE("Pasaporte"),
    @XmlEnumValue("CedulaIdentidad")
    CEDULA_IDENTIDAD("CedulaIdentidad"),
    @XmlEnumValue("Extranjero")
    EXTRANJERO("Extranjero");
    private final String value;

    TipoDocumento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoDocumento fromValue(String v) {
        for (TipoDocumento c: TipoDocumento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
