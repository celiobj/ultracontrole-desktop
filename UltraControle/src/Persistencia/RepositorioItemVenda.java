/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Estoque;
import Classes.ItemVenda;
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
public class RepositorioItemVenda implements RepositorioItemVendaInterface {

    @Override
    public boolean adcionar(ItemVenda item) {
        try {

            AccessDatabase a = new AccessDatabase();
            // try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`produto_venda` (`idproduto`, `idvenda`, `valorcusto`, `valorvenda`,`quantidade`,`situacao`,`tipo`) VALUES ('" + item.getIdProduto() + "', '" + item.getIdVenda() + "', '" + item.getValorCusto() + "', '" + item.getValorVenda() + "', '" + item.getQuantidade() + "','A', '" + item.getTipo() + "');");
            JOptionPane.showMessageDialog(null, "Registro Inserido com Sucesso!!!");
            return true;
            // }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
            return false;
        }
    }

    @Override
    public boolean alterar(String codigoVenda, String codigoProduto, String valor, String quantidade) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            st.execute("UPDATE " + VerificarUsuario.banco + ".produto_venda SET `quantidade`='" + quantidade + "',`valorvenda`='" + valor + "' WHERE `idvenda`='" + codigoVenda + "' and `idproduto`='" + codigoProduto + "'");
            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!!!");
            return true;
        } catch (HeadlessException | SQLException t) {
            JOptionPane.showMessageDialog(null, "Registro não inserido!!! Inconsistência nos valores inseridos - " + t.getMessage());
            return false;
        }
    }

    @Override
    public boolean remover(String codigoVenda, String codigoProduto) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            st.execute("DELETE FROM " + VerificarUsuario.banco + ".produto_venda WHERE `idvenda`='" + codigoVenda + "' and `idproduto`='" + codigoProduto + "'");
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso!!!");
            return true;
        } catch (HeadlessException | SQLException t) {
            JOptionPane.showMessageDialog(null, "Registro não removido!!! Inconsistência nos valores inseridos - " + t.getMessage());
            return false;
        }
    }

    @Override
    public ItemVenda verificarExistente(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarItemnaVenda(String idVenda, String idProduto, String tipo) {
        String registros = null;
        try {

            AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) as registros FROM " + VerificarUsuario.banco + ".produto_venda where idvenda = '" + idVenda + "' and idproduto = '" + idProduto + "' and tipo = '" + tipo + "';");
            rs.next();
            registros = rs.getString("registros");
            //con.close();
        } catch (SQLException t) {
            System.out.println(t.getMessage());
        }
        if (registros.equalsIgnoreCase("1")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Vector<Estoque> itensdeVenda(String idvenda) {
        Vector retorno = new Vector();
        retorno.addElement(" ");
        try {

            // AccessDatabase a = new AccessDatabase();
            //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".produto_venda where idvenda = '" + idvenda + "' and tipo = 'V';");
            while (rs.next()) {
                Estoque estoque = new Estoque();
                estoque.setCodigoProduto(String.valueOf(rs.getInt("idproduto")));
                estoque.setQuantidade(String.valueOf(rs.getInt("quantidade")));
                retorno.addElement(estoque);
            };

            // con.close();
            return retorno;

        } catch (Exception t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public Vector<Estoque> itensdeTroca(String idvenda, String tipo) {
        Vector retorno = new Vector();
        retorno.addElement(" ");
        try {

            // AccessDatabase a = new AccessDatabase();
            //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".produto_venda where idvenda = '" + idvenda + "' and tipo = '" + tipo + "';");
            while (rs.next()) {
                Estoque estoque = new Estoque();
                estoque.setCodigoProduto(String.valueOf(rs.getInt("idproduto")));
                estoque.setQuantidade(String.valueOf(rs.getInt("quantidade")));
                retorno.addElement(estoque);
            };

            // con.close();
            return retorno;

        } catch (Exception t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public boolean fechar(String cod) {

        try {

            // AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`produto_venda` SET situacao='F' WHERE idvenda = " + cod + "");
            //  con.close();
            //JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");
            return true;
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "ITEM - Erro: " + t.getMessage());
            return false;
        }

    }

    @Override
    public ItemVenda procurar(String cod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JTable listarTodos(String lote) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            // AccessDatabase a = new AccessDatabase();
            //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT codigo, idproduto, quantidade, valor_custo FROM " + VerificarUsuario.banco + ".produto_estoque where idlote =" + lote + " and situacao ='A'");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Código");
            cabecalho.addElement("Produto");
            cabecalho.addElement("Quantidade");
            cabecalho.addElement("Valor de Custo Unitário");

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
