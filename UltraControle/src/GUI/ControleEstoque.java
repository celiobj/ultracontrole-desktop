/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DAO.AccessDatabase;
import Persistencia.RepositorioEstoque;
import java.awt.Color;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author celiobj
 */
public class ControleEstoque extends JPanel {

    JTable estoque;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    RepositorioEstoque repositorioEstoque;

    public ControleEstoque() {

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();
        atualizarTabela();

        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();

        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(600, 400);
        setVisible(true);
    }

    public void atualizarTabela() {
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioEstoque = new RepositorioEstoque();
        estoque = repositorioEstoque.listarTodos(null);
        if (estoque.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(estoque, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            estoque.getColumnModel().getColumn(0).setPreferredWidth(50);
            estoque.getColumnModel().getColumn(1).setPreferredWidth(300);
            estoque.getColumnModel().getColumn(2).setPreferredWidth(200);
            estoque.getColumnModel().getColumn(3).setPreferredWidth(50);
            estoque.setAutoCreateRowSorter(true);
        }
        try {
            remove(scroller);
        } catch (Exception e) {

        }
        try {
            add(scroller);
        } catch (NullPointerException e) {

        }
    }

}
