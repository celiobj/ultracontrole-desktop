/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.TipoProduto;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioTipoInterface {
    
    public void adcionar(TipoProduto item);

    public void alterar(String cod, TipoProduto cli);
    
    public Vector popularCombo();

    public TipoProduto procurar(String cod);

    public JTable listarTodos(String lote);
    
}
