package backend.fxmlBackend;

import backend.enums.Hazard;
import backend.serialization.JBP;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import backend.serialization.JOS;
import backend.serialization.SaveObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class TableController implements Initializable {

    /**
     * Menu Bar
     */
    @FXML AnchorPane canvas;
    @FXML private Button addBtn;
    @FXML private Button deleteBtn;
    @FXML private Button open;
    @FXML private Button save;

    /**
     * Cargo Table
     */
    @FXML private TableView cargoTable;
    @FXML private TableColumn typeCol;
    @FXML private TableColumn customerCol;
    @FXML private TableColumn positionCol;
    @FXML private TableColumn sizeCol;
    @FXML private TableColumn radioactiveCol;
    @FXML private TableColumn flammableCol;
    @FXML private TableColumn toxicCol;
    @FXML private TableColumn explosiveCol;
    @FXML private TableColumn propertiesCol;
    @FXML private MenuButton type;
    @FXML private MenuButton saveWith;


    @FXML private TextField customerName;
    @FXML private TextField position;
    @FXML private TextField size;
    @FXML private RadioButton pressurized;
    @FXML private RadioButton solid;
    @FXML private RadioButton fragile;
    @FXML private RadioButton radioactive;
    @FXML private RadioButton flammable;
    @FXML private RadioButton toxic;
    @FXML private RadioButton explosive;

    FXMLAdministration fxa;


    //darstellungslogic ab hier -----------




    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        fxa = new FXMLAdministration();

        disableAll();
        populateTable();
        bindAddButton();
        bindDeleteButton();
        bindSaveOpen();
    }

    public void populateTable(){
        typeCol.setCellValueFactory(new PropertyValueFactory<TableObject, String>("Type"));
        customerCol.setCellValueFactory(new PropertyValueFactory<TableObject, String>("Customer"));
        positionCol.setCellValueFactory(new PropertyValueFactory<TableObject, Integer>("Position"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<TableObject, Integer>("Size"));
        radioactiveCol.setCellValueFactory(new PropertyValueFactory<TableObject, Hazard>("radioactive"));
        flammableCol.setCellValueFactory(new PropertyValueFactory<TableObject, Hazard>("flammable"));
        toxicCol.setCellValueFactory(new PropertyValueFactory<TableObject, Hazard>("toxic"));
        explosiveCol.setCellValueFactory(new PropertyValueFactory<TableObject, Hazard>("explosive"));
        propertiesCol.setCellValueFactory(new PropertyValueFactory<TableObject, String>("Properties"));

        try {
            fxa.getAllServerData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cargoTable.setItems(fxa.tableData);
    }

    public void addCargoToList(ActionEvent actionEvent) throws IOException{
        int pos = Integer.parseInt(position.getText());
        int siz = Integer.parseInt(size.getText());
        SaveObject obj = new SaveObject(type.getText(), customerName.getText(), pos, siz, radioactive.isSelected(), flammable.isSelected(), toxic.isSelected(), explosive.isSelected(), pressurizedArmed() + solidArmed() + fragileArmed());
        fxa.addSingleServerData(obj);
        populateTable();

    }


    public void jbpSelected(ActionEvent actionEvent) {
        saveWith.setText("JBP");
    }
    public void josSelected(ActionEvent actionEvent) {
        saveWith.setText("JOS");
    }


    public void saveItem(ActionEvent actionEvent) throws IOException {
        switch(saveWith.getText()){
            case "JBP":
                JBP.save(fxa.tableObjectList2SaveObjectList());
                break;
            case "JOS":
                JOS.save(fxa.tableObjectList2SaveObjectList());
                break;
            default:
                System.out.println("Something went wrong!");
        }
        System.out.println(fxa.tableData);
        System.out.println("all saved");

    }

    public void openItem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        fxa.tableData.clear();
        System.out.println(fxa.tableData);
        fxa.deleteAllServerData();
        populateTable();



        switch(saveWith.getText()){
            case "JOS":

                System.out.println(saveWith.getText() +" loading...");

                JOS.load().forEach(saveObject -> {

                    try {
                        fxa.addSingleServerData(saveObject);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    populateTable();




                });

                System.out.println(fxa.tableData);

                break;
            case "JBP":
                System.out.println(saveWith.getText() +" loading...");

                JBP.load().forEach(saveObject -> {
                    try {
                        fxa.addSingleServerData(saveObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    populateTable();
                });

                System.out.println(fxa.tableData);
                break;
            default:
                System.out.println("Unable to load");
                break;
        }
        populateTable();
    }


    @FXML
    private void deleteRowFromTable(ActionEvent event){
        int index = cargoTable.getSelectionModel().getSelectedIndex();
        TableObject so = (TableObject) cargoTable.getItems().get(index);
        System.out.println("Print Customer Name of Deleted: " + so.getCustomer());
        cargoTable.getItems().removeAll(cargoTable.getSelectionModel().getSelectedItem());
        try {
            fxa.deleteSingleServerData(FXMLAdministration.tableObject2SaveObject(so));
            populateTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
        populateTable();
    }




    private void enableCommonFields(){
        customerName.setDisable(false);
        position.setDisable(false);
        size.setDisable(false);
        radioactive.setDisable(false);
        flammable.setDisable(false);
        toxic.setDisable(false);
        explosive.setDisable(false);
    }

    //src: https://stackoverflow.com/questions/23040531/how-to-disable-button-when-textfield-is-empty
    private void bindAddButton(){
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(customerName.textProperty(),
                        size.textProperty(),
                        position.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return (customerName.getText().isEmpty()
                        || size.getText().isEmpty()
                        || position.getText().isEmpty());
            }
        };
        addBtn.disableProperty().bind(bb);
    }

    private void bindDeleteButton(){
        deleteBtn.disableProperty().bind(Bindings.isEmpty(cargoTable.getSelectionModel().getSelectedItems()));
    }

    private void disableAll(){
        customerName.setDisable(true);
        customerName.setText("");
        position.setDisable(true);
        position.setText("");
        size.setDisable(true);
        size.setText("");
        radioactive.setDisable(true);
        radioactive.setSelected(false);
        flammable.setDisable(true);
        flammable.setSelected(false);
        toxic.setDisable(true);
        toxic.setSelected(false);
        explosive.setDisable(true);
        explosive.setSelected(false);
        pressurized.setDisable(true);
        pressurized.setSelected(false);
        solid.setDisable(true);
        solid.setSelected(false);
        fragile.setDisable(true);
        fragile.setSelected(false);
        textFieldtoInt(position);
        textFieldtoInt(size);
    }

    private void bindSaveOpen(){
        BooleanBinding booleanBind = saveWith.textProperty().isEqualTo("SaveAs");
        open.disableProperty().bind(booleanBind);
        save.disableProperty().bind(booleanBind);
    }

    /**
     * Type
     */
    public void typeBoxedSelected(ActionEvent actionEvent) {
        type.setText("Boxed");
        disableAll();
        enableCommonFields();
        fragile.setDisable(false);
    }
    public void typeDrySelected(ActionEvent actionEvent) {
        type.setText("Dry");
        disableAll();
        enableCommonFields();
        solid.setDisable(false);
    }

    public void typeLiquidSelected(ActionEvent actionEvent) {
        type.setText("Liquid");
        disableAll();
        enableCommonFields();
        pressurized.setDisable(false);
    }

    public void typeDryBoxedSelected(ActionEvent actionEvent) {
        type.setText("Dry | Boxed");
        disableAll();
        enableCommonFields();
        solid.setDisable(false);
        fragile.setDisable(false);
    }

    public void typeDryLiquidSelected(ActionEvent actionEvent) {
        type.setText("Dry | Liquid");
        disableAll();
        enableCommonFields();
        pressurized.setDisable(false);
        solid.setDisable(false);
    }

    public void typeLiquidBoxedSelected(ActionEvent actionEvent) {
        type.setText("Liquid | Boxed");
        disableAll();
        enableCommonFields();
        pressurized.setDisable(false);
        fragile.setDisable(false);
    }

    public void typeLiquidDryBoxedSelected(ActionEvent actionEvent) {
        type.setText("Liquid | Boxed | Dry");
        disableAll();
        enableCommonFields();
        pressurized.setDisable(false);
        solid.setDisable(false);
        fragile.setDisable(false);
    }

    private String solidArmed(){
        if(solid.isSelected()) return "S";
        else return "-";
    }
    private String fragileArmed(){
        if(fragile.isSelected()) return "F";
        else return "-";
    }
    private String pressurizedArmed(){
        if(pressurized.isSelected()) return "P";
        else return "-";
    }

    //src https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
    private void textFieldtoInt(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}













