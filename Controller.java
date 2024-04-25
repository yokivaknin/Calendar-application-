package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;
import java.time.Month;
import java.util.HashMap;

public class Controller {

    //comboboxes
    @FXML
    private ComboBox<Integer> dayChoice;

    @FXML
    private ComboBox<String> monthChoice;

    @FXML
    private ComboBox<Integer> yearChoice;

    //text location
    @FXML
    private TextField reminderText;

    //The VBox that contain everything.
    @FXML
    private VBox VBoxRoot;

    //The HashMap that save the text data for every date.
    private HashMap <Date,String>  calendarPages;


    public void initialize(){
        setComboboxes();
        calendarPages = new HashMap<>();

        //user chooses file to lode to the HashMap.
        lodeFromFile();
    }

    //The function sets the comboBoxes according to required values.
    public void setComboboxes(){

        //Setting days combobox.
        Integer [] days = new Integer[31];
        for (int i = 0; i<days.length; i++){
            days[i] = i+1;
        }
        ObservableList<Integer> daysItems = FXCollections.observableArrayList(days);
        dayChoice.setItems(daysItems);
        dayChoice.getSelectionModel().selectFirst();

        //Setting month combobox.
        ObservableList<String> monthItems =
                FXCollections.observableArrayList("January", "February", "March",
                        "April", "May", "June",
                        "July", "August", "September",
                        "October", "November", "December");
        monthChoice.setItems(monthItems);
        monthChoice.getSelectionModel().selectFirst();

        //Setting years combobox.
        Integer [] years = new Integer[77];
        for (int i = 0; i<years.length; i++){
            years[i] = i+2023;
        }
        ObservableList<Integer> yearsItems = FXCollections.observableArrayList(years);
        yearChoice.setItems(yearsItems);
        yearChoice.getSelectionModel().selectFirst();
    }

    @FXML
    void onGetButtonPress(ActionEvent event) {

        //Getting the integer value of the month.
        Month month = Month.valueOf(monthChoice.getValue().toUpperCase());
        Date newDate = new Date(dayChoice.getValue(), month.getValue() ,yearChoice.getValue());

        //Inserting the data to the HashMap.
        reminderText.setText(calendarPages.get(newDate));
    }

    @FXML
    void onSaveButtonPress(ActionEvent event) {

        //Getting the integer value of the month.
        Month month = Month.valueOf(monthChoice.getValue().toUpperCase());
        Date newDate = new Date(dayChoice.getValue(), month.getValue() ,yearChoice.getValue());

        //Showing the relevant text data.
        calendarPages.put(newDate,reminderText.getText());

        //Waiting for close event to update the hashmap.
        closingEvent();
    }

    private void closingEvent(){
        Stage st = (Stage)VBoxRoot.getScene().getWindow();
        st.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event1 ->{
            saveToFile();
        });
    }

    //Save the HashMap Object to file chosen by the user.
    private void saveToFile(){
        File file = getFile();
        if(file != null){
            FileOutputStream FO;

            try {
                FO = new FileOutputStream(file);
                ObjectOutputStream ous = new ObjectOutputStream(FO);

                ous.writeObject(calendarPages);
                ous.close();
                FO.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //Lode the HashMap Object from file chosen by the user.
    private void lodeFromFile(){
        File file = getFile();
        if(file != null && file.length() != 0){
            FileInputStream FI;
            try {
                FI = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(FI);
                calendarPages = (HashMap<Date, String>)ois.readObject();
                ois.close();
                FI.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    //gets back File chosen by the user.
    private File getFile(){
        FileChooser FC = new FileChooser();
        FC.setTitle("Choose file to lode: ");
        FC.setInitialDirectory(new File("."));
        File file = FC.showOpenDialog(null);
        return file;
    }
}
