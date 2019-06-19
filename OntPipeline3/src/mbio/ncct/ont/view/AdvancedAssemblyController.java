package mbio.ncct.ont.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * This is the controller of the advanced assembly settings.
 *
 * @author Yan Zhou
 * created on 2019/05/14
 */
public class AdvancedAssemblyController {
  
  /** The dialog stage of advanced assembly setting. */
  private Stage dialogStage;
  
  /** The check box for if a VCF file will be produced. */
  @FXML
  public CheckBox cVcf;
  
  /** The signal of if OK button is clicked. */
  public int isOK = 0;

  /**
   * Initializes the controller of advanced assembly settings.
   */
  @FXML
  private void initialize() {
    
  }
  
  /**
   * Sets the advanced assembly setting dialog stage.
   * @param dialogStage advanced assembly setting dialog stage.
   */
  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }
  
  /**
   * If OK button is clicked, sets isOK to 1 and closes the dialog.
   */
  @FXML
  private void OK() {
    isOK = 1;
    dialogStage.close();
  }
  
  /**
   * If Cancel button is clicked, sets isOK to 0 and closes the dialog.
   */
  @FXML
  private void cancel() {
    dialogStage.close();
  }
  
  /**
   * Sets the check box if a VCF file will be produced.
   * @param ifVcf the Boolean value of if a VCF file will be produced.
   */
  public void setIfVcf(boolean ifVcf) {
    cVcf.setSelected(ifVcf);
  }
}