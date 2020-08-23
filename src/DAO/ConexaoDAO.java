import java.sql.*;

public class ConexaoDAO {
    
    /******Objeto Connection*********/
    public static Connection connSistema;
    
    /***************métodos****************/
    public static void abrirConexao() throws Exception{
        try{
            //Reconhecendo biblioteca Mysql
            Class.forName("com.mysql.jdbc.Driver");
            //abrindo conexao
            connSistema = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdsistema","root","");            
        }catch(Exception erro){
            //construindo msg de erro
            String msg = "Falha na abertura da conexão.\n "
                       + "Verifique a String de conexão da biblioteca e do BD.\n\n"
                       + "Erro Original: " + erro.getMessage();
            //disparando msg de erro
            throw new Exception(msg);
        }
    }//fechando abrirConexao
    
    public static void fecharConexao() throws Exception{
        
        try{
            //fechando conexao
            connSistema.close();
        }catch(Exception erro){
            String msg = "Falha no fechamento da conexão.\n"
                       + "Não existe uma conexão ativa.\n\n"
                       + "Erro Original: " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}
