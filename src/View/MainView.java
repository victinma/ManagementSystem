import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainView extends JFrame implements ActionListener {

    //DECLARAÇÃO DOS OBJETOS
    public static JMenuBar mbrPrincipal;
    public static JMenu mnuSistema;
    public static JMenuItem mniLogout;    
    public static Container ctnPrincipal, ctnTopo, ctnMenu;
    public static JLabel lblBanner;    
    public static JButton btnMenu[];
    public static JDesktopPane dskJanelas;
    
    //Janelas
    public static ClientesView objClientesUI;
    public static VendasView objVendasUI;
    
    public MainView() {//CONSTRUTOR

        super("Sistema de Gerenciamento por Módulos");

        //INSTÂNCIA E CONFIGURAÇÃO DE OBJETOS (CONSTRUÇÃO)
        
        mbrPrincipal = new JMenuBar();
        this.setJMenuBar(mbrPrincipal);
        
        mnuSistema = new JMenu("Sistema");
        mbrPrincipal.add(mnuSistema);
        
        mniLogout = new JMenuItem("Logout",new ImageIcon("img/icons/exit.png"));
        mniLogout.addActionListener(this);
        mniLogout.setEnabled(false);
        mnuSistema.add(mniLogout);
        
        ctnPrincipal = new Container();
        ctnPrincipal.setLayout(new BorderLayout());
        this.add(ctnPrincipal); //add princ na janela

        ctnTopo = new Container();
        ctnTopo.setLayout(new GridLayout(2,1));
        ctnPrincipal.add(ctnTopo, BorderLayout.NORTH);//add topo no norte do princ
        
        lblBanner = new JLabel(new ImageIcon("img/banner.jpg"));
        ctnTopo.add(lblBanner); //add banner na 1L do topo
                
        ctnMenu = new Container();
        ctnMenu.setLayout(new GridLayout(2,4));
        ctnTopo.add(ctnMenu); //add menu na 2L do topo
        
        //botoes de navegação (modulos)
        String strMenu[]={"CLIENTES","FUNCIONÁRIOS","FORNECEDORES","PRODUTOS",
                          "VENDAS","CONTAS","USUÁRIOS","CHAMADOS"};
        
        btnMenu = new JButton[8];
        for(int i=0; i<btnMenu.length; i++){
            btnMenu[i] = new JButton(new ImageIcon("img/" + i + ".jpg"));//VOLTAR AQUI
            btnMenu[i].setBackground(Color.white);
            btnMenu[i].setToolTipText(strMenu[i]);
            btnMenu[i].addActionListener(this);
            btnMenu[i].setEnabled(false);
            ctnMenu.add(btnMenu[i]);//add botoes no menu
        }//fechando for
        
        dskJanelas = new JDesktopPane();
        ctnPrincipal.add(dskJanelas,BorderLayout.CENTER); //add dskjanelas no centro do principal
        /****add tela de login******/
        dskJanelas.add(new LoginView());
        
        //CONFIGURAÇÃO E COMPORTAMENTO DA JANELA
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt){
                int resp = JOptionPane.showConfirmDialog(null, 
                        "Deseja fechar os módulos e encerrar o sistema?","Fechar Sistema",JOptionPane.YES_NO_OPTION);
                
                if(resp == JOptionPane.NO_OPTION){
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }else{
                    dispose();
                }
            }
        });
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(montarTela());
        this.setVisible(true);

    }//fechando construtor

    public void actionPerformed(ActionEvent evt) {
        
        if(evt.getSource() == btnMenu[0]){
            btnMenu[0].setEnabled(false);
            objClientesUI = new ClientesView();
            dskJanelas.add(objClientesUI);
            
        }else if(evt.getSource() == btnMenu[4]){
            btnMenu[4].setEnabled(false);
            objVendasUI = new VendasView();
            dskJanelas.add(objVendasUI);
        }
        
        if(evt.getSource() == mniLogout){
            objClientesUI.dispose();
            
            for(int i=0; i<btnMenu.length;i++){
                btnMenu[i].setEnabled(false);
            }
            
            dskJanelas.add(new LoginView());
        }
        
    }//fechando actionPerformed
    
    public static Dimension montarTela(){//fullscreen
        Toolkit info = Toolkit.getDefaultToolkit();
        Dimension res = info.getScreenSize();
        
        return res;
    }//fechando montarTela

}//fechando class
