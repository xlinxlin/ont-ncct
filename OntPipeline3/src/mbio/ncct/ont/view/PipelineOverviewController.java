package mbio.ncct.ont.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import mbio.ncct.ont.MainApp;
import mbio.ncct.ont.util.BashUtil;
import mbio.ncct.ont.util.CheckUtil;
import mbio.ncct.ont.util.FileUtil;
import mbio.ncct.ont.util.PipelineUtil;

/**
 * PipelineOverviewController The controller for pipeline overview.
 *
 * @author Yan Zhou
 * created on 2019/06/17
 */
public class PipelineOverviewController {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(PipelineOverviewController.class);
  
  /** Initializes MainApp. */
  public MainApp mainApp;
  
  /** Initializes Parent pane generalView. */
  @FXML
  private Parent generalView;
  
  /** Initializes Parent pane baseCallingView. */
  @FXML
  private Parent baseCallingView;
  
  /** Initializes Parent pane demultiplexingView. */
  @FXML
  private Parent demultiplexingView;
  
  /** Initializes Parent pane readsFilterView. */
  @FXML
  private Parent readsFilterView;
  
  /** Initializes Parent pane assemblyView. */
  @FXML
  private Parent assemblyView;
  
  /** Initializes Parent pane polishingView. */
  @FXML
  private Parent polishingView;
  
  /** Initializes general view setting controller. */
  @FXML
  private GeneralController generalViewController;
  
  /** Initializes base calling view setting controller. */
  @FXML
  private BaseCallingController baseCallingViewController;
  
  /** Initializes demultiplexing view setting controller. */
  @FXML
  private DemultiplexingController demultiplexingViewController;
  
  /** Initializes reads filter view setting controller. */
  @FXML
  private ReadsFilterController readsFilterViewController;
  
  /** Initializes assembly view setting controller. */
  @FXML
  private AssemblyController assemblyViewController;
  
  /** Initializes polishing view setting controller. */
  @FXML
  private PolishingController polishingViewController;
  
  /** Initializes TextArea taQstat to show the result from command qstat. */
  @FXML
  private TextArea taQstat;
  
  /** Initializes a BashUtil object. */
  private BashUtil bUtil = new BashUtil();
  
  /** Initializes a CheckUtil object. */
  private CheckUtil ckUtil = new CheckUtil();
  
  /** Initializes a PipelineUtil object. */
  private PipelineUtil pUtil = new PipelineUtil();
  
  /** Initializes a FileUtil object. */
  private FileUtil fUtil = new FileUtil();
  
  /**
   * Initializes the controller of pipeline overview.
   */
  @FXML
  private void initialize()  { 
    getQstat();
  }
  
  /**
   * Called when start pipeline button is clicked.
   * Errors check:
   * 01. ONT reads directory is empty.
   * 02. Output directory is empty.
   * 03. The threads number is not a positive integer.
   * 04. No module is selected.
   * 05. The format of selected barcodes is wrong (the right format: 1,2,3,4)
   * 06. User selects "Base calling" but the ONT directory contains no FAST5 file.
   * 07. User selects "Hybrid assembly" but the Illumina reads directory is empty.
   * 08. User selects "Polishing" but the Illumina reads directory is empty.
   * 09. User starts the pipeline from "Assembly(hybrid)"/"Polishing", but the prefixes of ONT reads (FASTQ) do not match the prefixes of Illumina reads.
   * 10. Guppy_basecaller is in fast mode, the Flowcell ID does not match the device. (PromethION should match FLO-PRO* and MinION* should match FLO-MIN*.)
   */
  @FXML
  private void startPipeline() {
    if (!ckUtil.checkOntDirIsNotEmpty(generalViewController.gm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 01", "Nanopore reads directory can not be empty.");
    } else if (!ckUtil.checkOutDirIsNotEmpty(generalViewController.gm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 02", "Output directory can not be empty.");
    } else if (!ckUtil.checkThreadsFormat(generalViewController.gm)){
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 03", "Threads should be a positive integer.");
    } else if (!ckUtil.checkAtLeastOneModuleIsSelected(baseCallingViewController.bcm, demultiplexingViewController.dm, readsFilterViewController.rfm, assemblyViewController.am, polishingViewController.pm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 04", "Please select at least one module.");
    } else if (!ckUtil.checkSelectedBarcodesFormat(generalViewController.gm)){
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 05", "The format of selected barcodes is wrong.");
    } else if (!ckUtil.checkBaseCallingWithFast5(baseCallingViewController.bcm, generalViewController.gm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 06", "Base calling runs only with FAST5 files");
    } else if (!ckUtil.checkHybridAssemblyWithIlluminaReads(assemblyViewController.am, generalViewController.gm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 07", "Hybrid assembly requires Illumina reads.");
    } else if (!ckUtil.checkPolishingWithIlluminaReads(polishingViewController.pm, generalViewController.gm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 08", "Polishing requires Illumina reads.");
    } else if (!ckUtil.CheckPrefixIfOntFastqWithHybridAssemblyOrPolishing(generalViewController.gm, assemblyViewController.am, polishingViewController.pm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 09", "The prefixes of ONT reads do not match the prefixes of Illumina reads.");
    } else if (!ckUtil.checkDeviceWithCorrectFlowcellInFasfMode(baseCallingViewController.bcm)) {
      pUtil.createAlertDialog(AlertType.ERROR, "Error code 10", "The flowcell ID does not match the device.");
    } else {
      String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
      fUtil.createUserLog(generalViewController.gm, baseCallingViewController.bcm, demultiplexingViewController.dm, readsFilterViewController.rfm, assemblyViewController.am, polishingViewController.pm, timestamp);
      fUtil.createPbsFile(generalViewController.gm, baseCallingViewController.bcm, demultiplexingViewController.dm, readsFilterViewController.rfm, assemblyViewController.am, polishingViewController.pm, timestamp);
      /*
      try {
        //Runtime.getRuntime().exec(new String[] {"bash","-c","qsub -k oe -N Ont_Pipeline_" + timestamp + " " + generalViewController.gm.getOutputPath() + "/pipelineWithLoop_" + timestamp + ".pbs" });
      } catch (Exception e) {
        logger.error("Can not run PBS file. " + e);
      }
      */
      bUtil.runPbsScript(generalViewController.gm.getOutputPath(), timestamp);
      pUtil.createAlertDialog(AlertType.INFORMATION, "Submitted.", "Your job has been submitted successfully.");
      getQstat();
      /*)
      try {
        Runtime.getRuntime().exec(new String[] {"bash","-c","gnome-terminal -- sh -c 'tail -F /home/sysgen/Ont_Pipeline_" + timestamp + ".o*'" });
      } catch (Exception e) {
        logger.error("Can not open terminal to show log. " + e);
      }
      */
      bUtil.runLogsInConsole(timestamp);
    }
  }

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;    
  }
  
  /**
   * Opens the document in a browser.
   */
  @FXML
  private void handleOpenDocument() {
    try {
      new ProcessBuilder("x-www-browser", "https://ontpipeline2.readthedocs.io/").start();
    } catch (Exception e) {
      logger.error("Can not open document. " + e);
    }
  }
  
  /**
   * Gets the result from qstat command.
   */
  @FXML
  private void getQstat() {
    ArrayList<String> alResult = bUtil.getQstat();
    String s = "";
    for (int i=0;i<alResult.size();i++ ) {
      s = s + alResult.get(i) + "\n";
    }
    s = "Job status:\n" + ( s.isEmpty() ? "No jobs." : s );
    taQstat.setText(s);
  }
}