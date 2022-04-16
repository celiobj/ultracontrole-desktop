/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Estoque;
import Classes.Produto;
import DAO.AccessDatabase;
import Persistencia.RepositorioEstoque;
import Persistencia.RepositorioFornecedor;
import Persistencia.RepositorioProduto;
import Persistencia.RepositorioTamanho;
import Persistencia.RepositorioTipo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author celiobj
 */
public class CadastroProdutoVenda extends JFrame {

    JLabel nomeLabel, descricaoLabel, codigoLabel, tipoLabel, tamanhoLabel, fornecedorLabel;
    JTextField tNome, tDescricao, tCodigo;
    JComboBox tTipo, tTamanho, tFornecedor;
    String tipos[] = {"", "Débito", "Crédito"};
    JTable produtos;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    Double soma = null;
    JButton botaoSalvar, botaoAlterar, botaoConsultar, botaoExcluir;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;
    String idProdutoAlterar;

    ActionListener acaoSalvar, acaoExcluir, acaoConsultar, acaoAlterar;
    MouseListener clickMouse;
    MouseAdapter clickMouseAdapter;

    RepositorioProduto repositorioProduto;
    RepositorioTipo repositorioTipo;
    RepositorioTamanho repositorioTamanho;
    RepositorioFornecedor repositorioFornecedor;

    ImageIcon imageIncluir = new ImageIcon("Icones\\incluir.png");
    ImageIcon imageExcluir = new ImageIcon("Icones\\excluir.png");
    ImageIcon imageSalvar = new ImageIcon("Icones\\salvar.png");
    ImageIcon imageFechar = new ImageIcon("Icones\\fechar.png");
    ImageIcon imageImprimir = new ImageIcon("Icones\\imprimir.png");

