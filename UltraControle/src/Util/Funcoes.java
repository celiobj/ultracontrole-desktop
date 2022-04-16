/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import GUI.Principal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author celiobj
 */
public class Funcoes {
    
     public static Vector proximaLinha(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        Vector LinhaAtual = new Vector();

        try {
            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                switch (rsmd.getColumnType(i)) {

                    case Types.VARCHAR:
                        LinhaAtual.addElement(rs.getString(i));
                        break;
                    case Types.TIMESTAMP:
                        LinhaAtual.addElement(rs.getDate(i).toLocaleString());
                        break;
                    case Types.INTEGER:
                        LinhaAtual.addElement(rs.getInt(i));
                        break;
                    case Types.NUMERIC:
                        LinhaAtual.addElement(paraFormatoDinheiro(rs.getDouble(i)));
                        break;
                    case Types.DECIMAL:
                        LinhaAtual.addElement(rs.getInt(i));
                        break;
                    case Types.DOUBLE:
                        LinhaAtual.addElement(paraFormatoDinheiro(rs.getDouble(i)));
                        break;
                }
            }
        } catch (SQLException e) {
        }
        return LinhaAtual;

    }

    public static String paraRecuperarData(String data) {
        String retorno;
        String dia;
        String mes;
        String ano;
        dia = data.substring(8, 10);
        mes = data.substring(5, 7);
        ano = data.substring(0, 4);
        retorno = dia + "/" + mes + "/" + ano;
        return retorno;
    }

    public static String paraFormatoDinheiro(Double valor) {
        Locale ptBr = new Locale("pt", "BR");
        String formato = NumberFormat.getCurrencyInstance(ptBr).format(valor);
        return formato;
    }

    public static String formatoParaInserir(String valor) throws ParseException {
        //String primeiraLetra = String.valueOf(valor.charAt(0));
        String valorFormatado1 = valor.replace("R", "");
        String valorFormatado2 = valorFormatado1.replace("$", "");
        String valorFormatado = valorFormatado2.replace(" ", "");
        // if (primeiraLetra.equalsIgnoreCase("R")) {
        //     NumberFormat formato2 = NumberFormat.getInstance();
        //    return formato2.parse(valor.substring(3)).toString();
        // } else {
        NumberFormat formato2 = NumberFormat.getInstance();
        return formato2.parse(valorFormatado).toString();
        //  }
    }

    public static String paraInserirData(String data) {
        String retorno;
        String dia;
        String mes;
        String ano;
        dia = data.substring(0, 2);
        mes = data.substring(3, 5);
        ano = data.substring(6, 10);
        retorno = ano + mes + dia;
        return retorno;
    }

    public String dataInicioMes(String mes) {
        String dataInicioMes;
        dataInicioMes = null;
        switch (mes) {
            case "Janeiro":
                {
                    dataInicioMes = "01/01";
                    break;
                }
            case "Fevereiro":
                {
                    dataInicioMes = "01/02";
                    break;
                }
            case "Mar\u00e7o":
                {
                    dataInicioMes = "01/03";
                    break;
                }
            case "Abril":
                {
                    dataInicioMes = "01/04";
                    break;
                }
            case "Maio":
                {
                    dataInicioMes = "01/05";
                    break;
                }
            case "Junho":
                {
                    dataInicioMes = "01/06";
                    break;
                }
            case "Julho":
                {
                    dataInicioMes = "01/07";
                    break;
                }
            case "Agosto":
                {
                    dataInicioMes = "01/08";
                    break;
                }
            case "Setembro":
                {
                    dataInicioMes = "01/09";
                    break;
                }
            case "Outubro":
                {
                    dataInicioMes = "01/10";
                    break;
                }
            case "Novembro":
                {
                    dataInicioMes = "01/11";
                    break;
                }
            case "Dezembro":
                {
                    dataInicioMes = "01/12";
                    break;
                }
        }
        return dataInicioMes;
    }

    public String dataFimMes(String mes) {
        String dataFimMes;
        dataFimMes = null;
        switch (mes) {
            case "Janeiro":
                {
                    dataFimMes = "31/01";
                    break;
                }
            case "Fevereiro":
                {
                    dataFimMes = "28/02";
                    break;
                }
            case "Mar\u00e7o":
                {
                    dataFimMes = "31/03";
                    break;
                }
            case "Abril":
                {
                    dataFimMes = "30/04";
                    break;
                }
            case "Maio":
                {
                    dataFimMes = "31/05";
                    break;
                }
            case "Junho":
                {
                    dataFimMes = "30/06";
                    break;
                }
            case "Julho":
                {
                    dataFimMes = "31/07";
                    break;
                }
            case "Agosto":
                {
                    dataFimMes = "31/08";
                    break;
                }
            case "Setembro":
                {
                    dataFimMes = "30/09";
                    break;
                }
            case "Outubro":
                {
                    dataFimMes = "31/10";
                    break;
                }
            case "Novembro":
                {
                    dataFimMes = "30/11";
                    break;
                }
            case "Dezembro":
                {
                    dataFimMes = "31/12";
                    break;
                }
        }
        return dataFimMes;
    }

    public String pesquisarDiaSemana(int _diaSemana) {
        String diaSemana = null;
        switch (_diaSemana) {
            case 1:
                {
                    diaSemana = "Domingo";
                    break;
                }
            case 2:
                {
                    diaSemana = "Segunda";
                    break;
                }
            case 3:
                {
                    diaSemana = "Ter\u00e7a";
                    break;
                }
            case 4:
                {
                    diaSemana = "Quarta";
                    break;
                }
            case 5:
                {
                    diaSemana = "Quinta";
                    break;
                }
            case 6:
                {
                    diaSemana = "Sexta";
                    break;
                }
            case 7:
                {
                    diaSemana = "S\u00e1bado";
                    break;
                }
        }
        return diaSemana;
    }

}
