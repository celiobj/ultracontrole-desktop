/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author celio
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author celio
 */
public class Principal extends JFrame {


    JToolBar menu;
    JMenuBar menuSuperior;
    JLabel tituloTela;
    JScrollPane scroller;
    JMenu acoesMenu, cadastroMenu, estoqueMenu, financeiroMenu, relatoriosMenu, configuracoesMenu, sairMenu;
    JMenuItem vendaJMenu, trocaJMenu, cadastroClienteJMenu, cadastroProdutoJMenu, precificacaoJMenu, cadastroFornecedorJMenu, cadastroTamanhoJMenu, cadastroTipoJMenu, cadastroFormaPagamentoJMenu, entradaItemEstoqueJMenu, controleEstoqueJMenu, acompanhamentoFinanceiroJMenu, custoVendas, relatoriosVendasJMenu, relatoriosLotesJMenu, relatoriosMovimentacaoJMenu,movimentacaoJMenu, paineldeControleJMenu, sairJMenu;
    JTable tabela;
    Vector<Vector> preencher;
    MaskFormatter mascaraHora;
    MaskFormatter mascaraDia;
    MaskFormatter mascaraCpf;
    RegistrarMovimentacao movimentacao;
    Relatorio relatorio;
    RealizarVenda realizarVenda;
    RealizarTroca realizarTroca;
    AcompanhamentoFinanceiro acompanhamentoFinanceiro;
    CustoXVenda custoVenda;
    CadastroCliente cadastroCliente;
    CadastroProduto cadastroProduto;
    Precificacao precificacao;
    CadastroTamanhoProduto cadastroTamanhoProduto;
    CadastroTipoProduto cadastroTipoProduto;
    CadastroFornecedorProduto cadastroFornecedorProduto;
    CadastroFormadePagamento cadastroFormadePagamento;
    EntradaItemEstoque entradaItemEstoque;
    ControleEstoque controleEstoque;
    PaineldeControle paineldeControle;
    RelatorioVendas relatorioVendas;
    RelatorioLotes relatorioLotes;
    RelatorioMovimentacao relatorioMovimentacao;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
    static int largura;
    static int altura;

    ActionListener acaoHome, acaoRealizarVenda, acaoRealizarTroca, acaoCadastrarCliente, acaoCadastrarProduto, acaoPrecificacao, acaoCadastrarTamanhoProduto,
            acaoCadastrarTipoProduto, acaoCadastrarFornecedorProduto, acaoCadastrarFormapagamento, acaoEntradaProduto,
            acaoControleEstoque, acaoAcompanhamento, acaoCustoVenda, acaoRelatorioVendas, acaoRelatorioLotes, acaoRelatorioMovimentacao, acaoPainelControle, acaoMovimentacao, acaoCadastroFormaPagamento,
            acaoRelatorio, acaoSair;

    int qtdDia;
    Calendar data;

    //Botão de Venda
    JButton botaoHome;
    ImageIcon imageHome = new ImageIcon("Icones\\home.png");

    //Botão de Venda
    JButton botaoVenda;
    ImageIcon imageVenda = new ImageIcon("Icones\\venda.png");
    
    //Botão Estoque
    JButton botaoEstoque;
    ImageIcon imageEstoque = new ImageIcon("Icones\\estoque.png");

    //Botão Movimentação diária
    JButton botaoMovimentacaoDiaria;
    ImageIcon imageMovimentacao = new ImageIcon("Icones\\movimentacao.png");

    //Botão Relatório
    JButton botaoRelatorio;
    ImageIcon imageRelatorio = new ImageIcon("Icones\\relatorio.png");

    //Botão Sair  
    JButton sair;
    ImageIcon imageSair = new ImageIcon("Icones\\botao_sair.png");

