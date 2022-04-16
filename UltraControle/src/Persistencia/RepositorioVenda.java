/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Classes.Venda;
import DAO.AccessDatabase;
import GUI.VerificarUsuario;
import Util.Funcoes;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author celiobj
 */
public class RepositorioVenda implements RepositorioVendaInterface {

    @Override
    public void adcionar(Venda item) {
        try {

            AccessDatabase a = new AccessDatabase();
            // try (Connection con = a.conectar()) {
            Statement st = VerificarUsuario.con.createStatement();
            st.execute("INSERT INTO `" + VerificarUsuario.banco + "`.`venda` (`idcliente`, `data`,`situacao`,`tipo`) VALUES ('" + item.getIdCliente() + "', '" + item.getData() + "', 'A', '" + item.getTipo() + "');");
            //}

        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());

        }
    }

    @Override
    public String pegarUltimo() {
        try {

            AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT max(idvenda) as codigo FROM " + VerificarUsuario.banco + ".venda;");
            rs.next();
            String saldo = rs.getString("codigo");
            //con.close();
            return saldo;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }

    @Override
    public boolean primeiraVenda() {
        String registros = null;
        try {

            AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) as registros FROM " + VerificarUsuario.banco + ".venda;");
            rs.next();
            registros = rs.getString("registros");
            //con.close();
        } catch (SQLException t) {
            System.out.println(t.getMessage());
        }
        if (registros.equalsIgnoreCase("0")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Venda verificarExistente(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venda verificarAberto(String tipo) {
        try {
            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `" + VerificarUsuario.banco + "`.`venda` WHERE (situacao) ='A' and tipo = '" + tipo + "';");
            rs.next();

            Venda venda = new Venda();
            venda.setIdVenda(rs.getString("idvenda"));
            venda.setIdCliente(rs.getString("idcliente"));

            try {
                venda.setValor(Funcoes.formatoParaInserir(String.valueOf(rs.getDouble("valor"))));
            } catch (Exception ex) {
                Logger.getLogger(RepositorioVenda.class.getName()).log(Level.SEVERE, null, ex);
            }

            String dataResult = Funcoes.paraRecuperarData(String.valueOf(rs.getDate("data"))).replace("/", "");
            venda.setData(dataResult);
            venda.setSituacao(rs.getString("situacao"));
            // con.close();

            return venda;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public boolean fechar(String cod) {

        try {

            AccessDatabase a = new AccessDatabase();
            // Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`venda` SET situacao='F' WHERE idvenda = " + cod + "");
            // con.close();
            //JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");
            return true;
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "VENDA - Erro: " + t.getMessage());
            return false;
        }

    }

    @Override
    public boolean alterarValorTotal(String cod, String valor) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`venda` SET valor='" + valor + "' WHERE idvenda = " + cod + "");
            // con.close();
            //JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");
            return true;
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "VENDA - Erro: " + t.getMessage());
            return false;
        }

    }

    @Override
    public String valorTotalVenda(String cod) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT SUM(valorvenda*quantidade) as valorvenda FROM " + VerificarUsuario.banco + ".produto_venda where idvenda = '" + cod + "';");
            rs.next();
            String saldo = Funcoes.paraFormatoDinheiro(rs.getDouble("valorvenda"));
            // con.close();
            return saldo;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }

    @Override
    public String valorTotalTroca(String cod) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT (SELECT SUM(pv.valorvenda)*pv.quantidade FROM " + VerificarUsuario.banco + ".produto_venda pv where pv.tipo = '3' and pv.idvenda = '" + cod + "') -  "
                    + "(SELECT SUM(pv.valorvenda)*pv.quantidade FROM bd_ultracontroledev.produto_venda pv where pv.tipo = '4' and pv.idvenda = '" + cod + "') as valorTroca;");
            rs.next();
            String saldo = Funcoes.paraFormatoDinheiro(rs.getDouble("valorTroca"));
            // con.close();
            return saldo;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }
    
    @Override
    public String ticketMedio(boolean data, String dataIni, String dataFim) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            String ticket;
            if (data) {
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".venda where data  between '" + dataIni + "' and '" + dataFim + "' and situacao = 'F';");
                rs.next();
            } else {
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".venda where situacao = 'F';");
                rs.next();
            }
                       ticket = Funcoes.paraFormatoDinheiro(rs.getDouble("valortotal"));
            return ticket;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }


    @Override
    public String valorTotaldeVendas(boolean data, boolean calculo, String dataIni, String dataFim) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            String saldo;
            if (data) {
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".venda where data  between '" + dataIni + "' and '" + dataFim + "' and situacao = 'F';");
                rs.next();
            } else {
                rs = st.executeQuery("SELECT sum(valor) as valortotal FROM " + VerificarUsuario.banco + ".venda where situacao = 'F';");
                rs.next();
            }

            if (calculo) {
                saldo = String.valueOf(rs.getDouble("valortotal"));
            } else {
                saldo = Funcoes.paraFormatoDinheiro(rs.getDouble("valortotal"));
            }

            // con.close();
            return saldo;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }

    @Override
    public String valorTotaldeCustos(boolean data, boolean calculo, String dataIni, String dataFim) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            String saldo;
            if (data) {
                rs = st.executeQuery("select SUM(c.custo) as custo, SUM(c.venda) as venda from (select distinct v.idvenda, v.data, c.nome, "
                        + "(select SUM(pv.valorcusto) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as custo, "
                        + "(select SUM(pv.valorvenda) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as venda "
                        + "from " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".produto_venda pv, " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".clientes c "
                        + "where v.data  between '" + dataIni + "' and '" + dataFim + "' "
                        + "and c.idcliente = v.idcliente "
                        + "and v.situacao = 'F' order by idvenda) c;");
                rs.next();
            } else {
                rs = st.executeQuery("select SUM(c.custo) as custo, SUM(c.venda) as venda from (select distinct v.idvenda, v.data, c.nome, "
                        + "(select SUM(pv.valorcusto) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as custo, "
                        + "(select SUM(pv.valorvenda) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as venda "
                        + "from " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".produto_venda pv, " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".clientes c "
                        + "where c.idcliente = v.idcliente "
                        + "and v.situacao = 'F' order by idvenda) c;");
                rs.next();
            }

            if (calculo) {
                saldo = String.valueOf(rs.getDouble("custo"));
            } else {
                saldo = Funcoes.paraFormatoDinheiro(rs.getDouble("custo"));
            }

            // con.close();
            return saldo;

        } catch (SQLException t) {
            System.out.println(t.getMessage());

        }
        return null;
    }

    @Override
    public Venda procurar(String cod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cancelar(String cod) {
        try {

            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            st.executeUpdate("UPDATE `" + VerificarUsuario.banco + "`.`venda` SET situacao='C' WHERE idvenda = " + cod + "");
            // con.close();
            //JOptionPane.showMessageDialog(null, "Lote fechado com Sucesso!!!");
            return true;
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "VENDA - Erro: " + t.getMessage());
            return false;
        }
    }

    @Override
    public JTable detalharVenda(String codigovenda) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT v.idvenda, pv.idproduto, CONCAT(p.nome,' - ',t.descricao) as produto, pv.quantidade, pv.valorvenda, tv.descricao FROM " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".produto_venda pv, " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".tamanhoproduto t, " + VerificarUsuario.banco + ".tipovenda tv "
                    + "WHERE pv.idvenda = v.idvenda and pv.idproduto = p.codigo and pv.tipo = tv.idtipo_venda and p.idtamanho = t.idtamanho and v.idvenda = '" + codigovenda + "' order by v.idvenda desc;");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cód");
            cabecalho.addElement("Produto");
            cabecalho.addElement("Nome");
            cabecalho.addElement("Quantidade");
            cabecalho.addElement("Valor(un)");
            cabecalho.addElement("Tipo de operaçao");
            

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

            // con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public JTable custoVendas(boolean data, String dataIni, String dataFim) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            if (data) {
                rs = st.executeQuery("select distinct v.idvenda, v.data, c.nome, "
                        + "(select SUM(pv.valorcusto) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as custo, "
                        + "(select SUM(pv.valorvenda) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as venda "
                        + "from " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".produto_venda pv, " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".clientes c "
                        + "where v.data  between '" + dataIni + "' and '" + dataFim + "' "
                        + "and c.idcliente = v.idcliente "
                        + "and v.situacao = 'F' order by idvenda;");
                rs.next();
            } else {
                rs = st.executeQuery("select distinct v.idvenda, v.data, c.nome, "
                        + "(select SUM(pv.valorcusto) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as custo, "
                        + "(select SUM(pv.valorvenda) from " + VerificarUsuario.banco + ".produto_venda pv where pv.idvenda = v.idvenda) as venda "
                        + "from " + VerificarUsuario.banco + ".produtos p, " + VerificarUsuario.banco + ".produto_venda pv, " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".clientes c "
                        + "where c.idcliente = v.idcliente "
                        + "and v.situacao = 'F' order by idvenda;");
                rs.next();
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cod.");
            cabecalho.addElement("Data");
            cabecalho.addElement("Cliente");
            cabecalho.addElement("Custo");
            cabecalho.addElement("Venda");

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

            // con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public JTable listarItensVenda(String cupom, String tipo) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT p.codigo, p.nome,pv.valorvenda, pv.quantidade, pv.valorvenda * pv.quantidade as total  FROM " + VerificarUsuario.banco + ".produto_venda pv, " + VerificarUsuario.banco + ".produtos p "
                    + "WHERE p.codigo = pv.idproduto and pv.idvenda = '" + cupom + "' and pv.tipo='" + tipo + "';");
            rs.next();

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cód");
            cabecalho.addElement("Produto");
            cabecalho.addElement("Valor(un)");
            cabecalho.addElement("Quantidade");
            cabecalho.addElement("Total");

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

            // con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public JTable listarTodos(boolean data, String dataIni, String dataFim) {
        try {
            Vector cabecalho = new Vector();
            Vector linhas = new Vector();
            AccessDatabase a = new AccessDatabase();
            //Connection con = a.conectar();
            Statement st = VerificarUsuario.con.createStatement();
            ResultSet rs;
            if (data) {
                rs = st.executeQuery("SELECT v.idvenda, c.nome, v.data, v.valor, tv.descricao FROM " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".clientes c, " + VerificarUsuario.banco + ".tipoVenda tv  where v.data  between '" + dataIni + "' and '" + dataFim + "' and c.idcliente = v.idcliente and v.tipo = tv.idtipo_venda and v.situacao = 'F';");
                rs.next();
            } else {
                rs = st.executeQuery("SELECT v.idvenda, c.nome, v.data, v.valor, tv.descricao FROM " + VerificarUsuario.banco + ".venda v, " + VerificarUsuario.banco + ".clientes c, " + VerificarUsuario.banco + ".tipoVenda tv where c.idcliente = v.idcliente and v.tipo = tv.idtipo_venda and v.situacao = 'F';");
                rs.next();
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            cabecalho.addElement("Cod.");
            cabecalho.addElement("Cliente");
            cabecalho.addElement("Data");
            cabecalho.addElement("Total");
            cabecalho.addElement("Tipo de operaçao");

            do {
                linhas.addElement(Funcoes.proximaLinha(rs, rsmd));
            } while (rs.next());

            JTable tabela = new JTable(linhas, cabecalho);

            // con.close();
            return tabela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }
}
