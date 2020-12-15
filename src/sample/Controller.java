package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import org.ini4j.*;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
    @FXML
    Label warningLabel;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    ListView<String> listView;


    @FXML
    Button exitButton;
    @FXML
    Button clearButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public boolean check(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    @FXML
    Button calculateButton;
    ObservableList<String> listViewList = FXCollections.observableArrayList();

    public void addToListView(String group) {
        listViewList.add(group);
        listView.setItems(listViewList);
    }

    public static String getList() {
        String strList = "";
        for (String str : olist)
            strList += str + ",";
        return strList.substring(0, strList.length() - 1);
    }

    static ObservableList<String> olist;

    @FXML
    void initialize() throws IOException {
        olist = FXCollections.observableArrayList();
        Ini aRead = new Ini();

        aRead.load(new FileReader("src/sample/settings.ini"));
        String[] list = aRead.get("numbers", "list", String.class).split(",");
        for (String str :
                list)
            olist.add(str);
        comboBox.setItems(olist);

        comboBox.setOnKeyPressed(ke -> {

            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                if (check(comboBox.getValue())) {
                    olist.add(comboBox.getValue());
                    comboBox.setItems(olist);
                    warningLabel.setText("");


                } else {
                    warningLabel.setText("invalid input");
                }

            }
        });


        calculateButton.setOnAction(ActionEvent -> {
            ObservableList<String> items = comboBox.getItems();
            for (String item : items) {
                String[] evenGroup = item.split(" ");
                for (String str : evenGroup) {
                    if (str.length() % 2 == 0) {
                        addToListView(str);
                    }
                }
            }
        });
        clearButton.setOnAction(ActionEvent -> {
            ObservableList<String> listViewList = FXCollections.observableArrayList();
            listView.setItems(listViewList);
        });
        /*comboBox.setOnAction(ActionEvent->{
            addToListView(comboBox.getValue());
        });*/
        exitButton.setOnAction(ActionEvent -> {
            handleCloseButtonAction();
        });
    }
}
