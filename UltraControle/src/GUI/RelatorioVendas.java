/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Venda;
import DAO.AccessDatabase;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RelatorioVendas extends JPanel {

    JLabel valorLabel, tValor, ticketMedioLabel, tTicketMedio, aLabel;
    JFormattedTextField tDataIni, tDataFim;
    MaskFormatter mascaraData;
    JComboBox tTipo;
    String tipos[] = {"Geral", "Por Data"};
    JTable vendas, vendaDetalhada;
    DefaultTableModel dtm;
    Vector linhas, dados, cabecalho;
    JScrollPane scroller;
    Double soma = null;
    JButton botaoConsultar, botaoLimpar, detalharVenda;
    Calendar data;
    int dia, dia_semana, mes, ano;
    public static String data_hoje;

    RepositorioVenda repositorioVenda;
    Venda venda;

    ActionListener acaoConsultar, acaoPerdefoco, acaoDetalharVenda;
    //FocusAdapter acaoPerdefoco;

    UtilDateModel modelInicio, modelFim;
    Properties p;
    JDatePanelImpl datePanelInicio, datePanelFim;
    JDatePickerImpl datePickerInicio, datePickerFim;

    public RelatorioVendas() {

        AccessDatabase a = new AccessDatabase();
        VerificarUsuario.con = a.conectar();

        try {
            mascaraData = new MaskFormatter("##/##/####");
        } catch (ParseException exp) {
        }

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
        ticketMedioLabel = new JLabel("Ticket médio: ");
        tTicketMedio = new JLabel();

        tTipo.setBounds(10, 10, 200, 20);
        botaoConsultar.setBounds(400, 10, 100, 20);
        botaoLimpar.setBounds(500, 10, 100, 20);
        detalharVenda.setBounds(500, 120, 100, 20);
        aLabel.setBounds(140, 40, 100, 20);
        datePickerInicio.setBounds(10, 40, 120, 27);
        datePickerFim.setBounds(160, 40, 120, 27);
        valorLabel.setBounds(400, 40, 100, 20);
        tValor.setBounds(500, 40, 100, 20);
        ticketMedioLabel.setBounds(400, 70, 100, 20);
        tTicketMedio.setBounds(500, 70, 100, 20);

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
        add(ticketMedioLabel);
        add(tTicketMedio);

        acaoConsultar = (ActionEvent e) -> {
            repositorioVenda = new RepositorioVenda();
            if (tTipo.getSelectedIndex() == 0) {
                tValor.setText(repositorioVenda.valorTotaldeVendas(false, false, null, null));
                atualizarTabela(false, "", "");
                double somaVendas = 0;
                for (int i = 0; i < vendas.getRowCount(); i++) {
                    try {
                        somaVendas += Double.parseDouble(Funcoes.formatoParaInserir(vendas.getValueAt(i, 3).toString()));
                    } catch (NumberFormatException | ParseException ex) {
                        Logger.getLogger(RelatorioVendas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                tTicketMedio.setText(Funcoes.paraFormatoDinheiro(somaVendas / vendas.getRowCount()));

            } else {
                if (tTipo.getSelectedIndex() == 1) {
                    String soma = repositorioVenda.valorTotaldeVendas(true, false, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText()));
                    tValor.setText(soma);
                    atualizarTabela(true, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText()));
                    double somaVendas = 0;
                    for (int i = 0; i < vendas.getRowCount(); i++) {
                        try {
                            somaVendas += Double.parseDouble(Funcoes.formatoParaInserir(vendas.getValueAt(i, 3).toString()));
                        } catch (NullPointerException | NumberFormatException | ParseException ex) {
                        }
                    }
                    tTicketMedio.setText(Funcoes.paraFormatoDinheiro(somaVendas / vendas.getRowCount()));
                    atualizarTabela(true, Funcoes.paraInserirData(datePickerInicio.getJFormattedTextField().getText()), Funcoes.paraInserirData(datePickerFim.getJFormattedTextField().getText()));
                    repaint();
                    revalidate();
                }
            }
            if (vendas.getRowCount() == 1) {
                try {
                    remove(scroller);
                } catch (Exception ex) {

                }
                try {
                    remove(vendas);
                } catch (Exception ex) {

                }
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
                int l = vendas.getSelectedRow();
                String codigoVenda = vendas.getValueAt(l, 0).toString();
                detalharVenda(codigoVenda);
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

    public void detalharVenda(String codVenda) {

        try {
            remove(vendas);
            repaint();
            revalidate();
        } catch (Exception et) {
        }
        try {
            remove(vendaDetalhada);
            repaint();
            revalidate();
        } catch (Exception et) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }

        repositorioVenda = new RepositorioVenda();
        vendaDetalhada = new JTable();
        vendaDetalhada = repositorioVenda.detalharVenda(codVenda);
        if (vendaDetalhada.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(vendaDetalhada, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            vendaDetalhada.getColumnModel().getColumn(0).setPreferredWidth(30);
            vendaDetalhada.getColumnModel().getColumn(1).setPreferredWidth(30);
            vendaDetalhada.getColumnModel().getColumn(2).setPreferredWidth(300);
            vendaDetalhada.getColumnModel().getColumn(3).setPreferredWidth(30);
            vendaDetalhada.getColumnModel().getColumn(4).setPreferredWidth(30);
            vendaDetalhada.setAutoCreateRowSorter(true);
        }

        try {
            add(scroller);
        } catch (NullPointerException e) {

        }
        // add(vendas);
    }

    public void atualizarTabela(boolean data, String dataIni, String dataFim) {
        try {
            remove(vendaDetalhada);
            repaint();
            revalidate();
        } catch (Exception et) {
        }
        try {
            remove(vendas);
            repaint();
            revalidate();
        } catch (Exception et) {
        }
        try {
            remove(scroller);
        } catch (Exception e) {
        }
        repositorioVenda = new RepositorioVenda();
        vendas = new JTable();
        vendas = repositorioVenda.listarTodos(data, dataIni, dataFim);
        if (vendas.getValueAt(0, 0) == null) {
            //JOptionPane.showMessageDialog(null, "Registros não Encontrados");
        } else {
            scroller = new JScrollPane(vendas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setBounds((int) Math.round(Principal.largura * 0.02), 200, (int) Math.round(Principal.largura / 1.3), (int) Math.round(Principal.altura * 0.35));
            vendas.getColumnModel().getColumn(0).setPreferredWidth(30);
            vendas.getColumnModel().getColumn(1).setPreferredWidth(100);
            vendas.getColumnModel().getColumn(2).setPreferredWidth(30);
            vendas.getColumnModel().getColumn(3).setPreferredWidth(30);
            vendas.setAutoCreateRowSorter(true);

        }
        try {
            add(scroller);
        } catch (NullPointerException e) {
        }
    }
}
