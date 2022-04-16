/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Classes.Usuario;

/**
 *
 * @author celio
 */
public interface RepositorioUsuarioInterface {

    public void adcionar(Usuario usuario);
    public void alterar(Usuario usuario);
    public boolean procurar(String login, String senha);
    public void remover(Usuario usuario);

}
