/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Estoque;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioEstoqueInterface {
    
    public void adcionar(Estoque item);

    public Estoque verificarExistente(String nome);
    
    public boolean verificarSaldoEstoque(String cod, String quantidadeVendida);

    public boolean alterar(Estoque estoque, boolean operacao);

    public Estoque procurar(String cod);

    public JTable listarTodos(String lote);
}
