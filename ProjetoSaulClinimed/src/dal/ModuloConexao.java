package dal;

import java.sql.*;

public class ModuloConexao {

    public static Connection conector() {

        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/SaulClinimed";
        String user = "root";
        String password = "";

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }
}
