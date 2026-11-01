
package consultasegurasql;

public class MainTransacciones {
    public static void main(String[] args) {
        //Instanciamos el servicio transaccional que creamos
        TransaccionService servicio = new TransaccionService();

        System.out.println("=== INICIANDO PRUEBA DE TRANSACCIÓN Y REINTENTOS ===");
        
        // Intentamos vender 2 unidades del producto con ID = 1
        int productoId = 2;
        int cantidadVendida = 2;

        servicio.registrarVentaConReintentos(productoId, cantidadVendida);
    }
}
