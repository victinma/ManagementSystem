
import java.sql.*;
import java.util.*;

public class VendasDAO {

    public static Statement stVendas;
    public static ResultSet rsVendas;

    public static int gerarCodigo() throws Exception {
        int novoID;
        try {
            ConexaoDAO.abrirConexao();

            String sqlCodigo = "Select codigo_VENDA from vendas order by codigo_VENDA desc limit 1";
            stVendas = ConexaoDAO.connSistema.createStatement();
            rsVendas = stVendas.executeQuery(sqlCodigo);

            if (!rsVendas.next()) {
                novoID = 1;
            } else {
                int ultimoID = rsVendas.getInt("codigo_VENDA");
                novoID = ultimoID + 1;
            }

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha ao gerar código da venda.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
        return novoID;
    }

    public static void cadastrarVenda(List<ItensVO> tmpItens, VendasVO tmpVenda) throws Exception {
        try {

            ConexaoDAO.abrirConexao();

            for (ItensVO itemAtual : tmpItens) {
                //multiplos inserts (um para cada item)
                String sqlItens = "Insert into itens(";
                sqlItens += "codigoVenda_ITEM, codigoProduto_ITEM,";
                sqlItens += "quantidade_ITEM, valorUnitario_ITEM)";
                sqlItens += "values(";
                sqlItens += itemAtual.getCodigoVenda() + ",";
                sqlItens += itemAtual.getCodigoProd() + ",";
                sqlItens += itemAtual.getQtdeItem() + ",";
                sqlItens += itemAtual.getValorItem() + ")";
                
                stVendas = ConexaoDAO.connSistema.createStatement();
                stVendas.executeUpdate(sqlItens);
                
                //UPDATE NO ESTOQUE
                int qtdeItem = itemAtual.getQtdeItem();
                String sqlEstoque = "Update produtos ";
                sqlEstoque += "set quantidadeEstoque_PRODUTO = (quantidadeEstoque_PRODUTO - " + qtdeItem + ") ";
                sqlEstoque += "where codigo_PRODUTO = " + itemAtual.getCodigoProd();
               
                stVendas = ConexaoDAO.connSistema.createStatement();
                stVendas.executeUpdate(sqlEstoque);
            }
            
            //INSERT NA VENDA
            String sqlVenda = "Insert into vendas(";
            sqlVenda += "data_VENDA, valorTotal_VENDA, cpfCliente_VENDA)";
            sqlVenda += "values(";
            sqlVenda += "'" + tmpVenda.getData() + "',";
            sqlVenda += tmpVenda.getTotal()+ ",";
            sqlVenda += "'" + tmpVenda.getCpfCliente() + "')";
            
            stVendas = ConexaoDAO.connSistema.createStatement();
            stVendas.executeUpdate(sqlVenda);
                    
                    
            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha ao registrar venda.\n"
                    + "Verifique a sintaxe da instrução Insert e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }

}
