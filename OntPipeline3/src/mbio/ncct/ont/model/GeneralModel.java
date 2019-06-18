package mbio.ncct.ont.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the pipeline general setting model.
 * 
 * @author Yan Zhou
 * created on 2019/06/14
 */

public class GeneralModel {
  
  /** Initializes and sets the ONT reads workspace. */
  private final StringProperty ontReadsWorkspace = new SimpleStringProperty("");
  
  /** Initializes and sets the Illunima reads workspace. */
  private final StringProperty illuminaReadsWorkspace = new SimpleStringProperty("");
  
  /** Initializes and sets the output path. */
  private final StringProperty outputPath = new SimpleStringProperty("");
  
  /** Initializes and sets the sample sheet file path. */
  private final StringProperty sampleSheetPath = new SimpleStringProperty("");
  
  /** Initializes and sets the sample sheet file content. */
  private final StringProperty sampleSheetContent = new SimpleStringProperty("");
  
  /** Initializes and sets the specified barcode(s). */
  private final StringProperty selectedBarcode = new SimpleStringProperty("");
  
  /** Initializes and sets the threads. */
  private final StringProperty threads = new SimpleStringProperty("8");
  
  /** Initializes and sets the prefix. */
  private final StringProperty prefix = new SimpleStringProperty("");
  
  /**
   * Gets the ONT reads workspace.
   * @return the String of ONT reads workspace
   */
  public String getOntReadsWorkspace() {
    return ontReadsWorkspace.get();
  }
  
  /**
   * Sets the ONT reads workspace.
   * @param ontReadsWorkspace the String of ONT reads workspace.
   */
  public void setOntReadsWorkspace(String ontReadsWorkspace) {
    this.ontReadsWorkspace.set(ontReadsWorkspace);
  }
  
  /**
   * Gets the Illumina reads workspace.
   * @return the String of Illumina reads workspace
   */
  public String getIlluminaReadsWorkspace() {
    return illuminaReadsWorkspace.get();
  }
  
  /**
   * Sets the Illumina reads workspace.
   * @param illuminaReadsWorkspace the String of Illumina reads workspace.
   */
  public void setIlluminaReadsWorkspace(String illuminaReadsWorkspace) {
    this.illuminaReadsWorkspace.set(illuminaReadsWorkspace);
  }
  
  /**
   * Gets the output path.
   * @return the String of output path.
   */
  public String getOutputPath() {
    return outputPath.get();
  }
  
  /**
   * Sets the output path.
   * @param outputPath the String of output path.
   */
  public void setOutputPath(String outputPath) {
    this.outputPath.set(outputPath);
  }
  
  /**
   * Gets the sample sheet file path.
   * @return the String of sample sheet file path.
   */
  public String getSampleSheetPath() {
    return sampleSheetPath.get();
  }
  
  /**
   * Sets the sample sheet file path.
   * @param sampleSheetPath the String of sample sheet file path.
   */
  public void setSampleSheetPath(String sampleSheetPath) {
    this.sampleSheetPath.set(sampleSheetPath);
  }
  
  /**
   * Gets the sample sheet content.
   * @return the String of sample sheet content.
   */
  public String getSampleSheetContent() {
    return sampleSheetContent.get();
  }
  
  /**
   * Sets the sample sheet content.
   * @param sampleSheetContent the String of sample sheet content.
   */
  public void setSampleSheetContent(String sampleSheetContent) {
    this.sampleSheetContent.set(sampleSheetContent);
  }
  
  /**
   * Gets the prefix.
   * @return the String of prefix.
   */
  public String getPrefix() {
    return prefix.get();
  }
  
  /**
   * Sets the prefix.
   * @param prefix the String of prefix.
   */
  public void setPrefix(String prefix) {
    this.prefix.set(prefix);
  }
  
  /**
   * Gets the specified barcode(s).
   * @return the String of the specified barcode(s).
   */
  public String getSelectedBarcode() {
    return selectedBarcode.get();
  }
  
  /**
   * Sets the specified barcode(s).
   * @param selectedBarcode the String of the specified barcode(s).
   */
  public void setSelectedBarcode(String selectedBarcode) {
    this.selectedBarcode.set(selectedBarcode);
  }
  
  /**
   * Gets the threads.
   * @return the String of threads.
   */
  public String getThreads() {
    return threads.get();
  }
  
  /**
   * Sets the threads.
   * @param threads the String of the threads.
   */
  public void setThreads(String threads) {
    this.threads.set(threads);
  }
  
}
