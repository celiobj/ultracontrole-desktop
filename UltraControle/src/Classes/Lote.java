/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author celiobj
 */
public class Lote {
    
    private int idLote;
    private String codigo;
    private String idfornecedo;
    private String frete;
    private String taxa;
    private String valor;
    private String data;    
    private int quantidade; 

    /**
     * @return the idLote
     */
    public int getIdLote() {
        return idLote;
    }

    /**
     * @param idLote the idLote to set
     */
    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the idfornecedo
     */
    public String getIdfornecedo() {
        return idfornecedo;
    }

    /**
     * @param idfornecedo the idfornecedo to set
     */
    public void setIdfornecedo(String idfornecedo) {
        this.idfornecedo = idfornecedo;
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
     * @return the taxa
     */
    public String getTaxa() {
        return taxa;
    }

    /**
     * @param taxa the taxa to set
     */
    public void setTaxa(String taxa) {
        this.taxa = taxa;
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
    
}
