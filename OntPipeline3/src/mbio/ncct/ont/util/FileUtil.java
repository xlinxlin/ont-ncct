package mbio.ncct.ont.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mbio.ncct.ont.model.AssemblyModel;
import mbio.ncct.ont.model.BaseCallingModel;
import mbio.ncct.ont.model.DemultiplexingModel;
import mbio.ncct.ont.model.GeneralModel;
import mbio.ncct.ont.model.PolishingModel;
import mbio.ncct.ont.model.ReadsFilterModel;

public class FileUtil {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(FileUtil.class);
  
  /** Initializes a ReadPropertyUtil object. */
  private ReadPropertyUtil rpUtil = new ReadPropertyUtil();
  
  /** Initializes a BashUtil object. */
  private BashUtil bUtil = new BashUtil();
  
  /** Initializes a PipelineUtil object. */
  private PipelineUtil pUtil = new PipelineUtil();
  
  /**
   * Creates a PBS file filled with the input parameters.
   * @param gm A GeneralModel object.
   * @param bcm A BaseCallingModel object.
   * @param dm A DemultiplexingModel object.
   * @param rfm A ReadsFilterModel object.
   * @param am A AssemblyModel object.
   * @param pm A PolishingModel object.
   * @param timestamp The current date and time yyyyMMdd_HHmmss.
   */
  public void createPbsFile(GeneralModel gm, BaseCallingModel bcm, DemultiplexingModel dm, ReadsFilterModel rfm, AssemblyModel am, PolishingModel pm, String timestamp) {
    if ( !bcm.getFlowcellId().equals("FLO-MIN107") && bcm.getGuppyMode().equals("fast")) {
      Map<String, String> combinationFlowcellKit = null;
      String cfg = null;
      combinationFlowcellKit = bUtil.getCombinationFlowcellKit();
      cfg = combinationFlowcellKit.get(bcm.getFlowcellId().concat(bcm.getKitNumber())).toString();
      int cfg_bps = cfg.indexOf("bps");
      String cfgFile = ( cfg.substring(0, cfg_bps + 3) + "_fast" ) + ( bcm.getDevice().equals("PromethION") ? "_prom" : "" ) + ".cfg";
      bcm.setIfGuppyFast(true);
      bcm.setGuppyCfgFile(rpUtil.getGuppyUrl() + "/data/" + cfgFile);
    }
    
    Path path = Paths.get(rpUtil.getPbsUrl());
    Path newPath = Paths.get(gm.getOutputPath() + "/pipelineWithLoop_" + timestamp + ".pbs");
    Charset charset = StandardCharsets.UTF_8;

    String content = null;
    try {
      content = new String(Files.readAllBytes(path), charset);
    } catch (Exception e) {
      logger.error("Can not read the .pbs template. " + e);
    }
    
    content = content.replaceAll("\\$ONTWORKSPACE", gm.getOntReadsWorkspace())
        .replaceAll("\\$ILLUMINAWORKSPACE", gm.getIlluminaReadsWorkspace())
        .replaceAll("\\$IF_TRIMILLUMINAREADS", gm.getIfTrimIlluminaReads().toString())
        .replaceAll("\\$OUTPUTPATH", gm.getOutputPath())
        .replaceAll("\\$IF_BASECALLING", bcm.getIfBasecalling().toString())
        .replaceAll("\\$FLOWCELL_ID", bcm.getFlowcellId())
        .replaceAll("\\$KIT_NUMBER", bcm.getKitNumber())
        .replaceAll("\\$THREADS", gm.getThreads())
        .replaceAll("\\$IF_DEMULTIPLEXING", dm.getIfDemultiplexing().toString())
        .replaceAll("\\$SAMPLESHEET", gm.getSampleSheetContent())
        .replaceAll("\\$PREFIX", gm.getPrefix().isEmpty() ? "barcode" : gm.getPrefix())
        .replaceAll("\\$BARCODEKIT", dm.getBarcodeKits())
        .replaceAll("\\$IF_ADAPTERTRIMMING", rfm.getIfAdapterTrimming().toString())
        .replaceAll("\\$BARCODENUMBERS", gm.getSelectedBarcode().isEmpty() ? "" : pUtil.formatSelectedBarcodes(gm.getSelectedBarcode()))
        .replaceAll("\\$IF_READSFILTER", rfm.getIfReadsFilter().toString())
        .replaceAll("\\$SCORE", rfm.getReadScore())
        .replaceAll("\\$LENGTH", rfm.getReadLength())
        .replaceAll("\\$HEADCROP", rfm.getHeadCrop())
        .replaceAll("\\$IF_ASSEMBLY", am.getIfAssembly().toString())
        .replaceAll("\\$IF_VCF", am.getIfVcf().toString())
        .replaceAll("\\$MODE", am.getMode())
        .replaceAll("\\$METHOD", am.getMethod())
        .replaceAll("\\$IF_POLISHING", pm.getIfPolishing().toString())
        .replaceAll("\\$IF_BUSCO", pm.getIfBusco().toString())
        .replaceAll("\\$LINEAGE", pm.getBuscoData())
        .replaceAll("\\$PTIMES", pm.getPtimes())
        .replaceAll("\\$IF_GUPPYFAST", bcm.getIfGuppyFast().toString())
        .replaceAll("\\$CFG_FILE", bcm.getGuppyCfgFile());
   try {
      Files.write(newPath, content.getBytes(charset));
    } catch (Exception e) {
      logger.error("Can not write .pbs file. " + e);
    }
  }
  
