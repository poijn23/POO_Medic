package Package;

import org.junit.Test;
import static org.junit.Assert.*;

// Mock simple de Consult_Database
class MockDB extends Consult_Database {
    @Override
    public boolean existsCurso(int id) {
        // Simula que solo el ID 1 ya existe
        return id == 1;
    }
}

public class RegistrarCursoFormTest {

    private final MockDB db = new MockDB();

    // CP_001: ID vacío o negativo
    @Test
    public void testIDInvalido() {
        RegistrarCursoForm form = new RegistrarCursoForm(db);

        boolean resultado = form.validarDatos(
                0,              // ID inválido
                "Curso A",
                "2025-12-01",
                "2025-12-10",
                true, false, false, false,
                db
        );

        assertFalse("CP_001 Falló: ID inválido debería retornar false", resultado);
    }

    // CP_002: ID ya existe
    @Test
    public void testIDExistente() {
        RegistrarCursoForm form = new RegistrarCursoForm(db);

        boolean resultado = form.validarDatos(
                1,              // ID que mock DB dice que ya existe
                "Curso B",
                "2025-12-01",
                "2025-12-10",
                true, false, false, false,
                db
        );

        assertFalse("CP_002 Falló: ID existente debería retornar false", resultado);
    }

    // CP_003: Nombre vacío
    @Test
    public void testNombreVacio() {
        RegistrarCursoForm form = new RegistrarCursoForm(db);

        boolean resultado = form.validarDatos(
                2,
                "",              // nombre vacío
                "2025-12-01",
                "2025-12-10",
                true, false, false, false,
                db
        );

        assertFalse("CP_003 Falló: Nombre vacío debería retornar false", resultado);
    }

    // CP_004: Fecha inválida (fin antes de inicio)
    @Test
    public void testFechaInvalida() {
        RegistrarCursoForm form = new RegistrarCursoForm(db);

        boolean resultado = form.validarDatos(
                2,
                "Curso C",
                "2025-12-10",
                "2025-12-01",  // fin antes de inicio
                true, false, false, false,
                db
        );

        assertFalse("CP_004 Falló: Fecha fin antes de inicio debería retornar false", resultado);
    }

    // CP_005: Ninguna especialidad seleccionada
    @Test
    public void testSinEspecialidad() {
        RegistrarCursoForm form = new RegistrarCursoForm(db);

        boolean resultado = form.validarDatos(
                2,
                "Curso D",
                "2025-12-01",
                "2025-12-10",
                false, false, false, false,   // ninguna seleccionada
                db
        );

        assertFalse("CP_005 Falló: Sin especialidad debería retornar false", resultado);
    }

    // CP_006: Datos correctos
    @Test
    public void testDatosValidos() {
        RegistrarCursoForm form = new RegistrarCursoForm(db);

        boolean resultado = form.validarDatos(
                2,
                "Curso E",
                "2025-12-01",
                "2025-12-10",
                true, false, false, false,
                db
        );

        assertTrue("CP_006 Falló: Datos válidos deberían retornar true", resultado);
    }
}
