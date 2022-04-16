/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.ItemVenda;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioItemVendaInterface {

    public boolean adcionar(ItemVenda item);

    public boolean remover(String codigoVenda, String codigoProduto);
    
    public boolean alterar(String codigoVenda, String codigoProduto, String valor, String quantidade);

    public Vector itensdeVenda(String idvenda);
    
     public Vector itensdeTroca(String idvenda, String tipo);

    public boolean consultarItemnaVenda(String idVenda, String idProduto, String tipo);

    public ItemVenda verificarExistente(String nome);

    public boolean fechar(String cod);

    public ItemVenda procurar(String cod);

    public JTable listarTodos(String lote);

}
