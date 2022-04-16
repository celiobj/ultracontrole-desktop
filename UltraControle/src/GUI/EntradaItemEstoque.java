/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Estoque;
import Classes.ItemEntrada;
import Classes.Lote;
import Classes.Produto;
import DAO.AccessDatabase;
import Persistencia.RepositorioEstoque;
import Persistencia.RepositorioFornecedor;
import Persistencia.RepositorioItemEstoque;
import Persistencia.RepositorioLote;
import Persistencia.RepositorioProduto;
import Persistencia.RepositorioTamanho;
import Util.ComboBoxSearch.ComboBoxFilterDecorator;
import Util.ComboBoxSearch.CustomComboRenderer;
import Util.ComboBoxSearch.JComboBoxFilterMain;
import Util.DateLabelFormatter;
import Util.Funcoes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author celiobj
 */
public class EntradaItemEstoque extends JPanel {

    JLabel codigoLabel, fornecedorLabel, quantidadeLabel, freteLabel, taxaLabel, valorLabel, valorVendaLabel, valorCustoLabel, dataLabel, produtoItemLabel, tamanhoLabel, valorItemLabel, quantidadeItemLabel;
    JTextField tCodigo, tQuantidade, tFrete, tTaxa, tValor, tValorVenda, tValorCusto, tValorItem, tQuantidadeItem;
    JFormattedTextField tData;
    MaskFormatter mascaraData;
    JComboBox tProdutoItem, tFornecedor;
    JTable itensTable;
    Vector linhas, dados, cabecalho, produtosVetor;
    JScrollPane scroller;
    ComboBoxFilterDecorator<String> decorate;
    ActionListener acaoSalvar, acaoRegistrarLote, acaoAdicionarItem, acaoAtualizarItens, acaoCancelar, acaoAcharTamanho;
    FocusAdapter acaoPerdeFocoQuantidade;
    JButton botaoRegistrarLote, botaoAdicionarItem, botaoAlterarItem, botaoSalvarEntrada, botaoCancelar;
    RepositorioItemEstoque repositorioItem;
    RepositorioLote repositorioLote;
    RepositorioEstoque repositorioEstoque;
    RepositorioProduto repositorioProduto;
    RepositorioFornecedor repositorioFornecedor;
    RepositorioTamanho repositorioTamanho;
    int qtdItens;

    Lote lote;
    ItemEntrada itemEntrada;
    Estoque estoque;

    UtilDateModel model;
    Properties p;
    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;

