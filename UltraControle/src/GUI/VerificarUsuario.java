/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Persistencia.RepositorioUsuario;
import Util.Funcoes;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;

/**
 *
 * @author celio
 */
public class VerificarUsuario extends JFrame {

    JButton ok, cancelar;
    JLabel usuario, senha;
    String tipos[] = {"Produção", "Desenvolvimento", "Treinamento"};
    JComboBox bancos;
    JTextField tUsuario;
    JPasswordField tSenha;
    RepositorioUsuario ru;
    public static String banco;
    public static String bancoTela;
    public static Connection con;
    // static final Logger LOGGER = Logger.getLogger(VerificarUsuario.class.getName());
    // Handler fileHandler;

    public VerificarUsuario() {
        super("Verificar Usuário - UltraCom 1.0");
        Container tela = getContentPane();
        tela.setLayout(null);

        //   try {
        //      fileHandler = fileHandler  = new FileHandler(".//Logs/log.log", 2000, 5);
        //    } catch (IOException | SecurityException ex) {
        //       Logger.getLogger(VerificarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        //   }
        //    LOGGER.addHandler(fileHandler);
        //   LOGGER.setLevel(Level.FINE);
        //   LOGGER.addHandler(new ConsoleHandler());
        ok = new JButton("Ok");
        cancelar = new JButton("Cancelar");
        usuario = new JLabel("Usuario: ");
        senha = new JLabel("Senha: ");
        tUsuario = new JTextField(10);
        tSenha = new JPasswordField(10);
        bancos = new JComboBox(tipos);

        usuario.setBounds(20, 20, 50, 20);
        tUsuario.setBounds(100, 20, 100, 20);
        senha.setBounds(20, 50, 50, 20);
        tSenha.setBounds(100, 50, 100, 20);
        bancos.setBounds(20, 80, 200, 20);
        ok.setBounds(40, 110, 50, 30);
        cancelar.setBounds(100, 110, 100, 30);

        bancos.setSelectedIndex(1);
        tela.add(ok);
        tela.add(cancelar);
        tela.add(usuario);
        tela.add(senha);
        tela.add(tUsuario);
        tela.add(tSenha);
        tela.add(bancos);

        tUsuario.setFont(new Font("Times New Roman", Font.BOLD, 14));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (bancos.getSelectedIndex()) {
                    case 0: {
                        banco = "bd_ultracontrole";
                        bancoTela = "Produção";
                        break;
                    }
                    case 1: {
                        banco = "bd_ultracontroledev";
                        bancoTela = "Desenvolvimento";
                        break;
                    }
                    case 2: {
                        banco = "bd_ultracontroletreinamento";
                        bancoTela = "Treinamento";
                        break;
                    }
                }
                // if(tUsuario.getText().equals("")||tSenha.getText().equals("")){
                //     JOptionPane.showMessageDialog(null,"Preencha Usuário e Senha!!!");
                // }else{
                //ru = new RepositorioUsuario();
                //ru.procurar("", "");
                // if(ru.procurar(tUsuario.getText(), tSenha.getText())){

                setVisible(false);
                //LOGGER.log(Level.FINE, "Acessou a tela Principal");
                Funcoes fu = new Funcoes();
                Principal pri = new Principal();
                pri.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                //  }
                //  else{
                //      JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
                //  }
                //   }
            }
        });

        cancelar.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        setSize(250, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String args[]) {

        VerificarUsuario vu = new VerificarUsuario();
        vu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

}
