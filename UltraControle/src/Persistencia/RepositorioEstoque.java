/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Estoque;
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
public class RepositorioEstoque implements RepositorioEstoqueInterface {

    @Override
    public void adcionar(Estoque item) {
        try {

            AccessDatabase a = new AccessDatabase();
           // try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                st.execute("INSERT INTO `"+VerificarUsuario.banco+"`.`estoque` (`idProduto`, `quantidade`) VALUES ('" + item.getCodigoProduto() + "', '" + item.getQuantidade() + "')");
          //  }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());

        }
    }

    @Override
    public Estoque verificarExistente(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterar(Estoque estoque, boolean operacao) {
        try {

            AccessDatabase a = new AccessDatabase();
         //   Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            if (operacao) {
                st.executeUpdate("UPDATE "+VerificarUsuario.banco+".estoque SET quantidade = quantidade+'" + estoque.getQuantidade() + "' WHERE idProduto ='" + estoque.getCodigoProduto() + "';");
            } else {
                st.executeUpdate("UPDATE "+VerificarUsuario.banco+".estoque SET quantidade = quantidade-'" + estoque.getQuantidade() + "' WHERE idProduto ='" + estoque.getCodigoProduto() + "';");
            }
           // con.close();
            //JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");
            return true;
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "ESTOQUE - Erro: " + t.getMessage());
            return false;
        }
    }

    @Override
    public Estoque procurar(String cod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean verificarSaldoEstoque(String cod, String quantidadeVendida) {
        try {
            
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT quantidade FROM "+VerificarUsuario.banco+".estoque where idProduto = '"+cod+"';");
            rs.next();

            int saldo = rs.getInt("quantidade");
            if(Integer.parseInt(quantidadeVendida)>saldo){
                JOptionPane.showMessageDialog(null, "Saldo insuficiente:\n"
                + "Quantidade solicitada: "+quantidadeVendida+"\n"
                + "Quantidade em estoque: "+saldo+"");
                return false;
            }else{
                return true;
            }
           

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return false;

        }
    }


    @Override
    public JTable listarTodos(String lote) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
          //  AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT P.CODIGO, p.nome, t.descricao,  e.quantidade, "
                    + "(SELECT MAX(pe.valor_custo) FROM "+VerificarUsuario.banco+".produto_estoque pe where pe.idproduto = e.idProduto and pe.situacao = 'F') as valorcustomedio, "
                    + "(SELECT MAX(pe.valor_venda) FROM "+VerificarUsuario.banco+".produto_estoque pe where pe.idproduto = e.idProduto and pe.situacao = 'F') as valorvendamedio "
                    + "FROM "+VerificarUsuario.banco+".estoque e,  "+VerificarUsuario.banco+".produtos p, "+VerificarUsuario.banco+".tamanhoproduto t "
                    + "WHERE e.idProduto = p.codigo "
                    + "and p.idtamanho = t.idtamanho order by p.nome, p.codigo;");
                   rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Código");
            cabecalho.addElement("Produto");
            cabecalho.addElement("Tamanho");
            cabecalho.addElement("Quantidade");
            cabecalho.addElement("Custo");
            cabecalho.addElement("Venda");

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
