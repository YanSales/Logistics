package cadastrobd.model;

import cadastro.model.util.ConectorBD;
import cadastro.model.util.SequenceManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    public PessoaFisica getPessoa(int id) {
        String sql = "SELECT * FROM Pessoas p JOIN Pessoa_Fisica pf ON p.id = pf.id WHERE p.id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = ConectorBD.getSelect(stmt)) {
                if (rs.next()) {
                    return new PessoaFisica(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("logradouro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cpf")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM Pessoas p JOIN Pessoa_Fisica pf ON p.id = pf.id";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql);
             ResultSet rs = ConectorBD.getSelect(stmt)) {
            while (rs.next()) {
                pessoas.add(new PessoaFisica(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    public void incluir(PessoaFisica pessoa) {
        String sqlPessoa = "INSERT INTO Pessoas (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO Pessoa_Fisica (id, cpf) VALUES (?, ?)";
        int id = SequenceManager.getValue("pessoa_seq");
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica)) {
            conn.setAutoCommit(false);
            stmtPessoa.setInt(1, id);
            stmtPessoa.setString(2, pessoa.getNome());
            stmtPessoa.setString(3, pessoa.getLogradouro());
            stmtPessoa.setString(4, pessoa.getCidade());
            stmtPessoa.setString(5, pessoa.getEstado());
            stmtPessoa.setString(6, pessoa.getTelefone());
            stmtPessoa.setString(7, pessoa.getEmail());
            stmtPessoa.executeUpdate();

            stmtPessoaFisica.setInt(1, id);
            stmtPessoaFisica.setString(2, pessoa.getCpf());
            stmtPessoaFisica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterar(PessoaFisica pessoa) {
        String sqlPessoa = "UPDATE Pessoas SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id = ?";
        String sqlPessoaFisica = "UPDATE Pessoa_Fisica SET cpf = ? WHERE id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica)) {
            conn.setAutoCommit(false);
            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getLogradouro());
            stmtPessoa.setString(3, pessoa.getCidade());
            stmtPessoa.setString(4, pessoa.getEstado());
            stmtPessoa.setString(5, pessoa.getTelefone());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getId());
            stmtPessoa.executeUpdate();

            stmtPessoaFisica.setString(1, pessoa.getCpf());
            stmtPessoaFisica.setInt(2, pessoa.getId());
            stmtPessoaFisica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sqlPessoaFisica = "DELETE FROM Pessoa_Fisica WHERE id = ?";
        String sqlPessoa = "DELETE FROM Pessoas WHERE id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoaFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica);
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa)) {
            conn.setAutoCommit(false);
            stmtPessoaFisica.setInt(1, id);
            stmtPessoaFisica.executeUpdate();

            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
