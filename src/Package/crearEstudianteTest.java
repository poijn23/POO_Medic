package Package;

import org.junit.Test;
import static org.junit.Assert.*;

public class crearEstudianteTest {

    @Test
    public void pruebaDatosCorrectos() {
        // 1. Instanciar (sin base de datos)
        crearEstudiante form = new crearEstudiante(null);

        // 2. Datos correctos
        boolean resultado = form.validarDatos(
                "Juan Perez",
                "A012345",
                "pass123",
                "CURP123456",
                "Medicina",
                "Regular"
        );

        // 3. Verificar
        assertTrue("Debería aceptar datos correctos", resultado);
    }

    @Test
    public void pruebaNombreVacio() {
        crearEstudiante form = new crearEstudiante(null);

        // Nombre vacío
        boolean resultado = form.validarDatos(
                "",
                "A012345",
                "pass123",
                "CURP", //18 digitos y validarla
                "Medicina",
                "Regular"
        );

        // Verificación (Esperamos FALSE)
        assertFalse("Debería fallar sin nombre", resultado);
    }

    @Test
    public void pruebaResidenteSinArea() {
        crearEstudiante form = new crearEstudiante(null);

        // Residente sin área
        boolean resultado = form.validarDatos(
                "Juan",
                "A01",
                "pass",
                "CURP",
                "", // Sin área
                "Residente"
        );

        // Verificación (Esperamos FALSE)
        assertFalse("Debería fallar residente sin área", resultado);
    }
}