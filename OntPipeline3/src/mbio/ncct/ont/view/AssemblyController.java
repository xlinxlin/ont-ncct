package mbio.ncct.ont.view;

import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import mbio.ncct.ont.MainApp;
import mbio.ncct.ont.model.AssemblyModel;

/**
 * This is the assembly controller class for pipeline assembly view.
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class AssemblyController {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(AssemblyController.class);
  
  /** Initializes AssemblyModel. */
  public AssemblyModel am = new AssemblyModel();
  
  /** Initializes window. */
  private Window mainApp;
  
  /** Initializes check box for assembly. */
  @FXML
  private CheckBox cAssembly;
  
  /** Initializes check box for assembly mode. */
  @FXML
  private ChoiceBox<String> cbMode = new ChoiceBox<String>() ;
  
  /** Initializes check box for assembly method. */
  @FXML
  private ChoiceBox<String> cbMethod = new ChoiceBox<String>() ;
  
  /** Initializes assembly group.  */
  @FXML
  private Group gpAssembly;
  
  /**
   * Initializes the controller of pipeline overview.
   */
  @FXML
  private void initialize()  { 
    cAssembly.setSelected(am.getIfAssembly());
    
    ArrayList<String> alMode = new ArrayList<String>();
    alMode.add("conservative");
    alMode.add("normal");
    alMode.add("bold");
    ObservableList<String> olMode = FXCollections.observableArrayList(alMode);
    cbMode.setItems(olMode);
    
    ArrayList<String> alMethod = new ArrayList<String>();
    alMethod.add("Long-read-only assembly");
    alMethod.add("Hybrid assembly");
    ObservableList<String> olMethod = FXCollections.observableArrayList(alMethod);
    cbMethod.setItems(olMethod);
    
    cbMode.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
      am.setMode(newValue);
    });
    
    cbMethod.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
      am.setMethod(newValue);
    });
    
    cAssembly.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(cAssembly.isSelected()) {
        gpAssembly.setDisable(false);
        am.setIfAssembly(true);
      } else {
        gpAssembly.setDisable(true);
        am.setIfAssembly(false);
      }
    });
  }
  
  /**
   * Called when advanced assembly button is clicked.
   */
  @FXML
  private void handleAdvancedAssembly() {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/AdvancedAssemblyView.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Advanced Assembly Setting");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(this.mainApp);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the controller.
      AdvancedAssemblyController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setIfVcf(am.getIfVcf());
     
      // Show the dialog and wait until the user closes it.
      dialogStage.showAndWait();
      if (controller.isOK == 1) {
        am.setIfVcf(controller.cVcf.isSelected()); 
      }
    } catch (Exception e) {
      logger.error("Can not load advanced assembly view. " + e);
    }
  }
}
