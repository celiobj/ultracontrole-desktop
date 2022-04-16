/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.TamanhoProduto;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioTamanhoInterface {

    public void adcionar(TamanhoProduto item);

    public void alterar(String cod, TamanhoProduto cli);

    public TamanhoProduto procurar(String cod);

    public Vector popularCombo();
    
    public JTable listarTodos(String lote);

}
