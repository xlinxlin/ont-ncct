package mbio.ncct.ont.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mbio.ncct.ont.model.AssemblyModel;
import mbio.ncct.ont.model.BaseCallingModel;
import mbio.ncct.ont.model.DemultiplexingModel;
import mbio.ncct.ont.model.GeneralModel;
import mbio.ncct.ont.model.PolishingModel;
import mbio.ncct.ont.model.ReadsFilterModel;
import mbio.ncct.ont.util.PipelineUtil;

/**
 * This is the pipeline utilities with check functions.
 * 
 * @author Yan Zhou
 * created on 2019/06/18
 */
public class CheckUtil {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(CheckUtil.class);
  
  /** Initializes PipelineUtil object. */
  private PipelineUtil pUtil = new PipelineUtil();
  
  /**
   * Checks if the ONT directory is empty.
   * @param gm A GeneralModel object.
   * @return true if the ONT directory is not empty.
   */
  public Boolean checkOntDirIsNotEmpty(GeneralModel gm) {
    return !gm.getOntReadsWorkspace().isEmpty();
  }

  /**
   * Checks if the output directory is empty.
   * @param gm A GeneralModel object.
   * @return true if the output directory is not empty.
   */
  public Boolean checkOutDirIsNotEmpty(GeneralModel gm) {
    return !gm.getOntReadsWorkspace().isEmpty();
  }
  
  /**
   * Checks if the format of threads is correct.
   * @param gm A GeneralModel object.
   * @return true if the format of threads is correct.
   */
  public Boolean checkThreadsFormat(GeneralModel gm) {
    return gm.getThreads().matches(("\\d+"));
  }
  
  /**
   * Checks if at least one module of pipeline is selected.
   * @param bcm A BaseCallingModel object.
   * @param dm A DemultiplexingModel object.
   * @param rfm A ReadsFilterModel object.
   * @param am An AssemblyModel object.
   * @param pm A PolishingModel object.
   * @return true if at least one module is selected.
   */
  public Boolean checkAtLeastOneModuleIsSelected(BaseCallingModel bcm, DemultiplexingModel dm, ReadsFilterModel rfm, AssemblyModel am, PolishingModel pm) {
    return bcm.getIfBasecalling() || dm.getIfDemultiplexing() || rfm.getIfReadsFilter() || am.getIfAssembly() || pm.getIfPolishing();
  }
  
  /**
   * Checks if the format of the selected barcodes is correct.
   * @param gm A GeneralModel object.
   * @return true if the format of the selected barcodes is correct.
   */
  public Boolean checkSelectedBarcodesFormat(GeneralModel gm) {
    return gm.getSelectedBarcode().matches("([123456789],{0,1})*");
  }
  
  /**
   * Checks if base calling with FAST5 files.
   * @param gm A GeneralModel object.
   * @param bcm A BaseCallingModel object.
   * @return true if base calling with FAST5 files.
   */
  public Boolean checkBaseCallingWithFast5(BaseCallingModel bcm, GeneralModel gm) {
    Boolean result = false;
    if(bcm.getIfBasecalling()) {
      result = checkDirectoryValidity(new File(gm.getOntReadsWorkspace()), "fast5") ? true : false;
    } else {
      result = checkDirectoryValidity(new File(gm.getOntReadsWorkspace()), "fast5") ? false : true;
    }
    return result;
  }
  
  /**
   * Checks if hybrid assembly with Illumina reads.
   * @param gm A GeneralModel object.
   * @param am An AssemblyModel object.
   * @return true if hybrid assembly with Illumina reads.
   */
  public Boolean checkHybridAssemblyWithIlluminaReads(AssemblyModel am, GeneralModel gm) {
    return am.getIfAssembly() && am.getMethod().equals("Hybrid assembly") ? 
        (!gm.getIlluminaReadsWorkspace().isEmpty() ? true : false) : true;
  }
  
  /**
   * Checks if polishing with Illumina reads.
   * @param gm A GeneralModel object.
   * @param pm A PolishingModel object.
   * @return true if polishing with Illumina reads.
   */
  public Boolean checkPolishingWithIlluminaReads(PolishingModel pm, GeneralModel gm) {
    return pm.getIfPolishing() ? ( gm.getIlluminaReadsWorkspace().isEmpty() ? false : true) : true;
  }
  
  /**
   * Checks if device with the correct flowcell ID when guppy_basecaller fast mode is used.<br>
   * Correct combinations:<br>
   * "PromethION" with "FLO-PRO", "MinION/GridION/MinIT" with "FLO-MIN" 
   * @param bcm A BaseCallingModel object.
   * @return true if device with the correct flowcell ID when guppy_basecaller fast mode is used.
   */
  public Boolean checkDeviceWithCorrectFlowcellInFasfMode(BaseCallingModel bcm) {
    return bcm.getIfGuppyFast() ? 
        (bcm.getDevice().equals("PromethION") ? 
            (bcm.getFlowcellId().startsWith("FLO-PRO") ? true: false) : (bcm.getFlowcellId().startsWith("FLO-MIN") ? true: false)) : true;
  }
  
  /**
   * Checks if the prefixes are correct when the format of ONT read is FASTQ and assembly or/and polishing module(s) is/are selected.
   * @param gm A GeneralModel object.
   * @param am An AssemblyModel object.
   * @param pm A PolishingModel object.
   * @return true if the prefixes are correct.
   */
  public Boolean CheckPrefixIfOntFastqWithHybridAssemblyOrPolishing(GeneralModel gm, AssemblyModel am, PolishingModel pm) {
    Boolean result = true;
    if(checkDirectoryValidity(new File(gm.getOntReadsWorkspace()), "fastq")) {
      if(am.getIfAssembly() && am.getMethod().equals("Hybrid assembly") || pm.getIfPolishing()) {
        result = checkOntReadsPrefix(new File(gm.getOntReadsWorkspace()), new File(gm.getIlluminaReadsWorkspace()));
      }
    }
    return result;
  }
  
