package application;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;

import edu.cmu.sphinx.linguist.acoustic.tiedstate.HTK.Lab;

import javax.sound.sampled.TargetDataLine;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import twitter4j.TwitterException;
import twitteraApp.Permissions;

public class HomeController {

    @FXML
    private ImageView exit;

    @FXML
    private ImageView Logo;
    @FXML
    private ImageView googleCalendarIcon;

    @FXML
    private ImageView weatherIcon;

    @FXML
    private ImageView timeDateIcon;

    @FXML
    private ImageView facebookicon;

    @FXML
    private ImageView twitterIcon;

    @FXML
    private ImageView youtubeIcon;

    @FXML
    private ImageView settingsIcon;
    @FXML
    private Label tweetLabel;
    @FXML
    private TextField tweetTextField;
    @FXML
    public Label showCommand;
    
    List<ImageView> pics = new ArrayList<>();
   
    private int position=0;
    private boolean twitterActivated=false;

    

    public  HomeController getController() {
        pics.add(exit);
        pics.add(weatherIcon);
        pics.add(timeDateIcon);
        pics.add(facebookicon);
        pics.add(twitterIcon);
        pics.add(youtubeIcon);
        pics.add(googleCalendarIcon);
        pics.add(settingsIcon);
        
        return this;
    }
    

    //All animations are to be placed in its own class
    public void fadein(Label label) {
        FadeTransition ft = new FadeTransition(Duration.millis(3000),label);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
       // ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(false);
        ft.play();
    }
    public void fadeout(Label label) {
        FadeTransition ft = new FadeTransition(Duration.millis(3000),label);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
       // ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(false);
        ft.play();
    }
    //End of animations
    
    public void highlighted(String move) throws MalformedURLException, TwitterException {
        


        if(move.equals("left")) {
            showCommand.setVisible(true);
           Platform.runLater(()->showCommand.setText("LEFT"));
            Platform.runLater(()->fadein(showCommand));
            prev();
            
        }
        if (move.equals("right")) {
            
            Platform.runLater(()-> showCommand.setText("RIGHT"));
            next();
            
            
        }
        if(move.equals("up")) {          
            selected();
        }
        if(move.equals("down")) {
            complete();
        }
        
        
    }
    

    
    private void complete() throws TwitterException {
        if (twitterActivated) {
            twitterActivated=false;
            tweetLabel.setVisible(false);
           tweetTextField.setOpacity(0);
            
           
            
            Permissions tweet = new Permissions(tweetTextField.getText());
            
            
            System.out.println("Tweet [ " +tweetTextField.getText()+" ] was sucessfully sent out.");
           
            
        }
        
    }



    private void selected() {
        if(pics.get(position).equals(twitterIcon)) {
            
            twitterActivated=true;
            tweetLabel.setVisible(true);
            tweetTextField.setOpacity(1);
        }
        
    }



    private void next() {
        if(position == pics.size()) {
            pics.get(position).setOpacity(.5);
            position = 0;
            pics.get(position).setOpacity(1);
            
        }else {
            position++;
            pics.get(position).setOpacity(1);
            pics.get(position-1).setOpacity(.5);
            
        }
    }
    
    private void prev() {
        if(position==0) {
            pics.get(position).setOpacity(.5);
            position=pics.size();
            pics.get(position).setOpacity(1);
            
        }else {
            position--;
            pics.get(position).setOpacity(1);
            pics.get(position+1).setOpacity(.5);
        }
        
    }
    
     
    

   
}
