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
public class Fornecedor {
    
    private String idfornecedor;
    private String cnpj;
    private String nome;
    private String codigo;

    /**
     * @return the idfornecedor
     */
    public String getIdfornecedor() {
        return idfornecedor;
    }

    /**
     * @param idfornecedor the idfornecedor to set
     */
    public void setIdfornecedor(String idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
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
    
}
