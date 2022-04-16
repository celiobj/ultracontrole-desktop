/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.FormaPagamentoVenda;
import DAO.AccessDatabase;
import GUI.VerificarUsuario;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public class RepositorioFormaPagamentoVenda implements RepositorioFormaPagamentoVendaInterface{

    @Override
    public boolean adcionar(FormaPagamentoVenda forma) {
    try {

            AccessDatabase a = new AccessDatabase();
          //  try (Connection con = a.conectar()) {
                Statement st = VerificarUsuario.con.createStatement();
                st.execute("INSERT INTO `"+VerificarUsuario.banco+"`.`fromapagamento_venda` (`idformapagamento`, `idvenda`) VALUES ('" + forma.getIdFormapagamentoVenda()+ "', '" + forma.getIdVenda()+ "')");
                //JOptionPane.showMessageDialog(null, "Registro Inserido com Sucesso!!!");
                return true;
         //   }

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "FORMA PAGAMENTO - Erro: " + t.getMessage());
            return false;

        }    
    }

    @Override
    public FormaPagamentoVenda verificarExistente(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(String cod, FormaPagamentoVenda cli) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormaPagamentoVenda procurar(String cod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JTable listarTodos(String lote) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
