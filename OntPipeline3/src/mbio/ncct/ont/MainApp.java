package mbio.ncct.ont;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mbio.ncct.ont.util.PipelineUtil;
import mbio.ncct.ont.view.PipelineOverviewController;

/**
 * This is the MainApp class of the ONT pipeline GUI.
 * 
 * @author Yan Zhou
 * created on 2019/05/16
 */
public class MainApp extends Application {
  
  /** Initializes primary stage. */
  private Stage primaryStage;
  
  /** Initializes root layout. */
  private BorderPane rootLayout;
  
  /** Initializes log4j2. */
  private static Logger logger = LogManager.getLogger(PipelineUtil.class);

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("ONT Pipeline");
    initRootLayout();
    showPipelineOverview();
  }
  
  /**
   * Initializes the root layout.
   */
  private void initRootLayout() {
    try {
      // Load root layout from fxml file.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
      rootLayout = (BorderPane) loader.load();
      
      // Show the scene containing the root layout.
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      logger.error("Can not load root layout. " + e);
    }
  }
  
  /**
   * Initializes the pipeline overview layout.
   */
  private void showPipelineOverview() {   
    try {
      // Load pipeline overview.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/PipelineOverview.fxml"));
      AnchorPane pipelineOverview = (AnchorPane) loader.load();
        
      // Set overview into the center of root layout.
      rootLayout.setCenter(pipelineOverview);
      PipelineOverviewController controller = loader.getController();
      controller.setMainApp(this);
    } catch (Exception e) {
      logger.error("Can not load pipeline overview layout. " + e);
    }
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}