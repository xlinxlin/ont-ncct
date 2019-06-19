package mbio.ncct.ont.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

/**
 * This is the PipelineUtil class for utilities used in Pipeline.
 * 
 * @author Yan Zhou
 * created on 2019/05/16
 */
public class PipelineUtil {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(PipelineUtil.class);
  
  /**
   * Reads sample sheet and parses the content.
   * @param sampleSheet the String of sample sheet file path.
   * @param extension the String of sample sheet file extension.
   * @return the HashMap of sample sheet content.
   */
  public HashMap<String,String> getSampleSheetContent(String sampleSheet, String extension) {
    HashMap<String,String> hmResult = new HashMap<String,String>();
    String ch = (extension.toLowerCase().equals("csv") ? "," : "\t");
    try (BufferedReader br = new BufferedReader(new FileReader(sampleSheet))) {
      String line;
      line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] values = line.split(ch);
        hmResult.put("barcode"+values[1].substring(values[1].length()-2), values[0]);
      }
    } catch (Exception e) {
      logger.error("Can not read sample sheet." + e);
    } 
    return hmResult;
  }
  
  /**
   * Formats the HashMap of sample sheet content to String. <br>
   * Return value example:<br>
   * (['barcode01']='MW_1' ['barcode02']='MW23' ['barcode03']='AA_45' )
   * @param hmSampleSheet the sample sheet content in HashMap format.
   * @return formatted String of sample sheet content (HashMap in Bash).
   */
  public String formatSampleSheetContent(HashMap<String,String> hmSampleSheet) {
    String result = null;
    for(Map.Entry<String, String> entry : hmSampleSheet.entrySet()) {
      result = result + "['" + entry.getKey() + "']='" + entry.getValue() + "' ";  
    }
    return "(" + result + ")";
  }
  
  /**
   * Creates an alert dialog.
   * @param alertType the alert type.
   * @param alertTitle the dialog title.
   * @param alertContent the dialog content.
   */
  public void createAlertDialog(AlertType alertType, String alertTitle, String alertContent) {
    Alert alert = new Alert(alertType);
    alert.setTitle(alertTitle);
    alert.setContentText(alertContent);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.showAndWait();
  }
  
  /**
   * Formats the String of selected barcodes.<br>
   * Input example: 1,2,3,5<br>
   * Output example: barcode{01,02,03,05}/
   * @param selectedBarcode selected barcodes.
   * @return formatted String of selected barcodes.
   */
  public String formatSelectedBarcodes(String selectedBarcode) {
    String result = null;
    if (!selectedBarcode.isEmpty()) {
      String[] strArr = selectedBarcode.split(",");
      String formattedSelectedBarcode = "";
      for(int i=0; i<strArr.length; i++) {
        String formatted = String.format("%02d", Integer.valueOf(strArr[i])) + ",";
        formattedSelectedBarcode = formattedSelectedBarcode + formatted;
      }
      result = "barcode{" + formattedSelectedBarcode.substring(0, formattedSelectedBarcode.length()-1) + "}/";
    }
    return result;
  }
  
  /**
   * Gets the extension of a given file.
   * @param fileName the String of file name.
   * @return the String of extension of a given file.
   */
  public String getFileExtension(String fileName) {
    String extension = "";
    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      extension = fileName.substring(i+1);
    }
    return extension;
  }
  
  
  /**
   * Gets the prefixs from the Illumina reads directory.
   * @param illuminaDirectory the path to the Illumina reads directory.
   * @return an ArrayList contains all prefixs.. 
   */
  public ArrayList<String> getIlluminaReadsPrefix(File illuminaDirectory) {
    ArrayList<String> alPrefixs = new ArrayList<String>();
    File[] f = illuminaDirectory.listFiles();
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile() && f[i].getName().matches(".*_HQ_1\\.fastq\\.gz")) {
        String prefix = f[i].getName().substring(0, f[i].getName().indexOf("_"));
        alPrefixs.add(prefix);
      }
    }
    return alPrefixs;
  }
  
  /**
   * Formats the String of barcodeKits.<br>
   * Input example: [EXP-NBD104, EXP-NBD114] <br>
   * Output example: "EXP-NBD104, EXP-NBD114"
   * @param barcodeKits selected barcode kits.
   * @return formatted String of barcode kits.
   */
  public String formatBarcodeKits(String barcodeKits) {
    return barcodeKits.replace("[", "\"").replace("]", "\"");
  }
  
  /**
   * Gets all prefixes of ONT reads in a given directory.
   * @param ontDirectory the path of ONT reads directory.
   * @return an ArrayList contains all prefixes.
   */
  public ArrayList<String> getOntReadsPrefix(File ontDirectory){
    ArrayList<String> alPrefixs = new ArrayList<String>();
    File[] f = ontDirectory.listFiles();
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile() && f[i].getName().matches(".*\\.fastq")) {
        String prefix = null;
        if (f[i].getName().contains("_")) {
          prefix = f[i].getName().substring(0, f[i].getName().indexOf("_")); 
        } else {
          prefix = f[i].getName().substring(0, f[i].getName().indexOf(".")); 
        }
        alPrefixs.add(prefix);
      }
    }
    return alPrefixs;
  }
}