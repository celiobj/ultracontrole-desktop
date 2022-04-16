/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Classes.Movimentacao;
import javax.swing.JTable;

/**
 *
 * @author celio
 */
public interface RepositorioMovimentacaoInterface {

    public boolean adcionar(Movimentacao movimentacao);
    
    public boolean alterar(Movimentacao movimentacao);
    
    public boolean remover(Movimentacao movimentacao);
    
    public String valorTotaldeMovimentacao(boolean data, boolean calculo, String dataIni, String dataFim);
    
    public JTable listarTodos(boolean data, String dataIni, String dataFim);

}
