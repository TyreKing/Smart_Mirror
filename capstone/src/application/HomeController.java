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



import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
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
    
    List<ImageView> pics = new ArrayList<>();
   

    public  HomeController getController() {
        return this;
    }
    
    
    public void highlighted(String move) throws MalformedURLException, TwitterException {
        Permissions permissions = new Permissions();
            pics.add(exit);
            pics.add(weatherIcon);
            pics.add(timeDateIcon);
            pics.add(facebookicon);
            pics.add(twitterIcon);
            pics.add(youtubeIcon);
            pics.add(googleCalendarIcon);
            pics.add(settingsIcon);
            
            
            
            
           
            
        
            
        
        if(move.equals("left")) {
            exit.setOpacity(1);
            Logo.setOpacity(.5);
            
        }
        if (move.equals("right")) {
            exit.setOpacity(.5);
            Logo.setOpacity(1);
            
        }
        if(move.equals("up")) {
            
        }
        
        
    }
    
    
   
}
