/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Produto;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioProdutoInterface {

    public void adcionar(Produto produto);
    
    public Vector popularCombo();

    public Produto verificarExistente(String nome);

    public boolean alterar(String cod, Produto cli);

    public Produto procurar(String cod);

    public Produto procurarNome(String nome);

    public JTable listarTodos();

}
