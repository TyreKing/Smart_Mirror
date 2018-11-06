package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;

import edu.cmu.sphinx.linguist.acoustic.tiedstate.HTK.Lab;

import javax.sound.sampled.TargetDataLine;

import org.json.JSONException;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import twitter4j.TwitterException;
import twitteraApp.Permissions;
import weatherApp.WeatherClass;

public class HomeController { 

    @FXML
    private Label timeDate;
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

    @FXML
    private HBox mediaChoiceBox;

    @FXML
    private Label mediaLabel;

    @FXML
    private Button viewPostButton;

    @FXML
    private Button writePostButton;
    @FXML
    private Label tweetOne;

    @FXML
    private Label tweetTwo;

    @FXML
    private Label tweetThree;

    @FXML
    private Label TweetFour;

    @FXML
    private Label tweetFive;

    @FXML
    private VBox timeLineContainer;
    
    
    List<ImageView> pics = new ArrayList<>();
    List<Button> viewPost = new ArrayList<>();

    private int position = 0;
    private int mediaPosition = 0;
    private boolean twitterActivated = false;
    private boolean weatherActivated = false;
    private boolean timeDateActivted = false;
    private boolean mediaTab = false;
    private WeatherClass weather;

    public HomeController getController() throws IOException, JSONException {

        pics.add(exit);
        pics.add(weatherIcon);
        pics.add(timeDateIcon);
        pics.add(facebookicon);
        pics.add(twitterIcon);
        pics.add(youtubeIcon);
        pics.add(googleCalendarIcon);
        pics.add(settingsIcon);

       
        viewPost.add(viewPostButton);
        viewPost.add(writePostButton);

        weather = new WeatherClass("Newport News",
                System.getenv("weatherKey"));
        return this;
    }

    // All animations are to be placed in its own class
    public void fadein(Label label) {
        FadeTransition ft = new FadeTransition(Duration.millis(1500), label);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
    }

//    public void fadeout(Label label) {
//        FadeTransition ft = new FadeTransition(Duration.millis(1500), label);
//        ft.setFromValue(1.0);
//        ft.setToValue(0);
//        ft.setCycleCount(2);
//        ft.setAutoReverse(true);
//        ft.play();
//    }
    // End of animations

    public void highlighted(String move) throws TwitterException, IOException, JSONException {

        if (move.equals("left")||move.equals("mirror left")||move.equals("mirror up")) {
            // if no other feature is activated
            if (!twitterActivated && !weatherActivated) {           
                Platform.runLater(() -> showCommand.setText(move));
                showCommand.setVisible(true);
                Platform.runLater(() -> fadein(showCommand));
                prev();
            }
            if (twitterActivated) {
                Platform.runLater(() -> nextbutton());
            }

        }
        if (move.equals("right")||move.equals("mirror right")|| move.equals("mirror down")) {
            if (!twitterActivated) {
                Platform.runLater(() -> showCommand.setText(move));
                showCommand.setVisible(true);
                Platform.runLater(() -> fadein(showCommand));
                next();
            }
            if (twitterActivated) {
                Platform.runLater(() -> nextbutton());
            }

        }
        if (move.equals("up")||move.equals("mirror select")) {
            if (mediaTab == true) {
                selectedMediaTab();
            }
            else {
                selected();
            }

        }
        if (move.equals("down")|| move.equals("mirror confirm")) {
            complete();
        }
        if(move.equals("mirror cancel")) {
            cancel();
        }

    }

    private void complete() throws TwitterException {
        if (twitterActivated) {
            twitterActivated = false;

            Platform.runLater(() -> mediaChoiceBox.setVisible(false));
            mediaLabel.setVisible(false);
            mediaTab = false;

            tweetLabel.setVisible(false);
            tweetTextField.setOpacity(0);

           //new Permissions(tweetTextField.getText());
            new Permissions().createTweet(tweetTextField.getText());

            Platform.runLater(() -> showCommand.setText("SENT"));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));

            System.out.println("Tweet [ " + tweetTextField.getText()
                    + " ] was sucessfully sent out.");

        }

    }

    private void selectedMediaTab() throws TwitterException {
        if (viewPost.get(mediaPosition).equals(writePostButton)) {
            tweetLabel.setVisible(true);
            tweetTextField.setOpacity(1);
        }
        else {
            List<Date> time =new Permissions().getTweetTime();
            System.out.println(time.get(0));
            //System.out.println(new Permissions().getScreenName());
            //List<String> post= new Permissions().getTimeLine();
            //System.out.println(post.get(0));
        }
    }

    private void selected() throws IOException, JSONException {
        if (pics.get(position).equals(twitterIcon)) {
            twitterActivated = true;
            Platform.runLater(() -> mediaLabel.setText("Twitter"));
            Platform.runLater(() -> mediaChoiceBox.setVisible(true));
            mediaTab = true;
        }
        if (pics.get(position).equals(timeDateIcon)) {
            showTime();
        }
    }

    private void showTime() throws IOException, JSONException {
        timeDateActivted = true;        
        Platform.runLater(
                () -> timeDate.setText(weather.getTime().toString()));
    }

    private void nextbutton() {
        if (viewPost.get(mediaPosition).equals(viewPostButton)) {
            viewPost.get(mediaPosition).setOpacity(.5);
            mediaPosition++;
            viewPost.get(mediaPosition).setOpacity(1);

        }
        else {
            viewPost.get(mediaPosition).setOpacity(.5);
            mediaPosition--;
            viewPost.get(mediaPosition).setOpacity(1);
        }

    }

    private void next() {
        if (position == 7) {
            pics.get(position).setOpacity(.5);
            position = 0;
            pics.get(position).setOpacity(1);
            System.out.println(position);

        }
        else {
            position++;
            pics.get(position).setOpacity(1);
            pics.get(position - 1).setOpacity(.5);
            System.out.println(position);


        }
    }

    private void prev() {
        if (position == 0) {
            pics.get(position).setOpacity(.5);
            position = 7;
            pics.get(position).setOpacity(1);
            System.out.println(position);


        }
        else {
            position--;
            pics.get(position).setOpacity(1);
            pics.get(position + 1).setOpacity(.5);
            System.out.println(position);

        }

    }
    
    private void cancel() {
        // create command mirror cancel
        // call cancel, sets all values back to default and closes everything in the running application. 
        twitterActivated = false;
        
        //position counter set to beginning
        position=0;
        //Icon value reset to beginning
        for(int i=0; i<8; i++) {
            pics.get(i).setOpacity(.5);
        }
        pics.get(position).setOpacity(1);
        
        //clear text field
        tweetTextField.clear();
        
        //close everything
        Platform.runLater(() -> mediaChoiceBox.setVisible(false));
        mediaLabel.setVisible(false);
        mediaTab = false;

        tweetLabel.setVisible(false);
        tweetTextField.setOpacity(0);
        
       
        
        
        
        
    }

}