    public Principal() {

        super("..:: UltraControle - V.1.0 ::.. - " + VerificarUsuario.bancoTela);
        final Container tela = getContentPane();
        largura = d.width;
        altura = d.height;
        tela.setLayout(null);
        setResizable(false);
        setSize(largura, (int) Math.round(altura * 0.9));
        setLocationRelativeTo(null);
        setVisible(true);
        criaMenu();

        try {
            mascaraHora = new MaskFormatter("##:##:##");
        } catch (ParseException exp) {
        }

        try {
            mascaraCpf = new MaskFormatter("###.###.###-##");
        } catch (ParseException exp) {
        }

        try {
            mascaraDia = new MaskFormatter("##/##/####");
        } catch (ParseException exp) {
        }

        try {
            VerificarUsuario.con.close();
        } catch (NullPointerException|SQLException ex) {
            //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        tituloTela = new JLabel(". . : : Principal : : . .");
        menu.setBounds(1, 1, tela.getWidth(), 80);
        tituloTela.setBounds((largura * 5) / 12, 75, 300, 30);
        tela.add(menu);
        tela.add(tituloTela);

        acaoHome = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(false);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Início : : . .");
            tela.revalidate();
            tela.repaint();
        };

        acaoRealizarVenda = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            realizarVenda = new RealizarVenda();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            //realizarVenda.setBounds(50, 100, dimension.width - 300, dimension.height - 300);
            realizarVenda.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //realizarVenda.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(false);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Realizar Venda : : . .");
            
            tela.add(realizarVenda);
            tela.revalidate();
            tela.repaint();
        };

        acaoRealizarTroca = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            realizarTroca = new RealizarTroca();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            //realizarVenda.setBounds(50, 100, dimension.width - 300, dimension.height - 300);
            realizarTroca.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //realizarTroca.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Realizar Troca : : . .");
            
