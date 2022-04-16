/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DAO.AccessDatabase;
import Persistencia.RepositorioItemEstoque;
import Util.Funcoes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author celiobj
 */
public class Precificacao extends JPanel {

    JTable produtos;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    Double soma = null;
    JButton botaoAlterar;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;

    ActionListener acaoAlterar;
    MouseListener clickMouse;
    MouseAdapter clickMouseAdapter;

    RepositorioItemEstoque repositorioItemEstoque;

    ImageIcon imageIncluir = new ImageIcon("Icones\\incluir.png");
    ImageIcon imageExcluir = new ImageIcon("Icones\\excluir.png");
    ImageIcon imageSalvar = new ImageIcon("Icones\\salvar.png");
    ImageIcon imageFechar = new ImageIcon("Icones\\fechar.png");
    ImageIcon imageImprimir = new ImageIcon("Icones\\imprimir.png");

    public Precificacao() {

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        botaoAlterar = new JButton("Alterar");
        botaoAlterar.setBounds(550, 40, 100, 20);

        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();
        atualizarTabela();

        add(botaoAlterar);

        acaoAlterar = (ActionEvent e) -> {
            int i = produtos.getSelectedRow();
            String codigoProduto = produtos.getValueAt(i, 0).toString();
            String valorVenda = null;
            try {
                valorVenda = Funcoes.formatoParaInserir(produtos.getValueAt(i, 3).toString());
            } catch (Exception ex) {
                Logger.getLogger(Precificacao.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (repositorioItemEstoque.precificar(codigoProduto, valorVenda)) {
                
                try {

                    remove(scroller);
                    repaint();
                    revalidate();
                    atualizarTabela();
                    
                } catch (Exception ex) {
                    
                    remove(scroller);
                    repaint();
                    revalidate();
                    atualizarTabela();
                }
            }
        };

        clickMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Teste");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Teste");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Teste");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Teste");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Teste");
            }
        };

        botaoAlterar.addActionListener(acaoAlterar);

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
        repositorioItemEstoque = new RepositorioItemEstoque();
        produtos = new JTable();
        produtos = repositorioItemEstoque.listarparaPrecificar();
        if (produtos.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(produtos, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura*0.02), 200,(int) Math.round(Principal.largura/1.3), (int) Math.round(Principal.altura*0.35));
            produtos.getColumnModel().getColumn(0).setPreferredWidth(30);
            produtos.getColumnModel().getColumn(1).setPreferredWidth(100);
            produtos.getColumnModel().getColumn(2).setPreferredWidth(30);
            produtos.getColumnModel().getColumn(3).setPreferredWidth(30);
            produtos.setAutoCreateRowSorter(true);

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
