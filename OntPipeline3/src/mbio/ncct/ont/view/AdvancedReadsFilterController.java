package mbio.ncct.ont.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mbio.ncct.ont.util.PipelineUtil;

/**
 * This is the controller of the advanced reads-filter settings.
 *
 * @author Yan Zhou
 * created on 2019/05/14
 */
public class AdvancedReadsFilterController {
  
  /** The advanced reads filter setting dialog stage. */
  private Stage dialogStage;
  
  /** The text field for read score. */
  @FXML
  public TextField tfReadScore; 
  
  /** The text field for read length. */
  @FXML
  public TextField tfReadLength;
  
  /** The text field for head crop. */
  @FXML
  public TextField tfHeadCrop; 
  
  /** The check box for adapter trimming. */
  @FXML
  public CheckBox cAdapterTrimming; 
  
  /** The check box for splitting the reads. */
  @FXML
  public CheckBox cSplitting; 
  
  /** The label for splitting the reads. */
  @FXML
  public Label lbSplit;
  
  /** The signal for if OK button is clicked. */
  public int isOK = 0;

  /**
   * Initializes the controller of advanced reads filter settings.
   */
  @FXML
  private void initialize() {
    cAdapterTrimming.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(cAdapterTrimming.isSelected()) {
        cSplitting.setDisable(false);
        lbSplit.setDisable(false);
      } else {
        cSplitting.setDisable(true);
        lbSplit.setDisable(true);
      }
    });
  }
  
  /**
   * Sets the advanced reads filter setting dialog stage.
   * @param dialogStage advanced reads filter setting dialog stage
   */
  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }
  
  /**
   * If OK button is clicked, return 1, close the advanced reads filter setting dialog.
   */
  @FXML
  private void OK() {
    if(tfReadScore.getText().matches("\\d+") && tfReadLength.getText().matches("\\d+") && tfHeadCrop.getText().matches("\\d+")) {
      isOK = 1;
      dialogStage.close();
    } else {
      PipelineUtil pUtil = new PipelineUtil();
      pUtil.createAlertDialog(AlertType.ERROR, "Error", "Only integer value is accepted. \nReset defaults.");
      tfReadScore.setText("9");
      tfReadLength.setText("500");
      tfHeadCrop.setText("50");
    }
  }
  
  /**
   * If Cancel button is clicked, return 0, close the advanced reads filter setting dialog.
   */
  @FXML
  private void Cancel() {
    dialogStage.close();
  }
  
  /**
   * Sets the read score to the text field.
   * @param readScore Filter on a minimum average read quality score.
   */
  public void setReadScore(String readScore) {
    tfReadScore.setText(readScore);
  }
  
  /**
   * Sets the read length to the text field.
   * @param readLength Filter on a minimum read length
   */
  public void setReadLength(String readLength) {
    tfReadLength.setText(readLength);
  }
  
  /**
   * Sets the head crop to the text field.
   * @param headCrop Trim n nucleotides from start of read.
   */
  public void setHeadCrop(String headCrop) {
    tfHeadCrop.setText(headCrop);
  }
  
  /**
   * Sets if adapter trimming will be used in check box.
   * @param ifAdapterTrimming If adapter trimming is selected.
   */
  public void setIfAdapterTrimming(boolean ifAdapterTrimming) {
    cAdapterTrimming.setSelected(ifAdapterTrimming);
  }
  
  /**
   * Sets if split the reads in check box.
   * @param ifSplitting If skip splitting reads based on middle adapters.
   */
  public void setIfSplitting(boolean ifSplitting) {
    cSplitting.setSelected(ifSplitting);
  }
}