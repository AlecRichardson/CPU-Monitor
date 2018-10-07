/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awrnndcpumonitorfxml;



import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author Alec Richardson <your.name at your.org>
 */
public class FXMLDocumentController implements Initializable {

  AwrnndCPUModel model = new AwrnndCPUModel();
  private boolean isRunning = false;
  private double rotate = -135;
  public Timeline timeline;
  private int recordCount = 1;
  private int recordCountPrint = 1;
  private DateFormat dateFormat;
  private Date date;
  
  @FXML 
  public ImageView dial;
  
  @FXML 
  public Button start;
  
  @FXML 
  public Button record;
  
  @FXML
  public Label CPUdigital;
  
  @FXML
  public Label record1;
  
  @FXML
  public Label record2;
  
  @FXML
  public Label record3;
  
  @FXML
  public VBox recordBox;
  
  
  
  @FXML
  private void startButtonAction(ActionEvent event){
      if(!isRunning){
          start.setText("Stop");
          start.setStyle("-fx-text-fill: #ff0000");
          timeline.play();
          isRunning = true;
          record.setText("Record");
          record.setStyle("-fx-text-fill: #1800f5");
          
      } else {
          start.setText("Start");
          start.setStyle("-fx-text-fill: #07ed0a");
          timeline.pause();
          isRunning = false;
          record.setText("Reset");
          record.setStyle("-fx-text-fill: #ff0000");
      }
  }
  
  @FXML
  private void recordButtonAction(ActionEvent event){
      dateFormat = new SimpleDateFormat("hh:mm:ss a");
      date = new Date();
      if(isRunning){
          switch(recordCount){
              case 1: 
                  record1.setText(String.format("Record %d: %.2f%% at %s", recordCountPrint, model.record(),dateFormat.format(date)));
                  recordCount++;
                  recordCountPrint++;
                  break;
              
              case 2:
                  record2.setText(String.format("Record %d: %.2f%% at %s", recordCountPrint, model.record(),dateFormat.format(date)));
                  recordCount++;
                  recordCountPrint++;
                  break;
              
              case 3:
                  record3.setText(String.format("Record %d: %.2f%% %s", recordCountPrint, model.record(),dateFormat.format(date)));
                  recordCount = 1;
                  recordCountPrint++;
                  break;
              
          }
      } else{
          reset();
      }
  }
  
  public void reset(){
      timeline.stop();
      isRunning = false;
      record1.setText("--.--%");
      record2.setText("--.--%");
      record3.setText("--.--%");
      CPUdigital.setText("--.--%");
      recordCount = 1;
      recordCountPrint = 1;
      dial.setRotate(-135);
      start.setText("Start");
      start.setStyle("-fx-text-fill: #07ed0a");
      record.setText("Record");
      record.setStyle("-fx-text-fill: #1800f5");
  }
  
  public void update(){
      CPUdigital.setText(String.format("%.2f%%", 100*AwrnndCPUModel.getCPUUsage()));
      rotate = model.getRotate();
      dial.setRotate(rotate);
  }
  
      
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           timeline = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent) -> {
            update();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }    
    
}
