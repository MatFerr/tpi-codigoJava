# Guía de Ejecución - Script en Java (Conector JDBC)

Esta guía detalla los pasos necesarios para configurar el driver JDBC de MySQL manualmente, establecer la conexión a la base de datos local y ejecutar el script en Java con consultas seguras (`PreparedStatement`).

## Paso 1: Descargar e integrar el Driver JDBC
Debes incorporar el conector de forma manual:
1. Descarga el archivo `.jar` oficial del conector **MySQL Connector/J** desde el sitio oficial de MySQL.
2. En tu entorno de desarrollo (como NetBeans), haz clic derecho sobre la carpeta **Libraries** de tu proyecto y selecciona **Add JAR/Folder**.
3. Selecciona el archivo `.jar` descargado para agregarlo al Classpath de tu proyecto.

## Paso 2: Configurar la Conexión a la Base de Datos
Configura y cambia las credenciales de la base de datos en el archivo `ConexionBD.java`. Completa con los datos del usuario con privilegios mínimos (`usuarioMinPriv`)
