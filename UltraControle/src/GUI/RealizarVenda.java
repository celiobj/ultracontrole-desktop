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
import java.awt.Dimension;
import java.awt.Toolkit;
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
public class RealizarVenda extends JPanel {

    JLabel clienteLabel, produtoLabel, quantidadeLabel, tamanhoLabel, valorCustoLabel, valorVendaLabel, valorTotalVenda, dataLabel, cupomLabel, tCupomLabel, formadePagamentoLabel;
    JTextField tQuantidade, tValorCusto, tValorVenda, tValorTotal;
    JFormattedTextField tData;
    MaskFormatter mascaraData;
    static JComboBox tCliente;
    static JComboBox tProduto;
    static JComboBox tFormadePagamento;
    JButton botaoCadastrarCliente, botaoCadastrarProduto, botaoAdicionarItem, botaoFinalizarVenda, botaoAbrirCupom, botaoAlterarItem, botaoCancelar;
    JTable itens;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho, clientesVetor, produtosVetor;
    JScrollPane scroller;
    ComboBoxFilterDecorator<String> decorate;
    ActionListener acaoCadastrarCliente, acaoCadastrarProduto, acaoInserirItem, acaoAbrirCupom, acaoFecharCupom, acaoPerdefocoValorCusto, acaoCancelarCupom, acaoAtualizarItens;
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

    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
    int largura = d.width;
    int altura = d.height;
    int meioTela = largura / 2;

    boolean primeiraVenda;

