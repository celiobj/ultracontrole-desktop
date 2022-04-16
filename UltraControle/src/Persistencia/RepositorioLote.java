/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Lote;
import DAO.AccessDatabase;
import GUI.VerificarUsuario;
import Util.Funcoes;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public class RepositorioLote implements RepositorioLoteInterface {

    @Override
    public boolean adcionar(Lote item) {
        try {
            AccessDatabase a = new AccessDatabase();
          //  try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`lote` (`data`, `valor`, `codigo`, `idfornecedor`, `frete`, `taxa`, `quantidade`,`situacao`) VALUES ('" + item.getData() + "', '" + item.getValor() + "', '" + item.getCodigo() + "', '" + item.getIdfornecedo() + "', '" + item.getFrete() + "', '" + item.getTaxa() + "', '" + item.getQuantidade() + "','A')");
                JOptionPane.showMessageDialog(null, "Lote aberto com Sucesso!!!");
                return true;
          //  }
        } catch (SQLException t) {

            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
            return false;
        }
    }

    @Override
    public void fechar(Lote item) {
        try {

           // AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`lote` SET situacao='F' WHERE codigo = " + item.getCodigo() + "");
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`produto_estoque` SET situacao='F' WHERE idlote = " + item.getCodigo() + "");
           // con.close();
            JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
        }
    }
    
    @Override
    public JTable detalharLote(String codigoLote) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT l.codigo, pe.idproduto, CONCAT(p.nome,' - ',t.descricao) as nome, pe.quantidade, l.data "
                    + "FROM " + VerificarUsuario.banco + ".lote l, " + VerificarUsuario.banco + ".produto_estoque pe, " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".tamanhoproduto t "
                    + "where l.codigo = pe.idlote "
                    + "and p.codigo = pe.idproduto "
                    + "and t.idtamanho = RIGHT(idproduto , 2) "
                    + "and l.codigo = '"+codigoLote+"' order by idproduto;");
                    rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cód Lote");
            cabecalho.addElement("Cód Produto");
            cabecalho.addElement("Nome");
            cabecalho.addElement("Quantidade");
            cabecalho.addElement("Data");

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
    public Lote verificarAberto() {
        try {
           // AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `" + VerificarUsuario.banco + "`.`lote` WHERE (situacao) ='A'");
            rs.next();

            Lote lote = new Lote();
            lote.setCodigo(rs.getString("codigo"));
            lote.setValor(Funcoes.paraFormatoDinheiro(rs.getDouble("valor")));
            lote.setIdfornecedo(String.valueOf(rs.getInt("idfornecedor")));
            lote.setFrete(Funcoes.paraFormatoDinheiro(rs.getDouble("frete")));
            lote.setTaxa(String.valueOf(rs.getDouble("taxa")).replace(".",","));
            lote.setQuantidade(rs.getInt("quantidade"));

            String dataResult = Funcoes.paraRecuperarData(String.valueOf(rs.getDate("data"))).replace("/", "");
            lote.setData(dataResult);
           // con.close();

            return lote;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

     @Override
    public boolean cancelar(String cod) {
    try {

           // AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`lote` SET situacao='C' WHERE codigo = " + cod + "");
           // con.close();
            //JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");
            return true;
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "LOTE - Erro: " + t.getMessage());
            return false;
        }    
    }
    
    @Override
    public int verificarQuantidadeItens(Lote lote) {
        try {
            //AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT SUM(quantidade) as quantidade FROM " + VerificarUsuario.banco + ".produto_estoque where idlote = '" + lote.getCodigo() + "'");
            rs.next();

            int quantidade = rs.getInt("quantidade");
           

            return quantidade;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return 0;
        }
    }

    @Override
    public void alterar(String cod, Lote cli) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lote procurar(String cod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String valorTotaldeLotes(boolean data, boolean calculo, String dataIni, String dataFim) {
        try {

           // AccessDatabase a = new AccessDatabase();
          //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            String saldo;
            if (data) {
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".lote where data  between '" + dataIni + "' and '" + dataFim + "' and situacao = 'F';");
                rs.next();
            } else {
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".lote where situacao = 'F';");
                rs.next();
            }
            if (calculo) {
                saldo = String.valueOf(rs.getDouble("valortotal"));
            } else {
                saldo = Funcoes.paraFormatoDinheiro(rs.getDouble("valortotal"));
            }

           // con.close();
            return saldo;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }

    @Override
    public JTable listarTodos(boolean data, String dataIni, String dataFim) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            //AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            if (data) {
                rs = st.executeQuery("SELECT l.codigo, f.nome, l.data, l.valor FROM " + VerificarUsuario.banco + ".lote l, " + VerificarUsuario.banco + ".fornecedores f where l.data  between '" + dataIni + "' and '" + dataFim + "' and l.situacao = 'F' and f.idfornecedor = l.idfornecedor order by data desc;");
                rs.next();
            } else {
                rs = st.executeQuery("SELECT l.codigo, f.nome, l.data, l.valor FROM " + VerificarUsuario.banco + ".lote l, " + VerificarUsuario.banco + ".fornecedores f where l.situacao = 'F' and f.idfornecedor = l.idfornecedor order by data desc;");
                rs.next();
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Lote");
            cabecalho.addElement("Fornecedor");
            cabecalho.addElement("Data");
            cabecalho.addElement("Total");

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
