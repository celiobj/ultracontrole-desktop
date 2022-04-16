/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Fornecedor;
import DAO.AccessDatabase;
import Persistencia.RepositorioFornecedor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author celiobj
 */
public class CadastroFornecedorProduto extends JPanel {

    JLabel nomeLabel;
    JTextField tNome;
    JTable itensTable;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    ActionListener acaoSalvar;
    JButton botaoRegistrarLote;
    RepositorioFornecedor repositorioFornecedor;
    Fornecedor fornecedor;

    public CadastroFornecedorProduto() {
        
        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        nomeLabel = new JLabel("Nome: ");
        tNome = new JTextField();
        tNome.setFont(new Font("Times New Roman", Font.BOLD, 14));
        botaoRegistrarLote = new JButton("Salvar");
        nomeLabel.setBounds(10, 10, 50, 20);
        tNome.setBounds(55, 10, 200, 20);

        botaoRegistrarLote.setBounds(550, 10, 130, 20);

        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();
        atualizarTabela();

        add(nomeLabel);
        add(tNome);
        add(botaoRegistrarLote);

        acaoSalvar = (ActionEvent e) -> {
            int resposta;
            resposta = JOptionPane.showConfirmDialog(null, "Deseja inserir esse registro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                if (tNome.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Preencha o nome!");
                } else {
                    repositorioFornecedor = new RepositorioFornecedor();
                    fornecedor = new Fornecedor();
                    fornecedor.setNome(tNome.getText());
                    
                    repositorioFornecedor.adcionar(fornecedor);
                }
                try {
                    tNome.setText("");
                    atualizarTabela();
                } catch (Exception ex) {
                    tNome.setText("");
                    atualizarTabela();
                }
            }
        };

        botaoRegistrarLote.addActionListener(acaoSalvar);

        atualizarTabela();
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
        repositorioFornecedor = new RepositorioFornecedor();
        itensTable = new JTable();

        itensTable = repositorioFornecedor.listarTodos(tNome.getText());
        if (itensTable.getValueAt(0, 0) == null) {
            // JOptionPane.showMessageDialog(null, "Ainda sem itens para este lote.");
        } else {
            scroller = new JScrollPane(itensTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura*0.02), 200,(int) Math.round(Principal.largura/1.3), (int) Math.round(Principal.altura*0.35));
            itensTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            itensTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            itensTable.setAutoCreateRowSorter(true);
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
