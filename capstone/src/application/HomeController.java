package application;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;

import javafx.animation.FadeTransition;
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
    
    
    
    public void highlighted(String move) throws MalformedURLException, TwitterException {
        


        if(move.equals("left")) {
            
            prev();
            
        }
        if (move.equals("right")) {
            
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