    public RealizarVenda() {

        //   VerificarUsuario.LOGGER.log(Level.FINE, "Acessou a tela de vendas");
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
        produtoLabel = new JLabel("Produto: ");
        dataLabel = new JLabel("Data: ");
        cupomLabel = new JLabel("");
        tCupomLabel = new JLabel("");
        tamanhoLabel = new JLabel();
        quantidadeLabel = new JLabel("Qtd: ");
        valorCustoLabel = new JLabel("Valor de custo: ");
        valorVendaLabel = new JLabel("Valor de venda: ");
        valorTotalVenda = new JLabel("Valor total: ");
        formadePagamentoLabel = new JLabel("Forma de Pagamento: ");

        repositorioCliente = new RepositorioCliente();
        clientesVetor = repositorioCliente.popularCombo();
        tCliente = new JComboBox<>(clientesVetor.toArray(new String[clientesVetor.size()]));
        decorate = ComboBoxFilterDecorator.decorate(tCliente, CustomComboRenderer::getEmployeeDisplayText, JComboBoxFilterMain::employeeFilter);
        tCliente.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
        repositorioProduto = new RepositorioProduto();
        produtosVetor = repositorioProduto.popularCombo();
        tProduto = new JComboBox<>(produtosVetor.toArray(new String[produtosVetor.size()]));
        decorate = ComboBoxFilterDecorator.decorate(tProduto, CustomComboRenderer::getEmployeeDisplayText, JComboBoxFilterMain::employeeFilter);
        tProduto.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
        repositorioFormaPagamento = new RepositorioFormaPagamento();
        tFormadePagamento = new JComboBox(repositorioFormaPagamento.popularCombo());
        tData = new JFormattedTextField(mascaraData);
        tQuantidade = new JTextField();
        tValorCusto = new JTextField();
        tValorVenda = new JTextField();
        tValorTotal = new JTextField();

        botaoCadastrarCliente = new JButton("+");
        botaoCadastrarProduto = new JButton("+");
        botaoAdicionarItem = new JButton("Inserir Item");
        botaoFinalizarVenda = new JButton("Finalizar Venda");
        botaoAlterarItem = new JButton("Atualizar Itens");
        botaoAbrirCupom = new JButton("Abrir Cupom");
        botaoCancelar = new JButton("Cancelar");

        clienteLabel.setBounds((int) Math.round(largura * 0.02), 10, 150, 20);
        produtoLabel.setBounds((int) Math.round(largura * 0.02), 40, 100, 20);
        dataLabel.setBounds((int) Math.round(largura * 0.5), 10, 100, 20);
        quantidadeLabel.setBounds((int) Math.round(largura * 0.5), 40, 100, 20);
        valorCustoLabel.setBounds((int) Math.round(largura * 0.02), 100, 100, 20);
        valorVendaLabel.setBounds((int) Math.round(largura * 0.25), 100, 100, 20);
        formadePagamentoLabel.setBounds((int) Math.round(largura * 0.02), 130, 150, 20);
        valorTotalVenda.setBounds((int) Math.round(largura * 0.5), 150, 100, 20);

        botaoCadastrarCliente.setBounds((int) Math.round(largura * 0.45), 10, 30, 20);
        botaoCadastrarProduto.setBounds((int) Math.round(largura * 0.45), 40, 30, 20);
        botaoAdicionarItem.setBounds((int) Math.round(largura * 0.7), 70, 120, 20);
        botaoFinalizarVenda.setBounds((int) Math.round(largura * 0.02), 160, 120, 20);
        botaoAlterarItem.setBounds((int) Math.round(largura * 0.7), 180, 120, 20);
        botaoAbrirCupom.setBounds((int) Math.round(largura * 0.7), 40, 120, 20);
        botaoCancelar.setBounds((int) Math.round(largura * 0.7), 100, 120, 20);
        datePicker.setBounds((int) Math.round(largura * 0.53), 10, 120, 27);

        tCliente.setBounds((int) Math.round(largura * 0.1), 10, (int) Math.round(largura / 3), 20);
        tProduto.setBounds((int) Math.round(largura * 0.1), 40, (int) Math.round(largura / 3), 20);
        tamanhoLabel.setBounds((int) Math.round(largura * 0.6), 70, 100, 20);
        tQuantidade.setBounds((int) Math.round(largura * 0.53), 40, 70, 20);
        tValorCusto.setBounds((int) Math.round(largura * 0.12), 100, 100, 20);
        tValorVenda.setBounds((int) Math.round(largura * 0.35), 100, 100, 20);
        tValorTotal.setBounds((int) Math.round(largura * 0.6), 150, 100, 20);
        tFormadePagamento.setBounds((int) Math.round(largura * 0.12), 130, 120, 20);

        cupomLabel.setBounds((int) Math.round(largura * 0.8), 10, 80, 20);
        tCupomLabel.setBounds((int) Math.round(largura * 0.85), 10, 30, 20);

        add(clienteLabel);
        add(produtoLabel);
        add(dataLabel);
        add(quantidadeLabel);
        add(valorCustoLabel);
        add(valorVendaLabel);
        add(formadePagamentoLabel);
        add(valorTotalVenda);

        add(tCliente);
        add(tProduto);
        // add(tamanhoLabel);
        add(tQuantidade);
        add(tValorCusto);
        add(tValorVenda);
        add(tValorTotal);
        add(tFormadePagamento);
        add(cupomLabel);
        add(tCupomLabel);

        add(botaoCadastrarCliente);
        add(botaoCadastrarProduto);
        add(botaoAdicionarItem);
        add(botaoFinalizarVenda);
        add(botaoAbrirCupom);
        add(botaoCancelar);
        add(botaoAlterarItem);
        add(datePicker);

        botaoFinalizarVenda.setEnabled(false);
        botaoCancelar.setVisible(false);
        botaoAlterarItem.setEnabled(false);
        tValorTotal.setEditable(false);
        tValorCusto.setEditable(false);

        acaoPerdefocoValorCusto = (ActionEvent e) -> {
            try {
                repositorioItemEstoque = new RepositorioItemEstoque();
                if (tProduto.getSelectedIndex() != 0) {
                    String produtos[] = tProduto.getSelectedItem().toString().split(Pattern.quote("-"));
                    double valorCusto = 0;
                    try {
                        valorCusto = Double.parseDouble(repositorioItemEstoque.verificarValorMedioCusto(produtos[2]));
                        tValorCusto.setText(Funcoes.paraFormatoDinheiro(valorCusto));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tValorVenda.setText(Funcoes.paraFormatoDinheiro(Double.parseDouble(repositorioItemEstoque.verificarValorMedioVenda(produtos[2]))));
                    String[] itens1 = tProduto.getSelectedItem().toString().split(Pattern.quote("-"));
                    repositorioProduto = new RepositorioProduto();
                    Produto produto = repositorioProduto.procurar(itens1[2]);
                    repositorioTamanho = new RepositorioTamanho();
                    tamanhoLabel.setText(repositorioTamanho.procurar(produto.getTamanho()).getDescricao());
                }
            } catch (NullPointerException en) {

            }
        };

        acaoAtualizarItens = (ActionEvent e) -> {
            repositorioItemVenda = new RepositorioItemVenda();
            try {
                int l = itens.getSelectedRow();
                String codigoProduto = itens.getValueAt(l, 0).toString();
                String valorProduto = null;
                try {
                    valorProduto = Funcoes.formatoParaInserir(itens.getValueAt(l, 2).toString());
                } catch (ParseException ex) {
                    Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
                String quantidadeProduto = itens.getValueAt(l, 3).toString();
                if (quantidadeProduto.equalsIgnoreCase("0")) {
                    if (JOptionPane.showConfirmDialog(null, "Você inseriu a quantidade '0'.\n"
                            + "Deseja escluir o item? ") == 0) {
                        repositorioItemVenda.remover(tCupomLabel.getText(), codigoProduto);
                    }
                } else {
                    repositorioItemVenda.alterar(tCupomLabel.getText(), codigoProduto, valorProduto, quantidadeProduto);
                }

                try {
                    remove(itens);
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
                String produtos[] = tProduto.getSelectedItem().toString().split(Pattern.quote("-"));
                if (!repositorioEstoque.verificarSaldoEstoque(produtos[2], tQuantidade.getText())) {
                    tQuantidade.setText("");
                }
            }
        };

        acaoCadastrarCliente = (ActionEvent e) -> {
            CadastroClienteVenda cadastroClienteVenda = new CadastroClienteVenda(1);
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

        acaoCadastrarProduto = (ActionEvent e) -> {
            CadastroProdutoVenda cadastroProdutoVenda = new CadastroProdutoVenda(1);
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
                venda.setTipo("1");
                String cliente[] = tCliente.getSelectedItem().toString().split(Pattern.quote("-"));
                venda.setIdCliente(cliente[0]);
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
                    remove(itens);
                } catch (Exception et) {
                }
            }
        };

        acaoInserirItem = (ActionEvent e) -> {
            if (tProduto.getSelectedIndex() == 0 || tQuantidade.getText().equalsIgnoreCase("") || tValorCusto.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos(Produto, Quantidade e valor de custo");
            } else {
                repositorioItemVenda = new RepositorioItemVenda();
                String produtos[] = tProduto.getSelectedItem().toString().split(Pattern.quote("-"));
                if (repositorioItemVenda.consultarItemnaVenda(tCupomLabel.getText(), produtos[2], "V")) {
                    JOptionPane.showMessageDialog(null, "Este produto já foi inserido na venda.");

                } else {
                    repositorioEstoque = new RepositorioEstoque();
                    if (!repositorioEstoque.verificarSaldoEstoque(produtos[2], tQuantidade.getText())) {
                        tQuantidade.setText("");
                    } else {
                        itemVenda = new ItemVenda();
                        itemVenda.setIdProduto(produtos[2]);
                        itemVenda.setIdVenda(tCupomLabel.getText());
                        try {
                            itemVenda.setValorCusto(Funcoes.formatoParaInserir(tValorCusto.getText()));
                        } catch (ParseException ex) {
                            Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            itemVenda.setValorVenda(Funcoes.formatoParaInserir(tValorVenda.getText()));
                        } catch (ParseException ex) {
                            Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        itemVenda.setQuantidade(Integer.parseInt(tQuantidade.getText()));
                        itemVenda.setTipo("4");
                        if (repositorioItemVenda.adcionar(itemVenda)) {
                            limparParaInserir();
                            HabilitarFinalizarCupom(true, tCupomLabel.getText());
                            try {
                                remove(itens);
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
            if (primeiraVenda) {
                JOptionPane.showMessageDialog(null, "Parabéns pela sua primeira venda, é o primeiro passo de um grande futuro!!!");
                JOptionPane.showMessageDialog(null, "\n”Os sonhos precisam de persistência e coragem para serem realizados.\n"
                        + "Nós os regamos com nossos erros, fragilidades e dificuldades.\n"
                        + "Quando lutamos por eles, nem sempre as pessoas que nos rodeiam nos apoiam e nos compreendem.\n"
                        + "Às vezes somos obrigados a tomar atitudes solitárias, tendo como companheiros apenas nossos próprios sonhos.”\n"
                        + "Augusto Cury");
            }
            if (tFormadePagamento.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Preencha a forma de pagamento.");
            } else {
                repositorioItemVenda = new RepositorioItemVenda();
                repositorioVenda = new RepositorioVenda();
                repositorioFormaPagamentoVenda = new RepositorioFormaPagamentoVenda();
                repositorioEstoque = new RepositorioEstoque();
                Vector<Estoque> estoque = new Vector<Estoque>();
                if (repositorioItemVenda.fechar(tCupomLabel.getText())) {
                    String valor = null;
                    try {
                        valor = Funcoes.formatoParaInserir(tValorTotal.getText());
                    } catch (ParseException ex) {
                        Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (repositorioVenda.alterarValorTotal(tCupomLabel.getText(), valor)) {
                        formaPagamentoVenda = new FormaPagamentoVenda();
                        String formas[] = tFormadePagamento.getSelectedItem().toString().split(Pattern.quote("-"));
                        formaPagamentoVenda.setIdFormapagamentoVenda(formas[0]);
                        formaPagamentoVenda.setIdVenda(tCupomLabel.getText());
                        if (repositorioFormaPagamentoVenda.adcionar(formaPagamentoVenda)) {
                            if (repositorioVenda.fechar(tCupomLabel.getText())) {
                                estoque = repositorioItemVenda.itensdeVenda(tCupomLabel.getText());
                                //System.out.println(estoque.size());
                                for (int i = 1; i <= estoque.size() - 1; i++) {
                                    repositorioEstoque.alterar(estoque.elementAt(i), false);
                                    //System.out.println(estoque.elementAt(i).getCodigoProduto());
                                    //System.out.println(estoque.elementAt(i).getQuantidade());
                                }
                                JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso");
                                HabilitarAbrirCupom(true);
                                HabilitarInserirItem(false);
                                HabilitarFinalizarCupom(false, null);
                                limparTudo();
                                try {
                                    remove(itens);
                                } catch (Exception et) {
                                }
                                repaint();
                                revalidate();
                            }
                        }
                    }
                }
            }
        };

        botaoCadastrarCliente.addActionListener(acaoCadastrarCliente);
        botaoCadastrarProduto.addActionListener(acaoCadastrarProduto);
        botaoAdicionarItem.addActionListener(acaoInserirItem);
        botaoAbrirCupom.addActionListener(acaoAbrirCupom);
        botaoFinalizarVenda.addActionListener(acaoFecharCupom);
        botaoAlterarItem.addActionListener(acaoAtualizarItens);
        botaoCancelar.addActionListener(acaoCancelarCupom);
        tProduto.addActionListener(acaoPerdefocoValorCusto);
        //tQuantidade.addFocusListener(acaoPerdeFocoQuantidadeEsqtoque);
        repositorioVenda = new RepositorioVenda();
        venda = repositorioVenda.verificarAberto("1");
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
            remove(itens);
        } catch (Exception e) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioVenda = new RepositorioVenda();
        itens = new JTable();
        itens = repositorioVenda.listarItensVenda(tCupomLabel.getText(), "4");
        if (itens.getValueAt(0, 0) == null) {
            botaoAlterarItem.setEnabled(false);
            HabilitarFinalizarCupom(false, null);
            // JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            botaoAlterarItem.setEnabled(true);
            scroller = new JScrollPane(itens, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            itens.getColumnModel().getColumn(0).setPreferredWidth(30);
            itens.getColumnModel().getColumn(1).setPreferredWidth(100);
            itens.getColumnModel().getColumn(2).setPreferredWidth(30);
            itens.getColumnModel().getColumn(3).setPreferredWidth(30);
            itens.getColumnModel().getColumn(4).setPreferredWidth(30);
            itens.setAutoCreateRowSorter(true);
        }
        try {
            add(scroller);
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
        tProduto.setEnabled(sinal);
        botaoCadastrarProduto.setEnabled(sinal);
        tQuantidade.setEditable(sinal);
        tValorVenda.setEditable(sinal);
        botaoAdicionarItem.setEnabled(sinal);
    }

    public void HabilitarFinalizarCupom(boolean sinal, String cod) {
        if (sinal) {
            repositorioVenda = new RepositorioVenda();
            tValorTotal.setText(repositorioVenda.valorTotalVenda(cod));
        }
        tFormadePagamento.setEnabled(sinal);
        // tValorTotal.setEditable(sinal);
        botaoFinalizarVenda.setEnabled(sinal);
        botaoCancelar.setVisible(sinal);
    }

    public void limparParaInserir() {
        tProduto.setSelectedIndex(0);
        tQuantidade.setText("");
        tValorCusto.setText("");
        tValorVenda.setText("");
        tamanhoLabel.setText("");
    }

    public void limparTudo() {
        tCliente.setSelectedIndex(0);
        tProduto.setSelectedIndex(0);
        tFormadePagamento.setSelectedIndex(0);
        //tData.setText("");
        datePicker.getJFormattedTextField().setText("");
        tQuantidade.setText("");
        tValorCusto.setText("");
        tValorVenda.setText("");
        tValorTotal.setText("");
        tCupomLabel.setText("");
        tamanhoLabel.setText("");
        remove(scroller);
        repaint();
        revalidate();
    }

    public double percentualTela(double valor, double posicao) {
        return (posicao * 100) / valor;
    }
}
