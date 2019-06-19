package mbio.ncct.ont.view;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import mbio.ncct.ont.model.DemultiplexingModel;
import mbio.ncct.ont.util.BashUtil;
import mbio.ncct.ont.util.PipelineUtil;

/**
 * This is the demultiplexing controller class for pipeline demultiplexing view.
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class DemultiplexingController {
  
  /** Initializes check combo box for barcode kits. */
  @FXML
  private CheckComboBox<String> ccbBarcodeKits = new CheckComboBox<String>();
  
  /** Initializes demultiplexing group.  */
  @FXML
  private Group gpDemultiplexing;
  
  /** Initializes check box for demultiplexing. */
  @FXML
  private CheckBox cDemultiplexing;
  
  /** Initializes demultiplexing model. */
  public DemultiplexingModel dm = new DemultiplexingModel();
  
  /** Initializes a BashUtil object. */
  private BashUtil bUtil = new BashUtil();
  
  /** Initializes a PipelineUtil object. */
  private PipelineUtil pUtil = new PipelineUtil();
  
  /**
   * Initializes the controller of demultiplexing setting view.
   */
  @FXML
  private void initialize()  { 
    cDemultiplexing.setSelected(dm.getIfDemultiplexing());
    
    ObservableList<String> olBarcodeKits = FXCollections.observableArrayList(bUtil.getBarcodeKits());
    ccbBarcodeKits.getItems().addAll(olBarcodeKits);
    IndexedCheckModel<String> icm = ccbBarcodeKits.getCheckModel();
    String[] strArrBarcodeKits = dm.getBarcodeKits().replaceAll("\"", "").split(" ");
    for(int i=0;i<strArrBarcodeKits.length;i++) {
      icm.check(strArrBarcodeKits[i]);
    }
    
    ccbBarcodeKits.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
      public void onChanged(ListChangeListener.Change<? extends String> c) {
        dm.setBarcodeKits(pUtil.formatBarcodeKits(ccbBarcodeKits.getCheckModel().getCheckedItems().toString()));
      }
    });
    
    cDemultiplexing.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (cDemultiplexing.isSelected()) {
        gpDemultiplexing.setDisable(false);
        dm.setIfDemultiplexing(true);
      } else {
        gpDemultiplexing.setDisable(true);
        dm.setIfDemultiplexing(false);
      }
    });
  }
  
  /**
   * Shows the hint of Barcode Kits Number setting.
   */
  @FXML
  private void showBarcodeKitsNumberHint() {
    pUtil.createAlertDialog(AlertType.INFORMATION, "Barcode Kits Number", "Choose one or several barcode kit(s) from the list if used.");
  }
}