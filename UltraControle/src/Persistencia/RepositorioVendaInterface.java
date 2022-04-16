/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Venda;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioVendaInterface {

    public void adcionar(Venda item);

    public String pegarUltimo();

    public String valorTotalVenda(String cod);
    
    public String valorTotalTroca(String cod);
    
    public String ticketMedio(boolean data, String dataIni, String dataFim);

    public boolean cancelar(String cod);

    public Venda verificarAberto(String tipo);

    public boolean primeiraVenda();
    
    public JTable detalharVenda(String codigoVenda);

    public Venda verificarExistente(String nome);

    public boolean alterarValorTotal(String cod, String valor);

    public boolean fechar(String cod);

    public Venda procurar(String cod);
    
    public JTable custoVendas(boolean data, String dataIni, String dataFim);

    public JTable listarItensVenda(String lote, String tipo);

    public String valorTotaldeVendas(boolean data, boolean calculo, String dataIni, String dataFim);
    
    public String valorTotaldeCustos(boolean data, boolean calculo, String dataIni, String dataFim);

    public JTable listarTodos(boolean data, String dataIni, String dataFim);

}
