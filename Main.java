package consultasegurasql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Intentando conectar usando ConexionBD...\n");

        // Utilizamos tu clase ConexionBD para obtener la conexión
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            System.out.println("| Conexión establecida con éxito |\n");
            System.out.println("--- DEMOSTRACIÓN DE INYECCIÓN SQL ---\n");

            // Entrada maliciosa típica que un atacante podría introducir en un formulario
            String entradaMaliciosa = "' OR '1'='1";

            // 1) Ejemplo vulnerable (Concatenación de Strings)
            
            System.out.println("1) Ejecutando consulta VULNERABLE:");
            
            // Si concatenamos directamente la variable, el atacante altera la estructura SQL:
            String sqlVulnerable = "SELECT * FROM vw_catalogo_publico WHERE nombre = '" + entradaMaliciosa + "'";
            System.out.println("SQL generado: " + sqlVulnerable);
            
            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlVulnerable)) {
                
                System.out.println("| Resultado vulnerable | La inyección modificó la lógica y expuso registros:");
                while (rs.next()) {
                    System.out.println("-- Registro expuesto: ID = " + rs.getLong("id") + " | Nombre = " + rs.getString("nombre"));
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("\n--------------------------------------------------\n");

            // 2) Ejemplo seguro (Usando PreparedStatement)
            
            System.out.println("2) Ejecutando consulta SEGURA con PreparedStatement:");
            
            // La estructura usa un marcador '?' en lugar de concatenar
            String sqlSegura = "SELECT * FROM vw_catalogo_publico WHERE nombre = ?";
            
            try (PreparedStatement pstmt = conexion.prepareStatement(sqlSegura)) {
                // Asignamos la entrada maliciosa de forma tipada y controlada
                pstmt.setString(1, entradaMaliciosa);
                
                try (ResultSet rsSeguro = pstmt.executeQuery()) {
                    boolean encontro = false;
                    while (rsSeguro.next()) {
                        encontro = true;
                        System.out.println("-> " + rsSeguro.getString("nombre"));
                    }
                    if (!encontro) {
                        System.out.println("Resultado: No se encontró ninguna categoría con ese nombre exacto");
                        System.out.println("| Ataque neutralizado con éxito | El motor trató todo como texto plano");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de conexión o SQL: " + e.getMessage());
        }
    }
}
