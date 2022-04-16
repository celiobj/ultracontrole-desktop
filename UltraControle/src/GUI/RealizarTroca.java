/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Estoque;
import Classes.FormaPagamentoVenda;
import Classes.ItemVenda;
import Classes.Produto;
import Classes.Venda;
import DAO.AccessDatabase;
import Persistencia.RepositorioCliente;
import Persistencia.RepositorioEstoque;
import Persistencia.RepositorioFormaPagamento;
import Persistencia.RepositorioFormaPagamentoVenda;
import Persistencia.RepositorioItemEstoque;
import Persistencia.RepositorioItemVenda;
import Persistencia.RepositorioProduto;
import Persistencia.RepositorioTamanho;
import Persistencia.RepositorioVenda;
import Util.ComboBoxSearch.ComboBoxFilterDecorator;
import Util.ComboBoxSearch.CustomComboRenderer;
import Util.ComboBoxSearch.JComboBoxFilterMain;
import Util.DateLabelFormatter;
import Util.Funcoes;
import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author celiobj
 */
public class RealizarTroca extends JPanel {

    JLabel clienteLabel, produtoEntradaLabel, produtoSaidaLabel, quantidadeEntradaLabel, quantidadeSaidaLabel, tamanhoLabel, valorCustoEntradaLabel, valorCustoSaidaLabel,
            valorVendaEntradaLabel, valorVendaSaidaLabel, valorTotalVenda, dataLabel, cupomLabel, tCupomLabel, formadePagamentoLabel,
            entradalabel, saidaLabel;
    JTextField tQuantidadeEntrada, tQuantidadeSaida, tValorEntradaCusto, tValorSaidaCusto, tValorEntradaVenda, tValorSaidaVenda, tValorTotal;
    JFormattedTextField tData;
    MaskFormatter mascaraData;
    static JComboBox tCliente;
    static JComboBox tProdutoEntrada, tProdutoSaida;
    static JComboBox tFormadePagamento;
    JButton botaoCadastrarCliente, botaoCadastrarProdutoEntrada, botaoCadastrarProdutoSaida, botaoAdicionarItemEntrada,
            botaoAdicionarItemSaida, botaoFinalizarVenda, botaoAbrirCupom, botaoAlterarItemEntrada, botaoAlterarItemSaida,
            botaoCancelar;
    JTable itensEntrada, itensSaida;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho, clientesVetor, produtosVetor;
    JScrollPane scrollerEntrada, scrollerSaida;
    ComboBoxFilterDecorator<String> decorate;
    ActionListener acaoCadastrarCliente, acaoCadastrarProdutoEntrada, acaoCadastrarProdutoSaida, acaoInserirItemEntrada, acaoInserirItemSaida,
            acaoAbrirCupom, acaoFecharCupom, acaoPerdefocoValorCustoEntrada, acaoPerdefocoValorCustoSaida,
            acaoCancelarCupom, acaoAtualizarItensEntrada, acaoAtualizarItensSaida;
    FocusAdapter acaoPerdeFocoQuantidadeEsqtoque;

    RepositorioVenda repositorioVenda;
    RepositorioItemVenda repositorioItemVenda;
    RepositorioFormaPagamentoVenda repositorioFormaPagamentoVenda;
    RepositorioEstoque repositorioEstoque;
    RepositorioCliente repositorioCliente;
    RepositorioProduto repositorioProduto;
    RepositorioFormaPagamento repositorioFormaPagamento;
    RepositorioItemEstoque repositorioItemEstoque;
    RepositorioTamanho repositorioTamanho;

    ItemVenda itemVenda;
    Venda venda;
    FormaPagamentoVenda formaPagamentoVenda;

    UtilDateModel model;
    Properties p;
    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;

    boolean primeiraVenda;

