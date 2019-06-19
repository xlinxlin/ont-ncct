package mbio.ncct.ont.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the pipeline utilities with bash commands.
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class BashUtil {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(BashUtil.class);
  
  /** Initializes a ReadPropertyUtil object. */
  private ReadPropertyUtil rpUtil = new ReadPropertyUtil();
  
  /**
   * Gets all flowcell IDs.
   * @return a String Array with all flowcell IDs. 
   */
  public ArrayList<String> getFlowcellIds() {
    String s = null;
    ArrayList<String> arFlowcellIds = new ArrayList<String>();
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(new String[] { "bash", "-c", rpUtil.getGuppyUrl() + "/bin/guppy_basecaller --print_workflows | awk 'NR>2 {print $1}' | sort | uniq" });
    } catch (Exception e) {
      logger.error("Can not get flowcell IDs. " + e);
    }
    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
    //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    try {
      while ((s = stdInput.readLine()) != null ) {
        if (!s.isEmpty()) {
          arFlowcellIds.add(s);
        }
      }
    } catch (Exception e) {
      logger.error("Can not read flowcell IDs. " + e);
    }
    return arFlowcellIds;
  }
  
  /**
   * Gets all kit numbers.
   * @return a String Array with all kit numbers.
   */
  public ArrayList<String> getKitNumbers() {
    String s = null;
    ArrayList<String> arKitNumbers = new ArrayList<String>();
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(new String[] { "bash", "-c", rpUtil.getGuppyUrl() + "/bin/guppy_basecaller --print_workflows | awk 'NR>2 {print $2}' | sort | uniq" });
    } catch (Exception e) {
      logger.error("Can not get kit numbers. " + e);
    }
    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
    //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    try {
      while ((s = stdInput.readLine()) != null ) {
        if (!s.isEmpty()) {
          arKitNumbers.add(s);
        }
      }
    } catch (Exception e) {
      logger.error("Can not read kit numbers. " + e);
    }
    return arKitNumbers;
  }
  
  /**
   * Gets all the combinations of flowcell ID and kit number.<br>
   * Return value Map<String, String>, key is FlowcellIdKitNumber, value is CFG file name. <br>
   * Output example: {FLO-FLG001SQK-PSK004=dna_r9.4.1_450bps_hac, FLO-FLG001SQK-LWP001=dna_r9.4.1_450bps_hac, FLO-MIN106SQK-DCS109=dna_r9.4.1_450bps_hac, ...}
   * @return a map with all the combinations of flowcell ID and kit number.
   */
  public Map<String, String> getCombinationFlowcellKit() {
    String s = null;
    Map<String, String> m = new HashMap<String, String>();
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(new String[] { "bash", "-c", rpUtil.getGuppyUrl() + "/bin/guppy_basecaller --print_workflows | awk 'NR>2 {print $1,$2,$3,$4}' " });
    } catch (Exception e) {
      logger.error("Can not run command: guppy_basecaller --print_workflows . " + e);
    }
    //BufferedReader stdError = null;
    try {
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      //stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
      while ((s = stdInput.readLine()) != null ) {
        if (s.length() > 3) {
          String[] arr = s.replaceAll("included ", "").split(" ");
          m.put(arr[0].concat(arr[1]), arr[2]);
        }
      }
    } catch (Exception e) {
      logger.error("Can not read result from guppy_basecaller --print_workflows . " + e);
    }
    return m;
  }
  
  /**
   * Gets all barcode kits.
   * @return an Array List with all barcode kits.
   */
  public ArrayList<String> getBarcodeKits() {
    String s = null;
    ArrayList<String> arBarcodeKits = new ArrayList<String>();
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(new String[] { "bash", "-c", rpUtil.getGuppyUrl() + "/bin/guppy_barcoder --print_kits | awk 'NR>1 {print $1}' | sort | uniq" });
    } catch (Exception e) {
      logger.error("Can not get barcode kits. " + e);
    }
    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
    //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    try {
      while ((s = stdInput.readLine()) != null ) {
        if (s.isEmpty() == false) {
          arBarcodeKits.add(s);
        }
      }
    } catch (Exception e) {
      logger.error("Can not read barcode kits. " + e);
    }
    return arBarcodeKits;
  }
  
  /**
   * Gets the result from the qstat command.
   * @return ArrayList with the result from qstat command.
   */  
  public ArrayList<String> getQstat() {
    String s = null;
    Process p = null;
    ArrayList<String> alResult = new ArrayList<String>();
    try {
      p = Runtime.getRuntime().exec(new String[] { "bash", "-c", "qstat" });
    } catch (Exception e) {
      logger.error("Can not get qstat result. " + e);
    }
    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
    //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    try {
      while ((s = stdInput.readLine()) != null ) {
        alResult.add(s);
      }
    } catch (Exception e) {
      logger.error("Can not read qstat result. " + e);
    }
    return alResult;
  }
  
  /**
   * Runs the PBS script.
   * @param pbsScriptPath the path to the PBS script.
   * @param timestamp the String format of timestamp.
   */  
  public void runPbsScript(String pbsScriptPath, String timestamp) {
    try {
      Runtime.getRuntime().exec(new String[] {"bash","-c","qsub -k oe -N Ont_Pipeline_" + timestamp + " " + pbsScriptPath + "/pipelineWithLoop_" + timestamp + ".pbs" });
    } catch (Exception e) {
      logger.error("Can not run PBS file. " + e);
    }
  }
  
  /**
   * Runs the logs in a console.
   * @param timestamp the String format of timestamp.
   */  
  public void runLogsInConsole(String timestamp) {
    try {
      Runtime.getRuntime().exec(new String[] {"bash","-c","gnome-terminal -- sh -c 'tail -F " + rpUtil.getRootUrl() + "/Ont_Pipeline_" + timestamp + ".o*'" });
    } catch (Exception e) {
      logger.error("Can not open terminal to show log. " + e);
    }
  }
}