  /**
   * Checks if the directory has the correct files with the given extension.
   * @param selectedDirectory the given directory to be checked.
   * @param extension the given extension to be checked in the directory
   * @return the Boolean value if the directory is valid.
   */
  public Boolean checkDirectoryValidity(File selectedDirectory, String extension) {
    boolean result = false;
    File[] f = selectedDirectory.listFiles();
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile() && pUtil.getFileExtension(f[i].getName()).toLowerCase().equals(extension)) {
        result = true;
        break;
      } 
    }
    return result;
  }
  
  /**
   * Checks if the prefixes ONT reads are unique and if the set of Illumina prefixes contains all ONT reads prefixes.
   * @param ontDirectory the path of ONT reads directory.
   * @param illuminaDirectory the path of Illumina reads directory.
   * @return true if all prefixes ONT reads are unique and the set of Illumina prefixes contains all ONT reads prefixes.
   */
  public Boolean checkOntReadsPrefix(File ontDirectory, File illuminaDirectory) {
    ArrayList<String> alOntPrefixes = pUtil.getOntReadsPrefix(ontDirectory);
    ArrayList<String> alIlluminaPrefixes = pUtil.getIlluminaReadsPrefix(illuminaDirectory);
    Set<String> stOntPrefixes = new HashSet<String>(alOntPrefixes);
    return (stOntPrefixes.size() != alOntPrefixes.size() || !alIlluminaPrefixes.containsAll(alOntPrefixes)) ? false : true;
  }
  
  /**
   * Checks the Illumina reads directory.<br>
   * Illumina reads name structure (trimming free) example:<br>
   * ID40_HQ_1.fastq.gz ID40_HQ_2.fastq.gz
   * @param illuminaDirectory the path to the Illumina reads directory.
   * @return the Boolean value if the Illumina reads directory is valid. 
   */
  public Boolean checkIlluminaReadsWithHq(File illuminaDirectory) {
    Boolean validity = true;
    File[] f = illuminaDirectory.listFiles();
    ArrayList<String> alFR1 = new ArrayList<String>();
    ArrayList<String> alFR2 = new ArrayList<String>();
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile() && f[i].getName().contains("_")) {
        String prefix = f[i].getName().substring(0, f[i].getName().indexOf("_"));
        if(f[i].getName().matches(".*_HQ_1\\.fastq\\.gz") && !alFR1.contains(prefix)) {
          alFR1.add(prefix);
        } else if (f[i].getName().matches(".*_HQ_2\\.fastq\\.gz") && !alFR2.contains(prefix)) {
          alFR2.add(prefix);
        } else {
          validity = false;
          break;
        }
      }
    }
    if ( !alFR1.containsAll(alFR2) || !alFR2.containsAll(alFR1) || alFR1.isEmpty()) {
      validity = false;
    }
    return validity;
  }
  
  /**
   * Checks the Illumina reads directory.<br>
   * Illumina reads name structure (must trimming) example:<br>
   * ID40_1.fastq.gz ID40_2.fastq.gz
   * @param illuminaDirectory the path to the Illumina reads directory.
   * @return the Boolean value if the Illumina reads directory is valid. 
   */
  public Boolean checkIlluminaReadsWithoutHq(File illuminaDirectory) {
    Boolean validity = true;
    File[] f = illuminaDirectory.listFiles();
    ArrayList<String> alFR1 = new ArrayList<String>();
    ArrayList<String> alFR2 = new ArrayList<String>();
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile() && f[i].getName().contains("_")) {
        String prefix = f[i].getName().substring(0, f[i].getName().indexOf("_"));
        if(f[i].getName().matches(".*_1\\.fastq\\.gz") && !alFR1.contains(prefix)) {
          alFR1.add(prefix);
        } else if (f[i].getName().matches(".*_2\\.fastq\\.gz") && !alFR2.contains(prefix)) {
          alFR2.add(prefix);
        } else {
          validity = false;
          break;
        }
      }
    }
    if ( !alFR1.containsAll(alFR2) || !alFR2.containsAll(alFR1) || alFR1.isEmpty()) {
      validity = false;
    }
    return validity;
  }
  
  /**
   * Checks if the sample sheet has the correct content.<br>
   * Sample sheet example see: <br>
   * https://ontpipeline2.readthedocs.io/en/latest/InputFileStructure.html#sample-sheet
   * @param sampleSheet the String of sample sheet file path
   * @param extension the String of the sample sheet extension.
   * @return the Boolean value if the sample sheet correct is.
   */
  public Boolean checkSampleSheet(String sampleSheet, String extension) {
    Boolean result = true;
    ArrayList<String> sampleNames = new ArrayList<String>();
    ArrayList<String> barcodeNames = new ArrayList<String>();
    String ch = (extension.equals("csv".toLowerCase()) ? "," : "\t");
    try (BufferedReader br = new BufferedReader(new FileReader(sampleSheet))) {
      String line;
      line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] values = line.split(ch);
        if (!values[1].substring(values[1].length() - 2).matches(".*\\d\\d") || sampleNames.contains(values[0]) || barcodeNames.contains(values[1])) {
          result = false;
          break;
        } else {
          sampleNames.add(values[0]);
          barcodeNames.add(values[1].substring(values[1].length() - 2)); 
        }
      }
    } catch (Exception e) {
      logger.error("Can not read sample sheet." + e);
    }
    return result;
  }
}