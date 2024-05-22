package cadastrobd.teste;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridicaDAO;

import java.util.List;

public class CadastroBDTeste {

    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        // Instanciar uma pessoa física e persistir no banco de dados
        PessoaFisica pf = new PessoaFisica(1, "João Silva", "Rua A", "Cidade X", "Estado Y", "123456789", "joao@example.com", "123.456.789-00");
        pessoaFisicaDAO.incluir(pf);

        // Alterar os dados da pessoa física no banco
        pf.setNome("João Silva Alterado");
        pessoaFisicaDAO.alterar(pf);

        // Consultar todas as pessoas físicas do banco de dados e listar no console
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        System.out.println("Pessoas Físicas:");
        for (PessoaFisica pessoa : pessoasFisicas) {
            pessoa.exibir();
            System.out.println();
        }

        // Excluir a pessoa física criada anteriormente no banco
        pessoaFisicaDAO.excluir(pf.getId());

        // Instanciar uma pessoa jurídica e persistir no banco de dados
        PessoaJuridica pj = new PessoaJuridica(2, "Empresa XYZ", "Avenida B", "Cidade W", "Estado Z", "987654321", "empresa@example.com", "12.345.678/0001-00");
        pessoaJuridicaDAO.incluir(pj);

        // Alterar os dados da pessoa jurídica no banco
        pj.setNome("Empresa XYZ Alterada");
        pessoaJuridicaDAO.alterar(pj);

        // Consultar todas as pessoas jurídicas do banco e listar no console
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        System.out.println("Pessoas Jurídicas:");
        for (PessoaJuridica pessoa : pessoasJuridicas) {
            pessoa.exibir();
            System.out.println();
        }

        // Excluir a pessoa jurídica criada anteriormente no banco
        pessoaJuridicaDAO.excluir(pj.getId());
    }
}
