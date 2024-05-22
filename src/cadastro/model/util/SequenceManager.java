package cadastro.model.util;

import java.sql.*;

public class SequenceManager {
    
    // Método para obter o próximo valor de uma sequência
    public static int getValue(String sequenceName) {
        String sql = "SELECT nextval('" + sequenceName + "')";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql);
             ResultSet rs = ConectorBD.getSelect(stmt)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Retorna -1 em caso de falha
    }
}
