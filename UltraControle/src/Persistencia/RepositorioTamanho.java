/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.TamanhoProduto;
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
public class RepositorioTamanho implements RepositorioTamanhoInterface {

    @Override
    public void adcionar(TamanhoProduto item) {
        try {

            AccessDatabase a = new AccessDatabase();
            //try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`tamanhoproduto` (`descricao`) VALUES ('" + item.getDescricao() + "')");
          //  }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());

        }
    }

    @Override
    public void alterar(String cod, TamanhoProduto cli) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TamanhoProduto procurar(String cod) {
    try {

            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".tamanhoproduto WHERE idtamanho = " + cod + "");
            rs.next();
            TamanhoProduto tamanhoProduto = new TamanhoProduto();
            tamanhoProduto.setIdTamanho(rs.getString("idtamanho"));
            tamanhoProduto.setDescricao(rs.getString("descricao"));
            
            return tamanhoProduto;

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

           // AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".tamanhoproduto;");
            while (rs.next()) {
                String cod_nome = String.valueOf(rs.getInt("idtamanho") + "- " + rs.getString("descricao"));
                retorno.addElement(cod_nome);
            };

            //con.close();
            return retorno;

        } catch (Exception t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public JTable listarTodos(String lote) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
           // AccessDatabase a = new AccessDatabase();
           //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + VerificarUsuario.banco + ".tamanhoproduto");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Código");
            cabecalho.addElement("Descrição");

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
