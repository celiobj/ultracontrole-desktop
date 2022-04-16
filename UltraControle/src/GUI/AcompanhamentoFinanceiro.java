/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Lote;
import Classes.Venda;
import DAO.AccessDatabase;
import Persistencia.RepositorioLote;
import Persistencia.RepositorioVenda;
import Util.DateLabelFormatter;
import Util.Funcoes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class AcompanhamentoFinanceiro extends JPanel {

    JLabel valorLabel, tValor, aLabel, entradaLabel, tEntradaLabel, saidaLabel, tSaidaLabel;
    JFormattedTextField tDataIni, tDataFim;
    MaskFormatter mascaraData;
    JComboBox tTipo;
    String tipos[] = {"Geral", "Por Data"};
    JTable entradas, saidas;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scrollerEntradas, scrollerSaidas;
    Double soma = null;
    JButton botaoConsultar, botaoLimpar;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;

    RepositorioVenda repositorioVenda;
    Venda venda;
    RepositorioLote repositorioLote;
    Lote lote;

    ActionListener acaoConsultar, acaoPerdefoco;
    //FocusAdapter acaoPerdefoco;

    UtilDateModel modelInicio, modelFim;
    Properties p;
    JDatePanelImpl datePanelInicio, datePanelFim;
    JDatePickerImpl datePickerInicio, datePickerFim;

    public AcompanhamentoFinanceiro() {

        try {
            mascaraData = new MaskFormatter("##/##/####");
        } catch (ParseException exp) {
        }

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        p = new Properties();
        p.put("text.today", "Hoje");
        p.put("text.month", "Mês");
        p.put("text.year", "Ano");
        modelInicio = new UtilDateModel();
        modelFim = new UtilDateModel();
        datePanelInicio = new JDatePanelImpl(modelInicio, p);
        datePanelFim = new JDatePanelImpl(modelFim, p);
        datePickerInicio = new JDatePickerImpl(datePanelInicio, new DateLabelFormatter());
        datePickerFim = new JDatePickerImpl(datePanelFim, new DateLabelFormatter());

        tTipo = new JComboBox(tipos);
        botaoConsultar = new JButton("Consultar");
        botaoLimpar = new JButton("Limpar");
        aLabel = new JLabel("a");
        entradaLabel = new JLabel("Entrada");
        saidaLabel = new JLabel("Saída");
        tEntradaLabel = new JLabel();
        tSaidaLabel = new JLabel();
        tDataIni = new JFormattedTextField(mascaraData);
        tDataFim = new JFormattedTextField(mascaraData);
        valorLabel = new JLabel("Total: ");
        tValor = new JLabel();

        tTipo.setBounds(10, 10, 200, 20);
        botaoConsultar.setBounds(400, 10, 100, 20);
        botaoLimpar.setBounds(500, 10, 100, 20);
        aLabel.setBounds(140, 40, 100, 20);
        entradaLabel.setBounds(10, 70, 100, 20);
        saidaLabel.setBounds(10, 110, 100, 20);
        tEntradaLabel.setBounds(80, 70, 100, 20);
        tSaidaLabel.setBounds(80, 110, 100, 20);
        datePickerInicio.setBounds(10, 40, 120, 27);
        datePickerFim.setBounds(160, 40, 120, 27);
        valorLabel.setBounds(400, 40, 100, 20);
        tValor.setBounds(440, 40, 100, 20);

        datePickerInicio.setVisible(false);
        datePickerFim.setVisible(false);
        aLabel.setVisible(false);

        add(tTipo);
        add(botaoConsultar);
        add(botaoLimpar);
        add(aLabel);
        add(datePickerInicio);
        add(datePickerFim);
        add(valorLabel);
        add(tValor);
        add(entradaLabel);
        add(saidaLabel);
        add(tEntradaLabel);
        add(tSaidaLabel);

        acaoConsultar = (ActionEvent e) -> {
            try {
                remove(scrollerEntradas);
                remove(scrollerSaidas);
                repaint();
                revalidate();
            } catch (NullPointerException exx) {
            }
            repositorioVenda = new RepositorioVenda();
            repositorioLote = new RepositorioLote();
            double in = 0;
            double out = 0;
            if (tTipo.getSelectedIndex() == 0) {
                repositorioVenda.listarTodos(false, data_hoje, data_hoje);
                tEntradaLabel.setText(repositorioVenda.valorTotaldeVendas(false, false, null, null));
                tSaidaLabel.setText(repositorioLote.valorTotaldeLotes(false, false, null, null));
                in = Double.parseDouble(repositorioVenda.valorTotaldeVendas(false, true, null, null));
                out = Double.parseDouble(repositorioLote.valorTotaldeLotes(false, true, null, null));
                tValor.setText(Funcoes.paraFormatoDinheiro(in - out));
                atualizarTabela(false, "", "");
                repaint();
                revalidate();
                
            } else {
                if (tTipo.getSelectedIndex() == 1) {
                    tEntradaLabel.setText(repositorioVenda.valorTotaldeVendas(true, false, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText())));
                    tSaidaLabel.setText(repositorioLote.valorTotaldeLotes(true, false, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText())));
                    in = Double.parseDouble(repositorioVenda.valorTotaldeVendas(true, true, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText())));
                    out = Double.parseDouble(repositorioLote.valorTotaldeLotes(true, true, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText())));
                    tValor.setText(String.valueOf(in - out));
                    atualizarTabela(true, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText()));
                    repaint();
                    revalidate();
                }
            }
        };
        acaoPerdefoco = (ActionEvent e) -> {
            if (tTipo.getSelectedIndex() == 0) {
                datePickerInicio.setVisible(false);
                datePickerFim.setVisible(false);
                aLabel.setVisible(false);
            } else {
                if (tTipo.getSelectedIndex() == 1) {
                    datePickerInicio.setVisible(true);
                    datePickerFim.setVisible(true);
                    aLabel.setVisible(true);
                }
            }
        };

        botaoConsultar.addActionListener(acaoConsultar);

        tTipo.addActionListener(acaoPerdefoco);

        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(600, 400);
        setVisible(true);
    }

    public void atualizarTabela(boolean data, String dataIni, String dataFim) {
        try {
            remove(scrollerSaidas);
        } catch (Exception e) {
        }
        try {
            remove(scrollerEntradas);
        } catch (Exception e) {
        }
        repositorioVenda = new RepositorioVenda();
        entradas = new JTable();
        entradas = repositorioVenda.listarTodos(data, dataIni, dataFim);
        repositorioLote = new RepositorioLote();
        saidas = new JTable();
        saidas = repositorioLote.listarTodos(data, dataIni, dataFim);
        if (entradas.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scrollerEntradas = new JScrollPane(entradas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollerEntradas.setBounds((int) Math.round(Principal.largura*0.02), 200,(int) Math.round(Principal.largura/1.3), (int) Math.round(Principal.altura*0.35));
            entradas.getColumnModel().getColumn(0).setPreferredWidth(30);
            entradas.getColumnModel().getColumn(1).setPreferredWidth(100);
            entradas.getColumnModel().getColumn(2).setPreferredWidth(30);
            entradas.setAutoCreateRowSorter(true);

        }

        if (saidas.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scrollerSaidas = new JScrollPane(saidas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollerSaidas.setBounds((int) Math.round(Principal.largura*0.02), 200,(int) Math.round(Principal.largura/1.3), (int) Math.round(Principal.altura*0.35));
            saidas.getColumnModel().getColumn(0).setPreferredWidth(30);
            saidas.getColumnModel().getColumn(1).setPreferredWidth(100);
            saidas.getColumnModel().getColumn(2).setPreferredWidth(30);
            saidas.setAutoCreateRowSorter(true);

        }

        try {
            remove(scrollerEntradas);
            remove(scrollerSaidas);
        } catch (Exception e) {

        }
        try {
            add(scrollerEntradas);
            add(scrollerSaidas);
        } catch (NullPointerException e) {

        }
    }
}