    public RealizarTroca() {

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

        clienteLabel = new JLabel("Cliente: ");
        produtoEntradaLabel = new JLabel("Prod.(entrada): ");
        produtoSaidaLabel = new JLabel("Prod.(saída): ");
        dataLabel = new JLabel("Data: ");
        cupomLabel = new JLabel("");
        tCupomLabel = new JLabel("");
        tamanhoLabel = new JLabel();
        quantidadeEntradaLabel = new JLabel("Qtd: ");
        quantidadeSaidaLabel = new JLabel("Qtd: ");
        valorCustoEntradaLabel = new JLabel("Valor de custo: ");
        valorVendaEntradaLabel = new JLabel("Valor de venda: ");
        valorCustoSaidaLabel = new JLabel("Valor de custo: ");
        valorVendaSaidaLabel = new JLabel("Valor de venda: ");
        valorTotalVenda = new JLabel("Valor total: ");
        formadePagamentoLabel = new JLabel("Forma de Pagamento: ");
        entradalabel = new JLabel("Entrada");
        saidaLabel = new JLabel("Saída");

        repositorioCliente = new RepositorioCliente();
        clientesVetor = repositorioCliente.popularCombo();
        tCliente = new JComboBox<>(clientesVetor.toArray(new String[clientesVetor.size()]));
        decorate = ComboBoxFilterDecorator.decorate(tCliente, CustomComboRenderer::getEmployeeDisplayText, JComboBoxFilterMain::employeeFilter);
        tCliente.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
        repositorioProduto = new RepositorioProduto();
        produtosVetor = repositorioProduto.popularCombo();
        tProdutoEntrada = new JComboBox<>(produtosVetor.toArray(new String[produtosVetor.size()]));
        decorate = ComboBoxFilterDecorator.decorate(tProdutoEntrada, CustomComboRenderer::getEmployeeDisplayText, JComboBoxFilterMain::employeeFilter);
        tProdutoEntrada.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));

        tProdutoSaida = new JComboBox<>(produtosVetor.toArray(new String[produtosVetor.size()]));
        decorate = ComboBoxFilterDecorator.decorate(tProdutoSaida, CustomComboRenderer::getEmployeeDisplayText, JComboBoxFilterMain::employeeFilter);
        tProdutoSaida.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));

        repositorioFormaPagamento = new RepositorioFormaPagamento();
        tFormadePagamento = new JComboBox(repositorioFormaPagamento.popularCombo());
        tData = new JFormattedTextField(mascaraData);
        tQuantidadeEntrada = new JTextField();
        tQuantidadeSaida = new JTextField();
        tValorEntradaCusto = new JTextField();
        tValorEntradaVenda = new JTextField();
        tValorSaidaCusto = new JTextField();
        tValorSaidaVenda = new JTextField();
        tValorTotal = new JTextField();

        botaoCadastrarCliente = new JButton("+");
        botaoCadastrarProdutoEntrada = new JButton("+");
        botaoCadastrarProdutoSaida = new JButton("+");
        botaoAdicionarItemEntrada = new JButton("Inserir Item");
        botaoAdicionarItemSaida = new JButton("Inserir Item");
        botaoFinalizarVenda = new JButton("Finalizar Troca");
        botaoAlterarItemEntrada = new JButton("Atualizar Itens");
        botaoAlterarItemSaida = new JButton("Atualizar Itens");
        botaoAbrirCupom = new JButton("Abrir Cupom");
        botaoCancelar = new JButton("Cancelar");

        entradalabel.setBounds((int) Math.round(Principal.largura * 0.02), 170, 100, 20);
        saidaLabel.setBounds((int) Math.round(Principal.largura * 0.02), 370, 100, 20);
        clienteLabel.setBounds((int) Math.round(Principal.largura * 0.02), 10, 150, 20);
        produtoEntradaLabel.setBounds((int) Math.round(Principal.largura * 0.02), 40, 100, 20);
        produtoSaidaLabel.setBounds((int) Math.round(Principal.largura * 0.02), 100, 100, 20);
        dataLabel.setBounds((int) Math.round(Principal.largura * 0.5), 10, 100, 20);
        quantidadeEntradaLabel.setBounds((int) Math.round(Principal.largura * 0.5), 40, 100, 20);
        quantidadeSaidaLabel.setBounds((int) Math.round(Principal.largura * 0.5), 100, 100, 20);
        valorCustoEntradaLabel.setBounds((int) Math.round(Principal.largura * 0.02), 70, 100, 20);
        valorVendaEntradaLabel.setBounds((int) Math.round(Principal.largura * 0.25), 70, 100, 20);
        valorCustoSaidaLabel.setBounds((int) Math.round(Principal.largura * 0.02), 130, 100, 20);
        valorVendaSaidaLabel.setBounds((int) Math.round(Principal.largura * 0.25), 130, 100, 20);
        formadePagamentoLabel.setBounds((int) Math.round(Principal.largura * 0.5), 160, 150, 20);
        valorTotalVenda.setBounds((int) Math.round(Principal.largura * 0.5), 130, 100, 20);

        botaoCadastrarCliente.setBounds((int) Math.round(Principal.largura * 0.45), 10, 30, 20);
        botaoCadastrarProdutoEntrada.setBounds((int) Math.round(Principal.largura * 0.45), 40, 30, 20);
        botaoCadastrarProdutoSaida.setBounds((int) Math.round(Principal.largura * 0.45), 100, 30, 20);
        botaoAdicionarItemEntrada.setBounds((int) Math.round(Principal.largura * 0.6), 40, 100, 20);
        botaoAdicionarItemSaida.setBounds((int) Math.round(Principal.largura * 0.6), 100, 100, 20);
        botaoFinalizarVenda.setBounds((int) Math.round(Principal.largura * 0.7), 130, 120, 20);
        botaoAlterarItemEntrada.setBounds((int) Math.round(Principal.largura * 0.7), 180, 120, 20);
        botaoAlterarItemSaida.setBounds((int) Math.round(Principal.largura * 0.7), 380, 120, 20);
        botaoAbrirCupom.setBounds((int) Math.round(Principal.largura * 0.7), 40, 120, 20);
        botaoCancelar.setBounds((int) Math.round(Principal.largura * 0.7), 100, 120, 20);
        datePicker.setBounds((int) Math.round(Principal.largura * 0.53), 10, 120, 27);

        tCliente.setBounds((int) Math.round(Principal.largura * 0.1), 10, (int) Math.round(Principal.largura / 3), 20);
        tProdutoEntrada.setBounds((int) Math.round(Principal.largura * 0.1), 40, (int) Math.round(Principal.largura / 3), 20);
        tProdutoSaida.setBounds((int) Math.round(Principal.largura * 0.1), 100, (int) Math.round(Principal.largura / 3), 20);
        //tamanhoLabel.setBounds((int) Math.round(Principal.largura * 0.6), 70, 100, 20);
        tQuantidadeEntrada.setBounds((int) Math.round(Principal.largura * 0.53), 40, 70, 20);
        tQuantidadeSaida.setBounds((int) Math.round(Principal.largura * 0.53), 100, 70, 20);
        tValorEntradaCusto.setBounds((int) Math.round(Principal.largura * 0.12), 70, 100, 20);
        tValorEntradaVenda.setBounds((int) Math.round(Principal.largura * 0.35), 70, 100, 20);
        tValorSaidaCusto.setBounds((int) Math.round(Principal.largura * 0.12), 130, 100, 20);
        tValorSaidaVenda.setBounds((int) Math.round(Principal.largura * 0.35), 130, 100, 20);
        tValorTotal.setBounds((int) Math.round(Principal.largura * 0.6), 130, 100, 20);
        tFormadePagamento.setBounds((int) Math.round(Principal.largura * 0.6), 160, 120, 20);

        cupomLabel.setBounds((int) Math.round(Principal.largura * 0.8), 10, 80, 20);
        tCupomLabel.setBounds((int) Math.round(Principal.largura * 0.85), 10, 30, 20);

        add(entradalabel);
        add(saidaLabel);
        add(clienteLabel);
        add(produtoEntradaLabel);
        add(produtoSaidaLabel);
        add(dataLabel);
        add(quantidadeEntradaLabel);
        add(quantidadeSaidaLabel);
        add(valorCustoEntradaLabel);
        add(valorVendaEntradaLabel);
        add(valorCustoSaidaLabel);
        add(valorVendaSaidaLabel);
        add(formadePagamentoLabel);
        add(valorTotalVenda);

        add(tCliente);
        add(tProdutoEntrada);
        add(tProdutoSaida);
        add(tamanhoLabel);
        add(tQuantidadeEntrada);
        add(tQuantidadeSaida);
        add(tValorEntradaCusto);
        add(tValorEntradaVenda);
        add(tValorSaidaCusto);
        add(tValorSaidaVenda);
        add(tValorTotal);
        add(tFormadePagamento);
        add(cupomLabel);
        add(tCupomLabel);

        add(botaoCadastrarCliente);
        add(botaoCadastrarProdutoEntrada);
        add(botaoCadastrarProdutoSaida);
        add(botaoAdicionarItemEntrada);
        add(botaoAdicionarItemSaida);
        add(botaoFinalizarVenda);
        add(botaoAbrirCupom);
        add(botaoCancelar);
        add(botaoAlterarItemEntrada);
        add(botaoAlterarItemSaida);
        add(datePicker);

        botaoFinalizarVenda.setEnabled(false);
        botaoCancelar.setVisible(false);
        botaoAlterarItemEntrada.setEnabled(false);
        botaoAlterarItemSaida.setEnabled(false);
        tValorTotal.setEditable(false);
        tValorEntradaCusto.setEditable(false);

        acaoPerdefocoValorCustoEntrada = (ActionEvent e) -> {
            try {
                repositorioItemEstoque = new RepositorioItemEstoque();
                if (tProdutoEntrada.getSelectedIndex() != 0) {
                    String produtos[] = tProdutoEntrada.getSelectedItem().toString().split(Pattern.quote("-"));
                    double valorCusto = 0;
                    try {
                        valorCusto = Double.parseDouble(repositorioItemEstoque.verificarValorMedioCusto(produtos[2]));
                        tValorEntradaCusto.setText(Funcoes.paraFormatoDinheiro(valorCusto));
                    } catch (Exception ex) {
                        Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tValorEntradaVenda.setText(Funcoes.paraFormatoDinheiro(Double.parseDouble(repositorioItemEstoque.verificarValorMedioVenda(produtos[2]))));
                    String itens[] = tProdutoEntrada.getSelectedItem().toString().split(Pattern.quote("-"));
                    repositorioProduto = new RepositorioProduto();
                    Produto produto = repositorioProduto.procurar(itens[2]);
                    repositorioTamanho = new RepositorioTamanho();
                    tamanhoLabel.setText(repositorioTamanho.procurar(produto.getTamanho()).getDescricao());
                }
            } catch (NullPointerException en) {
                
            }
        };

        acaoPerdefocoValorCustoSaida = (ActionEvent e) -> {
            try {
                repositorioItemEstoque = new RepositorioItemEstoque();
                if (tProdutoSaida.getSelectedIndex() != 0) {
                    String produtos[] = tProdutoSaida.getSelectedItem().toString().split(Pattern.quote("-"));
                    double valorCusto = 0;
                    try {
                        valorCusto = Double.parseDouble(repositorioItemEstoque.verificarValorMedioCusto(produtos[2]));
                        tValorSaidaCusto.setText(Funcoes.paraFormatoDinheiro(valorCusto));
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tValorSaidaVenda.setText(Funcoes.paraFormatoDinheiro(Double.parseDouble(repositorioItemEstoque.verificarValorMedioVenda(produtos[2]))));
                    String itens[] = tProdutoSaida.getSelectedItem().toString().split(Pattern.quote("-"));
                    repositorioProduto = new RepositorioProduto();
                    Produto produto = repositorioProduto.procurar(itens[2]);
                    repositorioTamanho = new RepositorioTamanho();
                    tamanhoLabel.setText(repositorioTamanho.procurar(produto.getTamanho()).getDescricao());
                }
            } catch (NullPointerException en) {
                
            }
        };

        acaoAtualizarItensEntrada = (ActionEvent e) -> {
            repositorioItemVenda = new RepositorioItemVenda();
            try {
                int l = itensEntrada.getSelectedRow();
                String codigoProduto = itensEntrada.getValueAt(l, 0).toString();
                String valorProduto = null;
                try {
                    valorProduto = Funcoes.formatoParaInserir(itensEntrada.getValueAt(l, 2).toString());
                } catch (Exception ex) {
                    Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                }
                String quantidadeProduto = itensEntrada.getValueAt(l, 3).toString();
                if (quantidadeProduto.equalsIgnoreCase("0")) {
                    if (JOptionPane.showConfirmDialog(null, "Você inseriu a quantidade '0'.\n"
                            + "Deseja escluir o item? ") == 0) {
                        repositorioItemVenda.remover(tCupomLabel.getText(), codigoProduto);
                    }
                } else {
                    repositorioItemVenda.alterar(tCupomLabel.getText(), codigoProduto, valorProduto, quantidadeProduto);
                }
                
                try {
                    remove(itensEntrada);
                } catch (Exception et) {
                }
                try {
                    remove(itensSaida);
                } catch (Exception et) {
                }
                HabilitarFinalizarCupom(true, tCupomLabel.getText());
                repaint();
                revalidate();
                atualizarTabela();
                
            } catch (ArrayIndexOutOfBoundsException er) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela.");
            }
        };

        acaoAtualizarItensSaida = (ActionEvent e) -> {
            repositorioItemVenda = new RepositorioItemVenda();
            try {
                int l = itensSaida.getSelectedRow();
                String codigoProduto = itensSaida.getValueAt(l, 0).toString();
                String valorProduto = null;
                try {
                    valorProduto = Funcoes.formatoParaInserir(itensSaida.getValueAt(l, 2).toString());
                } catch (Exception ex) {
                    Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                }
                String quantidadeProduto = itensSaida.getValueAt(l, 3).toString();
                if (quantidadeProduto.equalsIgnoreCase("0")) {
                    if (JOptionPane.showConfirmDialog(null, "Você inseriu a quantidade '0'.\n"
                            + "Deseja escluir o item? ") == 0) {
                        repositorioItemVenda.remover(tCupomLabel.getText(), codigoProduto);
                    }
                } else {
                    repositorioItemVenda.alterar(tCupomLabel.getText(), codigoProduto, valorProduto, quantidadeProduto);
                }
                try {
                    remove(itensEntrada);
                } catch (Exception et) {
                }
                try {
                    remove(itensSaida);
                } catch (Exception et) {
                }
                HabilitarFinalizarCupom(true, tCupomLabel.getText());
                repaint();
                revalidate();
                atualizarTabela();
                
            } catch (ArrayIndexOutOfBoundsException er) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela.");
            }
        };

        acaoPerdeFocoQuantidadeEsqtoque = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                repositorioEstoque = new RepositorioEstoque();
                String produtos[] = tProdutoEntrada.getSelectedItem().toString().split(Pattern.quote("-"));
                if (!repositorioEstoque.verificarSaldoEstoque(produtos[2], tQuantidadeEntrada.getText())) {
                    tQuantidadeEntrada.setText("");
                }
            }
        };

        acaoCadastrarCliente = (ActionEvent e) -> {
            CadastroClienteVenda cadastroClienteVenda = new CadastroClienteVenda(2);
            // JToolBar ferramentas = new JToolBar("Ferramentas");
            //ferramentas.setBounds(2, 584, 1095, 64);
            // cadastroClienteVenda.setBounds(0, 0, 700, 400);
            //cadastroClienteVenda.add(ferramentas);
            // add(cadastroClienteVenda);
            cadastroClienteVenda.setSize(800, 600);
            cadastroClienteVenda.setLocationRelativeTo(null);
            revalidate();
            repaint();
        };

        acaoCadastrarProdutoEntrada = (ActionEvent e) -> {
            CadastroProdutoVenda cadastroProdutoVenda = new CadastroProdutoVenda(2);
            // JToolBar ferramentas = new JToolBar("Ferramentas");
            //ferramentas.setBounds(2, 584, 1095, 64);
            // cadastroClienteVenda.setBounds(0, 0, 700, 400);
            //cadastroClienteVenda.add(ferramentas);
            // add(cadastroClienteVenda);
            cadastroProdutoVenda.setSize(800, 600);
            cadastroProdutoVenda.setLocationRelativeTo(null);
            revalidate();
            repaint();
        };
        acaoCadastrarProdutoSaida = (ActionEvent e) -> {
            CadastroProdutoVenda cadastroProdutoVenda = new CadastroProdutoVenda(3);
            // JToolBar ferramentas = new JToolBar("Ferramentas");
            //ferramentas.setBounds(2, 584, 1095, 64);
            // cadastroClienteVenda.setBounds(0, 0, 700, 400);
            //cadastroClienteVenda.add(ferramentas);
            // add(cadastroClienteVenda);
            cadastroProdutoVenda.setSize(800, 600);
            cadastroProdutoVenda.setLocationRelativeTo(null);
            revalidate();
            repaint();
        };

        acaoAbrirCupom = (ActionEvent e) -> {
            if (tCliente.getSelectedIndex() == 0 || datePicker.getJFormattedTextField().getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos(Cliente e Data)");
            } else {
                repositorioVenda = new RepositorioVenda();
                primeiraVenda = repositorioVenda.primeiraVenda();
                venda = new Venda();
                venda.setData(Funcoes.paraInserirData(datePicker.getJFormattedTextField().getText()));
                venda.setSituacao("A");
                String cliente[] = tCliente.getSelectedItem().toString().split(Pattern.quote("-"));
                venda.setIdCliente(cliente[0]);
                venda.setTipo("2");
                repositorioVenda.adcionar(venda);
                
                cupomLabel.setText("Cupom: ");
                tCupomLabel.setText(repositorioVenda.pegarUltimo());
                HabilitarAbrirCupom(false);
                HabilitarInserirItem(true);
                HabilitarFinalizarCupom(false, null);
            }
        };

        acaoCancelarCupom = (ActionEvent e) -> {
            repositorioVenda = new RepositorioVenda();
            if (repositorioVenda.cancelar(tCupomLabel.getText())) {
                JOptionPane.showMessageDialog(null, "Cupom cancelado com sucesso!");
                limparTudo();
                HabilitarAbrirCupom(true);
                HabilitarInserirItem(false);
                HabilitarFinalizarCupom(false, null);
                try {
                    remove(itensEntrada);
                } catch (Exception et) {
                }
            }
        };

        acaoInserirItemEntrada = (ActionEvent e) -> {
            if (tProdutoEntrada.getSelectedIndex() == 0 || tQuantidadeEntrada.getText().equalsIgnoreCase("") || tValorEntradaCusto.getText().equalsIgnoreCase("") || tValorEntradaVenda.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos(Produto, Quantidade e valor de custo/venda");
            } else {
                repositorioItemVenda = new RepositorioItemVenda();
                String produtos[] = tProdutoEntrada.getSelectedItem().toString().split(Pattern.quote("-"));
                if (repositorioItemVenda.consultarItemnaVenda(tCupomLabel.getText(), produtos[2], "3")) {
                    JOptionPane.showMessageDialog(null, "Este produto já foi inserido na venda.");
                    
                } else {
                    itemVenda = new ItemVenda();
                    itemVenda.setIdProduto(produtos[2]);
                    itemVenda.setIdVenda(tCupomLabel.getText());
                    try {
                        itemVenda.setValorCusto(Funcoes.formatoParaInserir(tValorEntradaCusto.getText()));
                    } catch (Exception ex) {
                        Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        itemVenda.setValorVenda(Funcoes.formatoParaInserir(tValorEntradaVenda.getText()));
                    } catch (Exception ex) {
                        Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    itemVenda.setQuantidade(Integer.parseInt(tQuantidadeEntrada.getText()));
                    itemVenda.setTipo("3");
                    if (repositorioItemVenda.adcionar(itemVenda)) {
                        limparParaInserir();
                        HabilitarFinalizarCupom(true, tCupomLabel.getText());
                        try {
                            remove(itensEntrada);
                        } catch (Exception et) {
                        }
                        try {
                            remove(itensSaida);
                        } catch (Exception et) {
                        }
                        atualizarTabela();
                        repaint();
                        revalidate();
                    }
                }
            }
        };
        acaoInserirItemSaida = (ActionEvent e) -> {
            if (tProdutoSaida.getSelectedIndex() == 0 || tQuantidadeSaida.getText().equalsIgnoreCase("") || tValorSaidaCusto.getText().equalsIgnoreCase("") || tValorSaidaVenda.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos(Produto, Quantidade e valor de custo/venda");
            } else {
                repositorioItemVenda = new RepositorioItemVenda();
                String produtos[] = tProdutoSaida.getSelectedItem().toString().split(Pattern.quote("-"));
                if (repositorioItemVenda.consultarItemnaVenda(tCupomLabel.getText(), produtos[2], "4")) {
                    JOptionPane.showMessageDialog(null, "Este produto já foi inserido na venda.");
                    
                } else {
                    repositorioEstoque = new RepositorioEstoque();
                    if (!repositorioEstoque.verificarSaldoEstoque(produtos[2], tQuantidadeSaida.getText())) {
                        tQuantidadeSaida.setText("");
                    } else {
                        itemVenda = new ItemVenda();
                        itemVenda.setIdProduto(produtos[2]);
                        itemVenda.setIdVenda(tCupomLabel.getText());
                        try {
                            itemVenda.setValorCusto(Funcoes.formatoParaInserir(tValorSaidaCusto.getText()));
                        } catch (Exception ex) {
                            Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            itemVenda.setValorVenda(Funcoes.formatoParaInserir(tValorSaidaVenda.getText()));
                        } catch (Exception ex) {
                            Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        itemVenda.setQuantidade(Integer.parseInt(tQuantidadeSaida.getText()));
                        itemVenda.setTipo("4");
                        if (repositorioItemVenda.adcionar(itemVenda)) {
                            limparParaInserir();
                            HabilitarFinalizarCupom(true, tCupomLabel.getText());
                            try {
                                remove(itensEntrada);
                            } catch (Exception et) {
                            }
                            try {
                                remove(itensSaida);
                            } catch (Exception et) {
                            }
                            atualizarTabela();
                            repaint();
                            revalidate();
                        }
                    }
                }
            }
        };
        acaoFecharCupom = (ActionEvent e) -> {
            if (tFormadePagamento.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Preencha a forma de pagamento.");
            } else {
                repositorioItemVenda = new RepositorioItemVenda();
                repositorioVenda = new RepositorioVenda();
                repositorioFormaPagamentoVenda = new RepositorioFormaPagamentoVenda();
                repositorioEstoque = new RepositorioEstoque();
                Vector<Estoque> estoqueEntrada = new Vector<Estoque>();
                Vector<Estoque> estoqueSaida = new Vector<Estoque>();
                if (repositorioItemVenda.fechar(tCupomLabel.getText())) {
                    String valor = null;
                    try {
                        valor = Funcoes.formatoParaInserir(tValorTotal.getText());
                    } catch (Exception ex) {
                        Logger.getLogger(RealizarTroca.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (repositorioVenda.alterarValorTotal(tCupomLabel.getText(), valor)) {
                        formaPagamentoVenda = new FormaPagamentoVenda();
                        String formas[] = tFormadePagamento.getSelectedItem().toString().split(Pattern.quote("-"));
                        formaPagamentoVenda.setIdFormapagamentoVenda(formas[0]);
                        formaPagamentoVenda.setIdVenda(tCupomLabel.getText());
                        if (repositorioFormaPagamentoVenda.adcionar(formaPagamentoVenda)) {
                            if (repositorioVenda.fechar(tCupomLabel.getText())) {
                                estoqueEntrada = repositorioItemVenda.itensdeTroca(tCupomLabel.getText(), "3");
                                for (int i = 1; i <= estoqueEntrada.size() - 1; i++) {
                                    repositorioEstoque.alterar(estoqueEntrada.elementAt(i), true);
                                }
                                estoqueSaida = repositorioItemVenda.itensdeTroca(tCupomLabel.getText(), "4");
                                for (int i = 1; i <= estoqueSaida.size() - 1; i++) {
                                    repositorioEstoque.alterar(estoqueSaida.elementAt(i), false);
                                }
                                JOptionPane.showMessageDialog(null, "Troca finalizada com sucesso");
                                HabilitarAbrirCupom(true);
                                HabilitarInserirItem(false);
                                HabilitarFinalizarCupom(false, null);
                                limparTudo();
                                try {
                                    remove(itensEntrada);
                                } catch (Exception et) {
                                }
                                repaint();
                                revalidate();
                                atualizarTabela();
                            }
                        }
                    }
                }
            }
        };

        botaoCadastrarCliente.addActionListener(acaoCadastrarCliente);
        botaoCadastrarProdutoEntrada.addActionListener(acaoCadastrarProdutoEntrada);
        botaoCadastrarProdutoSaida.addActionListener(acaoCadastrarProdutoSaida);
        botaoAdicionarItemEntrada.addActionListener(acaoInserirItemEntrada);
        botaoAdicionarItemSaida.addActionListener(acaoInserirItemSaida);
        botaoAbrirCupom.addActionListener(acaoAbrirCupom);
        botaoFinalizarVenda.addActionListener(acaoFecharCupom);
        botaoAlterarItemEntrada.addActionListener(acaoAtualizarItensEntrada);
        botaoAlterarItemSaida.addActionListener(acaoAtualizarItensSaida);
        botaoCancelar.addActionListener(acaoCancelarCupom);
        tProdutoEntrada.addActionListener(acaoPerdefocoValorCustoEntrada);
        tProdutoSaida.addActionListener(acaoPerdefocoValorCustoSaida);
        //tQuantidade.addFocusListener(acaoPerdeFocoQuantidadeEsqtoque);
        repositorioVenda = new RepositorioVenda();
        venda = repositorioVenda.verificarAberto("2");
        if (venda == null) {
            HabilitarAbrirCupom(true);
            HabilitarInserirItem(false);
            HabilitarFinalizarCupom(false, null);
        } else {
            //JOptionPane.showMessageDialog(null, "Existe uma venda em aberto");
            repositorioCliente = new RepositorioCliente();
            tCliente.setSelectedItem(venda.getIdCliente() + "- " + repositorioCliente.procurar(venda.getIdCliente()).getNome());
            //dataLabel.setText(venda.getData());
            datePicker.getJFormattedTextField().setText(venda.getData().substring(0, 2) + "/" + venda.getData().substring(2, 4) + "/" + venda.getData().substring(4, 8));
            cupomLabel.setText("Cupom: ");
            tCupomLabel.setText(venda.getIdVenda());
            HabilitarAbrirCupom(false);
            HabilitarInserirItem(true);
            HabilitarFinalizarCupom(true, venda.getIdVenda());
        }
        atualizarTabela();
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(600, 400);
        setVisible(true);
    }

    public void atualizarTabela() {
        try {
            remove(itensEntrada);
        } catch (Exception e) {
        }
        try {
            remove(itensSaida);
        } catch (Exception e) {
        }
        try {
            remove(scrollerSaida);
        } catch (Exception e) {
        }
        try {
            remove(scrollerEntrada);
        } catch (Exception e) {
        }
        repositorioVenda = new RepositorioVenda();
        itensEntrada = new JTable();
        itensEntrada = repositorioVenda.listarItensVenda(tCupomLabel.getText(), "3");
        itensSaida = new JTable();
        itensSaida = repositorioVenda.listarItensVenda(tCupomLabel.getText(), "4");
        if (itensEntrada.getValueAt(0, 0) == null) {
            botaoAlterarItemEntrada.setEnabled(false);
            botaoAlterarItemSaida.setEnabled(false);
            HabilitarFinalizarCupom(false, null);
            // JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            botaoAlterarItemEntrada.setEnabled(true);
            botaoAlterarItemSaida.setEnabled(true);
            scrollerEntrada = new JScrollPane(itensEntrada, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollerEntrada.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.18));
            itensEntrada.getColumnModel().getColumn(0).setPreferredWidth(30);
            itensEntrada.getColumnModel().getColumn(1).setPreferredWidth(100);
            itensEntrada.getColumnModel().getColumn(2).setPreferredWidth(30);
            itensEntrada.getColumnModel().getColumn(3).setPreferredWidth(30);
            itensEntrada.getColumnModel().getColumn(4).setPreferredWidth(30);
            itensEntrada.setAutoCreateRowSorter(true);
        }
        if (itensSaida.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scrollerSaida = new JScrollPane(itensSaida, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollerSaida.setBounds((int) Math.round(Principal.largura * 0.02), 400, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.18));
            itensSaida.getColumnModel().getColumn(0).setPreferredWidth(30);
            itensSaida.getColumnModel().getColumn(1).setPreferredWidth(100);
            itensSaida.getColumnModel().getColumn(2).setPreferredWidth(30);
            itensSaida.getColumnModel().getColumn(3).setPreferredWidth(30);
            itensSaida.getColumnModel().getColumn(4).setPreferredWidth(30);
            itensSaida.setAutoCreateRowSorter(true);
        }
        try {
            add(scrollerEntrada);
        } catch (NullPointerException e) {
        }
        try {
            add(scrollerSaida);
        } catch (NullPointerException e) {
        }
    }

    public void HabilitarAbrirCupom(boolean sinal) {
        tCliente.setEnabled(sinal);
        botaoCadastrarCliente.setEnabled(sinal);
        datePicker.setEnabled(sinal);
        botaoAbrirCupom.setEnabled(sinal);
    }

    public void HabilitarInserirItem(boolean sinal) {
        tProdutoEntrada.setEnabled(sinal);
        tProdutoSaida.setEnabled(sinal);
        botaoCadastrarProdutoEntrada.setEnabled(sinal);
        botaoCadastrarProdutoSaida.setEnabled(sinal);
        tQuantidadeEntrada.setEditable(sinal);
        tQuantidadeSaida.setEditable(sinal);
        tValorEntradaVenda.setEditable(sinal);
        botaoAdicionarItemEntrada.setEnabled(sinal);
        botaoAdicionarItemSaida.setEnabled(sinal);
    }

    public void HabilitarFinalizarCupom(boolean sinal, String cod) {
        if (sinal) {
            repositorioVenda = new RepositorioVenda();
            tValorTotal.setText(repositorioVenda.valorTotalTroca(cod));
        }
        tFormadePagamento.setEnabled(sinal);
        // tValorTotal.setEditable(sinal);
        botaoFinalizarVenda.setEnabled(sinal);
        botaoCancelar.setVisible(sinal);
    }

    public void limparParaInserir() {
        tProdutoEntrada.setSelectedIndex(0);
        tProdutoSaida.setSelectedIndex(0);
        tQuantidadeEntrada.setText("");
        tQuantidadeSaida.setText("");
        tValorEntradaCusto.setText("");
        tValorEntradaVenda.setText("");
        tValorSaidaCusto.setText("");
        tValorSaidaVenda.setText("");
        tamanhoLabel.setText("");
    }

    public void limparTudo() {
        tCliente.setSelectedIndex(0);
        tProdutoEntrada.setSelectedIndex(0);
        tProdutoSaida.setSelectedIndex(0);
        tFormadePagamento.setSelectedIndex(0);
        //tData.setText("");
        datePicker.getJFormattedTextField().setText("");
        tQuantidadeEntrada.setText("");
        tQuantidadeSaida.setText("");
        tValorEntradaCusto.setText("");
        tValorEntradaVenda.setText("");
        tValorSaidaCusto.setText("");
        tValorSaidaVenda.setText("");
        tValorTotal.setText("");
        tCupomLabel.setText("");
        tamanhoLabel.setText("");
        remove(scrollerEntrada);
        remove(scrollerSaida);
        repaint();
        revalidate();
    }

    public double percentualTela(double valor, double posicao) {
        return (posicao * 100) / valor;
    }
}
