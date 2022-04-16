/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.ItemEntrada;
import Classes.Lote;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioLoteInterface {

    public boolean adcionar(Lote item);

    public void fechar(Lote item);

    public int verificarQuantidadeItens(Lote lote);

    public Lote verificarAberto();

    public void alterar(String cod, Lote cli);

    public Lote procurar(String cod);

    public JTable detalharLote(String codigoLote);

    public boolean cancelar(String cod);

    public String valorTotaldeLotes(boolean data, boolean calculo, String dataIni, String dataFim);

    public JTable listarTodos(boolean data, String dataIni, String dataFim);

}
