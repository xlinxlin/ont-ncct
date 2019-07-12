package mbio.ncct.ont.view;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import mbio.ncct.ont.model.GeneralModel;
import mbio.ncct.ont.util.CheckUtil;
import mbio.ncct.ont.util.PipelineUtil;

/**
 * This is the controller for pipeline general setting view.
 *
 * @author Yan Zhou
 * created on 2019/06/17
 */
public class GeneralController {
  
  /** Initializes text field for Nanopore reads workspace. */
  @FXML
  private TextField tfNanoporeWorkspace;
  
  /** Initializes text field for Illumina reads workspace. */
  @FXML
  private TextField tfIlluminaWorkspace;
  
  /** Initializes text field for threads. */
  @FXML
  private TextField tfThreads;
  
  /** Initializes text field for selected barcodes. */
  @FXML
  private TextField tfSelectedBarcode;
  
  /** Initializes text field for output path. */
  @FXML
  private TextField tfOutputPath;
  
  /** Initializes text field for prefix. */
  @FXML
  public TextField tfPrefix;
  
  /** Initializes text field for sample sheet. */
  @FXML
  private TextField tfSampleSheet;
  
  /** Initializes a CheckUtil object. */
  private CheckUtil ckUtil = new CheckUtil();
  
  /** Initializes a PipelineUtil object. */
  private PipelineUtil pUtil = new PipelineUtil();
    
  /** Initializes General Model. */
  public GeneralModel gm = new GeneralModel();
  
  /**
   * Initializes the controller of general setting view.
   */
  @FXML
  private void initialize()  { 
    tfThreads.setText(gm.getThreads());
    
    tfPrefix.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setPrefix(newValue);
    });
    
    tfNanoporeWorkspace.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setOntReadsWorkspace(newValue);
    });
    
    tfIlluminaWorkspace.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setIlluminaReadsWorkspace(newValue);
    });
    
    tfOutputPath.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setOutputPath(newValue);
    });
    
    tfThreads.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setThreads(newValue);
    });

    tfSelectedBarcode.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setSelectedBarcode(newValue);
    });
    
    tfPrefix.textProperty().addListener((observable, oldValue, newValue) -> {
      gm.setPrefix(newValue);
    });
  }
  
  /**
   * Called when select Nanopore reads workspace button is clicked.
   * Error check:
   * 01. No FAST5 or FASTQ file is found in the directory.
   */
  @FXML
  private void handleSelectNanoporeWorkspace() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedDirectory = directoryChooser.showDialog(null);
    if (selectedDirectory != null) {
      if (ckUtil.checkDirectoryValidity(selectedDirectory,"fast5") || ckUtil.checkDirectoryValidity(selectedDirectory,"fastq")) {
        tfNanoporeWorkspace.setText(selectedDirectory.toString()); 
      } else {
        tfNanoporeWorkspace.setText("");
        pUtil.createAlertDialog(AlertType.ERROR, "Wrong ONT workspace.", "No FAST5/FASTQ file(s) found in this directory.");
      }
    } 
  }
  
  /**
   * Called when select Illumina reads workspace button is clicked.
   * If the check is pass and there is only one pair of Illumina reads, set the Prefix to this pair of Illumina reads.
   * Error check:
   * 01. The name structure is wrong.
   */
  @FXML
  private void handleSelectIlluminaWorkspace() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedDirectory = directoryChooser.showDialog(null);
    if (selectedDirectory != null) {
      if (ckUtil.checkIlluminaReadsWithHq(selectedDirectory)) {
        tfIlluminaWorkspace.setText(selectedDirectory.toString()); 
        if (pUtil.getIlluminaReadsPrefix(selectedDirectory).size() == 1) {
          tfPrefix.setText(pUtil.getIlluminaReadsPrefix(selectedDirectory).get(0));
        } else {
          tfPrefix.setText("");
        }
      } else if (ckUtil.checkIlluminaReadsWithoutHq(selectedDirectory)) {
        tfIlluminaWorkspace.setText(selectedDirectory.toString()); 
        pUtil.createAlertDialog(AlertType.INFORMATION, "", "Illumina reads will be trimmed.");
        if (pUtil.getIlluminaReadsPrefix(selectedDirectory).size() == 1) {
          tfPrefix.setText(pUtil.getIlluminaReadsPrefix(selectedDirectory).get(0));
        } else {
          tfPrefix.setText("");
        }
      } else {
        pUtil.createAlertDialog(AlertType.ERROR, "Wrong Illumina directory.", "This directory is not valid.");
        tfIlluminaWorkspace.setText(""); 
        tfPrefix.setText("");
      }
    }
  }
  
  /**
   * Called when select output directory button is clicked.
   */
  @FXML
  private void handleSelectOutputDirectory() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedDirectory = directoryChooser.showDialog(null);
    if (selectedDirectory != null) {
      tfOutputPath.setText(selectedDirectory.toString());
    }
  }
  
  /**
   * Reads the sample sheet.
   * Error check:
   * The name structure in the sample sheet is wrong.
   */
  @FXML
  private void handleReadSampleSheet() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("CSV/TSV Files", "*.csv", "*.CSV", "*.tsv", "*.TSV"));
    File sampleSheetFile = fileChooser.showOpenDialog(null);
    if (sampleSheetFile != null) {
      String sSampleSheet = sampleSheetFile.toString();
      String sExtension = pUtil.getFileExtension(sSampleSheet);
      if (ckUtil.checkSampleSheet(sSampleSheet, sExtension)) {
        tfSampleSheet.setText(sSampleSheet);
        gm.setSampleSheetContent(pUtil.formatSampleSheetContent(pUtil.getSampleSheetContent(sSampleSheet, sExtension)));
        gm.setSampleSheetPath(sSampleSheet);
      } else {
        pUtil.createAlertDialog(AlertType.ERROR, "Wrong sample sheet.", "The format of sample sheet is wrong.");
      };
    }
  }
}
