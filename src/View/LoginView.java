

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JInternalFrame implements ActionListener {

    public static Container ctnLogin;
    public static JLabel lblUsuario, lblSenha;
    public static JTextField txtUsuario;
    public static JPasswordField pwdSenha;
    public static JButton btnAcessar;
    
    public static JComboBox cmbItens;
       
    public LoginView() {

        super("Acesso ao sistema");
        
        
            
        ctnLogin = new Container();
        ctnLogin.setLayout(null);
        this.add(ctnLogin);
        
                
        lblUsuario = new JLabel("Nome de Usuário:");
        lblUsuario.setBounds(15,15,150,20);
        ctnLogin.add(lblUsuario);
        
        txtUsuario = new JTextField();
        txtUsuario.setBounds(15,45,300,20);
        ctnLogin.add(txtUsuario);
        
        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(15,65,150,20);
        ctnLogin.add(lblSenha);
        
        pwdSenha = new JPasswordField();
        pwdSenha.setBounds(15,95,300,20);
        ctnLogin.add(pwdSenha);
        
        pwdSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt){
                if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                    efetuarLogin();
                    }
                }
             }
         );
        
        btnAcessar = new JButton("Acessar Sistema");
        btnAcessar.addActionListener(this);
        btnAcessar.setBounds(15,135,300,30);
        ctnLogin.add(btnAcessar);
              
        
        this.setLocation(480,80); //local de abertura
        this.setSize(340,210);
        this.show();
        
    }//fechando construtor
    
    public void efetuarLogin(){
        try{
                String tmpNome = txtUsuario.getText();
                String tmpSenha = pwdSenha.getText();
                int permissao;
                
                permissao = UsuariosDAO.validarUsuario(tmpNome,tmpSenha);
                
                if(permissao == 0){
                    JOptionPane.showMessageDialog(null,"Dados Inválidos.");
                }else{
                    JOptionPane.showMessageDialog(null, "Acesso Concedido.");
                    this.dispose(); //fecha a interface atual(login)
                    MainView.mniLogout.setEnabled(true);
                    
                    if(permissao == 1){//ADM
                        for(int i=0; i<MainView.btnMenu.length;i++){
                            MainView.btnMenu[i].setEnabled(true); //habilitando todos os botoes
                        }
                    }else if(permissao == 2){//COMUM
                        MainView.btnMenu[0].setEnabled(true);
                        MainView.btnMenu[2].setEnabled(true);
                        MainView.btnMenu[3].setEnabled(true);
                        MainView.btnMenu[4].setEnabled(true);
                        MainView.btnMenu[7].setEnabled(true);
                    }
                }
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null,erro.getMessage());
            }
    }

    public void actionPerformed(ActionEvent evt) {
        
        if(evt.getSource() == btnAcessar){
            
            efetuarLogin();
            
        }        
        
    }

}//fechando classe
