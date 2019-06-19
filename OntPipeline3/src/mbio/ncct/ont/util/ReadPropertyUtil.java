package mbio.ncct.ont.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the utilities for reading the properties in the config.properties file.
 * 
 * @author Yan Zhou
 * created on 2019/06/19
 */
public class ReadPropertyUtil {
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(ReadPropertyUtil.class);
  
  /**
   * Opens the config.properties file and retrieves the URL of Guppy.
   */
  public String getGuppyUrl() {
    String guppyUrl = "";
    try (InputStream input = new FileInputStream("/opt/ontpipeline/config/config.properties")) {
      Properties prop = new Properties();
      prop.load(input);
      guppyUrl = prop.getProperty("guppyUrl");
    } catch (Exception e) {
      logger.error("Can not open config.properties file. " + e);
    }
    return guppyUrl;
  }
  
  /**
   * Opens the config.properties file and retrieves the URL of PBS template.
   */
  public String getPbsUrl() {
    String pbsUrl = "";
    try (InputStream input = new FileInputStream("/opt/ontpipeline/config/config.properties")) {
      Properties prop = new Properties();
      prop.load(input);
      pbsUrl = prop.getProperty("pbsUrl");
    } catch (Exception e) {
      logger.error("Can not open config.properties file. " + e);
    }
    return pbsUrl;
  }
  
  /**
   * Opens the config.properties file and retrieves the URL of root($HOME).
   */
  public String getRootUrl() {
    String pbsUrl = "";
    try (InputStream input = new FileInputStream("/opt/ontpipeline/config/config.properties")) {
      Properties prop = new Properties();
      prop.load(input);
      pbsUrl = prop.getProperty("userUrl");
    } catch (Exception e) {
      logger.error("Can not open config.properties file. " + e);
    }
    return pbsUrl;
  }
}