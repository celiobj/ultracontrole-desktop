/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Produto;
import DAO.AccessDatabase;
import GUI.Principal;
import GUI.VerificarUsuario;
import Util.Funcoes;
import java.awt.HeadlessException;
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
public class RepositorioProduto implements RepositorioProdutoInterface {

    @Override
    public void adcionar(Produto produto) {
        try {

            AccessDatabase a = new AccessDatabase();
            // try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            st.execute("INSERT INTO " + VerificarUsuario.banco + ".produtos(nome,descricao,idtamanho,idtipo, codigo, idfornecedor) VALUES('" + produto.getNome() + "','" + produto.getDescricao() + "','" + produto.getTamanho() + "','" + produto.getTipo() + "','" + produto.getCodigo() + "','" + produto.getFornecedor() + "')");
            JOptionPane.showMessageDialog(null, "Registro Inserido com Sucesso!!!");
            //  }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());

        }
    }

    @Override
    public Produto verificarExistente(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterar(String cod, Produto produto) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            st.execute("UPDATE " + VerificarUsuario.banco + ".produtos SET `codigo`='" + produto.getCodigo() + "',`nome`='" + produto.getNome() + "', `descricao`='" + produto.getDescricao() + "', `idtamanho`='" + produto.getTamanho() + "', `idtipo`='" + produto.getTipo() + "', `idfornecedor`='" + produto.getFornecedor() + "'    WHERE `idprodutos`='" + cod + "'");
            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!!!");
            return true;
        } catch (HeadlessException | SQLException t) {
            JOptionPane.showMessageDialog(null, "Registro não inserido!!! Inconsistência nos valores inseridos - " + t.getMessage());
            return false;
        }
    }

    @Override
    public Produto procurar(String cod) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".produtos WHERE codigo = " + cod + "");
            rs.next();
            Produto produto = new Produto();
            produto.setIdProduto(rs.getString("idprodutos"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setTamanho(rs.getString("idtamanho"));
            produto.setTipo(rs.getString("idtipo"));
            produto.setCodigo(rs.getString("codigo"));
            produto.setFornecedor(rs.getString("idfornecedor"));

            return produto;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    @Override
    public Vector popularCombo() {
        Vector retorno = new Vector();
        retorno.addElement(" ");
        try {

            AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT p.nome, p.codigo, t.descricao FROM " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".tamanhoproduto t "
                    + "where p.idtamanho = t.idtamanho;");
            while (rs.next()) {
                String cod_nome = rs.getString("nome") + " - " + rs.getString("descricao") + " -" + rs.getString("codigo");
                retorno.addElement(cod_nome);
            };

            //  con.close();
            return retorno;

        } catch (Exception t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public Produto procurarNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JTable listarTodos() {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            // AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT p.codigo, p.nome,p.descricao, ta.descricao, ti.descricao, f.nome"
                    + " FROM " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".tamanhoproduto ta, " + VerificarUsuario.banco + ".tipoproduto ti, " + VerificarUsuario.banco + ".fornecedores f"
                    + " WHERE ta.idtamanho = p.idtamanho AND ti.idtipoproduto = p.idtipo AND f.idfornecedor = p.idfornecedor;");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Código");
            cabecalho.addElement("Nome");
            cabecalho.addElement("Descrição");
            cabecalho.addElement("Tamanho");
            cabecalho.addElement("Tipo");
            cabecalho.addElement("Fornecedor");

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

            // con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }
}
