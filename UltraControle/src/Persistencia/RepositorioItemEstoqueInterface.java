/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.ItemEntrada;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioItemEstoqueInterface {

    public boolean adcionar(ItemEntrada item);

    public ItemEntrada verificarExistente(String nome);

    public boolean alterar(String codigoLote, String codigoProduto, String quantidade);

    public boolean precificar(String codigoProduto, String preco);

    public boolean remover(String codigoLote, String codigoProduto);

    public boolean consultarItemnaCompra(String idLote, String idProduto);

    public ItemEntrada procurar(String cod);

    public String verificarValorMedioCusto(String cod);

    public String verificarValorMedioVenda(String cod);
    
    public JTable listarparaPrecificar();

    public JTable listarTodos(String lote);

}
