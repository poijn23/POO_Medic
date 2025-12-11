package Package;
/*
import org.junit.Test;
import static org.junit.Assert.*;

public class crearEstudianteTest {

    // CP_001: Validación de Nombre Vacío
    @Test
    public void testCP001_NombreVacio() {
        crearEstudiante formulario = new crearEstudiante(null);

        //  NOMBRE vacío.
        boolean resultado = formulario.validarDatos(
                "",             //  Nombre vacío
                "AUTO",
                "AUTO",
                "ABCD12345678901234" // CURP válida
        );

        //FALSE (Rechazado)
        assertFalse("CP_001 Falló: Debería rechazar si falta el nombre", resultado);
    }

    // CP_002: Validación de Longitud de CURP
    @Test
    public void testCP002_CurpCorta() {
        crearEstudiante formulario = new crearEstudiante(null);

        // CURP de 5 caracteres
        boolean resultado = formulario.validarDatos(
                "Juan Perez",
                "AUTO",
                "AUTO",
                "MA123"         // Curp muy corta
        );

        // Esperamos FALSE
        assertFalse("CP_002 Falló: Debería rechazar CURPs menores a 18 caracteres", resultado);
    }

    // CP_003: Registro Exitoso (Flujo Correcto)
    @Test
    public void testCP003_RegistroExitoso() {
        crearEstudiante formulario = new crearEstudiante(null);

        boolean resultado = formulario.validarDatos(
                "Juan Perez",           // Nombre Correcto
                "AUTO",                 // (Se genera solo)
                "AUTO",                 // (Se genera solo)
                "ABCD12345678901234"    // CURP Correcta
        );

        // Esperamos TRUE (Aceptado)
    }
}
*/