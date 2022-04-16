/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.FormaPagamento;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public interface RepositorioFormaPagamentoInterface {

    public void adcionar(FormaPagamento item);

    public Vector popularCombo();

    public void alterar(String cod, FormaPagamento cli);

    public FormaPagamento procurar(String cod);

    public JTable listarTodos(String lote);

}
