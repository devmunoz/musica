#!/bin/bash

# Variables
SRC_DIR="Musica/src"
BIN_DIR="bin"
OUTPUT_DIR="output"
JAR_NAME="musica.jar"
MANIFEST_FILE="Musica/MANIFEST.MF"
MYSQL_DRIVER_JAR="mysql-connector-j-8.4.0.jar"

# Crear directorios si no existen
mkdir -p "$BIN_DIR"
mkdir -p "$OUTPUT_DIR"

# Eliminar el archivo JAR existente si existe
if [ -f "$OUTPUT_DIR/$JAR_NAME" ]; then
    echo "Eliminando el archivo JAR existente: $OUTPUT_DIR/$JAR_NAME"
    rm "$OUTPUT_DIR/$JAR_NAME"
fi

# Limpiar el directorio bin/ si existe
if [ -d "$BIN_DIR" ]; then
    echo "Limpiando el directorio bin/"
    rm -rf "$BIN_DIR/*"
fi

# Compilar los archivos .java
echo "Compilando los archivos Java..."
javac -d "$BIN_DIR" $(find "$SRC_DIR" -name "*.java")

# Verificar si la compilación fue exitosa
if [ $? -ne 0 ]; then
    echo "Error en la compilación. Abortando."
    exit 1
fi

# Copiar los recursos al directorio bin/
echo "Copiando recursos..."
cp -r "$SRC_DIR/canciones/swing/res" "$BIN_DIR/canciones/swing/"

# Extraer el contenido del driver de MySQL al directorio bin/
echo "Incluyendo el driver de MySQL..."
cd "$BIN_DIR"
jar xf "../Musica/libs/$MYSQL_DRIVER_JAR"
cd ..

# Crear el JAR ejecutable incluyendo todas las clases y recursos
echo "Creando el archivo JAR ejecutable..."
jar cfm "$OUTPUT_DIR/$JAR_NAME" "$MANIFEST_FILE" -C "$BIN_DIR" .

# Verificar si el JAR fue creado exitosamente
if [ $? -eq 0 ]; then
    echo "El archivo JAR se ha creado exitosamente en $OUTPUT_DIR/$JAR_NAME"
else
    echo "Error al crear el archivo JAR."
    exit 1
fi

# Limpiar el directorio bin/
echo "Limpiando el directorio bin/"
rm -rf "$BIN_DIR"

echo "Compilación y empaquetado completados."