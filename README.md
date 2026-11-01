# Guía de Ejecución - Script en Java (Conector JDBC)

Esta guía detalla los pasos necesarios para configurar el driver JDBC de MySQL manualmente, establecer la conexión a la base de datos local y ejecutar los script en Java 

## Paso 1: Descargar e integrar el Driver JDBC
Debes incorporar el conector de forma manual:
1. Descarga el archivo `.jar` oficial del conector **MySQL Connector/J** desde el sitio oficial de MySQL.
2. En tu entorno de desarrollo (como NetBeans), haz clic derecho sobre la carpeta **Libraries** de tu proyecto y selecciona **Add JAR/Folder**.
3. Selecciona el archivo `.jar` descargado para agregarlo al Classpath de tu proyecto.

## Paso 2: Configurar la Conexión a la Base de Datos
Configura y cambia las credenciales de la base de datos en el archivo `ConexionBD.java`. Completa con los datos del usuario con privilegios mínimos (`usuarioMinPriv`)
## Paso 3: Ejecuta el archivo adecuado
 * Etapa 4 (Consultas seguras con PreparedStatement): Ejecutar el archivo `Main.java`
 * Etapa 5 (Procedimiento almacenado): Configurar el id y cantidades del archivo `TransaccionService.java`, y posteriormente ejecutar el archivo                         `MainTransacciones.java`
