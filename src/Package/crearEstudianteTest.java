package Package;

import org.junit.Test;
import static org.junit.Assert.*;

public class crearEstudianteTest {

    // CP_001: Validación de Campos Vacíos
    @Test
    public void testCP001_CamposVacios() {
        crearEstudiante formulario = new crearEstudiante(null);

        // NOMBRE vacío
        boolean resultado = formulario.validarDatos(
                "",             //ERROR: Nombre vacío
                "A012345",
                "password123",
                "ABCD12345678901234"
        );

        //  (Rechazado)
        assertFalse("CP_001 Falló: El sistema debería rechazar nombres vacíos", resultado);
    }

    // CP_002: Validación de Longitud de CURP
    @Test
    public void testCP002_CurpCorta() {
        crearEstudiante formulario = new crearEstudiante(null);

        // 2. Probar  CURP de 5 caracteres
        boolean resultado = formulario.validarDatos(
                "Juan Perez",
                "A012345",
                "password123",
                "MA123"
        );

        // 3. FALSE
        assertFalse("CP_002 Falló: El sistema debería rechazar CURPs cortas", resultado);
    }

    // CP_003: Registro Exitoso
    @Test
    public void testCP003_RegistroExitoso() {
        crearEstudiante formulario = new crearEstudiante(null);

        // 2. Probamos enviando TODO CORRECTO
        boolean resultado = formulario.validarDatos(
                "Juan Perez",
                "A012345",
                "password123",
                "ABCD12345678901234" // 18 caracteres
        );

        // 3. Esperamos TRUE
        assertTrue("CP_003 Falló: El sistema debería aceptar datos correctos", resultado);
    }
}