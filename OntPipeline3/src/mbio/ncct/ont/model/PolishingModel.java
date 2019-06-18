package mbio.ncct.ont.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the pipeline polishing setting model .
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class PolishingModel {
  
  /** Initializes and sets if polishing will be used. */
  private final BooleanProperty ifPolishing = new SimpleBooleanProperty(true);
  
  /** Initializes and sets the polishing times. */
  private final StringProperty pTimes = new SimpleStringProperty("1");
  
  /** Initializes and sets if BUSCO check will be used. */
  private final BooleanProperty ifBusco = new SimpleBooleanProperty(false);
  
  /** Initializes and sets the database of BUSCO. */
  private final StringProperty buscoDatabase = new SimpleStringProperty("bacteria");
  
  /**
   * Gets the polishing times.
   * @return the String of polishing times.
   */
  public String getPtimes() {
    return pTimes.get();
  }
  
  /**
   * Sets the polishing times.
   * @param pTimes The polishing times.
   */
  public void setPtimes(String pTimes) {
    this.pTimes.set(pTimes);
  }
  
  /**
   * Sets if polishing will be used.
   * @param ifPolishing the Boolean value of if polishing will be used.
   */
  public void setIfPolishing(Boolean ifPolishing) {
    this.ifPolishing.set(ifPolishing);
  }
  
  /**
   * Gets if polishing will be used.
   * @return the Boolean value of if polishing will be used.
   */
  public Boolean getIfPolishing() {
    return ifPolishing.get();
  }
  
  /**
   * Gets the database of BUSCO.
   * @return the String of the BUSCO database.
   */
  public String getBuscoData() {
    return buscoDatabase.get();
  }
  
  /**
   * Sets the BUSCO database.
   * @param buscoDatabase the String of busco database.
   */
  public void setBuscoData(String buscoDatabase) {
    this.buscoDatabase.set(buscoDatabase);
  }
  
  /**
   * Sets if BUSCO will be used.
   * @param ifBusco the Boolean value of if BUSCO will be used.
   */
  public void setIfBusco(Boolean ifBusco) {
    this.ifBusco.set(ifBusco);
  }
  
  /**
   * Gets if BUSCO will be used.
   * @return the Boolean value of if BUSCO will be used.
   */
  public Boolean getIfBusco() {
    return ifBusco.get();
  }
}