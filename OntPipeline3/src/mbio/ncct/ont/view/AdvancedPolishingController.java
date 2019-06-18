package mbio.ncct.ont.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This is the controller of the advanced polishing settings.
 *
 * @author Yan Zhou
 * created on 2019/05/14
 */
public class AdvancedPolishingController {
  
  /** The dialog stage for advanced polishing setting. */
  private Stage dialogStage;
  
  /** The choice box for polishing times. */
  @FXML
  public ChoiceBox<String> cbPtimes;
  
  /** The choice box for BUSCO database. */
  @FXML
  public ChoiceBox<String> cbBuscoData;
  
  /** The check box for if BUSCO will be used. */
  @FXML
  public CheckBox cBusco;
  
  /** The label for BUSCO databse. */
  @FXML
  public Label lbDatabase;
  
  /** Signal for if OK button is clicked. */
  public int isOK = 0;
  
  /**
   * Initializes the controller of advanced polishing settings.
   */
  @FXML
  private void initialize() {
    cbBuscoData.setDisable(true);
    lbDatabase.setDisable(true);
    cBusco.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(cBusco.isSelected()) {
        cbBuscoData.setDisable(false);
        lbDatabase.setDisable(false);
      } else {
        cbBuscoData.setDisable(true);
        lbDatabase.setDisable(true);
      }
    });
  }
  
  /**
   * Set the advanced polishing setting dialog stage.
   * @param dialogStage advanced polishing setting dialog stage
   */
  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }
  
  /**
   * If OK button is clicked, return 1, close the advanced polishing setting dialog.
   */
  @FXML
  private void OK() {
    isOK = 1;
    dialogStage.close();
  }
  
  /**
   * If Cancel button is clicked, return 0, close the advanced polishing setting dialog.
   */
  @FXML
  private void cancel() {
    dialogStage.close();
  }
  
  /**
   * Initializes and set the polishing times.
   * @param pTimes the initial value of polishing times
   */
  public void setPtimes(String pTimes) {
    ArrayList<String> alTimes = new ArrayList<String>();
    alTimes.add("1");
    alTimes.add("2");
    alTimes.add("3");
    alTimes.add("4");
    ObservableList<String> olTimes = FXCollections.observableArrayList(alTimes);
    cbPtimes.setItems(olTimes);
    cbPtimes.setValue(pTimes);
  }
  
  /**
   * Initializes and set the BUSCO database.
   * @param buscoData the initial value of BUSCO database
   */
  public void setBuscoData (String buscoData) {
    ArrayList<String> alDatabase = new ArrayList<String>();
    alDatabase.add("bacteria");
    alDatabase.add("proteobacteria");
    alDatabase.add("rhizobiales");
    alDatabase.add("beta_proteobacteria");
    alDatabase.add("gamma_proteobacteria");
    alDatabase.add("enterobacteriales");
    alDatabase.add("delta_epsilon_proteobacteria");
    alDatabase.add("actinobacteria");
    alDatabase.add("cyanobacteria");
    alDatabase.add("firmicutes");
    alDatabase.add("clostridia");
    alDatabase.add("lactobacillales");
    alDatabase.add("bacillales");
    alDatabase.add("enterobacteriales");
    alDatabase.add("bacteroidetes");
    alDatabase.add("spirochaetes");
    alDatabase.add("tenericutes");
    ObservableList<String> olDatabase = FXCollections.observableArrayList(alDatabase);
    cbBuscoData.setItems(olDatabase);
    cbBuscoData.setValue(buscoData);
  }
  
  /**
   * Initializes and set if BUSCO check will be used or not.
   * @param ifBusco true if BUSCO check will be used
   */
  public void setIfBusco (boolean ifBusco) {
    cBusco.setSelected(ifBusco);
  }
}
