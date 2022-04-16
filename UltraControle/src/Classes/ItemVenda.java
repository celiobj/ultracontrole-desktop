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
public class ItemVenda {

    private String idItemVenda;
    private String idProduto;
    private String idVenda;
    private String valorCusto;
    private String valorVenda;
    private int quantidade;
    private String tipo;

    /**
     * @return the idItemVenda
     */
    public String getIdItemVenda() {
        return idItemVenda;
    }

    /**
     * @param idItemVenda the idItemVenda to set
     */
    public void setIdItemVenda(String idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    /**
     * @return the idProduto
     */
    public String getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * @return the idVenda
     */
    public String getIdVenda() {
        return idVenda;
    }

    /**
     * @param idVenda the idVenda to set
     */
    public void setIdVenda(String idVenda) {
        this.idVenda = idVenda;
    }

    /**
     * @return the valorCusto
     */
    public String getValorCusto() {
        return valorCusto;
    }

    /**
     * @param valorCusto the valorCusto to set
     */
    public void setValorCusto(String valorCusto) {
        this.valorCusto = valorCusto;
    }

    /**
     * @return the valorVenda
     */
    public String getValorVenda() {
        return valorVenda;
    }

    /**
     * @param valorVenda the valorVenda to set
     */
    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