  /**
   * Creates an user log file with all the input parameters.
   * @param gm A GeneralModel object.
   * @param bcm A BaseCallingModel object.
   * @param dm A DemultiplexingModel object.
   * @param rfm A ReadsFilterModel object.
   * @param am A AssemblyModel object.
   * @param pm A PolishingModel object.
   * @param timestamp the current date and time: yyyyMMdd_HHmmss.
   */
  public void createUserLog(GeneralModel gm, BaseCallingModel bcm, DemultiplexingModel dm, ReadsFilterModel rfm, AssemblyModel am, PolishingModel pm, String timestamp) {
    String path = gm.getOutputPath() + "/userlog_" + timestamp + ".log";
    File f = new File(path);
    f.getParentFile().mkdirs(); 
    try {
     f.createNewFile();
    } catch (Exception e) {
      logger.error("Can not create user log file. " + e);
    }
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
      writer.append("====General Settings====\n");
      writer.append("Nanopore reads directory: " + gm.getOntReadsWorkspace() + "\n");
      writer.append("Illumina reads directory: " + (gm.getIlluminaReadsWorkspace().isEmpty() ? "Not given." : gm.getIlluminaReadsWorkspace()) + "\n");
      if (!gm.getIlluminaReadsWorkspace().isEmpty()) {
        writer.append("Trim Illumina reads:" + gm.getIfTrimIlluminaReads().toString());
      }
      writer.append("Output directory: " + gm.getOutputPath() + "\n");
      writer.append("Sample sheet path: " + (gm.getSampleSheetPath().isEmpty() ? "Not given." : gm.getSampleSheetPath()) + "\n");
      writer.append("Prefix: " + (gm.getPrefix().isEmpty() ? "Not given." : gm.getPrefix()) + "\n");
      writer.append("Threads: " + gm.getThreads() + "\n");
      writer.append("Selected barcodes: " + ( gm.getSelectedBarcode().isEmpty() ? "Default: all. " : pUtil.formatSelectedBarcodes(gm.getSelectedBarcode()) ) + "\n");
      if (bcm.getIfBasecalling()) {
        writer.append("\n====Basecalling Settings====\n");
        writer.append("Flowcell ID: " + bcm.getFlowcellId() + " \n");
        writer.append("Kit number: " + bcm.getKitNumber() + " \n");
        writer.append("Guppy mode: " + bcm.getGuppyMode() + " \n");
        if(bcm.getIfGuppyFast()) {
          writer.append("Guppy config file: " + bcm.getGuppyCfgFile() + " \n");
        }
        writer.append("Device: " + bcm.getDevice() + " \n");
      } else {
        writer.append("\n====No Basecalling====\n");
      }
      if (dm.getIfDemultiplexing()) {
        writer.append("\n====Demultiplexing Settings====\n");
        writer.append("Barcode kits: " + (dm.getBarcodeKits().isEmpty() ? "" : dm.getBarcodeKits()) + " \n"); 
      } else {
        writer.append("\n====No Demultiplexing====\n");
      }
      if (rfm.getIfReadsFilter()) {
        writer.append("\n====Reads Filter Settings====\n");
        writer.append("Read score: " + rfm.getReadScore() + " \n");
        writer.append("Read length: " + rfm.getReadLength() + " \n");
        writer.append("Head crop: " + rfm.getHeadCrop() + " \n");
        writer.append("If adapter trimming: " + rfm.getIfAdapterTrimming() + " \n");
        if (rfm.getIfAdapterTrimming()) {
          writer.append("If split reads: " + rfm.getIfNoSplit() + "\n"); 
        }
      } else {
        writer.append("\n====No Reads Filter====\n");
      }
      if (am.getIfAssembly()) {
        writer.append("\n====Assembly Settings====\n");
        writer.append("Assembly mode: " + am.getMode() + " \n");
        writer.append("Assembly method: " + am.getMethod() + " \n");
        writer.append("VCF: " + am.getIfVcf() + " \n");
      } else {
        writer.append("\n====No Assembly====\n");
      }
      if (pm.getIfPolishing()) {
        writer.append("\n====Polishing Settings====\n");
        writer.append("Polishing times: " + pm.getPtimes() + " \n");
        writer.append("BUSCO: " + pm.getIfBusco() + " \n");
        if (pm.getIfBusco()) {
          writer.append("BUSCO database: " + pm.getBuscoData() + " \n"); 
        }
      } else {
        writer.append("\n====No Polishing====\n");
      }
      writer.close();
    } catch (Exception e) {
      logger.error("Can not create user log file. " +  e);
    }
  }
}