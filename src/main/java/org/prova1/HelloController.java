package org.prova1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    @FXML private TableView<Contato> tableView;
    @FXML private TableColumn<Contato, Integer> colId;
    @FXML private TableColumn<Contato, String> colNome;
    @FXML private TableColumn<Contato, String> colTelefone;
    @FXML private TableColumn<Contato, String> colEmail;

    private ObservableList<Contato> contatosObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.setItems(contatosObservable);

        atualizarTabela();
    }

    private void atualizarTabela() {
        ContatoDAO dao = new ContatoDAO();
        List<Contato> lista = dao.listar();

        contatosObservable.clear();
        contatosObservable.addAll(lista);
    }

    //AÇÕES DOS BOTÕES

    @FXML
    private void adicionarContato() {
        Contato c = new Contato(
                txtNome.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
        );

        new ContatoDAO().inserir(c);
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void editarContato() {
        Contato selecionado = tableView.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        selecionado.setNome(txtNome.getText());
        selecionado.setTelefone(txtTelefone.getText());
        selecionado.setEmail(txtEmail.getText());

        new ContatoDAO().atualizar(selecionado);
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void excluirContato() {
        Contato selecionado = tableView.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        new ContatoDAO().excluir(selecionado.getId());
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
    }
}
