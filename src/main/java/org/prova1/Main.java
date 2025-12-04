package org.prova1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.prova1.dao.ContatoDAO;
import org.prova1.model.Contato;

public class Main extends Application {

    private TextField txtNome;
    private TextField txtTelefone;
    private TextField txtEmail;

    private TableView<Contato> tableView;
    private ObservableList<Contato> contatosObservable;

    private ContatoDAO contatoDAO = new ContatoDAO();

    @Override
    public void start(Stage primaryStage) {
        // Campos de texto
        txtNome = new TextField();
        txtTelefone = new TextField();
        txtEmail = new TextField();

        txtNome.setPromptText("Nome");
        txtTelefone.setPromptText("Telefone");
        txtEmail.setPromptText("Email");

        // Layout dos campos
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);

        form.add(new Label("Telefone:"), 0, 1);
        form.add(txtTelefone, 1, 1);

        form.add(new Label("Email:"), 0, 2);
        form.add(txtEmail, 1, 2);

        // TableView
        tableView = new TableView<>();
        TableColumn<Contato, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Contato, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Contato, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Contato, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.getColumns().addAll(colId, colNome, colTelefone, colEmail);

        contatosObservable = FXCollections.observableArrayList();
        tableView.setItems(contatosObservable);

        // Carrega dados iniciais
        carregarContatos();

        // Ao selecionar um contato na tabela, preencher os campos
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtNome.setText(newSel.getNome());
                txtTelefone.setText(newSel.getTelefone());
                txtEmail.setText(newSel.getEmail());
            }
        });

        // Botões
        Button btnAdicionar = new Button("Adicionar Contato");
        Button btnEditar = new Button("Editar Contato");
        Button btnExcluir = new Button("Excluir Contato");
        Button btnLimpar = new Button("Limpar");

        btnAdicionar.setOnAction(e -> adicionarContato());
        btnEditar.setOnAction(e -> editarContato());
        btnExcluir.setOnAction(e -> excluirContato());
        btnLimpar.setOnAction(e -> limparCampos());

        HBox botoes = new HBox(10, btnAdicionar, btnEditar, btnExcluir, btnLimpar);
        botoes.setPadding(new Insets(10));

        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(form);
        root.setCenter(tableView);
        root.setBottom(botoes);

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Agenda de Contatos");
        primaryStage.show();
    }

    private void carregarContatos() {
        contatosObservable.setAll(contatoDAO.listar());


    }

    private void adicionarContato() {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();

        if (nome == null || nome.isBlank()) {
            mostrarAlerta("Validação", "Nome é obrigatório");
            return;
        }

        Contato novo = new Contato(nome, telefone, email);
        contatoDAO.inserir(novo);
        carregarContatos();
        limparCampos();
    }

    private void editarContato() {
        Contato selecionado = tableView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Edição", "Selecione um contato para editar.");
            return;
        }

        selecionado.setNome(txtNome.getText());
        selecionado.setTelefone(txtTelefone.getText());
        selecionado.setEmail(txtEmail.getText());

        contatoDAO.atualizar(selecionado);
        carregarContatos();
    }

    private void excluirContato() {
        Contato selecionado = tableView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Exclusão", "Selecione um contato para excluir.");
            return;
        }

        contatoDAO.excluir(selecionado.getId());
        carregarContatos();
        limparCampos();
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
