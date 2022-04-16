/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Fornecedor;
import DAO.AccessDatabase;
import GUI.Principal;
import GUI.VerificarUsuario;
import Util.Funcoes;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public class RepositorioFornecedor implements RepositorioFornecedorInterface {

    @Override
    public void adcionar(Fornecedor item) {
        try {

            AccessDatabase a = new AccessDatabase();
            //  try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`fornecedores` (`nome`) VALUES ('" + item.getNome() + "')");
            //   }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());

        }
    }

    @Override
    public void alterar(String cod, Fornecedor cli) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector popularCombo() {
        Vector retorno = new Vector();
        retorno.addElement(" ");
        try {

            //  AccessDatabase a = new AccessDatabase();
            //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".fornecedores;");
            while (rs.next()) {
                String cod_nome = String.valueOf(rs.getInt("idfornecedor") + "- " + rs.getString("nome"));
                retorno.addElement(cod_nome);
            };

            // con.close();
            return retorno;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public Fornecedor procurar(String cod) {
        try {

            AccessDatabase a = new AccessDatabase();
            Fornecedor fornecedor;
            // try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".fornecedores WHERE idfornecedor = " + cod + "");
            rs.next();
            fornecedor = new Fornecedor();
            fornecedor.setIdfornecedor(rs.getString("idfornecedor"));
            fornecedor.setNome(rs.getString("nome"));
            fornecedor.setCnpj(rs.getString("cnpj"));
            fornecedor.setCodigo(rs.getString("codigo"));
            //  }
            return fornecedor;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public JTable listarTodos(String lote) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            //  AccessDatabase a = new AccessDatabase();
            //   Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT idfornecedor, nome FROM " + VerificarUsuario.banco + ".fornecedores");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Código");
            cabecalho.addElement("Nome");

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

            //con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }
}
