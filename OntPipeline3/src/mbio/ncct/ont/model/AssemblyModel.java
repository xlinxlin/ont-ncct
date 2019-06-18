package mbio.ncct.ont.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the pipeline assembly setting model .
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class AssemblyModel {
  
  /** Initializes and sets the assembly mode. */
  private final StringProperty mode = new SimpleStringProperty("normal");
  
  /** Initializes and sets the assembly method. */
  private final StringProperty method = new SimpleStringProperty("Hybrid assembly");
  
  /** Initializes and sets if a .vcf file will be output. */
  private final BooleanProperty ifVcf = new SimpleBooleanProperty(false);
  
  /** Initializes and sets if assembly will be used. */
  private final BooleanProperty ifAssembly = new SimpleBooleanProperty(true);
  
  /**
   * Gets the assembly mode.
   * @return the String of the assembly mode.
   */
  public String getMode() {
    return mode.get();
  }
  
  /**
   * Sets the assembly mode.
   * @param mode the String of the assembly mode.
   */
  public void setMode(String mode) {
    this.mode.set(mode);
  }
  
  /**
   * Gets the assembly method.
   * @return the String of the assembly method.
   */
  public String getMethod() {
    return method.get();
  }
  
  /**
   * Sets the assembly method.
   * @param method the String of the assembly method.
   */
  public void setMethod(String method) {
    this.method.set(method);
  }
  
  /**
   * Sets if .vcf file will be produced.
   * @param ifVcf The Boolean value of if .vcf file will be produced.
   */
  public void setIfVcf(Boolean ifVcf) {
    this.ifVcf.set(ifVcf);
  }
  
  /**
   * Gets if a .vcf file will be produced.
   * @return the Boolean value of if if a .vcf file will be produced.
   */
  public Boolean getIfVcf() {
    return ifVcf.get();
  }
  
  /**
   * Sets if assembly will be used.
   * @param ifAssembly the Boolean value of if assembly will be used.
   */
  public void setIfAssembly(Boolean ifAssembly) {
    this.ifAssembly.set(ifAssembly);
  }
  
  /**
   * Gets if assembly will be used.
   * @return the Boolean value of if assembly will be used.
   */
  public Boolean getIfAssembly() {
    return ifAssembly.get();
  }
}