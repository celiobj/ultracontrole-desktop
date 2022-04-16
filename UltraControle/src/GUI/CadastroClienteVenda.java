/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Cliente;
import DAO.AccessDatabase;
import Persistencia.RepositorioCliente;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author celiobj
 */
public class CadastroClienteVenda extends JFrame {

    JLabel nomeLabel, cpfLabel, telefoneLabel, enderecoLabel, emailLabel;
    JTextField tNome, tEndereco, tEmail;
    JFormattedTextField tCpf, tTelefone;
    MaskFormatter mascaraCpf, mascaraTelefone;
    JComboBox tTipo;
    String tipos[] = {"", "Débito", "Crédito"};
    JTable clientes;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    Double soma = null;
    JButton botaoSalvar, botaoExcluir, botaoAlterar, botaoConsultar;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;
    String codAlterar;
    ActionListener acaoSalvar, acaoConsultar, acaoAlterar, acaoExluir;

    RepositorioCliente repositorioCLiente;

    ImageIcon imageIncluir = new ImageIcon("Icones\\incluir.png");
    ImageIcon imageExcluir = new ImageIcon("Icones\\excluir.png");
    ImageIcon imageSalvar = new ImageIcon("Icones\\salvar.png");
    ImageIcon imageFechar = new ImageIcon("Icones\\fechar.png");
    ImageIcon imageImprimir = new ImageIcon("Icones\\imprimir.png");

