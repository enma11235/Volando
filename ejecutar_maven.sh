#!/bin/bash

# Directorio base donde están los proyectos
base_dir="$HOME/tpgr28"

# Directorios de los proyectos
server_dir="$base_dir/volandoPuntoUy"
web_dir="$base_dir/volandoPuntoUy_web"
mov_dir="$base_dir/volandoPuntoUy_mov"

# Destino final de los archivos empaquetados (directorio tpgr28)
dest_dir="$base_dir"

# Definir la ruta al archivo .jar copiado al directorio tpgr28
server_jar="$dest_dir/volandoPuntoUy-0.0.1-SNAPSHOT-jar-with-dependencies.jar"

# Función para compilar y empaquetar un proyecto Maven
build_project() {
    local project_dir=$1
    local output_file=$2

    echo "Compilando y empaquetando el proyecto en $project_dir..."
    cd "$project_dir" || { echo "Error: No se pudo acceder al directorio $project_dir"; exit 1; }

    # Limpiar y construir el proyecto
    mvn install -U || { echo "Error al compilar el proyecto en $project_dir"; exit 1; }

    # Verificar si el archivo esperado fue generado
    if [ ! -f "$output_file" ]; then
        echo "Error: No se encontró el archivo generado en $output_file"
        exit 1
    fi

    # Copiar el archivo al directorio destino
    echo "Copiando $output_file a $dest_dir"
    cp "$output_file" "$dest_dir" || { echo "Error al copiar $output_file a $dest_dir"; exit 1; }
    echo "Archivo $output_file copiado exitosamente."
}

# Compilar y empaquetar cada proyecto
build_project "$server_dir" "$server_dir/target/volandoPuntoUy-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
build_project "$web_dir" "$web_dir/target/volandoPuntoUy_web-0.0.1-SNAPSHOT.war"
build_project "$mov_dir" "$mov_dir/target/volandoPuntoUy_mov-0.0.1-SNAPSHOT.war"

echo "Todos los archivos se han copiado exitosamente al directorio $dest_dir."

# Preguntar al usuario si quiere ejecutar el .jar
while true; do
    read -p "¿Quieres ejecutar el servidor central (volandoPuntoUy)? (S/N): " choice
    case "$choice" in
        [Ss]* )  # Si elige S o s
            echo "Ejecutando el servidor central..."
            # Ejecutar el .jar desde el directorio tpgr28 (destino)
            if [ -f "$server_jar" ]; then
                cd "$base_dir"  # Cambiar al directorio tpgr28
                java -jar volandoPuntoUy-0.0.1-SNAPSHOT-jar-with-dependencies.jar

            else
                echo "Error: No se encontró el archivo en $server_jar"
                exit 1
            fi
            break
            ;;
        [Nn]* )  # Si elige N o n
            echo "No se ejecutará el servidor."
            break
            ;;
        * )  # Si no se ingresa una opción válida
            echo "Por favor ingresa S para ejecutar o N para no ejecutar."
            ;;
    esac
done