    public EntradaItemEstoque() {

        try {
            mascaraData = new MaskFormatter("##/##/####");
        } catch (ParseException exp) {
        }
        
        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        model = new UtilDateModel();
        p = new Properties();
        p.put("text.today", "Hoje");
        p.put("text.month", "Mês");
        p.put("text.year", "Ano");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        codigoLabel = new JLabel("Código: ");
        fornecedorLabel = new JLabel("Fornecedor: ");
        quantidadeLabel = new JLabel("qtd: ");
        freteLabel = new JLabel("Frete: ");
        taxaLabel = new JLabel("Taxa: ");
        valorLabel = new JLabel("Valor: ");
        valorVendaLabel = new JLabel("Valor venda: ");
        valorCustoLabel = new JLabel("Valor custo: ");
        dataLabel = new JLabel("Data: ");

        produtoItemLabel = new JLabel("Produto: ");
        tamanhoLabel = new JLabel("Tamanho");
        valorItemLabel = new JLabel("Valor: ");
        quantidadeItemLabel = new JLabel("qtd: ");

        tCodigo = new JTextField();
        repositorioFornecedor = new RepositorioFornecedor();
        tFornecedor = new JComboBox(repositorioFornecedor.popularCombo());
        tQuantidade = new JTextField();
        tFrete = new JTextField();
        tTaxa = new JTextField();
        tData = new JFormattedTextField(mascaraData);
        tValor = new JTextField();
        tValorVenda = new JTextField();
        tValorCusto = new JTextField();

        repositorioProduto = new RepositorioProduto();
        produtosVetor = repositorioProduto.popularCombo();
        tProdutoItem = new JComboBox<>(produtosVetor.toArray(new String[produtosVetor.size()]));
        decorate = ComboBoxFilterDecorator.decorate(tProdutoItem, CustomComboRenderer::getEmployeeDisplayText, JComboBoxFilterMain::employeeFilter);
        tProdutoItem.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));

        tValorItem = new JTextField();
        tQuantidadeItem = new JTextField();

        tCodigo.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tFornecedor.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tQuantidade.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tFrete.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tTaxa.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tData.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tValor.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tValorVenda.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tValorCusto.setFont(new Font("Times New Roman", Font.BOLD, 14));

        tProdutoItem.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tValorItem.setFont(new Font("Times New Roman", Font.BOLD, 14));
        tQuantidadeItem.setFont(new Font("Times New Roman", Font.BOLD, 14));

        botaoRegistrarLote = new JButton("Registrar Lote");
        botaoAdicionarItem = new JButton("Adicionar Item");
        botaoAlterarItem = new JButton("Atualizar Itens");
        botaoSalvarEntrada = new JButton("Fechar Lote");
        botaoCancelar = new JButton("Cancelar");

        codigoLabel.setBounds(10, 10, 50, 20);
        fornecedorLabel.setBounds(120, 10, 100, 20);
        dataLabel.setBounds(410, 10, 50, 20);
        quantidadeLabel.setBounds(10, 40, 80, 20);
        freteLabel.setBounds(120, 40, 80, 20);
        taxaLabel.setBounds(285, 40, 50, 20);
        valorLabel.setBounds(410, 40, 80, 20);

        produtoItemLabel.setBounds(10, 100, 80, 20);
        valorItemLabel.setBounds(10, 130, 50, 20);
        quantidadeItemLabel.setBounds(200, 130, 80, 20);
        valorVendaLabel.setBounds(325, 160, 80, 20);
        valorCustoLabel.setBounds(325, 130, 80, 20);

        tCodigo.setBounds(55, 10, 50, 20);
        tFornecedor.setBounds(200, 10, 200, 20);
        datePicker.setBounds(430, 10, 120, 27);
        tQuantidade.setBounds(55, 40, 50, 20);
        tFrete.setBounds(200, 40, 80, 20);
        tTaxa.setBounds(320, 40, 80, 20);
        tValor.setBounds(450, 40, 80, 20);

        tProdutoItem.setBounds(60, 100, 450, 20);
        tamanhoLabel.setBounds(320, 100, 100, 20);
        tValorItem.setBounds(60, 130, 80, 20);
        tQuantidadeItem.setBounds(230, 130, 80, 20);
        tValorVenda.setBounds(400, 160, 80, 20);
        tValorCusto.setBounds(400, 130, 80, 20);

        botaoRegistrarLote.setBounds(550, 10, 130, 20);
        botaoAdicionarItem.setBounds(550, 100, 130, 20);
        botaoAlterarItem.setBounds(550, 180, 120, 20);
        botaoSalvarEntrada.setBounds(550, 40, 130, 20);
        botaoCancelar.setBounds(550, 70, 130, 20);

        habilitarItem(false);
        habilitarLote(true);

        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();

        add(codigoLabel);
        add(fornecedorLabel);
        add(quantidadeLabel);
        add(freteLabel);
        add(taxaLabel);
        add(valorLabel);
        // add(dataLabel);

        add(produtoItemLabel);
        add(valorItemLabel);
        add(quantidadeItemLabel);

        add(tCodigo);
        add(tFornecedor);
        add(tQuantidade);
        add(tFrete);
        add(tTaxa);
        add(datePicker);
        add(tValor);

        add(tProdutoItem);
        add(tValorItem);
        add(tQuantidadeItem);
        add(tamanhoLabel);
        add(valorVendaLabel);
        add(tValorVenda);
        add(valorCustoLabel);
        add(tValorCusto);

        add(botaoRegistrarLote);
        add(botaoAdicionarItem);
        add(botaoSalvarEntrada);
        add(botaoCancelar);
        add(botaoAlterarItem);

        botaoSalvarEntrada.setEnabled(false);
        botaoCancelar.setEnabled(false);
        botaoAlterarItem.setEnabled(false);
        tValorCusto.setEditable(false);

        acaoRegistrarLote = (ActionEvent e) -> {
            if (tCodigo.getText().equalsIgnoreCase("") || tFornecedor.getSelectedIndex() == 0 || datePicker.getJFormattedTextField().getText().equals("") || tQuantidade.getText().equalsIgnoreCase("") || tFrete.getText().equalsIgnoreCase("") || tTaxa.getText().equalsIgnoreCase("") || tValor.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos(Código, Fornecedor, Data, Quantidade, Frete, Taxa e Valor)");
            } else {
                
                repositorioLote = new RepositorioLote();
                lote = new Lote();
                lote.setData(Funcoes.paraInserirData(datePicker.getJFormattedTextField().getText()));
                try {
                    lote.setValor(Funcoes.formatoParaInserir(tValor.getText()));
                } catch (ParseException ex) {
                    Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
                lote.setCodigo(tCodigo.getText());
                String fornecedor[] = tFornecedor.getSelectedItem().toString().split(Pattern.quote("-"));
                lote.setIdfornecedo(fornecedor[0]);
                try {
                    lote.setFrete(Funcoes.formatoParaInserir(tFrete.getText()));
                } catch (ParseException ex) {
                    Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    lote.setTaxa(Funcoes.formatoParaInserir(tTaxa.getText()));
                } catch (ParseException ex) {
                    Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
                lote.setQuantidade(Integer.parseInt(tQuantidade.getText()));
                
                if (repositorioLote.adcionar(lote)) {
                    habilitarItem(true);
                    habilitarLote(false);
                    repaint();
                    revalidate();
                    atualizarTabela();
                }
            }
        };

        acaoPerdeFocoQuantidade = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                double frete = 0;
                try {
                    frete = Double.parseDouble(Funcoes.formatoParaInserir(tFrete.getText())) / Double.parseDouble(tQuantidade.getText());
                } catch (ParseException ex) {
                    Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
                double custo = 0;
                try {
                    custo = (((Double.parseDouble(Funcoes.formatoParaInserir(tValorItem.getText())) * Double.parseDouble(Funcoes.formatoParaInserir(tTaxa.getText()))) / 100) + frete + Double.parseDouble(Funcoes.formatoParaInserir(tValorItem.getText())));
                } catch (ParseException ex) {
                    Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    custo = (((Double.parseDouble(Funcoes.formatoParaInserir(tValorItem.getText())) * Double.parseDouble(Funcoes.formatoParaInserir(tTaxa.getText()))) / 100) + frete + Double.parseDouble(Funcoes.formatoParaInserir(tValorItem.getText())));
                } catch (ParseException ex) {
                    Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
                tValorCusto.setText(Funcoes.paraFormatoDinheiro(custo));
            }
        };

        acaoAdicionarItem = (ActionEvent e) -> {
            if (tProdutoItem.getSelectedIndex() == 0 || tValorItem.getText().equalsIgnoreCase("") || tQuantidadeItem.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos(Produto, Valor e Quantidade)!");
            } else {
                String itens[] = tProdutoItem.getSelectedItem().toString().split(Pattern.quote("-"));
                repositorioItem = new RepositorioItemEstoque();
                if (repositorioItem.consultarItemnaCompra(lote.getCodigo(), itens[2])) {
                    JOptionPane.showMessageDialog(null, "Este item já foi incluído no lote.");
                } else {
                    qtdItens = verificarQuantidadeItens(lote);
                    if (qtdItens <= Integer.parseInt(tQuantidade.getText())) {
                        
                        itemEntrada = new ItemEntrada();
                        
                        itemEntrada.setIdProduto(itens[2]);
                        itemEntrada.setIdLote(tCodigo.getText());
                        itemEntrada.setSituacao("A");
                        try {
                            itemEntrada.setValorCompra(Funcoes.formatoParaInserir(tValorItem.getText()));
                        } catch (ParseException ex) {
                            Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            itemEntrada.setTaxaCompra(Funcoes.formatoParaInserir(tTaxa.getText()));
                        } catch (ParseException ex) {
                            Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        double frete = 0;
                        try {
                            frete = Double.parseDouble(Funcoes.formatoParaInserir(tFrete.getText())) / Double.parseDouble(tQuantidade.getText());
                        } catch (ParseException ex) {
                            Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        itemEntrada.setFrete(String.valueOf(frete));
                        itemEntrada.setQuantidade(Integer.parseInt(tQuantidadeItem.getText()));
                        double custo = 0;
                        try {
                            custo = (((Double.parseDouble(Funcoes.formatoParaInserir(tValorItem.getText())) * Double.parseDouble(Funcoes.formatoParaInserir(tTaxa.getText()))) / 100) + frete + Double.parseDouble(Funcoes.formatoParaInserir(tValorItem.getText())));
                        } catch (ParseException ex) {
                            Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        itemEntrada.setValorCusto(String.valueOf(custo));
                        try {
                            itemEntrada.setValorVenda(Funcoes.formatoParaInserir(tValorVenda.getText()));
                        } catch (ParseException ex) {
                            Logger.getLogger(EntradaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        repositorioItem.adcionar(itemEntrada);
                        
                        estoque = new Estoque();
                        estoque.setCodigoProduto(itens[2]);
                        estoque.setQuantidade(tQuantidadeItem.getText());
                        repositorioEstoque = new RepositorioEstoque();
                        repositorioEstoque.alterar(estoque, true);
                        limparItem();
                        qtdItens = verificarQuantidadeItens(lote);
                        if (qtdItens == Integer.parseInt(tQuantidade.getText())) {
                            JOptionPane.showMessageDialog(null, "Este lote completou a quantidade de itens");
                            botaoSalvarEntrada.setEnabled(true);
                            botaoAdicionarItem.setEnabled(false);
                            tProdutoItem.setEnabled(false);
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Este lote já está com " + tQuantidade.getText() + " itens");
                    }
                }
                try {
                    remove(itensTable);
                } catch (Exception et) {
                }
                repaint();
                revalidate();
                atualizarTabela();
            }
        };

        acaoSalvar = (ActionEvent e) -> {
            repositorioLote = new RepositorioLote();
            lote = repositorioLote.verificarAberto();
            repositorioLote.fechar(lote);
            habilitarLote(true);
            habilitarItem(false);
            botaoSalvarEntrada.setEnabled(false);
            limparItem();
            limparLote();
            try {
                remove(itensTable);
            } catch (Exception et) {
            }
            repaint();
            revalidate();
            atualizarTabela();
        };

        acaoAtualizarItens = (ActionEvent e) -> {
            repositorioItem = new RepositorioItemEstoque();
            try {
                int l = itensTable.getSelectedRow();
                String codigoProduto = itensTable.getValueAt(l, 0).toString();
                String quantidadeProduto = itensTable.getValueAt(l, 3).toString();
                if (quantidadeProduto.equalsIgnoreCase("0")) {
                    if (JOptionPane.showConfirmDialog(null, "Você inseriu a quantidade '0'.\n"
                            + "Deseja escluir o item? ") == 0) {
                        repositorioItem.remover(tCodigo.getText(), codigoProduto);
                    }
                } else {
                    repositorioItem.alterar(tCodigo.getText(), codigoProduto, quantidadeProduto);
                }
                Lote loteAgora = new Lote();
                lote.setCodigo(tCodigo.getText());
                qtdItens = verificarQuantidadeItens(loteAgora);
                if (qtdItens >= Integer.parseInt(tQuantidade.getText())) {
                    JOptionPane.showMessageDialog(null, "Este lote já completou a quantidade de itens");
                }
                
                try {
                    remove(itensTable);
                } catch (Exception et) {
                }
                repaint();
                revalidate();
                atualizarTabela();
                
            } catch (ArrayIndexOutOfBoundsException er) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela.");
            }
        };

        acaoAcharTamanho = (ActionEvent e) -> {
            if (tProdutoItem.getSelectedIndex() != 0) {
                try {
                    String itens[] = tProdutoItem.getSelectedItem().toString().split(Pattern.quote("-"));
                    String cod = itens[2];
                    if (cod.equalsIgnoreCase("")) {
                        tamanhoLabel.setText("");
                    } else {
                        repositorioProduto = new RepositorioProduto();
                        Produto produto = repositorioProduto.procurar(cod);
                        repositorioTamanho = new RepositorioTamanho();
                        tamanhoLabel.setText(repositorioTamanho.procurar(produto.getTamanho()).getDescricao());
                    }
                } catch (Exception ew) {
                    
                }
            }
        };

        acaoCancelar = (ActionEvent e) -> {
            repositorioLote = new RepositorioLote();
            repositorioLote.cancelar(tCodigo.getText());
            habilitarLote(true);
            habilitarItem(false);
            botaoSalvarEntrada.setEnabled(false);
            botaoCancelar.setEnabled(false);
            limparItem();
            limparLote();
            try {
                remove(itensTable);
            } catch (Exception et) {
            }
            repaint();
            revalidate();
            atualizarTabela();
        };

        botaoRegistrarLote.addActionListener(acaoRegistrarLote);
        botaoAdicionarItem.addActionListener(acaoAdicionarItem);
        botaoSalvarEntrada.addActionListener(acaoSalvar);
        botaoCancelar.addActionListener(acaoCancelar);
        tProdutoItem.addActionListener(acaoAcharTamanho);
        botaoAlterarItem.addActionListener(acaoAtualizarItens);
        tQuantidadeItem.addFocusListener(acaoPerdeFocoQuantidade);

        verificarAberto();
        atualizarTabela();
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(600, 400);
        setVisible(true);
    }

    public void atualizarTabela() {

        try {
            remove(itensTable);
        } catch (Exception e) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }

        repositorioItem = new RepositorioItemEstoque();
        itensTable = new JTable();
        if (tCodigo.getText().equalsIgnoreCase("")) {
            try {
                remove(itensTable);
            } catch (Exception e) {

            }
        } else {
            itensTable = repositorioItem.listarTodos(tCodigo.getText());
            if (itensTable.getValueAt(0, 0) == null) {
                botaoAlterarItem.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Ainda sem itens para este lote.");
            } else {
                botaoAlterarItem.setEnabled(true);
                scroller = new JScrollPane(itensTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
                itensTable.getColumnModel().getColumn(0).setPreferredWidth(50);
                itensTable.getColumnModel().getColumn(1).setPreferredWidth(150);
                itensTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                itensTable.getColumnModel().getColumn(3).setPreferredWidth(30);
                itensTable.getColumnModel().getColumn(4).setPreferredWidth(30);
                itensTable.setAutoCreateRowSorter(true);

                try {
                    add(scroller);
                } catch (NullPointerException e) {
                }
            }
        }
    }

    public void habilitarItem(boolean sinal) {

        tProdutoItem.setEnabled(sinal);
        tValorVenda.setEditable(sinal);
        tValorItem.setEditable(sinal);
        tQuantidadeItem.setEditable(sinal);
        botaoAdicionarItem.setEnabled(sinal);
    }

    public void habilitarLote(boolean sinal) {

        tCodigo.setEditable(sinal);
        tFornecedor.setEnabled(sinal);
        tQuantidade.setEditable(sinal);
        tFrete.setEditable(sinal);
        tTaxa.setEditable(sinal);
        tData.setEditable(sinal);
        tValor.setEditable(sinal);
        botaoRegistrarLote.setEnabled(sinal);
    }

    public void limparItem() {

        tProdutoItem.setSelectedIndex(0);
        tValorItem.setText("");
        tQuantidadeItem.setText("");
        tamanhoLabel.setText("");
        tValorCusto.setText("");
        tValorVenda.setText("");
    }

    public void limparLote() {

        tCodigo.setText("");
        tFornecedor.setSelectedIndex(0);
        tQuantidade.setText("");
        tFrete.setText("");
        tTaxa.setText("");
        tData.setText("");
        tValor.setText("");
        tamanhoLabel.setText("");
        remove(scroller);
        repaint();
        revalidate();
    }

    public void verificarAberto() {
        repositorioLote = new RepositorioLote();
        lote = repositorioLote.verificarAberto();
        if (lote != null) {
            //JOptionPane.showMessageDialog(null, "Existe um lote aberto");
            repositorioFornecedor = new RepositorioFornecedor();
            tCodigo.setText(lote.getCodigo());
            tFornecedor.setSelectedItem(lote.getIdfornecedo() + "- " + repositorioFornecedor.procurar(lote.getIdfornecedo()).getNome());
            datePicker.getJFormattedTextField().setText(lote.getData().substring(0, 2) + "/" + lote.getData().substring(2, 4) + "/" + lote.getData().substring(4, 8));
            tQuantidade.setText(String.valueOf(lote.getQuantidade()));
            tFrete.setText(lote.getFrete());
            tTaxa.setText(lote.getTaxa());
            tValor.setText(lote.getValor());
            qtdItens = verificarQuantidadeItens(lote);
            if (qtdItens >= Integer.parseInt(tQuantidade.getText())) {
                JOptionPane.showMessageDialog(null, "Este lote já está com " + tQuantidade.getText() + " itens");
                habilitarItem(false);
                habilitarLote(false);
                botaoSalvarEntrada.setEnabled(true);
                atualizarTabela();
                repaint();
                revalidate();
            } else {
                habilitarItem(true);
                habilitarLote(false);
            }
            botaoCancelar.setEnabled(true);
        }
    }

    public int verificarQuantidadeItens(Lote lote) {
        repositorioLote = new RepositorioLote();
        int quantidade = repositorioLote.verificarQuantidadeItens(lote);
        return quantidade;
    }
}
