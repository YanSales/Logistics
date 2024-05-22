package cadastro.model.util;

import java.sql.*;

public class ConectorBD {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SistemaComercial;encrypt=true;trustServerCertificate=true;loginTimeout=30";
    private static final String USUARIO = "Loja";
    private static final String SENHA = "Loja";

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    // Método para obter um PreparedStatement a partir de uma conexão e um SQL
    public static PreparedStatement getPrepared(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    // Método para obter um ResultSet a partir de um PreparedStatement
    public static ResultSet getSelect(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

    // Métodos sobrecarregados para fechar recursos do banco de dados

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Métodos sobrecarregados para fechar múltiplos recursos ao mesmo tempo

    public static void close(Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(conn);
    }
}
