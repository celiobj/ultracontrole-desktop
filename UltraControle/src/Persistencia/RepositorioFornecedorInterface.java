/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Fornecedor;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioFornecedorInterface {
    
     public void adcionar(Fornecedor item);

    public void alterar(String cod, Fornecedor cli);

    public Fornecedor procurar(String cod);

    public Vector popularCombo();
    
    public JTable listarTodos(String lote);
    
}
