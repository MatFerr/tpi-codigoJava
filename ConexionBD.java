package consultasegurasql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/tpi?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "usuarioMinPriv";
    private static final String PASSWORD = "contraseñaSegura";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
