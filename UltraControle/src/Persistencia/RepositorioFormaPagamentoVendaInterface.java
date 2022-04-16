/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.FormaPagamentoVenda;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioFormaPagamentoVendaInterface {
    
     public boolean adcionar(FormaPagamentoVenda forma);

    public FormaPagamentoVenda verificarExistente(String nome);

    public void alterar(String cod, FormaPagamentoVenda cli);

    public FormaPagamentoVenda procurar(String cod);

    public JTable listarTodos(String lote );
    
}
