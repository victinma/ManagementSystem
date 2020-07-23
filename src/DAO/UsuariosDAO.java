import java.sql.*;

public class UsuariosDAO {
    
    //Statement - executa instruções SQL
    public static Statement stUsuarios;
    //ResultSet - armazena resultado de um select
    public static ResultSet rsUsuarios;
    
    
    public static int validarUsuario(String tmpNome, String tmpSenha) throws Exception{
        int permissao = 0;
        
        try{
            ConexaoDAO.abrirConexao();
            
            //Execução do Processo de Login
            String sqlLogin;
            sqlLogin = "Select permissao_USUARIO from usuarios where nome_USUARIO like '" 
                    + tmpNome + "' and senha_USUARIO like '" + tmpSenha + "'";
            
            //preparando o statement
            stUsuarios = ConexaoDAO.connSistema.createStatement();
            //executando o select
            rsUsuarios = stUsuarios.executeQuery(sqlLogin);
            
            if(rsUsuarios.next()){//se houver registros
                //pegando permissao do select e jogando na variavel permissao
                permissao = rsUsuarios.getInt("permissao_USUARIO");
            }
            
            ConexaoDAO.fecharConexao();
            
        }catch(Exception erro){
            String msg = "Falha na validação em BD.\n"
                       + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                       + "Erro Original: " + erro.getMessage();
            throw new Exception(msg);
        }
        
        
        return permissao;
    }//fechando validarUsuario
    
}
