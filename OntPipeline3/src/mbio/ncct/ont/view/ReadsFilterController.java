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
import mbio.ncct.ont.model.ReadsFilterModel;

/**
 * This is the controller for reads filter setting view.
 *
 * @author Yan Zhou
 * created on 2019/06/17
 */
public class ReadsFilterController {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(ReadsFilterController.class);
  
  /** Initializes reads filter group.  */
  @FXML
  private Group gpReadsFilter;
  
  /** Initializes check box for reads filter. */
  @FXML
  private CheckBox cReadsFilter;
  
  /** Initializes ReadsFilterModel. */
  public ReadsFilterModel rfm = new ReadsFilterModel();
  
  /** Window mainApp. */
  private Window mainApp;
  
  /**
   * Initializes the controller of reads filter setting view.
   */
  @FXML
  private void initialize()  { 
    cReadsFilter.setSelected(rfm.getIfReadsFilter());
    
    cReadsFilter.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(cReadsFilter.isSelected()) {
        gpReadsFilter.setDisable(false);
        rfm.setIfReadsFilter(true);
      } else {
        gpReadsFilter.setDisable(true);
        rfm.setIfReadsFilter(false);
      }
    });
  }
  
  /**
   * Called when advanced reads filter button is clicked.
   */
  @FXML
  private void handleAdvancedReadsFilter() {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/AdvancedReadsFilterView.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Advanced Reads Filter Setting");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      //dialogStage.initOwner(mainApp.primaryStage);
      dialogStage.initOwner(this.mainApp);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the controller.
      AdvancedReadsFilterController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setReadScore(rfm.getReadScore());
      controller.setReadLength(rfm.getReadLength());
      controller.setHeadCrop(rfm.getHeadCrop());
      controller.setIfAdapterTrimming(rfm.getIfAdapterTrimming());
      controller.setIfSplitting(rfm.getIfNoSplit());
   
      // Show the dialog and wait until the user closes it.
      dialogStage.showAndWait();
      if (controller.isOK == 1) {
        rfm.setReadScore(controller.tfReadScore.getText());
        rfm.setReadLength(controller.tfReadLength.getText());
        rfm.setHeadCrop(controller.tfHeadCrop.getText());
        rfm.setIfAdapterTrimming(controller.cAdapterTrimming.isSelected());
        rfm.setIfNoSplit(controller.cSplitting.isSelected()); 
      }
    } catch (Exception e) {
      logger.error("Can not load advanced reads filter view. " + e);
    }
  }
}