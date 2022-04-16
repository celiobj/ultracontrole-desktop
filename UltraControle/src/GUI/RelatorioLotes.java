/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Lote;
import DAO.AccessDatabase;
import Persistencia.RepositorioLote;
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
import javax.swing.JOptionPane;
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
public class RelatorioLotes extends JPanel {

    JLabel valorLabel, tValor, aLabel;
    JFormattedTextField tDataIni, tDataFim;
    MaskFormatter mascaraData;
    JComboBox tTipo;
    String tipos[] = {"Geral", "Por Data"};
    JTable lotes, loteDetalhado;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    Double soma = null;
    JButton botaoConsultar, botaoLimpar, detalharVenda;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;

    RepositorioLote repositorioLote;
    Lote lote;

    ActionListener acaoConsultar, acaoPerdefoco, acaoDetalharVenda;
    //FocusAdapter acaoPerdefoco;

    UtilDateModel modelInicio, modelFim;
    Properties p;
    JDatePanelImpl datePanelInicio, datePanelFim;
    JDatePickerImpl datePickerInicio, datePickerFim;

    public RelatorioLotes() {

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
        detalharVenda = new JButton("Detalhar");
        aLabel = new JLabel("a");
        tDataIni = new JFormattedTextField(mascaraData);
        tDataFim = new JFormattedTextField(mascaraData);
        valorLabel = new JLabel("Total: ");
        tValor = new JLabel();

        tTipo.setBounds(10, 10, 200, 20);
        botaoConsultar.setBounds(400, 10, 100, 20);
        botaoLimpar.setBounds(500, 10, 100, 20);
        detalharVenda.setBounds(500, 120, 100, 20);
        aLabel.setBounds(140, 40, 100, 20);
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
        add(detalharVenda);
        add(aLabel);
        add(datePickerInicio);
        add(datePickerFim);
        add(valorLabel);
        add(tValor);

        acaoConsultar = (ActionEvent e) -> {
            repositorioLote = new RepositorioLote();
            if (tTipo.getSelectedIndex() == 0) {
                tValor.setText(repositorioLote.valorTotaldeLotes(false, false, null, null));
                atualizarTabela(false, "", "");
                
            } else {
                if (tTipo.getSelectedIndex() == 1) {
                    tValor.setText(repositorioLote.valorTotaldeLotes(true, false, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText())));
                    atualizarTabela(true, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText()));
                }
            }
             if (lotes.getRowCount() == 1) {
                try {
                    remove(scroller);
                } catch (Exception ex) {

                }
                try {
                    remove(lotes);
                } catch (Exception ex) {

                }
                repaint();
                revalidate();
                tValor.setText("");
                JOptionPane.showMessageDialog(null, "Sem registros.");
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

        acaoDetalharVenda = (ActionEvent e) -> {
            try {
                int l = lotes.getSelectedRow();
                String codigoVenda = lotes.getValueAt(l, 0).toString();
                detalharLote(codigoVenda);
                tValor.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Selecione um registro!");
            }
        };

        botaoConsultar.addActionListener(acaoConsultar);
        detalharVenda.addActionListener(acaoDetalharVenda);
        tTipo.addActionListener(acaoPerdefoco);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY));
        setSize(600, 400);
        setVisible(true);
    }

    public void detalharLote(String codLote) {

        try {
            remove(lotes);
            repaint();
            revalidate();
        } catch (Exception et) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }

        repositorioLote = new RepositorioLote();
        loteDetalhado = new JTable();
        loteDetalhado = repositorioLote.detalharLote(codLote);
        if (loteDetalhado.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(loteDetalhado, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            loteDetalhado.getColumnModel().getColumn(0).setPreferredWidth(30);
            loteDetalhado.getColumnModel().getColumn(1).setPreferredWidth(30);
            loteDetalhado.getColumnModel().getColumn(2).setPreferredWidth(300);
            loteDetalhado.getColumnModel().getColumn(3).setPreferredWidth(30);
            loteDetalhado.getColumnModel().getColumn(4).setPreferredWidth(30);
            loteDetalhado.setAutoCreateRowSorter(true);
        }

        try {
            add(scroller);
        } catch (NullPointerException e) {

        }
        // add(lotes);
    }

    public void atualizarTabela(boolean data, String dataIni, String dataFim) {
        try {
            remove(loteDetalhado);
            repaint();
            revalidate();
        } catch (Exception et) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioLote = new RepositorioLote();
        lotes = new JTable();
        lotes = repositorioLote.listarTodos(data, dataIni, dataFim);
        if (lotes.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(lotes, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            lotes.getColumnModel().getColumn(0).setPreferredWidth(30);
            lotes.getColumnModel().getColumn(1).setPreferredWidth(100);
            lotes.getColumnModel().getColumn(2).setPreferredWidth(30);
            lotes.getColumnModel().getColumn(3).setPreferredWidth(30);
            lotes.setAutoCreateRowSorter(true);
        }
        try {
            add(scroller);
        } catch (NullPointerException e) {
        }
    }
}
