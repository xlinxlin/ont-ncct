package mbio.ncct.ont.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import mbio.ncct.ont.MainApp;
import mbio.ncct.ont.model.BaseCallingModel;
import mbio.ncct.ont.util.BashUtil;
import mbio.ncct.ont.util.PipelineUtil;
import mbio.ncct.ont.view.AdvancedBasecallingController;

/**
 * This is the base calling controller class for pipeline base calling view.
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class BaseCallingController {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(BaseCallingController.class);
  
  /** Initializes check box for base calling. */
  @FXML
  private CheckBox cBasecalling;
  
  /** Initializes choice box for flowcell ID. */
  @FXML
  private ChoiceBox<String> cbFlowcellId = new ChoiceBox<String>();
  
  /** Initializes base calling conrols group. */
  @FXML
  private Group gpBaseCalling = new Group();
  
  /** Initializes check box for kit number. */
  @FXML
  private ChoiceBox<String> cbKitNumber = new ChoiceBox<String>();
  
  /** Initializes a BashUtil object. */
  private BashUtil bUtil = new BashUtil();
  
  /** Initializes base calling model. */
  public BaseCallingModel bcm = new BaseCallingModel();

  /** Window mainApp. */
  private Window mainApp;
  
  /** Initializes a PipelineUtil object. */
  private PipelineUtil pUtil = new PipelineUtil();
  
  /**
   * Is called by the main application to give a reference back to itself.
   * @param mainApp Main app.
   */
  public void setMainApp(MainApp mainApp) {
    //this.mainApp = mainApp;
  }
  
  /**
   * Initializes the controller of base calling setting overview.
   */
  @FXML
  private void initialize()  { 
    cBasecalling.setSelected(bcm.getIfBasecalling());
    
    ObservableList<String> olFlowcellIds = FXCollections.observableArrayList(bUtil.getFlowcellIds());
    cbFlowcellId.setItems(olFlowcellIds);
    if(olFlowcellIds.contains("FLO-PRO001")) {
      cbFlowcellId.getSelectionModel().select("FLO-PRO001");
    } else {
      cbFlowcellId.getSelectionModel().selectFirst();
      bcm.setFlowcellId(cbFlowcellId.getSelectionModel().getSelectedItem());
    }
  
    ObservableList<String> olKitNumbers = FXCollections.observableArrayList(bUtil.getKitNumbers());
    cbKitNumber.setItems(olKitNumbers);
    if(olKitNumbers.contains("SQK-LSK109")) {
      cbKitNumber.getSelectionModel().select("SQK-LSK109");
    } else {
      cbKitNumber.getSelectionModel().selectFirst();
      bcm.setKitNumber(cbKitNumber.getSelectionModel().getSelectedItem());
    }
    
    cBasecalling.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(cBasecalling.isSelected()) {
        gpBaseCalling.setDisable(false);
        bcm.setIfBasecalling(true);
      } else {
        gpBaseCalling.setDisable(true);
        bcm.setIfBasecalling(false);
      }
    });
    
    cbFlowcellId.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
      bcm.setFlowcellId(newValue);
    });
    
    cbKitNumber.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
      bcm.setKitNumber(newValue);
    });
    
  }
  
  /**
   * Called when advanced basecalling button is clicked.
   */
  @FXML
  private void handleAdvancedBasecalling() {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/AdvancedBasecallingView.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Advanced Basecalling Setting");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(this.mainApp);
      //dialogStage.initOwner();
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the controller.
      AdvancedBasecallingController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setGuppyMode(bcm.getGuppyMode());
      controller.setDevice(bcm.getDevice());
     
      // Show the dialog and wait until the user closes it.
      dialogStage.showAndWait();
      if(controller.isOK == 1) {
        bcm.setGuppyMode(controller.cbGuppyMode.getValue());
        bcm.setDevice(controller.cbDevice.getValue());
      }
    } catch (Exception e) {
      logger.error("Can not load advanced base calling view. " + e);
    }
  }
  
  /**
   * Shows the hint of Flowcell ID setting.
   */
  @FXML
  private void showFlowcellIdHint() {
    pUtil.createAlertDialog(AlertType.INFORMATION, "Flowcell ID", "Choose a Flowcell ID from the select list.");
  }
  
  /**
   * Shows the hint of Kit Number setting.
   */
  @FXML
  private void showKitNumberHint() {
    pUtil.createAlertDialog(AlertType.INFORMATION, "Kit Number", "Choose a Kit Number from the select list.");
  }
}