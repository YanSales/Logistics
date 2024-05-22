package cadastrobd.model;

import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaJuridica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    // Método para obter uma pessoa jurídica a partir do seu id
    public PessoaJuridica getPessoa(int id) {
        String sqlPessoa = "SELECT * FROM Pessoas WHERE id = ?";
        String sqlPessoaJuridica = "SELECT * FROM Pessoa_Juridica WHERE id = ?";
        PessoaJuridica pessoa = null;

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {
             
            stmtPessoa.setInt(1, id);
            stmtPessoaJuridica.setInt(1, id);

            try (ResultSet rsPessoa = ConectorBD.getSelect(stmtPessoa);
                 ResultSet rsPessoaJuridica = ConectorBD.getSelect(stmtPessoaJuridica)) {

                if (rsPessoa.next() && rsPessoaJuridica.next()) {
                    pessoa = new PessoaJuridica(
                        rsPessoa.getInt("id"),
                        rsPessoa.getString("nome"),
                        rsPessoa.getString("logradouro"),
                        rsPessoa.getString("cidade"),
                        rsPessoa.getString("estado"),
                        rsPessoa.getString("telefone"),
                        rsPessoa.getString("email"),
                        rsPessoaJuridica.getString("cnpj")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoa;
    }

    // Método para obter todas as pessoas jurídicas do banco de dados
    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        String sqlPessoa = "SELECT * FROM Pessoas";
        String sqlPessoaJuridica = "SELECT * FROM Pessoa_Juridica";

        try (Connection conn = ConectorBD.getConnection();
             Statement stmtPessoa = conn.createStatement();
             Statement stmtPessoaJuridica = conn.createStatement();
             ResultSet rsPessoa = stmtPessoa.executeQuery(sqlPessoa);
             ResultSet rsPessoaJuridica = stmtPessoaJuridica.executeQuery(sqlPessoaJuridica)) {

            while (rsPessoa.next() && rsPessoaJuridica.next()) {
                PessoaJuridica pessoa = new PessoaJuridica(
                    rsPessoa.getInt("id"),
                    rsPessoa.getString("nome"),
                    rsPessoa.getString("logradouro"),
                    rsPessoa.getString("cidade"),
                    rsPessoa.getString("estado"),
                    rsPessoa.getString("telefone"),
                    rsPessoa.getString("email"),
                    rsPessoaJuridica.getString("cnpj")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoas;
    }

    // Método para incluir uma pessoa jurídica no banco de dados
    public void incluir(PessoaJuridica pessoa) {
        String sqlPessoa = "INSERT INTO Pessoas (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO Pessoa_Juridica (id, cnpj) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {

            conn.setAutoCommit(false);

            stmtPessoa.setInt(1, pessoa.getId());
            stmtPessoa.setString(2, pessoa.getNome());
            stmtPessoa.setString(3, pessoa.getLogradouro());
            stmtPessoa.setString(4, pessoa.getCidade());
            stmtPessoa.setString(5, pessoa.getEstado());
            stmtPessoa.setString(6, pessoa.getTelefone());
            stmtPessoa.setString(7, pessoa.getEmail());

            stmtPessoaJuridica.setInt(1, pessoa.getId());
            stmtPessoaJuridica.setString(2, pessoa.getCnpj());

            stmtPessoa.executeUpdate();
            stmtPessoaJuridica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para alterar os dados de uma pessoa jurídica no banco de dados
    public void alterar(PessoaJuridica pessoa) {
        String sqlPessoa = "UPDATE Pessoas SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id = ?";
        String sqlPessoaJuridica = "UPDATE Pessoa_Juridica SET cnpj = ? WHERE id = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {

            conn.setAutoCommit(false);

            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getLogradouro());
            stmtPessoa.setString(3, pessoa.getCidade());
            stmtPessoa.setString(4, pessoa.getEstado());
            stmtPessoa.setString(5, pessoa.getTelefone());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getId());

            stmtPessoaJuridica.setString(1, pessoa.getCnpj());
            stmtPessoaJuridica.setInt(2, pessoa.getId());

            stmtPessoa.executeUpdate();
            stmtPessoaJuridica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir uma pessoa jurídica do banco de dados
    public void excluir(int id) {
        String sqlPessoaJuridica = "DELETE FROM Pessoa_Juridica WHERE id = ?";
        String sqlPessoa = "DELETE FROM Pessoas WHERE id = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica);
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa)) {

            conn.setAutoCommit(false);

            stmtPessoaJuridica.setInt(1, id);
            stmtPessoa.setInt(1, id);

            stmtPessoaJuridica.executeUpdate();
            stmtPessoa.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
