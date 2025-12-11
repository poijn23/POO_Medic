package Package;

import org.junit.Test;
import static org.junit.Assert.*;

public class crearEstudianteTest {

    // CP_001: Validación de Campos Vacíos
    @Test
    public void testCP001_CamposVacios() {
        crearEstudiante formulario = new crearEstudiante(null);

        // 2. Probamos enviando el NOMBRE vacío
        boolean resultado = formulario.validarDatos(
                "",
                "A1",
                "123",
                "ABCD12345678901234" // CURP válida de 18
        );

        // 3. El resultado debe ser FALSE (rechazado)
        assertFalse("CP_001 Falló: El sistema debería rechazar nombres vacíos", resultado);
    }

    // CP_002: Validación de Longitud de CURP
    @Test
    public void testCP002_CurpCorta() {
        crearEstudiante formulario = new crearEstudiante(null);

        // 2. Probamos enviando una CURP de solo 5 caracteres
        boolean resultado = formulario.validarDatos(
                "Juan Perez",
                "A012345",
                "password123",
                "MA123"
        );

        // 3. El resultado debe ser FALSE
        assertFalse("CP_002 Falló: El sistema debería rechazar CURPs menores a 18 caracteres", resultado);
    }

    // CP_003: Registro Exitoso (Datos Válidos)
    @Test
    public void testCP003_RegistroExitoso() {
        crearEstudiante formulario = new crearEstudiante(null);

        // 2. Probamos enviando TODO CORRECTO
        boolean resultado = formulario.validarDatos(
                "Juan Perez",
                "A012345",
                "password123",
                "ABCD12345678901234"
        );

        // 3. El resultado debe ser TRUE (Aceptado)
        assertTrue("CP_003 Falló: El sistema debería aceptar datos correctos", resultado);
    }
}