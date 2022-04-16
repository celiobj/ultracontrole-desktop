/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Movimentacao;
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
public class RepositorioMovimentacao implements RepositorioMovimentacaoInterface {

    @Override
    public boolean adcionar(Movimentacao movimentacao) {
        try {

            AccessDatabase a = new AccessDatabase();
            // try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`movimentacao` (`tipo`, `descricao`, `valor`, `data`, `situacao`) VALUES ('" + movimentacao.getTipo() + "', '" + movimentacao.getDescricao() + "', '" + movimentacao.getValor() + "', '" + movimentacao.getData() + "', '" + movimentacao.getSituacao()+ "');");
            // }
            return true;

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
            return false;
        }
    }

    @Override
    public boolean alterar(Movimentacao movimentacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String valorTotaldeMovimentacao(boolean data, boolean calculo, String dataIni, String dataFim) {
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
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".movimentacao where situacao = 'A';");
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
    public boolean remover(Movimentacao movimentacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JTable listarTodos(boolean data, String dataIni, String dataFim) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            //AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = null;
            if (data) {
                rs = st.executeQuery("SELECT m.idmovimentacao,tv.descricao, m.descricao, m.valor, m.data FROM " + VerificarUsuario.banco + ".movimentacao m, " + VerificarUsuario.banco + ".tipovenda tv where m.data  between '" + dataIni + "' and '" + dataFim + "' and m.tipo = tv.idtipo_venda and m.situacao = 'A';");
            } else {
                rs = st.executeQuery("SELECT m.idmovimentacao, tv.descricao, m.descricao, m.valor, m.data FROM " + VerificarUsuario.banco + ".movimentacao m, " + VerificarUsuario.banco + ".tipovenda tv where m.tipo = tv.idtipo_venda and m.situacao = 'A' order by m.idmovimentacao desc;");
            }
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cód.");
            cabecalho.addElement("Tipo");
            cabecalho.addElement("Descrição");
            cabecalho.addElement("Valor");
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

}
