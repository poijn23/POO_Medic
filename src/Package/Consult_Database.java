package Package;

import java.sql.*;

public class Consult_Database {
    private static final String DB_URL = "jdbc:mysql://34.31.14.40:3306/ProyectoDyPOO";
    private static final String DB_USER = "grupoPOO";
    private static final String DB_PASS = "LfftG2acd9Mv7%k7";


    private Connection getConnection() throws SQLException{
            return DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
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

    public boolean createTuplaPersonal(String nombre, Date fechaNac, String rol, String clavePersonal, String password) {
        String sql = "INSERT INTO Personal (Nombre, Fech_Nacimiento, Rol, ClavePersonal, Password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            if (conn == null) return false; // Falló la conexión

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, nombre.trim());
                statement.setDate(2, fechaNac); // java.sql.Date
                statement.setString(3, rol.trim());
                statement.setString(4, clavePersonal.trim());
                statement.setString(5, password);


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
}
