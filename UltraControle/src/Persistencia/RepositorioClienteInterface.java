/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Classes.Cliente;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author celio
 */
public interface RepositorioClienteInterface {

    public void adcionar(Cliente cli);
    public Cliente verificarExistente(String nome);
    public boolean alterar(String cod, Cliente cli);
    public Cliente procurar(String cod);
    public Cliente procurarNome(String nome);
    public Vector popularCombo();
    public JTable listarTodos();

}
