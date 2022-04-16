/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.ItemEntrada;
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
public class RepositorioItemEstoque implements RepositorioItemEstoqueInterface {

    @Override
    public boolean adcionar(ItemEntrada item) {
        try {

            AccessDatabase a = new AccessDatabase();
            //   try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`produto_estoque` (`idproduto`, `idlote`, `valor_compra`, `taxa`, `frete`, `quantidade`, `valor_custo`, `valor_venda`, `situacao`) VALUES ('" + item.getIdProduto() + "', '" + item.getIdLote() + "', '" + item.getValorCompra() + "', '" + item.getTaxaCompra() + "', '" + item.getFrete() + "', '" + item.getQuantidade() + "', '" + item.getValorCusto() + "', '" + item.getValorVenda()+ "', '" + item.getSituacao()+ "')");
            JOptionPane.showMessageDialog(null, "Registro Inserido com Sucesso!!!");
            return true;
            //   }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
            return false;

        }
    }

    @Override
    public String verificarValorMedioCusto(String cod) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            String valorCusto;
            //rs = st.executeQuery("SELECT SUM(valor_custo*quantidade)/SUM(quantidade) as valorcustomedio FROM " + VerificarUsuario.banco + ".produto_estoque where idproduto = '" + cod + "';");
            rs = st.executeQuery("SELECT MAX(valor_custo) as valorcustomedio FROM " + VerificarUsuario.banco + ".produto_estoque where idproduto = '" + cod + "';");
            rs.next();

            valorCusto = rs.getString("valorcustomedio");

            return valorCusto;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
        }
        return null;
    }
    
    @Override
    public String verificarValorMedioVenda(String cod) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            String valorCusto;
            //rs = st.executeQuery("SELECT SUM(valor_venda*quantidade)/SUM(quantidade) as valorvendamedio FROM " + VerificarUsuario.banco + ".produto_estoque where idproduto = '" + cod + "';");
           rs = st.executeQuery("SELECT MAX(valor_venda) as valorvendamedio FROM " + VerificarUsuario.banco + ".produto_estoque where idproduto = '" + cod + "';");
           rs.next();

            valorCusto = rs.getString("valorvendamedio");

            return valorCusto;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
        }
        return null;
    }

    @Override
    public boolean consultarItemnaCompra(String idLote, String idProduto) {
        String registros = null;
        try {

            AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) as registros FROM " + VerificarUsuario.banco + ".produto_estoque where idlote = '" + idLote + "' and idproduto = '" + idProduto + "';");
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
    public ItemEntrada verificarExistente(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterar(String codigoLote, String codigoProduto, String quantidade) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            st.execute("UPDATE " + VerificarUsuario.banco + ".produto_estoque SET `quantidade`='" + quantidade + "' WHERE `idlote`='" + codigoLote + "' and `idproduto`='" + codigoProduto + "'");
            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!!!");
            return true;
        } catch (HeadlessException | SQLException t) {
            JOptionPane.showMessageDialog(null, "Registro não inserido!!! Inconsistência nos valores inseridos - " + t.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean precificar(String codigoProduto, String preco) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            st.execute("UPDATE " + VerificarUsuario.banco + ".produto_estoque SET `valor_venda`='" + preco + "' WHERE `idproduto`='" + codigoProduto + "'");
            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!!!");
            return true;
        } catch (HeadlessException | SQLException t) {
            JOptionPane.showMessageDialog(null, "Registro não inserido!!! Inconsistência nos valores inseridos - " + t.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean remover(String codigoLote, String codigoProduto) {
        try {

            Statement st = VerificarUsuario.con.createStatement();
            st.execute("DELETE FROM " + VerificarUsuario.banco + ".produto_estoque WHERE `idlote`='" + codigoLote + "' and `idproduto`='" + codigoProduto + "'");
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso!!!");
            return true;
        } catch (HeadlessException | SQLException t) {
            JOptionPane.showMessageDialog(null, "Registro não removido!!! Inconsistência nos valores inseridos - " + t.getMessage());
            return false;
        }
    }

    @Override
    public ItemEntrada procurar(String cod) {
     try {

            AccessDatabase a = new AccessDatabase();
            ItemEntrada itemEntrada;
          //  try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM "+VerificarUsuario.banco+".produto_estoque WHERE idproduto = " + cod + "AND situacao = 'F'");
                rs.next();
                itemEntrada = new ItemEntrada();
                itemEntrada.setIdItemEntrada(rs.getString("iditem_estoque"));
                itemEntrada.setIdProduto(rs.getString("idproduto"));
                itemEntrada.setIdLote(rs.getString("idlote"));
                itemEntrada.setValorCompra(rs.getString("valor_compra"));
                itemEntrada.setTaxaCompra(rs.getString("taxa"));
                itemEntrada.setFrete(rs.getString("frete"));
                itemEntrada.setQuantidade(rs.getInt("quantidade"));
                itemEntrada.setValorCusto(rs.getString("valor_custo"));
                itemEntrada.setValorVenda(rs.getString("valor_venda"));
//  }
            return itemEntrada;

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
            ResultSet rs = st.executeQuery("SELECT p.codigo, p.nome, tp.descricao, pe.quantidade, pe.valor_custo FROM " + VerificarUsuario.banco + ".produto_estoque pe, " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".tamanhoproduto tp "
                    + "where p.codigo = pe.idproduto "
                    + "and tp.idtamanho = p.idtamanho "
                    + "AND pe.situacao = 'A' "
                    + "and pe.idlote =" + lote);
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cód");
            cabecalho.addElement("Produto");
            cabecalho.addElement("Tamanho");
            cabecalho.addElement("Quantidade");
            cabecalho.addElement("Valor de Custo(Un)");

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
    @Override
    public JTable listarparaPrecificar() {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            //  AccessDatabase a = new AccessDatabase();
            //   Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT pe.idproduto, p.nome, pe.valor_custo, pe.valor_venda "
                    + "FROM " + VerificarUsuario.banco + ".produto_estoque pe, " + VerificarUsuario.banco + ".produtos p "
                    + "where p.codigo= pe.idproduto AND pe.situacao = 'F';");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cód");
            cabecalho.addElement("Produto");
            cabecalho.addElement("Valor de Custo(Un)");
            cabecalho.addElement("Valor de Venda(Un)");
            
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
