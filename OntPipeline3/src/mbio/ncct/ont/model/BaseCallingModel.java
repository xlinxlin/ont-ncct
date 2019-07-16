package mbio.ncct.ont.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the pipeline base calling setting model .
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class BaseCallingModel {
  
  /** Initializes and sets if base calling will be used. */
  private final BooleanProperty ifBasecalling = new SimpleBooleanProperty(true);
  
  /** Initializes and sets the flowcell ID. */
  private final StringProperty flowcellId = new SimpleStringProperty("FLO-PRO001");
  
  /** Initializes and sets the kit number. */
  private final StringProperty kitNumber = new SimpleStringProperty("SQK-LSK109");
  
  /** Initializes and sets Guppy mode. */
  private final StringProperty guppyMode = new SimpleStringProperty("fast");
  
  /** Initializes and sets nanopore device. */
  private final StringProperty device = new SimpleStringProperty("PromethION");
  
  /** Initializes and sets if Guppy fast mode will be used. */
  private final BooleanProperty ifGuppyFast = new SimpleBooleanProperty(false);
  
  /** Initializes and sets Guppy CFG configuration file. */
  private final StringProperty guppyCfgFile = new SimpleStringProperty("");
  
  /**
   * Sets if base calling will be used.
   * @param ifBasecalling the Boolean value of if base calling will be used.
   */
  public void setIfBasecalling(Boolean ifBasecalling) {
    this.ifBasecalling.set(ifBasecalling);
  }
  
  /**
   * Gets if base calling will be used.
   * @return the Boolean value of if base calling will be used.
   */
  public Boolean getIfBasecalling() {
    return ifBasecalling.get();
  }
  
  /**
   * Gets the flowcell ID.
   * @return the String of flowcell ID.
   */
  public String getFlowcellId() {
    return flowcellId.get();
  }
  
  /**
   * Sets the flowcell ID.
   * @param flowcellId the String of the flowcell ID.
   */
  public void setFlowcellId(String flowcellId) {
    this.flowcellId.set(flowcellId);
  }
  
  /**
   * Gets the kit number.
   * @return the String of kit number.
   */
  public String getKitNumber() {
    return kitNumber.get();
  }
  
  /**
   * Sets the kit number.
   * @param kitNumber the String of the kit number.
   */
  public void setKitNumber(String kitNumber) {
    this.kitNumber.set(kitNumber);
  }
  
  /**
   * Gets the Guppy mode.
   * @return the String of the Guppy mode.
   */
  public String getGuppyMode() {
    return guppyMode.get();
  }
  
  /**
   * Sets the Guppy mode.
   * @param guppyMode the String of the Guppy mode.
   */
  public void setGuppyMode(String guppyMode) {
    this.guppyMode.set(guppyMode);
  }
  
  /**
   * Gets the Nanopore device.
   * @return the String of the Nanopore device.
   */
  public String getDevice() {
    return device.get();
  }
  
  /**
   * Sets the Nanopore device.
   * @param device the String of the Nanopore device.
   */
  public void setDevice(String device) {
    this.device.set(device);
  }
  
  /**
   * Sets if Guppy fast mode will be used.
   * @param ifGuppyFast the Boolean value of if Guppy fast mode will be used.
   */
  public void setIfGuppyFast(Boolean ifGuppyFast) {
    this.ifGuppyFast.set(ifGuppyFast);
  }
  
  /**
   * Gets if Guppy fast mode will be used.
   * @return the Boolean value of if Guppy fast mode will be used.
   */
  public Boolean getIfGuppyFast() {
    return ifGuppyFast.get();
  }
  
  /**
   * Gets the Guppy configuration file.
   * @return the String of the Guppy configuration file.
   */
  public String getGuppyCfgFile() {
    return guppyCfgFile.get();
  }
  
  /**
   * Sets the Guppy configuration file.
   * @param guppyCfgFile The String of the Guppy configuration file.
   */
  public void setGuppyCfgFile(String guppyCfgFile) {
    this.guppyCfgFile.set(guppyCfgFile);
  }
}