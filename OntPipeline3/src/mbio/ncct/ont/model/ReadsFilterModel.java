package mbio.ncct.ont.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the pipeline reads filter setting model .
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class ReadsFilterModel {
  
  /** Initializes and sets the read quality score for filter. */
  private final StringProperty readScore = new SimpleStringProperty("9");
  
  /** Initializes and sets the read length for filter. */
  private final StringProperty readLength = new SimpleStringProperty("500");
  
  /** Initializes and sets the head crop for filter. */
  private final StringProperty headCrop = new SimpleStringProperty("50");
  
  /** Initializes and sets if adapter trimming will be used. */
  private final BooleanProperty ifAdapterTrimming = new SimpleBooleanProperty(true);
  
  /** Initializes and sets if skip splitting reads based on middle adapters. */
  private final BooleanProperty ifNoSplit = new SimpleBooleanProperty(false);
  
  /** Initializes and sets if reads filter will be used. */
  private final BooleanProperty ifReadsFilter = new SimpleBooleanProperty(true);
  
  /**
   * Gets the read quality score.
   * @return the String of read quality score.
   */
  public String getReadScore() {
    return readScore.get();
  }
  
  /**
   * Sets the read quality score.
   * @param readScore the String of the read quality score.
   */
  public void setReadScore(String readScore) {
    this.readScore.set(readScore);
  }
  
  /**
   * Gets the read length.
   * @return the String of the read length.
   */
  public String getReadLength() {
    return readLength.get();
  }
  
  /**
   * Sets the read length.
   * @param readLength the String of the read length.
   */
  public void setReadLength(String readLength) {
    this.readLength.set(readLength);
  }
  
  /**
   * Gets the head crop.
   * @return the String of the head crop.
   */
  public String getHeadCrop() {
    return headCrop.get();
  }
  
  /**
   * Sets the head crop.
   * @param headCrop the String of the head crop.
   */
  public void setHeadCrop(String headCrop) {
    this.headCrop.set(headCrop);
  }
  
  /**
   * Sets if adapter trimming will be used.
   * @param ifAdapterTrimming the Boolean value of if adapter trimming will be used.
   */
  public void setIfAdapterTrimming(Boolean ifAdapterTrimming) {
    this.ifAdapterTrimming.set(ifAdapterTrimming);
  }
  
  /**
   * Gets if adapter trimming is used.
   * @return the Boolean value of if adapter trimming is used.
   */
  public Boolean getIfAdapterTrimming() {
    return ifAdapterTrimming.get();
  }
  
  /**
   * Sets if split reads in Porechop.
   * @param ifNoSplit the Boolean value of if split reads in Porechop.
   */
  public void setIfNoSplit(Boolean ifNoSplit) {
    this.ifNoSplit.set(ifNoSplit);
  }
  
  /**
   * Gets if split the reads in Porechop.
   * @return the Boolean value of if split the reads in Porechop.
   */
  public Boolean getIfNoSplit() {
    return ifNoSplit.get();
  }
  
  /**
   * Sets if reads filter will be used.
   * @param ifReadsFilter the Boolean value of if reads filter will be used.
   */
  public void setIfReadsFilter(Boolean ifReadsFilter) {
    this.ifReadsFilter.set(ifReadsFilter);
  }
  
  /**
   * Gets if reads filter will be used.
   * @return the Boolean value of if reads filter will be used.
   */
  public Boolean getIfReadsFilter() {
    return ifReadsFilter.get();
  }
}