    public CadastroClienteVenda(final int tela) {

        try {
            mascaraCpf = new MaskFormatter("###.###.###-##");
        } catch (ParseException exp) {
        }
        try {
            mascaraTelefone = new MaskFormatter("#####-####");
        } catch (ParseException exp) {
        }

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        nomeLabel = new JLabel("Nome: ");
        cpfLabel = new JLabel("CPF: ");
        enderecoLabel = new JLabel("Endereço: ");
        telefoneLabel = new JLabel("Telefone: ");
        emailLabel = new JLabel("E-mail: ");

        tNome = new JTextField();
        tCpf = new JFormattedTextField(mascaraCpf);
        tEndereco = new JTextField();
        tTelefone = new JFormattedTextField(mascaraTelefone);
        tEmail = new JTextField();

        tNome.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tCpf.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tEndereco.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tTelefone.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tEmail.setFont(new Font("Times New Roman", Font.BOLD, 14));

        botaoSalvar = new JButton("Salvar");
        botaoAlterar = new JButton("Alterar");
        botaoConsultar = new JButton("Consultar");
        botaoExcluir = new JButton("Escolher");

        nomeLabel.setBounds(10, 10, 50, 20);
        cpfLabel.setBounds(10, 40, 50, 20);
        enderecoLabel.setBounds(10, 70, 80, 20);
        telefoneLabel.setBounds(10, 100, 80, 20);
        emailLabel.setBounds(10, 130, 50, 20);

        tNome.setBounds(80, 10, 300, 20);
        tCpf.setBounds(80, 40, 120, 20);
        tEndereco.setBounds(80, 70, 500, 20);
        tTelefone.setBounds(80, 100, 80, 20);
        tEmail.setBounds(80, 130, 150, 20);

        botaoSalvar.setBounds(450, 10, 100, 20);
        botaoExcluir.setBounds(550, 10, 100, 20);
        botaoAlterar.setBounds(550, 40, 100, 20);
        botaoConsultar.setBounds(450, 40, 100, 20);

        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();
        atualizarTabela();

        add(nomeLabel);
        add(cpfLabel);
        add(enderecoLabel);
        add(telefoneLabel);
        add(emailLabel);

        add(tNome);
        add(tCpf);
        add(tEndereco);
        add(tTelefone);
        add(tEmail);

        add(botaoSalvar);
        add(botaoExcluir);
        add(botaoAlterar);
        add(botaoConsultar);

        acaoSalvar = (ActionEvent e) -> {
            Vector<Cliente> clientesCombo = null;
            int resposta;
            resposta = JOptionPane.showConfirmDialog(null, "Deseja inserir esse registro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                if (tNome.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Preencha o nome!");
                } else {
                    repositorioCLiente = new RepositorioCliente();
                    Cliente novo = new Cliente();
                    novo.setNome(tNome.getText());
                    novo.setEndereco(tEndereco.getText());
                    novo.setCpf(tCpf.getText());
                    novo.setEmail(tEmail.getText());
                    novo.setTelefone(tTelefone.getText());
                    repositorioCLiente.adcionar(novo);
                    RealizarVenda.tCliente.removeAllItems();
                    clientesCombo = repositorioCLiente.popularCombo();
                    for (int i = 0; i < clientesCombo.size(); i++) {
                        RealizarVenda.tCliente.addItem(clientesCombo.elementAt(i));
                    }
                }
                try {
                    tNome.setText("");
                    tCpf.setText("");
                    tEndereco.setText("");
                    tTelefone.setText("");
                    tEmail.setText("");
                    atualizarTabela();
                    switch (tela) {
                        case 1: {
                            RealizarVenda.tCliente.setSelectedIndex(clientesCombo.size() - 1);
                        }
                        case 2: {
                            RealizarTroca.tCliente.setSelectedIndex(clientesCombo.size() - 1);
                        }
                    }
                    
                } catch (Exception ex) {
                    
                    tNome.setText("");
                    tCpf.setText("");
                    tEndereco.setText("");
                    tTelefone.setText("");
                    tEmail.setText("");
                    atualizarTabela();
                    
                    switch (tela) {
                        case 1: {
                            RealizarVenda.tCliente.setSelectedIndex(clientesCombo.size() - 1);
                        }
                        case 2: {
                            RealizarTroca.tCliente.setSelectedIndex(clientesCombo.size() - 1);
                        }
                    }

                }
                dispose();
            }
        };

        acaoConsultar = (ActionEvent e) -> {
            repositorioCLiente = new RepositorioCliente();
            int l = clientes.getSelectedRow();
            Cliente novo = repositorioCLiente.procurar(clientes.getValueAt(l, 0).toString());
            codAlterar = novo.getIdCliente();
            tNome.setText(novo.getNome());
            tEndereco.setText(novo.getEndereco());
            tCpf.setText(novo.getCpf());
            tEmail.setText(novo.getEmail());
            tTelefone.setText(novo.getTelefone());
            botaoSalvar.setEnabled(false);
        };

        acaoExluir = (ActionEvent e) -> {
            int i = clientes.getSelectedRow();
            String codigoCliente = clientes.getValueAt(i, 0).toString();
            String nomeCliente = clientes.getValueAt(i, 1).toString();
            RealizarVenda.tCliente.setSelectedItem(codigoCliente + "- " + nomeCliente);
            dispose();
        };

        acaoAlterar = (ActionEvent e) -> {
            int resposta;
            resposta = JOptionPane.showConfirmDialog(null, "Deseja alterar esse registro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                repositorioCLiente = new RepositorioCliente();
                Cliente novo = new Cliente();
                novo.setNome(tNome.getText());
                novo.setEndereco(tEndereco.getText());
                novo.setCpf(tCpf.getText());
                novo.setEmail(tEmail.getText());
                novo.setTelefone(tTelefone.getText());
                if (repositorioCLiente.alterar(codAlterar, novo)) {
                    botaoSalvar.setEnabled(true);
                    
                    try {
                        tNome.setText("");
                        tCpf.setText("");
                        tEndereco.setText("");
                        tTelefone.setText("");
                        tEmail.setText("");
                        remove(scroller);
                        repaint();
                        revalidate();
                        atualizarTabela();
                        
                    } catch (Exception ex) {
                        
                        tNome.setText("");
                        tCpf.setText("");
                        tEndereco.setText("");
                        tTelefone.setText("");
                        tEmail.setText("");
                        repaint();
                        revalidate();
                        atualizarTabela();
                    }
                }
            }
        };

        botaoSalvar.addActionListener(acaoSalvar);
        botaoAlterar.addActionListener(acaoAlterar);
        botaoConsultar.addActionListener(acaoConsultar);
        botaoExcluir.addActionListener(acaoExluir);

        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        //setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(800, 600);
        setVisible(true);
    }

    public void atualizarTabela() {
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioCLiente = new RepositorioCliente();
        clientes = new JTable();
        clientes = repositorioCLiente.listarTodos();
        if (clientes.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(clientes, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            clientes.getColumnModel().getColumn(0).setPreferredWidth(30);
            clientes.getColumnModel().getColumn(1).setPreferredWidth(100);
            clientes.getColumnModel().getColumn(2).setPreferredWidth(30);
            clientes.setAutoCreateRowSorter(true);

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
