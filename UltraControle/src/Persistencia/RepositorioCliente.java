/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Cliente;
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
 * @author celio
 */
public class RepositorioCliente implements RepositorioClienteInterface {

    @Override
    public void adcionar(Cliente cli) {
        try {

            AccessDatabase a = new AccessDatabase();
           // try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                st.execute("INSERT INTO "+VerificarUsuario.banco+".clientes(nome,cpf,endereco,telefone,email) VALUES('" + cli.getNome() + "','" + cli.getCpf() + "','" + cli.getEndereco() + "','" + cli.getTelefone() + "','" + cli.getEmail() + "')");
                JOptionPane.showMessageDialog(null, "Registro Inserido com Sucesso!!!");
          //  }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());

        }
    }

    @Override
    public boolean alterar(String cod, Cliente cli) {

        try {

           Statement st = VerificarUsuario.con.createStatement();
            st.execute("UPDATE "+VerificarUsuario.banco+".clientes SET nome='" + cli.getNome() + "', endereco='" + cli.getEndereco() + "', telefone='" + cli.getTelefone() + "',cpf='" + cli.getCpf() + "',email = '" + cli.getEmail() + "' WHERE idcliente = " + cod + "");
          
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!!!");
            return true;
        } catch (SQLException t) {
            System.out.println(t.getMessage());
return false;
        }

    }

    @Override
    public Cliente procurar(String idCliente) {
        try {

            AccessDatabase a = new AccessDatabase();
            Cliente cli;
          //  try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM "+VerificarUsuario.banco+".clientes WHERE idCliente = " + idCliente + "");
                rs.next();
                cli = new Cliente();
                cli.setIdCliente(rs.getString("idcliente"));
                cli.setNome(rs.getString("nome"));
                cli.setEndereco(rs.getString("endereco"));
                cli.setTelefone(rs.getString("telefone"));
                cli.setCpf(rs.getString("cpf"));
                cli.setEmail(rs.getString("email"));
          //  }
            return cli;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public Cliente procurarNome(String nome) {
        try {

            AccessDatabase a = new AccessDatabase();
            Cliente cli;
         //   try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM "+VerificarUsuario.banco+".clientes WHERE nome = '" + nome + "'");
                rs.next();
                cli = new Cliente();
                cli.setIdCliente(rs.getString("idcliente"));
                cli.setNome(rs.getString("nome"));
                cli.setEndereco(rs.getString("endereco"));
                cli.setTelefone(rs.getString("telefone"));
                cli.setCpf(rs.getString("cpf"));
                cli.setEmail(rs.getString("email"));
          //  }
            return cli;

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

          //  AccessDatabase a = new AccessDatabase();
          //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+VerificarUsuario.banco+".clientes;");
            while (rs.next()) {
                String cod_nome = String.valueOf(rs.getInt("idcliente")+"- "+rs.getString("nome"));
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
    public JTable listarTodos() {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
          //  AccessDatabase a = new AccessDatabase();
           // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT idcliente,nome,cpf FROM "+VerificarUsuario.banco+".clientes");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Código");
            cabecalho.addElement("Nome");
            cabecalho.addElement("CPF");

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

         //   con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    @Override
    public Cliente verificarExistente(String nome) {
        try {
            AccessDatabase a = new AccessDatabase();
          //  Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+VerificarUsuario.banco+".clientes WHERE nome = ('" + nome + "')");
            rs.next();
            Cliente cli = new Cliente();
            try {
                cli.setIdCliente(rs.getString("idcliente"));
                cli.setNome(rs.getString("nome"));
                //con.close();
                return cli;

            } catch (SQLException ez) {
             //   con.close();
                cli.setNome("");
                return cli;
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }
}
