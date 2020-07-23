
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class BotoesBarra extends AbstractAction {

    public BotoesBarra(int indiceBotao, ImageIcon imgIcone, String strDescricao){

        super(strDescricao, imgIcone);

        this.putValue("id", indiceBotao);
        this.putValue(SHORT_DESCRIPTION, strDescricao);

    }

    public void actionPerformed(ActionEvent evt) {

        if ((int) getValue("id") == 0) { //bbrNovo
            ClientesView.acao = 1; //cadastro
            ClientesView.btnEditar.setEnabled(false);
            ClientesView.bbrEditar.setEnabled(false);
            ClientesView.mniEditar.setEnabled(false);
            ClientesView.desbloquearCampos(true);
            ClientesView.btnDesativar.setEnabled(false);
            ClientesView.bbrDesativar.setEnabled(false);
            ClientesView.mniDesativar.setEnabled(false);
            ClientesView.limparCampos();

        } else if ((int) getValue("id") == 1) { //bbrEditar
            ClientesView.acao = 2;
            ClientesView.desbloquearCampos(true);
            ClientesView.txtCampos[0].setEditable(false);
            ClientesView.btnEditar.setEnabled(false);
            ClientesView.mniEditar.setEnabled(false);
            ClientesView.bbrEditar.setEnabled(false);

        } else if ((int) getValue("id") == 2) {
            ClientesView.status = ClientesView.validarCampos();

            if (ClientesView.acao == 1) {

                if (ClientesView.status == true) {

                    if (ClientesView.statusFoto == JFileChooser.APPROVE_OPTION) {
                        //salvar a foto
                        int ultimoPonto = ClientesView.strNomeArquivoOrigem.lastIndexOf(".");//pegando a posição do ultimo ponto
                        ClientesView.extensao = ClientesView.strNomeArquivoOrigem
                                .substring(ultimoPonto + 1, ClientesView.strNomeArquivoOrigem.length());
                        ClientesView.strCaminhoDestino = "img\\system\\" + ClientesView.txtCampos[0]
                                .getText() + "." + ClientesView.extensao;

                        try {
                            ClientesView.flsEntrada = new FileInputStream(ClientesView.strCaminhoOrigem);
                            ClientesView.flsSaida = new FileOutputStream(ClientesView.strCaminhoDestino);

                            ClientesView.flcOrigem = ClientesView.flsEntrada.getChannel();
                            ClientesView.flcDestino = ClientesView.flsSaida.getChannel();

                            //cópia total do arquivo
                            ClientesView.flcOrigem.transferTo(0, ClientesView.flcOrigem.size(), ClientesView.flcDestino);

                            ClientesView.flcOrigem.close();
                            ClientesView.flcDestino.close();

                        } catch (Exception erro) {
                            JOptionPane.showMessageDialog(null, erro.getMessage());
                        }
                    }

                    //cadastrar
                    try {
                        ClientesVO novoCliente = new ClientesVO();
                        //preenchendo objeto
                        novoCliente.setCpf(ClientesView.txtCampos[0].getText());
                        novoCliente.setNome(ClientesView.txtCampos[1].getText());
                        novoCliente.setDataNascimento(ClientesView.txtCampos[2].getText());
                        novoCliente.setEndereco(ClientesView.txtCampos[3].getText());
                        novoCliente.setBairro(ClientesView.txtCampos[4].getText());
                        novoCliente.setCidade(ClientesView.txtCampos[5].getText());
                        novoCliente.setTelefone(ClientesView.txtCampos[6].getText());
                        novoCliente.setEmail(ClientesView.txtCampos[7].getText());
                        novoCliente.setStatus(1);
                        novoCliente.setFoto(ClientesView.txtCampos[0].getText() + "." + ClientesView.extensao);

                        ClientesDAO.cadastrarCliente(novoCliente);
                        JOptionPane.showMessageDialog(null, "Cliente " + novoCliente.getNome() + " cadastrado.");
                        ClientesView.desbloquearCampos(false);
                        ClientesView.limparCampos();
                        ClientesView.carregarDados(0, "");

                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }

            } else if (ClientesView.acao == 2) {
                //ALTERAÇÃO
                try {
                    ClientesVO clienteAtual = new ClientesVO();
                    String tmpCpf = ClientesView.txtCampos[0].getText();
                    //preenchendo objeto
                    clienteAtual.setNome(ClientesView.txtCampos[1].getText());
                    clienteAtual.setDataNascimento(ClientesView.txtCampos[2].getText());
                    clienteAtual.setEndereco(ClientesView.txtCampos[3].getText());
                    clienteAtual.setBairro(ClientesView.txtCampos[4].getText());
                    clienteAtual.setCidade(ClientesView.txtCampos[5].getText());
                    clienteAtual.setTelefone(ClientesView.txtCampos[6].getText());
                    clienteAtual.setEmail(ClientesView.txtCampos[7].getText());

                    ClientesDAO.alterarCliente(clienteAtual, tmpCpf);
                    JOptionPane.showMessageDialog(null, "Dados alterados.");
                    ClientesView.desbloquearCampos(false);
                    ClientesView.carregarDados(0, "");

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }

        } else if ((int) getValue("id") == 3) {
            try {
                String cpf = ClientesView.txtCampos[0].getText();

                ClientesDAO.alterarStatus(cpf, ClientesView.statusAtual);
                ClientesView.carregarDados(0, "");
                ClientesView.carregarCampos(ClientesDAO.consultarCliente(cpf));

                JOptionPane.showMessageDialog(null, "Status Alterado!");

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }

        }else if((int)getValue("id") == 4){
            String cpf = JOptionPane.showInputDialog
                            ("Informe o CPF do cliente:");
            
            try{
                ClientesVO dadosCliente = ClientesDAO.consultarCliente(cpf);
                
                if(dadosCliente == null){
                    JOptionPane.showMessageDialog(null,"Cliente não encontrado");
                }else{
                    ClientesView.carregarCampos(dadosCliente);
                }
            
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null,erro.getMessage());
            }
        }else if((int)getValue("id") == 5){
            try{
                String cpf = ClientesView.txtCampos[0].getText();
                
                int verif = JOptionPane.showConfirmDialog(
                            null, "Deseja realmente excluir " + 
                                   ClientesView.txtCampos[1].getText(),
                                   "Exclusão de Dados",
                                    JOptionPane.YES_NO_OPTION);
                
                if(verif == JOptionPane.YES_OPTION){
                    ClientesDAO.excluirCliente(cpf);
                    JOptionPane.showMessageDialog(null, "Cliente excluído");
                    ClientesView.desbloquearCampos(false);
                    ClientesView.limparCampos();
                    ClientesView.carregarDados(0,"");                    
                }
                    ClientesView.bbrExcluir.setEnabled(false);
                    ClientesView.mniExcluir.setEnabled(false);
                
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }
        }

    }

}
