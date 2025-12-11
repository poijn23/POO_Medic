package Package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Consult_Database {
    private static final String DB_URL = "jdbc:mysql://34.31.14.40:3306/ProyectoDyPOO";
    private static final String DB_USER = "grupoPOO";
    private static final String DB_PASS = "LfftG2acd9Mv7%k7";


    private Connection getConnection() throws SQLException{
            return DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
    }

    public Connection isConnected() throws SQLException{
        Connection conn = getConnection();
        return conn;
    }

    public boolean validarUsuario(String usuario,String contrasenia) {
        String sql = "select * from view_name where ID = ? and Password = ?";

        try(Connection connection= getConnection()) {
            assert connection != null;
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1,usuario.trim());
                statement.setString(2,contrasenia.trim());
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                        return true;
                    }
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public String getRol(String name, String pass){
        String sql = "select role from view_name where ID = ? and Password = ?";
        String rol = null;
        try(Connection connection= getConnection()) {
            assert connection != null;
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1,name.trim());
                statement.setString(2,pass.trim());
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                       rol= rs.getString("role");
                    }
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return rol;
        }
        return rol;
    }

    public void createTuplaAlumnos(String usuario, String contrasenia, String nombre, Date fech_nac, String curp, String especial, String area){
        try(Connection conn=getConnection()){
            assert conn != null;
            String sql = "insert into Estudiantes (Nombre,ID,Password,Fech_Nac,CURP,Especialidad,Area) values (?,?,?,?,?,?,?)";
            try(PreparedStatement statement=conn.prepareStatement(sql)){
                statement.setString(1,nombre.trim());
                statement.setString(2,usuario);
                statement.setString(3,contrasenia.trim());
                statement.setDate(4,fech_nac);
                statement.setString(5,curp);
                statement.setString(6,especial);
                statement.setString(7,area);
                int rs = statement.executeUpdate();
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isInAsistencia(String usuario, Date today){
    try(Connection conn=getConnection()){
        assert conn != null;
        String sql = "select * from RegistroAsistencia where matricula = ? and fecha = ?";
        try(PreparedStatement statement=conn.prepareStatement(sql)){
            statement.setString(1,usuario.trim());
            statement.setDate(2,today);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    return true;
                }
            }
        }
    }catch(SQLException e){System.out.println(e.getMessage()); return false;}
        return false;
    }

    public boolean isInAsistencia(String usuario, Date today, Boolean asistencia){
        try(Connection conn=getConnection()){
            assert conn != null;
            String sql = "select * from RegistroAsistencia where matricula = ? and fecha = ? and asistencia = ?";
            try(PreparedStatement statement=conn.prepareStatement(sql)){
                statement.setString(1,usuario.trim());
                statement.setDate(2,today);
                statement.setBoolean(3,asistencia);
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                        return true;
                    }
                }
            }
        }catch(SQLException e){System.out.println(e.getMessage()); return false;}
        return false;
    }

    public void createTuplaAsistencia(String usuario, Date today, Time RegistroEntrada){
        try(Connection con=getConnection()){
            assert con != null;
            String sql="Insert into RegistroAsistencia (matricula,fecha,registroEntrada) values (?,?,?)";
            try(PreparedStatement statement=con.prepareStatement(sql)){
                statement.setString(1,usuario);
                statement.setDate(2,today);
                statement.setTime(3,RegistroEntrada);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registroSalida(String usuario, Date today,Time RegistroSalida){
        try(Connection con=getConnection()){
            assert con != null;
            String sql="update RegistroAsistencia set registroSalida=?,asistencia=? where matricula=? and fecha=?";
            try(PreparedStatement statement=con.prepareStatement(sql)){
                statement.setTime(1,RegistroSalida);
                statement.setInt(2,1);
                statement.setString(3,usuario);
                statement.setDate(4,today);

                statement.executeUpdate();
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public boolean createTuplaPersonal(String nombre, Date fechaNac, String rol, String password) {
        String sql = "INSERT INTO Personal (Password, Role, NOMBRE, FECHA_NACIMIENTO ) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            if (conn == null) return false; // Falló la conexión

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, password);
                statement.setString(2, rol.trim());
                statement.setString(3, nombre.trim());
                statement.setDate(4, fechaNac);

                int filasAfectadas = statement.executeUpdate();

                return filasAfectadas > 0;

            }
        } catch (SQLException e) {
            System.err.println("Error al insertar Personal en la BD.");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Dar de alta un curso
    public boolean createCurso(Curso curso) {
        String sql = "INSERT INTO Cursos (id, nombre, obligatorio, medicina, enfermeria, odontologia, nutriologia, fecha_inicio, fecha_fin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, curso.getId());
            stmt.setString(2, curso.getNombreCurso());
            stmt.setBoolean(3, curso.isObligatorio());
            stmt.setBoolean(4, curso.isMedicina());
            stmt.setBoolean(5, curso.isEnfermeria());
            stmt.setBoolean(6, curso.isOdontologia());
            stmt.setBoolean(7, curso.isNutriologia());

            if (curso.getFechaInicio() != null)
                stmt.setDate(8, java.sql.Date.valueOf(curso.getFechaInicio()));
            else
                stmt.setNull(8, Types.DATE);

            if (curso.getFechaFin() != null)
                stmt.setDate(9, java.sql.Date.valueOf(curso.getFechaFin()));
            else
                stmt.setNull(9, Types.DATE);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar curso: " + e.getMessage());
            return false;
        }
    }

    //Validar si el Id ya existe
    public boolean existsCurso(int id) {
        String sql = "SELECT id FROM Cursos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error verificando ID: " + e.getMessage());
            return true;
        }
    }

    //Obtener listado de cursos agregados
    public List<Curso> getAllCursos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, obligatorio, medicina, enfermeria, odontologia, nutriologia, fecha_inicio, fecha_fin FROM Cursos";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso c = new Curso();

                c.setId(rs.getInt("id"));
                c.setNombreCurso(rs.getString("nombre"));
                c.setObligatorio(rs.getBoolean("obligatorio"));
                c.setMedicina(rs.getBoolean("medicina"));
                c.setEnfermeria(rs.getBoolean("enfermeria"));
                c.setOdontologia(rs.getBoolean("odontologia"));
                c.setNutriologia(rs.getBoolean("nutriologia"));

                java.sql.Date ini = rs.getDate("fecha_inicio");
                java.sql.Date fin = rs.getDate("fecha_fin");

                c.setFechaInicio(ini != null ? ini.toLocalDate() : null);
                c.setFechaFin(fin != null ? fin.toLocalDate() : null);

                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos: " + e.getMessage());
        }

        return lista;
    }

    //Actualizar curso
    public boolean updateCurso (Curso curso) {
        String sql = "UPDATE Cursos SET nombre = ?, obligatorio = ?, medicina = ?, enfermeria = ?, odontologia = ?, nutriologia = ?, fecha_inicio = ?, fecha_fin = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombreCurso());
            stmt.setBoolean(2, curso.isObligatorio());
            stmt.setBoolean(3, curso.isMedicina());
            stmt.setBoolean(4, curso.isEnfermeria());
            stmt.setBoolean(5, curso.isOdontologia());
            stmt.setBoolean(6, curso.isNutriologia());

            if (curso.getFechaInicio() != null)
                stmt.setDate(7, java.sql.Date.valueOf(curso.getFechaInicio()));
            else
                stmt.setNull(7, Types.DATE);

            if (curso.getFechaFin() != null)
                stmt.setDate(8, java.sql.Date.valueOf(curso.getFechaFin()));
            else
                stmt.setNull(8, Types.DATE);

            stmt.setInt(9, curso.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar curso: " + e.getMessage());
            return false;
        }
    }

    //Eliminar un curso
    public boolean deleteCurso (int id) {
        String sql = "DELETE FROM Cursos WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar curso: " + e.getMessage());
            return false;
        }
    }

    //Obtener un curso por su ID
    public Curso getCursoByID(int id) {
        Curso curso = null;
        String sql = "SELECT id, nombre, obligatorio, medicina, enfermeria, odontologia, nutriologia, fecha_inicio, fecha_fin FROM Cursos WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    curso = new Curso();

                    curso.setId(rs.getInt("id"));
                    curso.setNombreCurso(rs.getString("nombre"));
                    curso.setObligatorio(rs.getBoolean("obligatorio"));
                    curso.setMedicina(rs.getBoolean("medicina"));
                    curso.setEnfermeria(rs.getBoolean("enfermeria"));
                    curso.setOdontologia(rs.getBoolean("odontologia"));
                    curso.setNutriologia(rs.getBoolean("nutriologia"));

                    java.sql.Date ini = rs.getDate("fecha_inicio");
                    java.sql.Date fin = rs.getDate("fecha_fin");

                    curso.setFechaInicio(ini != null ? ini.toLocalDate() : null);
                    curso.setFechaFin(fin != null ? fin.toLocalDate() : null);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener curso por ID: " + e.getMessage());
        }

        return curso;
    }

    public List<Object[]> getReporteAsistencias(String fechaDesde, String fechaHasta, String especialidad, String area, String matricula) {

        List<Object[]> resultados = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT RA.fecha, E.ID, E.Nombre, E.Especialidad, E.Area, RA.registroEntrada, RA.registroSalida ");
        sql.append("FROM RegistroAsistencia RA ");
        sql.append("JOIN Estudiantes E ON RA.matricula = E.ID ");
        sql.append("WHERE 1=1 ");

        List<String> params = new ArrayList<>();

        if (fechaDesde != null && !fechaDesde.isEmpty() && fechaHasta != null && !fechaHasta.isEmpty()) {
            sql.append("AND RA.fecha BETWEEN ? AND ? ");
            params.add(fechaDesde);
            params.add(fechaHasta);
        }

        if (!"Todas".equals(especialidad)) {
            sql.append("AND E.Especialidad = ? ");
            params.add(especialidad);
        }

        if (!"Todos".equals(area)) {
            sql.append("AND E.Area = ? ");
            params.add(area);
        }

        if (matricula != null && !matricula.isEmpty()) {
            sql.append("AND E.ID = ? ");
            params.add(matricula);
        }

        sql.append("ORDER BY RA.fecha, E.Nombre");

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                statement.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    java.sql.Time entrada = rs.getTime("registroEntrada");
                    java.sql.Time salida = rs.getTime("registroSalida");
                    double totalHoras = calcularHoras(entrada, salida);

                    Object[] fila = new Object[]{
                            rs.getDate("fecha"),
                            rs.getString("ID"),
                            rs.getString("Nombre"),
                            rs.getString("Especialidad"),
                            rs.getString("Area"),
                            entrada,
                            salida,
                            totalHoras
                    };
                    resultados.add(fila);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener reporte de asistencias: " + e.getMessage());
            e.printStackTrace();
        }
        return resultados;
    }

    private double calcularHoras(java.sql.Time entrada, java.sql.Time salida) {
        if (entrada == null || salida == null) return 0.0;
        long diff = salida.getTime() - entrada.getTime();
        return diff / (1000.0 * 60.0 * 60.0);
    }
}
