package com.example.sistemadegerenciamento.controller;

import com.example.sistemadegerenciamento.DAO.DAO;
import com.example.sistemadegerenciamento.HelloApplication;
import com.example.sistemadegerenciamento.models.Cliente;
import com.example.sistemadegerenciamento.models.Ordem;
import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.sistemadegerenciamento.DAO.ordem.OrdemDAOArquivo;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class inicialController {

    @FXML
    private Button btnAbrirOrdem;

    @FXML
    private Button btnCriarOrdemDeServico;

    @FXML
    private Button btnOrdemDeCompra;

    @FXML
    private Button btnPegarOrdemServico;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnSceneClientes;
    @FXML
    private Button btnSceneEstoque;
    @FXML
    private Button btnSceneHome;
    @FXML
    private Button btnSceneTecnicos;
    @FXML
    private TableView<Ordem> tabelaOrdensEmAberto;
    @FXML
    private TableView<Ordem> tabelaOrdensEmEspera;

    private ObservableList<Ordem> ordensEmAbertoData;

    private ObservableList<Ordem> ordensEmEsperaData;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        //Em Aberto
        DAO.getOrdem().atualizaColecaoDoArquivoOrdensAbertas(DAO.getOrdemDAOArquivo().lerArquivoOrdensEmAberto());

        this.ordensEmAbertoData = FXCollections.observableArrayList();
        this.ordensEmAbertoData.addAll(DAO.getOrdem().findManyEmAberto());

        //Cria a coluna para usar na tabela, de maneira manual.
        TableColumn coluna1EmAberto = new TableColumn("ID");
        TableColumn coluna2EmAberto = new TableColumn("NOME DO CLIENTE");
        TableColumn coluna3EmAberto = new TableColumn("SERVIÇOS");

        coluna1EmAberto.setCellValueFactory(new PropertyValueFactory<Ordem, String>("ordemID"));
        coluna2EmAberto.setCellValueFactory(new PropertyValueFactory<Ordem, String>("nomeCliente"));
        coluna3EmAberto.setCellValueFactory(new PropertyValueFactory<Ordem, String>("servicosEmString"));

        this.tabelaOrdensEmAberto.getColumns().addAll(coluna1EmAberto, coluna2EmAberto, coluna3EmAberto);
        this.tabelaOrdensEmAberto.setItems(ordensEmAbertoData);

        //Em espera
        DAO.getOrdem().atualizaColecaoDoArquivoOrdensEmEspera(DAO.getOrdemDAOArquivo().lerArquivoOrdensEmEspera());

        this.ordensEmEsperaData = FXCollections.observableArrayList();
        this.ordensEmEsperaData.addAll(DAO.getOrdem().findManyEmEspera());

        //Cria a coluna para usar na tabela, de maneira manual.
        TableColumn coluna1EmEspera = new TableColumn("ID");
        TableColumn coluna2EmEspera = new TableColumn("NOME DO CLIENTE");
        TableColumn coluna3EmEspera = new TableColumn("SERVIÇOS");

        coluna1EmEspera.setCellValueFactory(new PropertyValueFactory<Ordem, String>("ordemID"));
        coluna2EmEspera.setCellValueFactory(new PropertyValueFactory<Ordem, String>("nomeCliente"));
        coluna3EmEspera.setCellValueFactory(new PropertyValueFactory<Ordem, String>("servicosEmString"));

        this.tabelaOrdensEmEspera.getColumns().addAll(coluna1EmEspera, coluna2EmEspera, coluna3EmEspera);
        this.tabelaOrdensEmEspera.setItems(ordensEmEsperaData);
    }

    @FXML
    void btnCriaOrdemDeServico(ActionEvent event) {
        HelloApplication.telaScreen("ordens");

    }

    @FXML
    void btnFazOrdemDeCompra(ActionEvent event) {
        HelloApplication.telaScreen("estoque");

    }

    @FXML
    void btnPegaOrdemServico(ActionEvent event) {
        Dialog dialog = new Dialog();
        dialog.setTitle("PEGAR ORDEM DE SERVIÇO");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.show();
    }

    @FXML
    void btnSalvaDados(ActionEvent event) throws IOException {
        DAO.getClienteDAOArquivo().salvarArquivo();
        DAO.getEstoqueDAOArquivo().salvarArquivo();
        DAO.getTecnicoDAOArquivo().salvarArquivo();
        DAO.getOrdemDAOArquivo().salvarArquivoOrdensCanceladas();
        DAO.getOrdemDAOArquivo().salvarArquivoOrdensEmAberto();
        DAO.getOrdemDAOArquivo().salvarArquivoOrdensEmEspera();
        DAO.getOrdemDAOArquivo().salvarArquivoOrdensFinalizadas();
    }

    @FXML
    void btnSceneClientesAction(ActionEvent event) {
        HelloApplication.telaScreen("cliente");
    }

    @FXML
    void btnSceneEstoqueAction(ActionEvent event) {
        HelloApplication.telaScreen("estoque");

    }

    @FXML
    void btnSceneHomeAction(ActionEvent event) {
        HelloApplication.telaScreen("inicial");
    }

    @FXML
    void btnSceneTecnicosAction(ActionEvent event) {
        HelloApplication.telaScreen("tecnico");

    }
    @FXML
    void btnSceneOrdensAction(ActionEvent event) {
        HelloApplication.telaScreen("ordens");
    }

    @FXML
    void btnAbreOrdem(ActionEvent event) {
        int selecionadoTabelaEmEsperaIndice = this.tabelaOrdensEmEspera.getSelectionModel().getSelectedIndex();
        int selecionadoTabelaEmAbertoIndice = this.tabelaOrdensEmAberto.getSelectionModel().getSelectedIndex();
        if (selecionadoTabelaEmAbertoIndice>=0) {
            Ordem selecionadoTabela = this.tabelaOrdensEmAberto.getSelectionModel().getSelectedItem();
            int clienteID = selecionadoTabela.getClienteID();
            servicosDialogController.ordemAbertaNoMomento = selecionadoTabela;
            System.out.println("1) btnAbreOrdem");
            HelloApplication.telaScreen("servicosDialog");
        } else if (selecionadoTabelaEmEsperaIndice>=0){
            Ordem selecionadoTabela = this.tabelaOrdensEmEspera.getSelectionModel().getSelectedItem();
            int clienteID = selecionadoTabela.getClienteID();
            servicosDialogController.ordemAbertaNoMomento = selecionadoTabela;
            HelloApplication.telaScreen("servicosDialog");
        }
    }
}
