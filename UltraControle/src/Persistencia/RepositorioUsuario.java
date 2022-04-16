/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Usuario;
import DAO.AccessDatabase;
import GUI.VerificarUsuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author celio
 */
public class RepositorioUsuario implements RepositorioUsuarioInterface {

    public void adcionar(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void alterar(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean procurar(String login, String senha) {

        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            VerificarUsuario.con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+VerificarUsuario.banco+".usuarios WHERE login = '" + login + "'");
            rs.next();
            Usuario usu = new Usuario();
            usu.setLogin(rs.getString("login"));
            usu.setSenha(rs.getString("senha"));
            if (usu.getSenha().equals(senha)) {
               // con.close();
                return true;
            } else {
               // con.close();
            }
            return false;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return false;

        }
    }

    public void remover(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