            tela.add(realizarTroca);
            tela.revalidate();
            tela.repaint();
        };
        acaoCadastroFormaPagamento = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroFormadePagamento = new CadastroFormadePagamento();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroFormadePagamento.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroFormadePagamento.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Forma de Pagamento : : . .");
            
            tela.add(cadastroFormadePagamento);
            tela.revalidate();
            tela.repaint();
        };

        acaoCadastrarCliente = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroCliente = new CadastroCliente();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroCliente.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroCliente.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Cliente : : . .");
            
            tela.add(cadastroCliente);
            tela.revalidate();
            tela.repaint();
        };

        acaoCadastrarProduto = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroProduto = new CadastroProduto();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroProduto.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroProduto.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Produto : : . .");
            
            tela.add(cadastroProduto);
            tela.revalidate();
            tela.repaint();
        };
        acaoPrecificacao = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            precificacao = new Precificacao();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            precificacao.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //precificacao.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Precificação : : . .");
            
            tela.add(precificacao);
            tela.revalidate();
            tela.repaint();
        };
        acaoCadastrarTamanhoProduto = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroTamanhoProduto = new CadastroTamanhoProduto();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroTamanhoProduto.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroTamanhoProduto.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Tamanho de Produto : : . .");
            
            tela.add(cadastroTamanhoProduto);
            tela.revalidate();
            tela.repaint();
        };

        acaoCadastrarTipoProduto = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroTipoProduto = new CadastroTipoProduto();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroTipoProduto.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroTipoProduto.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Tipo de Produto : : . .");
            
            tela.add(cadastroTipoProduto);
            tela.revalidate();
            tela.repaint();
        };
        acaoCadastrarFormapagamento = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroFormadePagamento = new CadastroFormadePagamento();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroFormadePagamento.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroFormadePagamento.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Forma de Pagamento : : . .");
            
            tela.add(cadastroFormadePagamento);
            tela.revalidate();
            tela.repaint();
        };

        acaoCadastrarFornecedorProduto = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            cadastroFornecedorProduto = new CadastroFornecedorProduto();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            cadastroFornecedorProduto.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //cadastroFornecedorProduto.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Cadastro de Fornecedor de Produto : : . .");
            
            tela.add(cadastroFornecedorProduto);
            tela.revalidate();
            tela.repaint();
        };

        acaoEntradaProduto = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            entradaItemEstoque = new EntradaItemEstoque();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            entradaItemEstoque.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //entradaItemEstoque.add(ferramentas);
            
            botaoEstoque.setEnabled(false);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Entrada de Produto : : . .");
            
            tela.add(entradaItemEstoque);
            tela.revalidate();
            tela.repaint();
        };

        acaoControleEstoque = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            controleEstoque = new ControleEstoque();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            controleEstoque.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //controleEstoque.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Controle de Estoque : : . .");
            
            tela.add(controleEstoque);
            tela.revalidate();
            tela.repaint();
        };

        acaoAcompanhamento = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            acompanhamentoFinanceiro = new AcompanhamentoFinanceiro();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            acompanhamentoFinanceiro.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //acompanhamentoFinanceiro.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Acompanhamento Financeiro: : . .");
            
            tela.add(acompanhamentoFinanceiro);
            tela.revalidate();
            tela.repaint();
        };

        acaoCustoVenda = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            custoVenda = new CustoXVenda();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            custoVenda.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //custoVenda.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Custo x Venda : : . .");
            
            tela.add(custoVenda);
            tela.revalidate();
            tela.repaint();
        };

        acaoPainelControle = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            paineldeControle = new PaineldeControle();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            paineldeControle.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //paineldeControle.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Painel de Controle : : . .");
            
            tela.add(paineldeControle);
            tela.revalidate();
            tela.repaint();
        };

        acaoRelatorioVendas = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            relatorioVendas = new RelatorioVendas();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            relatorioVendas.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //relatorioVendas.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Relatório de Vendas : : . .");
            
            tela.add(relatorioVendas);
            tela.revalidate();
            tela.repaint();
        };

        acaoRelatorioLotes = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            relatorioLotes = new RelatorioLotes();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            relatorioLotes.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //relatorioLotes.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Relatório de Lotes : : . .");
            
            tela.add(relatorioLotes);
            tela.revalidate();
            tela.repaint();
        };
        
        acaoRelatorioMovimentacao = (ActionEvent e) -> {
            RemoverTelas(tela);
            
            relatorioMovimentacao = new RelatorioMovimentacao();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            relatorioMovimentacao.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //relatorioLotes.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Relatório de Movimentaçoes : : . .");
            
            tela.add(relatorioMovimentacao);
            tela.revalidate();
            tela.repaint();
        };

        acaoMovimentacao = (ActionEvent e) -> {
            RemoverTelas(tela);
            movimentacao = new RegistrarMovimentacao();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            movimentacao.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //movimentacao.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(true);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(false);
            tituloTela.setText(". . : : Movimentação : : . .");
            
            tela.add(movimentacao);
            tela.revalidate();
            tela.repaint();
        };
        acaoRelatorio = (ActionEvent e) -> {
            RemoverTelas(tela);
            relatorio = new Relatorio();
            JToolBar ferramentas = new JToolBar("Ferramentas");
            ferramentas.setBounds(2, 584, 1095, 64);
            relatorio.setBounds(50, 100, (largura / 12) * 11, (int) Math.round(altura * 0.7));
            //relatorio.add(ferramentas);
            
            botaoEstoque.setEnabled(true);
            botaoRelatorio.setEnabled(false);
            botaoHome.setEnabled(true);
            botaoVenda.setEnabled(true);
            botaoMovimentacaoDiaria.setEnabled(true);
            tituloTela.setText(". . : : Relatórios : : . .");
            
            tela.add(relatorio);
            tela.revalidate();
            tela.repaint();
        };
        acaoSair = (ActionEvent e) -> {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
            if (resposta == 0) {
                try {
                    VerificarUsuario.con.close();
                } catch (SQLException ex) {
                    //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        };

        botaoHome.addActionListener(acaoHome);
        botaoVenda.addActionListener(acaoRealizarVenda);
        vendaJMenu.addActionListener(acaoRealizarVenda);
        trocaJMenu.addActionListener(acaoRealizarTroca);
        cadastroClienteJMenu.addActionListener(acaoCadastrarCliente);
        cadastroProdutoJMenu.addActionListener(acaoCadastrarProduto);
        precificacaoJMenu.addActionListener(acaoPrecificacao);
        cadastroTipoJMenu.addActionListener(acaoCadastrarTipoProduto);
        cadastroTamanhoJMenu.addActionListener(acaoCadastrarTamanhoProduto);
        cadastroFornecedorJMenu.addActionListener(acaoCadastrarFornecedorProduto);
        cadastroFormaPagamentoJMenu.addActionListener(acaoCadastrarFormapagamento);
        entradaItemEstoqueJMenu.addActionListener(acaoEntradaProduto);
        controleEstoqueJMenu.addActionListener(acaoControleEstoque);
        acompanhamentoFinanceiroJMenu.addActionListener(acaoAcompanhamento);
        movimentacaoJMenu.addActionListener(acaoMovimentacao);
        custoVendas.addActionListener(acaoCustoVenda);
        relatoriosVendasJMenu.addActionListener(acaoRelatorioVendas);
        relatoriosLotesJMenu.addActionListener(acaoRelatorioLotes);
        relatoriosMovimentacaoJMenu.addActionListener(acaoRelatorioMovimentacao);
        paineldeControleJMenu.addActionListener(acaoPainelControle);
        botaoMovimentacaoDiaria.addActionListener(acaoMovimentacao);
        botaoEstoque.addActionListener(acaoEntradaProduto);
        botaoRelatorio.addActionListener(acaoRelatorio);
        sair.addActionListener(acaoSair);
        sairJMenu.addActionListener(acaoSair);

    }

    private void criaMenu() {

        menuSuperior = new JMenuBar();
        setJMenuBar(menuSuperior);
        acoesMenu = new JMenu("Ações");
        cadastroMenu = new JMenu("Cadastro");
        estoqueMenu = new JMenu("Estoque");
        financeiroMenu = new JMenu("Financeiro");
        relatoriosMenu = new JMenu("Relatorio");
        configuracoesMenu = new JMenu("Configurações");
        sairMenu = new JMenu("Sair");

        vendaJMenu = new JMenuItem("|Realizar Venda|");
        trocaJMenu = new JMenuItem("|Realizar Troca|");
        cadastroClienteJMenu = new JMenuItem("|Cadastro de Cliente|");
        cadastroProdutoJMenu = new JMenuItem("|Cadastro de Produto|");
        cadastroFornecedorJMenu = new JMenuItem("|Cadastro de Fornecedor|");
        cadastroTipoJMenu = new JMenuItem("|Cadastro de Tipo de produto|");
        cadastroTamanhoJMenu = new JMenuItem("|Cadastro de Tamanho de Produto|");
        cadastroFormaPagamentoJMenu = new JMenuItem("|Cadastro de Forma de Pagamento|");
        entradaItemEstoqueJMenu = new JMenuItem("|Entrada de Produto|");
        controleEstoqueJMenu = new JMenuItem("|Controle de estoque|");
        precificacaoJMenu = new JMenuItem("|Precificação|");
        acompanhamentoFinanceiroJMenu = new JMenuItem("|Acompanhamento|");
        movimentacaoJMenu = new JMenuItem("|Movimentaçao|");
        custoVendas = new JMenuItem("|Custo x Venda|");
        relatoriosVendasJMenu = new JMenuItem("|Vendas|");
        relatoriosLotesJMenu = new JMenuItem("|Lotes|");
        relatoriosMovimentacaoJMenu = new JMenuItem("|Movimentações|");
        paineldeControleJMenu = new JMenuItem("|Painel de Controle|");
        sairJMenu = new JMenuItem("|Sair|");

        acoesMenu.add(vendaJMenu);
        acoesMenu.add(trocaJMenu);
        cadastroMenu.add(cadastroClienteJMenu);
        cadastroMenu.add(cadastroProdutoJMenu);
        cadastroMenu.add(cadastroFornecedorJMenu);
        cadastroMenu.add(cadastroTipoJMenu);
        cadastroMenu.add(cadastroTamanhoJMenu);
        cadastroMenu.add(cadastroFormaPagamentoJMenu);
        financeiroMenu.add(acompanhamentoFinanceiroJMenu);
        financeiroMenu.add(movimentacaoJMenu);
        financeiroMenu.add(custoVendas);
        estoqueMenu.add(entradaItemEstoqueJMenu);
        estoqueMenu.add(controleEstoqueJMenu);
        estoqueMenu.add(precificacaoJMenu);
        relatoriosMenu.add(relatoriosVendasJMenu);
        relatoriosMenu.add(relatoriosLotesJMenu);
        relatoriosMenu.add(relatoriosMovimentacaoJMenu);
        configuracoesMenu.add(paineldeControleJMenu);
        sairMenu.add(sairJMenu);

        menuSuperior.add(acoesMenu);
        menuSuperior.add(cadastroMenu);
        menuSuperior.add(estoqueMenu);
        menuSuperior.add(financeiroMenu);
        menuSuperior.add(relatoriosMenu);
        menuSuperior.add(configuracoesMenu);
        menuSuperior.add(sairMenu);

        menu = new JToolBar("Barra de Ferramentas");
        menu.setBackground(Color.white);

        botaoHome = new JButton();
        botaoHome.setIcon(imageHome);
        botaoHome.setToolTipText("Início");
        botaoHome.setBounds(10, 10, 100, 100);
        menu.add(botaoHome);

        botaoVenda = new JButton();
        botaoVenda.setIcon(imageVenda);
        botaoVenda.setToolTipText("Venda");
        botaoVenda.setBounds(10, 10, 100, 100);
        menu.add(botaoVenda);

        botaoEstoque = new JButton();
        botaoEstoque.setIcon(imageEstoque);
        botaoEstoque.setToolTipText("Estoque");
        botaoEstoque.setBounds(10, 10, 100, 100);
        menu.add(botaoEstoque);
        
        botaoMovimentacaoDiaria = new JButton();
        botaoMovimentacaoDiaria.setIcon(imageMovimentacao);
        botaoMovimentacaoDiaria.setToolTipText("Movimentação");
        botaoMovimentacaoDiaria.setBounds(10, 10, 100, 100);
        menu.add(botaoMovimentacaoDiaria);

        botaoRelatorio = new JButton();
        botaoRelatorio.setIcon(imageRelatorio);
        botaoRelatorio.setToolTipText("Relatórios");
        botaoRelatorio.setBounds(10, 10, 100, 100);
        //menu.add(botaoRelatorio);

        sair = new JButton();
        sair.setIcon(imageSair);
        sair.setToolTipText("Sair");
        sair.setBounds(10, 10, 100, 100);
        menu.add(sair);
    }


    public void RemoverTelas(Container tela) {
        try {

            tela.remove(relatorioMovimentacao);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(movimentacao);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(relatorio);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(realizarVenda);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(realizarTroca);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(acompanhamentoFinanceiro);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(custoVenda);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(cadastroFormadePagamento);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(cadastroCliente);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(cadastroProduto);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(precificacao);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(cadastroTamanhoProduto);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(cadastroTipoProduto);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(cadastroFornecedorProduto);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(controleEstoque);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(paineldeControle);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(relatorioVendas);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(relatorioLotes);
        } catch (NullPointerException npe) {
        }
        try {

            tela.remove(entradaItemEstoque);
        } catch (NullPointerException npe) {
        }
    }

}
