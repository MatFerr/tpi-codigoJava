package consultasegurasql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransaccionService {
    /* Registra una venta actualizando el stock de forma transaccional.
       Si ocurre un deadlock (Error 1213 / SQLState 40001), aplica hasta 2 reintentos inmediatos.   */
  
    public void registrarVentaConReintentos(int productoId, int cantidad) {
        int maxReintentos = 2;
        int intentoActual = 0;
        boolean transaccionExitosa = false;

        while (intentoActual <= maxReintentos && !transaccionExitosa) {
            Connection conexion = null;
            try {
                // Obtenemos la conexión 
                conexion = ConexionBD.obtenerConexion();
                
                // Desactivamos el autocommit para iniciar la transacción
                conexion.setAutoCommit(false);

                // Realizamos la operación de actualización de stock
                String sqlUpdateStock = "UPDATE producto SET stock = stock - ? WHERE id = ?";
                try (PreparedStatement pstmt = conexion.prepareStatement(sqlUpdateStock)) {
                    pstmt.setInt(1, cantidad);
                    pstmt.setInt(2, productoId);
                    pstmt.executeUpdate();
                }

                // Confirmamos la transacción (COMMIT)
                conexion.commit();
                transaccionExitosa = true;
                System.out.println("| Transacción confirmada (COMMIT) con éxito |");

            } catch (SQLException e) {
                // Verificamos si el error corresponde a un Deadlock (MySQL Error 1213 o SQLState 40001)
                if (e.getErrorCode() == 1213 || "40001".equals(e.getSQLState())) {
                    intentoActual++;
                    System.err.println("|ADVERTENCIA| Deadlock detectado (Intento " + intentoActual + " de " + maxReintentos + ") |");

                    // Revertir los cambios de esta iteración (ROLLBACK)
                    if (conexion != null) {
                        try {
                            conexion.rollback();
                            System.err.println("| Rollback ejecutado correctamente debido al interbloqueo |");
                        } catch (SQLException rollbackEx) {
                            System.err.println("| Error al ejecutar el rollback: " + rollbackEx.getMessage() + " |");
                        }
                    }

                    // Si superamos los reintentos permitidos, salimos del bucle
                    if (intentoActual > maxReintentos) {
                        System.err.println("| Se agotaron los reintentos máximos por deadlock | | Operación abortada |");
                        break;
                    }

                } else {
                    // Si es cualquier otro error SQL no recuperable automáticamente
                    System.err.println("| Error SQL |  " + e.getMessage());
                    
                    if (conexion != null) {
                        try {
                            conexion.rollback();
                        } catch (SQLException rollbackEx) {
                            System.err.println("| Error en rollback | " + rollbackEx.getMessage());
                        }
                    }
                    break; 
                }
            } finally {
                // Asegurar el cierre de la conexión en cada intento
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (SQLException closeEx) {
                        System.err.println("| No se pudo cerrar la conexión | " + closeEx.getMessage());
                    }
                }
            }
        }
    }
}
