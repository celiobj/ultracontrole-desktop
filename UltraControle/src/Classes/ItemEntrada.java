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
public class ItemEntrada {

    private String idItemEntrada;
    private String idProduto;
    private String idLote;
    private String valorCompra;
    private String taxaCompra;
    private String frete;
    private String valorCusto;
    private String valorVenda;
    private int quantidade;
    private String situacao;

    /**
     * @return the idItemEntrada
     */
    public String getIdItemEntrada() {
        return idItemEntrada;
    }

    /**
     * @param idItemEntrada the idItemEntrada to set
     */
    public void setIdItemEntrada(String idItemEntrada) {
        this.idItemEntrada = idItemEntrada;
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
     * @return the valorCompra
     */
    public String getValorCompra() {
        return valorCompra;
    }

    /**
     * @param valorCompra the valorCompra to set
     */
    public void setValorCompra(String valorCompra) {
        this.valorCompra = valorCompra;
    }

    /**
     * @return the taxaCompra
     */
    public String getTaxaCompra() {
        return taxaCompra;
    }

    /**
     * @param taxaCompra the taxaCompra to set
     */
    public void setTaxaCompra(String taxaCompra) {
        this.taxaCompra = taxaCompra;
    }

    /**
     * @return the frete
     */
    public String getFrete() {
        return frete;
    }

    /**
     * @param frete the frete to set
     */
    public void setFrete(String frete) {
        this.frete = frete;
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
     * @return the idLote
     */
    public String getIdLote() {
        return idLote;
    }

    /**
     * @param idLote the idLote to set
     */
    public void setIdLote(String idLote) {
        this.idLote = idLote;
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
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

}
