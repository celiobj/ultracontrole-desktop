/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Movimentacao;
import DAO.AccessDatabase;
import Persistencia.RepositorioMovimentacao;
import Util.Funcoes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author celio
 */
public class RegistrarMovimentacao extends JPanel {

    JLabel descricao, valor, tipo, saldo, saldoOff, labelData;
    JTextField tDescricao, tValor;
    JComboBox tTipo;
    String tipos[] = {"", "Débito", "Crédito"};
    JTable entradas;
    Vector linhas, dados, cabecalho, movimentacaoVetor;
    JScrollPane scroller;
    Double soma = null;
    JButton botaoInserir, botaoExcluir, botaoGravar, botaoFechar, imprimir;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;
    int data_amanha;
    ImageIcon imageIncluir = new ImageIcon("Icones\\incluir.png");
    ImageIcon imageExcluir = new ImageIcon("Icones\\excluir.png");
    ImageIcon imageSalvar = new ImageIcon("Icones\\salvar.png");
    ImageIcon imageFechar = new ImageIcon("Icones\\fechar.png");
    ImageIcon imageImprimir = new ImageIcon("Icones\\imprimir.png");

    RepositorioMovimentacao repositorioMovimentacao;
    Movimentacao movimentacao;

    public RegistrarMovimentacao() {

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        data = Calendar.getInstance();
        dia = data.get(Calendar.DAY_OF_MONTH);
        dia_semana = data.get(Calendar.DAY_OF_WEEK);
        mes = data.get(Calendar.MONTH);
        ano = data.get(Calendar.YEAR);
        data_hoje = +dia + "/" + (mes + 1) + "/" + ano;
        if (dia < 10 && mes < 10) {
            data_hoje = "0" + dia + "/0" + (mes + 1) + "/" + ano;
        } else if (dia < 10 && mes >= 10) {
            data_hoje = "0" + dia + "/" + (mes + 1) + "/" + ano;
        } else if (dia >= 10 && mes < 10) {
            data_hoje = dia + "/0" + (mes + 1) + "/" + ano;
        } else {
            data_hoje = dia + "/" + (mes + 1) + "/" + ano;
        }

        labelData = new JLabel(data_hoje);
        saldo = new JLabel("0");
        saldoOff = new JLabel("0");
        botaoInserir = new JButton();
        botaoInserir.setIcon(imageIncluir);
        botaoInserir.setToolTipText("Incluir");
        botaoExcluir = new JButton();
        botaoExcluir.setIcon(imageExcluir);
        botaoExcluir.setToolTipText("Excluir");
        botaoGravar = new JButton();
        botaoGravar.setIcon(imageSalvar);
        botaoGravar.setToolTipText("Salvar");
        botaoFechar = new JButton();
        botaoFechar.setIcon(imageFechar);
        botaoFechar.setToolTipText("Fechar Caixa");
        imprimir = new JButton();
        imprimir.setIcon(imageImprimir);
        imprimir.setToolTipText("Imprimir");

        descricao = new JLabel("Descrição: ");
        valor = new JLabel("Valor: ");
        tipo = new JLabel("Tipo: ");
        tDescricao = new JTextField();
        tValor = new JTextField();
        tTipo = new JComboBox(tipos);
        cabecalho = new Vector();
        linhas = new Vector();
        dados = new Vector();

        cabecalho.addElement("Cód");
        cabecalho.addElement("Tipo");
        cabecalho.addElement("Decrição");
        cabecalho.addElement("Valor");

        labelData.setBounds((int) Math.round(Principal.largura / 1.2), 45, 100, 50);
        descricao.setBounds(10, 10, 80, 20);
        valor.setBounds(10, 40, 80, 20);
        tipo.setBounds(10, 70, 80, 20);
        tDescricao.setBounds(80, 10, 300, 20);
        tValor.setBounds(80, 40, 80, 20);
        tTipo.setBounds(80, 70, 150, 20);
        saldo.setBounds((int) Math.round(Principal.largura / 1.2), 100, 200, 20);
        botaoInserir.setBounds((int) Math.round(Principal.largura / 1.2), 200, 50, 50);
        botaoExcluir.setBounds((int) Math.round(Principal.largura / 1.2), 250, 50, 50);
        botaoFechar.setBounds(550, 10, 50, 50);
        imprimir.setBounds(630, 10, 50, 50);

        add(labelData);
        add(descricao);
        add(valor);
        add(tipo);
        add(tDescricao);
        add(tValor);
        add(tTipo);
        add(saldo);
        add(botaoInserir);
        add(botaoExcluir);
        //add(botaoFechar);
        //add(imprimir);

        botaoInserir.addActionListener((ActionEvent e) -> {
            repositorioMovimentacao = new RepositorioMovimentacao();
            movimentacao = new Movimentacao();

            switch (tTipo.getSelectedIndex()) {
                case 1:
                    movimentacao.setTipo("5");
                    break;
                case 2:
                    movimentacao.setTipo("6");
                    break;
            }
            movimentacao.setDescricao(tDescricao.getText());
            movimentacao.setData(Funcoes.paraInserirData(data_hoje));
            try {
                movimentacao.setValor(Funcoes.formatoParaInserir(tValor.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(RegistrarMovimentacao.class.getName()).log(Level.SEVERE, null, ex);
            }
            movimentacao.setSituacao("A");
            if (repositorioMovimentacao.adcionar(movimentacao)) {
                limparParaInserir();
                try {
                    remove(entradas);
                } catch (Exception et) {

                }
                atualizarTabela();
                repaint();
                revalidate();
            }
        });

        botaoExcluir.addActionListener((ActionEvent e) -> {
        });

        botaoFechar.addActionListener((ActionEvent e) -> {
        });

        imprimir.addActionListener((ActionEvent e) -> {
            /* JasperPrint rel = null;
            try {
            AccessDatabase a = new AccessDatabase();
            Connection con = a.conectar();
            HashMap map = new HashMap();
            Statement st = con.createStatement();
            String query = "Select * from `bd_ultracaixa`.`movimentacao` where data='" + Principal.paraInserirData(data_hoje) + "'";
            ResultSet rs = st.executeQuery(query);
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            String arquivoJasper = "Relatorios//diario.jasper";
            rel = JasperFillManager.fillReport(arquivoJasper, map, jrRS);
            JasperViewer.viewReport(rel, false);
            con.close();
            } catch (Exception t) {
            System.out.println(t.getMessage());
            }
             */
        });
        atualizarTabela();
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(600, 400);
        setVisible(true);
    }

    public void limparParaInserir() {
        tTipo.setSelectedIndex(0);
        tDescricao.setText("");
        tValor.setText("");
    }

    public void atualizarTabela() {
        try {
            remove(entradas);
        } catch (Exception e) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioMovimentacao = new RepositorioMovimentacao();
        entradas = new JTable();
        entradas = repositorioMovimentacao.listarTodos(true, Funcoes.paraInserirData(data_hoje),Funcoes.paraInserirData(data_hoje));
        if (entradas.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(null, "Sem movimentaoes de hoje");
        } else {
            scroller = new JScrollPane(entradas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            entradas.getColumnModel().getColumn(0).setPreferredWidth(100);
            entradas.getColumnModel().getColumn(1).setPreferredWidth(100);
            entradas.getColumnModel().getColumn(2).setPreferredWidth(300);
            entradas.getColumnModel().getColumn(3).setPreferredWidth(100);
            entradas.getColumnModel().getColumn(4).setPreferredWidth(100);
            entradas.setAutoCreateRowSorter(true);

            double somaDebitos = 0;
            double somaCreditos = 0;
            for (int i = 0; i < entradas.getRowCount(); i++) {
                try {
                    if (entradas.getValueAt(i, 1).toString().equalsIgnoreCase("Débito")) {
                        somaDebitos += Double.parseDouble(Funcoes.formatoParaInserir(entradas.getValueAt(i, 3).toString()));
                    }
                    if (entradas.getValueAt(i, 1).toString().equalsIgnoreCase("Crédito")) {
                        somaCreditos += Double.parseDouble(Funcoes.formatoParaInserir(entradas.getValueAt(i, 3).toString()));
                    }
                    saldo.setText(Funcoes.paraFormatoDinheiro(somaCreditos - somaDebitos));
                } catch (NumberFormatException | ParseException ex) {
                    Logger.getLogger(RelatorioVendas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            add(scroller);
        } catch (NullPointerException e) {

        }
    }

}