    public CadastroProdutoVenda(final int tela) {

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        nomeLabel = new JLabel("Nome: ");
        codigoLabel = new JLabel("Código: ");
        descricaoLabel = new JLabel("Descrição: ");
        tipoLabel = new JLabel("Tipo: ");
        tamanhoLabel = new JLabel("Tamanho: ");
        fornecedorLabel = new JLabel("Fornecedor: ");

        tCodigo = new JTextField();
        tNome = new JTextField();
        tDescricao = new JTextField();
        repositorioTipo = new RepositorioTipo();
        tTipo = new JComboBox(repositorioTipo.popularCombo());
        repositorioTamanho = new RepositorioTamanho();
        tTamanho = new JComboBox(repositorioTamanho.popularCombo());
        repositorioFornecedor = new RepositorioFornecedor();
        tFornecedor = new JComboBox(repositorioFornecedor.popularCombo());

        tNome.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tCodigo.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tDescricao.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tTipo.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tTamanho.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tFornecedor.setFont(new Font("Times New Roman", Font.BOLD, 14));

        botaoSalvar = new JButton("Salvar");
        botaoAlterar = new JButton("Alterar");
        botaoConsultar = new JButton("Consultar");
        botaoExcluir = new JButton("Escolher");

        codigoLabel.setBounds(10, 10, 50, 20);
        nomeLabel.setBounds(10, 40, 50, 20);
        descricaoLabel.setBounds(10, 70, 80, 20);
        tipoLabel.setBounds(10, 100, 80, 20);
        tamanhoLabel.setBounds(10, 130, 100, 20);
        fornecedorLabel.setBounds(10, 160, 100, 20);

        tCodigo.setBounds(80, 10, 50, 20);
        tNome.setBounds(80, 40, 200, 20);
        tDescricao.setBounds(80, 70, 300, 20);
        tTipo.setBounds(80, 100, 200, 20);
        tTamanho.setBounds(80, 130, 200, 20);
        tFornecedor.setBounds(80, 160, 250, 20);

        botaoSalvar.setBounds(450, 10, 100, 20);
        botaoAlterar.setBounds(550, 40, 100, 20);
        botaoConsultar.setBounds(450, 40, 100, 20);
        botaoExcluir.setBounds(550, 10, 100, 20);

        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();
        atualizarTabela();

        add(nomeLabel);
        add(descricaoLabel);
        add(tipoLabel);
        add(codigoLabel);
        add(tamanhoLabel);
        add(fornecedorLabel);

        add(tNome);
        add(tCodigo);
        add(tDescricao);
        add(tTipo);
        add(tTamanho);
        add(tFornecedor);

        // add(botaoSalvar);
        add(botaoExcluir);
        // add(botaoAlterar);
        // add(botaoConsultar);
        botaoSalvar.setEnabled(false);

        acaoSalvar = (ActionEvent e) -> {
            Vector<Produto> produtoCombo = null;
            int resposta;
            resposta = JOptionPane.showConfirmDialog(null, "Deseja inserir esse registro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                if (tNome.getText().equalsIgnoreCase("") || tDescricao.getText().equalsIgnoreCase("") || tTipo.getSelectedIndex() == 0 || tTamanho.getSelectedIndex() == 0 || tFornecedor.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Preencha todos o campos!");
                } else {
                    RepositorioProduto repositorioProduto1 = new RepositorioProduto();
                    RepositorioEstoque repositorioEstoque = new RepositorioEstoque();
                    String[] tipos1 = tTipo.getSelectedItem().toString().split(Pattern.quote("-"));
                    String tamanho[] = tTamanho.getSelectedItem().toString().split(Pattern.quote("-"));
                    String fornecedor[] = tFornecedor.getSelectedItem().toString().split(Pattern.quote("-"));
                    Produto novo = new Produto();
                    novo.setCodigo(tCodigo.getText() + tamanho[0]);
                    novo.setNome(tNome.getText());
                    novo.setDescricao(tDescricao.getText());
                    novo.setTipo(tipos1[0]);
                    novo.setTamanho(tamanho[0]);
                    novo.setFornecedor(fornecedor[0]);
                    repositorioProduto1.adcionar(novo);
                    Estoque estoque = new Estoque();
                    estoque.setCodigoProduto(novo.getCodigo());
                    estoque.setQuantidade("0");
                    repositorioEstoque.adcionar(estoque);
                    RealizarVenda.tProduto.removeAllItems();
                    produtoCombo = repositorioProduto1.popularCombo();
                    for (int i = 0; i < produtoCombo.size(); i++) {
                        RealizarVenda.tProduto.addItem(produtoCombo.elementAt(i));
                    }
                }
                try {
                    tCodigo.setText("");
                    tNome.setText("");
                    tTipo.setSelectedIndex(0);
                    tDescricao.setText("");
                    tTamanho.setSelectedIndex(0);
                    tFornecedor.setSelectedIndex(0);
                    atualizarTabela();
                    switch (tela) {
                        case 1: {
                            RealizarVenda.tProduto.setSelectedIndex(produtoCombo.size() - 1);
                        }
                        case 2: {
                            RealizarTroca.tProdutoEntrada.setSelectedIndex(produtoCombo.size() - 1);
                        }
                        case 3: {
                            RealizarTroca.tProdutoSaida.setSelectedIndex(produtoCombo.size() - 1);
                        }
                    }
                } catch (Exception ex) {
                    
                    tCodigo.setText("");
                    tNome.setText("");
                    tDescricao.setText("");
                    tTipo.setSelectedIndex(0);
                    tTamanho.setSelectedIndex(0);
                    tFornecedor.setSelectedIndex(0);
                    atualizarTabela();
                    switch (tela) {
                        case 1: {
                            RealizarVenda.tProduto.setSelectedIndex(produtoCombo.size() - 1);
                        }
                        case 2: {
                            RealizarTroca.tProdutoEntrada.setSelectedIndex(produtoCombo.size() - 1);
                        }
                        case 3: {
                            RealizarTroca.tProdutoSaida.setSelectedIndex(produtoCombo.size() - 1);
                        }
                    }
                }
                dispose();
            }
        };

        acaoConsultar = (ActionEvent e) -> {
            repositorioProduto = new RepositorioProduto();
            repositorioFornecedor = new RepositorioFornecedor();
            repositorioTamanho = new RepositorioTamanho();
            repositorioTipo = new RepositorioTipo();
            
            int l = produtos.getSelectedRow();
            Produto produto = repositorioProduto.procurar(produtos.getValueAt(l, 0).toString());
            idProdutoAlterar = produto.getIdProduto();
            tCodigo.setText(produto.getCodigo());
            tNome.setText(produto.getNome());
            tDescricao.setText(produto.getDescricao());
            tTipo.setSelectedItem(produto.getTipo() + "- " + repositorioTipo.procurar(produto.getTipo()).getDescricao());
            tTamanho.setSelectedItem(produto.getTamanho() + "- " + repositorioTamanho.procurar(produto.getTamanho()).getDescricao());
            tFornecedor.setSelectedItem(produto.getFornecedor() + "- " + repositorioFornecedor.procurar(produto.getFornecedor()).getNome());
            tCodigo.setEditable(true);
            botaoSalvar.setEnabled(false);
        };

        acaoExcluir = (ActionEvent e) -> {
            int i = produtos.getSelectedRow();
            String codigoProduto = produtos.getValueAt(i, 0).toString();
            String nomeProduto = produtos.getValueAt(i, 1).toString();
            String TamanhoProduto = produtos.getValueAt(i, 3).toString();
            switch (tela) {
                case 1: {
                    RealizarVenda.tProduto.setSelectedItem(nomeProduto + " - " + TamanhoProduto + " -" + codigoProduto);
                    break;
                }
                case 2: {
                    RealizarTroca.tProdutoEntrada.setSelectedItem(nomeProduto + " - " + TamanhoProduto + " -" + codigoProduto);
                    break;
                }
                case 3: {
                    RealizarTroca.tProdutoSaida.setSelectedItem(nomeProduto + " - " + TamanhoProduto + " -" + codigoProduto);
                    break;
                }
            }
            dispose();
        };

        acaoAlterar = (ActionEvent e) -> {
            repositorioProduto = new RepositorioProduto();
            repositorioFornecedor = new RepositorioFornecedor();
            repositorioTamanho = new RepositorioTamanho();
            repositorioTipo = new RepositorioTipo();
            String[] tipos1 = tTipo.getSelectedItem().toString().split(Pattern.quote("-"));
            String tamanho[] = tTamanho.getSelectedItem().toString().split(Pattern.quote("-"));
            String fornecedor[] = tFornecedor.getSelectedItem().toString().split(Pattern.quote("-"));
            Produto novo = new Produto();
            novo.setCodigo(tCodigo.getText() + tamanho[0]);
            novo.setNome(tNome.getText());
            novo.setDescricao(tDescricao.getText());
            novo.setTipo(tipos1[0]);
            novo.setTamanho(tamanho[0]);
            novo.setFornecedor(fornecedor[0]);
            if (repositorioProduto.alterar(idProdutoAlterar, novo)) {
                botaoSalvar.setEnabled(true);
                try {
                    tCodigo.setText("");
                    tNome.setText("");
                    tTipo.setSelectedIndex(0);
                    tDescricao.setText("");
                    tTamanho.setSelectedIndex(0);
                    tFornecedor.setSelectedIndex(0);
                    remove(scroller);
                    repaint();
                    revalidate();
                    atualizarTabela();
                    
                } catch (Exception ex) {
                    
                    tCodigo.setText("");
                    tNome.setText("");
                    tDescricao.setText("");
                    tTipo.setSelectedIndex(0);
                    tTamanho.setSelectedIndex(0);
                    tFornecedor.setSelectedIndex(0);
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

        botaoSalvar.addActionListener(acaoSalvar);
        botaoConsultar.addActionListener(acaoConsultar);
        botaoAlterar.addActionListener(acaoAlterar);
        botaoExcluir.addActionListener(acaoExcluir);

        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setSize(600, 400);
        setVisible(true);
    }

    public void atualizarTabela() {
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioProduto = new RepositorioProduto();
        produtos = new JTable();
        produtos = repositorioProduto.listarTodos();
        if (produtos.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(produtos, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            produtos.getColumnModel().getColumn(0).setPreferredWidth(30);
            produtos.getColumnModel().getColumn(1).setPreferredWidth(100);
            produtos.getColumnModel().getColumn(2).setPreferredWidth(30);
            produtos.getColumnModel().getColumn(3).setPreferredWidth(30);
            produtos.getColumnModel().getColumn(4).setPreferredWidth(30);
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
