package mbio.ncct.ont.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import mbio.ncct.ont.MainApp;
import mbio.ncct.ont.model.PolishingModel;

/**
 * This is the controller for polishing setting view.
 *
 * @author Yan Zhou
 * created on 2019/06/17
 */
public class PolishingController {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(PolishingController.class);
  
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
}