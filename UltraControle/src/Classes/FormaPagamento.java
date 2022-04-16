/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author celiobj
 */
public class FormaPagamento {
    private int idFormaPagamento;
    private String descricao;

    /**
     * @return the idFormaPagamento
     */
    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }

    /**
     * @param idFormaPagamento the idFormaPagamento to set
     */
    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
