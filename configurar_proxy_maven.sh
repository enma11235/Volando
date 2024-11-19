#!/bin/bash

# Ruta al archivo settings.xml
#archivo="$HOME/.m2/settings.xml"
archivo="/usr/share/maven/conf/settings.xml"

# Contenido del proxy a agregar
proxy='    <proxy>
        <id>proxy-fing</id>
        <active>true</active>
        <protocol>http</protocol>
        <host>proxy.fing.edu.uy</host>
        <port>3128</port>
        <nonProxyHosts></nonProxyHosts>
    </proxy>'

# Crear el archivo si no existe
if [ ! -f "$archivo" ]; then
    mkdir -p "$(dirname "$archivo")"
    cat > "$archivo" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">
</settings>
EOF
    echo "Archivo settings.xml creado."
fi

# Verificar si el proxy ya existe
if grep -q "<id>proxy-fing</id>" "$archivo"; then
    echo "El proxy con ID 'proxy-fing' ya existe. No se realizarán cambios."
    exit 0
fi

# Verificar si ya tiene configurado <proxies>
if grep -q "<proxies>" "$archivo"; then
    # Insertar el proxy dentro de la sección <proxies>
    awk -v proxy="$proxy" '
    /<proxies>/ && !done {
        print $0
        print proxy
        done = 1
        next
    }
    { print $0 }
    ' "$archivo" > "$archivo.tmp" && mv "$archivo.tmp" "$archivo"
    echo "Proxy agregado correctamente dentro de la sección <proxies>."
else
    # Si no existe la sección <proxies>, agregarla junto con el proxy
    awk -v proxy="$proxy" '
    /<\/settings>/ && !done {
        print "  <proxies>"
        print proxy
        print "  </proxies>"
        done = 1
    }
    { print $0 }
    ' "$archivo" > "$archivo.tmp" && mv "$archivo.tmp" "$archivo"
    echo "Sección <proxies> y proxy agregados correctamente."
fi
