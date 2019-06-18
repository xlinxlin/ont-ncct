package mbio.ncct.ont.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the pipeline demultiplexing setting model .
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class DemultiplexingModel {
  
  /** Initializes and sets if demultiplexing will be used. */
  private final BooleanProperty ifDemultiplexing = new SimpleBooleanProperty(true);
  
  /** Initializes and sets the barcode kit(s). */
  private final StringProperty barcodeKits = new SimpleStringProperty("");
  
  /**
   * Sets if demultiplexing will be used.
   * @param ifDemultiplexing the Boolean value of if demultiplexing will be used.
   */
  public void setIfDemultiplexing(Boolean ifDemultiplexing) {
    this.ifDemultiplexing.set(ifDemultiplexing);
  }
  
  /**
   * Gets if demultiplexing will be used.
   * @return the Boolean value of if demultiplexing will be used.
   */
  public Boolean getIfDemultiplexing() {
    return ifDemultiplexing.get();
  }
  
  /**
   * Gets the barcode kit(s).
   * @return the String of the barcode kit(s).
   */
  public String getBarcodeKits() {
    return barcodeKits.get();
  }
  
  /**
   * Sets the barcode kit(s).
   * @param barcodeKits the String of the barcode kit(s).
   */
  public void setBarcodeKits(String barcodeKits) {
    this.barcodeKits.set(barcodeKits);
  }
}