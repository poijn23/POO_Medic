package Package;

import java.sql.*;

public class Consult_Database {
    private static final String DB_URL = "jdbc:mysql://34.31.14.40:3306/ProyectoDyPOO";
    private static final String DB_USER = "grupoPOO";
    private static final String DB_PASS = "LfftG2acd9Mv7%k7";

    public boolean validarUsuario(String usuario,String contrasenia) {
        String sql = "select * from view_name where ID = ? and Password = ?";

        try(Connection connection= getConnection();
            PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setString(1,usuario.trim());
            statement.setString(2,contrasenia.trim());
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    return true;
                }
            }
        }catch (SQLException e){
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
