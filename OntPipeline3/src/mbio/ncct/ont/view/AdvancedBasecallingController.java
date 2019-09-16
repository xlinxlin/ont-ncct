package mbio.ncct.ont.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * This is the controller of the advanced base calling settings.
 *
 * @author Yan Zhou
 * created on 2019/05/14
 */
public class AdvancedBasecallingController {
  
  /** The choice box for Guppy mode. */
  @FXML
  public ChoiceBox<String> cbGuppyMode = new ChoiceBox<String>();
  
  /** The choice box for ONT device. */
  @FXML
  public ChoiceBox<String> cbDevice = new ChoiceBox<String>();
   
  /** The advanced basecalling dialog stage. */
  private Stage dialogStage;
  
  /** Signal for if OK is clicked. */
  public int isOK = 0;
  
  /** Initializes device group.  */
  @FXML
  private Group gpDevice;
  
  /**
   * Initializes the controller of advanced base calling settings.
   */
  @FXML
  private void initialize() {
    ArrayList<String> alGuppyMode = new ArrayList<String>();
    alGuppyMode.add("fast");
    alGuppyMode.add("high-accuracy");
    ObservableList<String> olGuppyMode = FXCollections.observableArrayList(alGuppyMode);
    cbGuppyMode.setItems(olGuppyMode);
    
    ArrayList<String> alDevice = new ArrayList<String>();
    alDevice.add("MinION/GridION/MinIT");
    alDevice.add("PromethION");
    ObservableList<String> olDevice = FXCollections.observableArrayList(alDevice);
    cbDevice.setItems(olDevice);
    
    cbGuppyMode.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
      if(newValue.equals("fast")) {  //"fast" mode doesn't care about device. 
        //cbDevice.setDisable(true);
        gpDevice.setDisable(true);
      } else {
        //cbDevice.setDisable(false);
        gpDevice.setDisable(false);
      }
    });
  }
  
  /**
   * Sets the dialog stage for advanced base calling setting.
   * @param dialogStage dialog stage.
   */
  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }
  
  /**
   * Called if OK button is clicked, set isOK to 1 and close the dialog.
   */
  @FXML
  private void OK() {
    isOK = 1;
    dialogStage.close();
  }
  
  /**
   * Called if Cancel button is clicked, set isOK to 0 and close the dialog.
   */
  @FXML
  private void Cancel() {
    dialogStage.close();
  }
  
  /**
   * Sets Guppy mode.
   * @param guppyMode String of Guppy mode.
   */
  public void setGuppyMode(String guppyMode) {
    cbGuppyMode.setValue(guppyMode);
  }
  
  /**
  * Sets ONT device.
  * @param device String of ONT device.
  */
  public void setDevice(String device) {
    cbDevice.setValue(device);
  }
}
