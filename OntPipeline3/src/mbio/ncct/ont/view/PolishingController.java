package mbio.ncct.ont.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import mbio.ncct.ont.MainApp;
import mbio.ncct.ont.model.PolishingModel;
import mbio.ncct.ont.util.BashUtil;
import mbio.ncct.ont.util.PipelineUtil;

/**
 * This is the controller for polishing setting view.
 *
 * @author Yan Zhou
 * created on 2019/06/17
 */
public class PolishingController {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(PolishingController.class);
  
  /** Initializes a BashUtil object. */
  private BashUtil bUtil = new BashUtil();
  
  /** Initializes a PipelineUtil object.  */
  private PipelineUtil pUtil = new PipelineUtil();;
  
  /** Initializes polishing group.  */
  @FXML
  private Group gpPolishing;
  
  /** Initializes check box for polishing. */
  @FXML
  private CheckBox cPolishing;
  
  /** Initializes PolishingModel.  */
  public PolishingModel pm = new PolishingModel();

  /** Window mainApp.  */
  private Window mainApp;
  
  /** The choice box for Medaka models. */
  @FXML
  public ChoiceBox<String> cbMedakaModel;
  
  /**
   * Initializes the controller of polishing setting view.
   */
  @FXML
  private void initialize()  { 
    cPolishing.setSelected(true);
    
    cPolishing.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(cPolishing.isSelected()) {
        gpPolishing.setDisable(false);
        pm.setIfPolishing(true);
      } else {
        gpPolishing.setDisable(true);
        pm.setIfPolishing(false);
      }
    });
    
    ObservableList<String> olMedakaModels = FXCollections.observableArrayList(bUtil.getMedakaModels());
    cbMedakaModel.setItems(olMedakaModels);
    if(olMedakaModels.contains("r941_min_high")) {
      cbMedakaModel.getSelectionModel().select("r941_min_high");
    } else {
      cbMedakaModel.getSelectionModel().selectFirst();
      pm.setMedakaModel(cbMedakaModel.getSelectionModel().getSelectedItem());
    }
  }
  
  /**
   * Called when advanced polishing button is clicked.
   */
  @FXML
  private void handleAdvancedPolishing() {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/AdvancedPolishingView.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Advanced Polishing Setting");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(this.mainApp);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the controller.
      AdvancedPolishingController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setPtimes(pm.getPtimes());
      controller.setBuscoData(pm.getBuscoData());
      controller.setIfBusco(pm.getIfBusco());
     
      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();
      if (controller.isOK == 1) {
        pm.setPtimes(controller.cbPtimes.getValue()); 
        pm.setBuscoData(controller.cbBuscoData.getValue());
        pm.setIfBusco(controller.cBusco.isSelected());
      }
    } catch (Exception e) {
      logger.error("Can not load advanced polishing view. " + e);
    }
  }
  
  /**
   * Shows the hint of Assembly Mode setting.
   */
  @FXML
  private void showMedakaModelHint() {
    pUtil.createAlertDialog(AlertType.INFORMATION, "Medaka model", "This option is only for Nanopore reads only polishing method"
        + " (without Illumina reads).\n\n"
        + "min: abbreviation for MinION.\n"
        + "prom: abbreviation for PromethION.\n"
        + "fast: if you basecalled the data with Guppy fast mode.\n"
        + "high: if you basecalled the data with Guppy high-accuracy mode.");
  }
}