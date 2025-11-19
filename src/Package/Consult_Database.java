package Package;

import java.sql.*;
import java.time.LocalDate;

public class Consult_Database {
    private static final String DB_URL = "jdbc:mysql://34.31.14.40:3306/ProyectoDyPOO";
    private static final String DB_USER = "grupoPOO";
    private static final String DB_PASS = "LfftG2acd9Mv7%k7";

    public boolean validarUsuario(String usuario,String contrasenia) {
        String sql = "select * from view_name where ID = ? and Password = ?";

        try(Connection connection= getConnection()) {
            assert connection != null;
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1,usuario.trim());
                statement.setString(2,contrasenia.trim());
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                        connection.close();
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

    public boolean createTuplaAlumnos(String usuario,String contrasenia,String nombre,Date fech_nac,String curp,String especial,String area){
        try(Connection conn=getConnection()){
            assert conn != null;
            String sql = "instert into Estudiantes ('Nombre','ID','Password','Fech_Nac','CURP','Especialidad','Area') values (?,?,?,?,?,?,?)";
            try(PreparedStatement statement=conn.prepareStatement(sql)){
                statement.setString(1,nombre.trim());
                statement.setString(2,usuario);
                statement.setString(3,contrasenia.trim());
                statement.setDate(4,fech_nac);
                statement.setString(5,curp);
                statement.setString(6,especial);
                statement.setString(7,area);
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                        conn.close();
                        return true;
                    }
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    private Connection getConnection(){
        try(
                Connection conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
        ) {
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
