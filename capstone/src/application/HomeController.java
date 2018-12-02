package application;

/**
 * @author Tyre King
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;

import edu.cmu.sphinx.linguist.acoustic.tiedstate.HTK.Lab;

import javax.sound.sampled.TargetDataLine;

import org.json.JSONException;

import com.restfb.types.Page.VoipInfo;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import twitter4j.TwitterException;
import twitteraApp.Permissions;
import weatherApp.WeatherClass;
import youtubeApp.youtubeClass;
import icons.*;

public class HomeController {

    @FXML
    private Label timeDate;
    @FXML
    private ImageView exit;

    @FXML
    private ImageView Logo;

    @FXML
    private ImageView weatherIcon;

    @FXML
    private ImageView timeDateIcon;

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

    // This displays the command on screen
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

    // VBox container for twitter feed
    @FXML
    private VBox timeLineContainer;

    @FXML
    private ImageView dailyWeatherIcon;

    @FXML
    private Text temp;

    @FXML
    private Text chanceOfRain;

    @FXML
    private Text description;

    // WebView for the embedded youTube video
    @FXML
    private WebView youtubeVideoContainer;
    // The VBox that hold the weather objects
    @FXML
    private VBox weatherContainer;

    // class Globals
    // ---------------------------------------
    // This holds all of the icons
    List<ImageView> pics = new ArrayList<>();

    // this holds the buttons
    List<Button> viewPost = new ArrayList<>();

    private int position = 0;
    private int mediaPosition = 0;
    private boolean twitterActivated = false;
    private boolean weatherActivated = false;
    private boolean timeDateActivated = false;
    private boolean youtubeActivated = false;
    private boolean mediaTab = false;
    private WeatherClass weather;
    private Image dailyweatherImage;
    // ----------------------------------------

    /**
     * This is the Constructor for the class
     * <p>
     * This constructor loads the icons and buttons into their respective Lists.
     * It creates a new weather class then returns itself.
     * 
     * @return the HomeController object
     * @throws IOException
     * @throws JSONException
     */
    public HomeController getController() throws IOException, JSONException {

        pics.add(exit);
        pics.add(weatherIcon);
        pics.add(timeDateIcon);
        pics.add(twitterIcon);
        pics.add(youtubeIcon);
        pics.add(settingsIcon);

        viewPost.add(viewPostButton);
        viewPost.add(writePostButton);

        // The Weather key is provided by OpenWeatherMap.org
        weather = new WeatherClass("Newport News",
                System.getenv("weatherKey"));
        return this;
    }

    // fade in and out animation
    private void fadein(Label label) {
        FadeTransition ft = new FadeTransition(Duration.millis(1500), label);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
    }

    /**
     * <p>
     * This method takes the command being passed through and checks to see if
     * it is equal to the known commands. If it is a known command, the method
     * will execute the command.
     * 
     * @param move  a command used to control the mirror
     * @throws TwitterException
     * @throws IOException
     * @throws JSONException
     * @throws AWTException
     */
    public void highlighted(String move)
            throws TwitterException, IOException, JSONException, AWTException {
        //checks for command
        if (move.equals("left") || move.equals("mirror left")
                || move.equals("mirror up")) {

            //if nothing is activated
            if (!twitterActivated && !weatherActivated) {
                //set the command, show the command,and run animation,
                Platform.runLater(() -> showCommand.setText(move));
                showCommand.setVisible(true);
                Platform.runLater(() -> fadein(showCommand));
                //moves the position of the user back one place in the list
                prev();
            }
            //(left command)when twitter is activated commands are now moved to button selection
            if (twitterActivated) {
                Platform.runLater(() -> nextbutton());
            }

        }
      //if twitter is activated
        if (move.equals("right") || move.equals("mirror right")
                || move.equals("mirror down")) {
            
            //set the command, show the command,and run animation,
            if (!twitterActivated) {
                Platform.runLater(() -> showCommand.setText(move));
                showCommand.setVisible(true);
                Platform.runLater(() -> fadein(showCommand));
                
                //moves the position of the user forward one place in the list
                next();
            }
            //(right command)when twitter is activated commands are now moved to button selection
            if (twitterActivated) {
                Platform.runLater(() -> nextbutton());
            }

        }
        //checks commands
        //these are the commands for selection
        if (move.equals("up") || move.equals("mirror select")) {
          
            if (mediaTab == true) {
                selectedMediaTab();
            }
            else {
                selected();
            }
        }
        //checks commands
        if (move.equals("down") || move.equals("mirror confirm")
                || move.equals("mirror post tweet")
                || move.equals("mirror search")) {
            
            //completes action wanted by the user
            complete();
        }
        // resets all values and hides features
        if (move.equals("mirror cancel")) {
            cancel();
        }
        // play and pause the youTube video
        if (move.equals("mirror play") || move.equals("mirror pause")) {
            playPause();
        }
        //activates the mirror to execute commands
        if (move.equals("mirror mirror")) {
            Platform.runLater(() -> showCommand.setText(":)"));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));
        }
        //makes the mirror stop doing commands
        if (move.equals("mirror stop listening")) {
            Platform.runLater(() -> showCommand.setText("Zzz..."));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));
        }
        //shows twitter feed
        if(move.equals("mirror show twitter feed")) {
            cancel();
            mediaPosition =0;
            selectedMediaTab();
        }
        
        // opens the twitter menu
        if(move.equals("mirror open twitter")) {
            cancel();
            position=3;
            selected();
        }
        //shows twitter text field
        if(move.equals("mirror create tweet")) {
            cancel();
            mediaPosition=1;
            selectedMediaTab();
        }
        if(move.equals("mirror open youtube")) {
            cancel();
            position=4;
            selected();
        }
        if(move.equals("mirror show weather")) {
            cancel();
            showWeather();
        }
        if(move.equals("mirror show time")) {
            cancel();
            showTime();
        }
        
        
    }

    //executes actions based on what is activated
    private void complete()
            throws TwitterException, IOException, AWTException {
        if (twitterActivated) {
           //turn off twitter
            twitterActivated = false;

            Platform.runLater(() -> mediaChoiceBox.setVisible(false));
            mediaLabel.setVisible(false);
            mediaTab = false;

            tweetLabel.setVisible(false);
            tweetTextField.setOpacity(0);

            //create a new tweet with the values in the text field
            new Permissions().createTweet(tweetTextField.getText());
            
            // show confirmation that the tweet has been posted
            Platform.runLater(() -> showCommand.setText("SENT"));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));

            System.out.println("Tweet [ " + tweetTextField.getText()
                    + " ] was sucessfully sent out.");
            tweetTextField.clear();
        }
        if (youtubeActivated) {
            
            //turns off youTube
            tweetLabel.setVisible(false);
            tweetTextField.setOpacity(0);
            youtubeVideoContainer.setOpacity(1);
            
            //method call to show embedded youTube video in the webView
            grabVideo();
        }

    }

    // secondary selection menu for twitter
    private void selectedMediaTab() throws TwitterException {
        if (viewPost.get(mediaPosition).equals(writePostButton)) {
            //show text field and label
            Platform.runLater(()->tweetLabel.setText("Post A Tweet"));
            tweetTextField.setOpacity(1);
            tweetLabel.setOpacity(1);
            
           
        }
        else {
            //show twitter feed container
            Platform.runLater(() -> timeLineContainer.setVisible(true));
            //get a list of the times of user tweets
            List<Date> time = new Permissions().getTweetTime();
            //get a list of user tweets
            List<String> post = new Permissions().getTimeLine();
            
            //sets the containers to the tweets
            Platform.runLater(() -> {
                try {
                    tweetOne.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(0) + "\r\n" + time.get(0));
                    tweetTwo.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(1) + "\r\n" + time.get(1));
                    tweetThree.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(2) + "\r\n" + time.get(2));
                }
                catch (TwitterException e) {
                    e.printStackTrace();
                }
            });
            
            //sets more container tweets
            Platform.runLater(() -> {
                try {
                    TweetFour.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(3) + "\r\n" + time.get(3));
                    tweetFive.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(4) + "\r\n" + time.get(4));
                }
                catch (TwitterException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    //user selection process
    private void selected() throws IOException, JSONException {
        //if user highlighted the twitter icon
        if (pics.get(position).equals(twitterIcon)) {
            //activate and show twitter
            twitterActivated = true;
            Platform.runLater(() -> mediaLabel.setText("Twitter"));
            Platform.runLater(() -> mediaLabel.setVisible(true));
            Platform.runLater(() -> mediaChoiceBox.setVisible(true));
            mediaTab = true;

        }
        //if user highlighted timeDateIcon and selected
        if (pics.get(position).equals(timeDateIcon)) {
            showTime();
        }
        //user highlighted weatherIcon and selected
        if (pics.get(position).equals(weatherIcon)) {
            showWeather();
        }
        //user highlighted the youtubeIcon and selected
        if (pics.get(position).equals(youtubeIcon)) {
            Platform.runLater(() -> tweetLabel.setText("Youtube"));
            tweetTextField.setOpacity(1);
            Platform.runLater(()->tweetTextField.setVisible(true));
            tweetLabel.setOpacity(1);
//            Platform.runLater(() -> tweetLabel.setText("Youtube"));
            youtubeActivated = true;

        }
        //exits the program
        if (pics.get(position).equals(exit)) {
            Platform.exit();
        }
    }

    
    private void showWeather() {
      //turns off weather
        if (weatherActivated) {
            weatherActivated = false;
            weatherContainer.setOpacity(0);
        }
        else {
            //activates the weather
            weatherActivated = true;

            //sets the weather temperature
            Platform.runLater(() -> temp
                    .setText(Double.toString(Math.floor(weather.getTemp()))
                            + " Degrees"));
            //sets the description of the weather
            Platform.runLater(
                    () -> description.setText(weather.getDescription()));
           
            //shows the weather
            weatherContainer.setOpacity(1);
            
            
            //sets the image with corresponding weather type
            //--------------------------------------------------------------
            if (weather.getWeatherType().equals("Clear")) {
                dailyweatherImage = new Image(
                        "icons/icons8-partly-cloudy-day-64.png");
                Platform.runLater(
                        () -> dailyWeatherIcon.setImage(dailyweatherImage));

            }
            if (weather.getWeatherType().equals("Clouds")) {
                dailyweatherImage = new Image("icons/icons8-sun-64.png");
                Platform.runLater(
                        () -> dailyWeatherIcon.setImage(dailyweatherImage));
            }
            if (weather.getWeatherType().equals("Drizzle")) {
                dailyweatherImage = new Image("icons/icons8-rain-64.png");
                Platform.runLater(
                        () -> dailyWeatherIcon.setImage(dailyweatherImage));
            }
            if (weather.getWeatherType().equals("Rain")) {

                dailyweatherImage = new Image("icons/icons8-rain-64.png");
                Platform.runLater(
                        () -> dailyWeatherIcon.setImage(dailyweatherImage));
            }
            //---------------------------------------------------------------

        }
    }

    //show the time and date 
    private void showTime() throws IOException, JSONException {
        if (timeDateActivated) {
            timeDateActivated = false;
            Platform.runLater(() -> timeDate.setVisible(false));

        }
        else {
            timeDateActivated = true;
            Platform.runLater(() -> timeDate.setVisible(true));
            Platform.runLater(
                    () -> timeDate.setText(weather.getTime().toString()));
        }

    }
    //movement for the media tab
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

    //next movement for icons
    private void next() throws IOException {
        if (position == pics.size()) {
            pics.get(position).setOpacity(.5);
            position = 0;
            pics.get(position).setOpacity(1);
        }
        else {
            position++;
            pics.get(position).setOpacity(1);
            pics.get(position - 1).setOpacity(.5);
        }
    }

    //previous movement for icons
    private void prev() {
        if (position == 0) {
            pics.get(position).setOpacity(.5);
            position = pics.size() - 1;
            pics.get(position).setOpacity(1);

        }
        else {
            position--;
            System.out.println(position);
            Platform.runLater(() -> pics.get(position).setOpacity(1));
            pics.get(position + 1).setOpacity(.5);

        }

    }

    private void cancel() throws IOException, JSONException, AWTException {
        // create command mirror cancel
        // call cancel, sets all values back to default and closes everything in
        // the running application.
        twitterActivated = false;

        // Icon value reset to beginning
        pics.get(position).setOpacity(.5);
        // position counter set to beginning
        position = 0;
        pics.get(position).setOpacity(.5);
        pics.get(position).setOpacity(1);
        // clear text field
        tweetTextField.clear();

        // close everything
        Platform.runLater(() -> mediaChoiceBox.setVisible(false));
        mediaLabel.setVisible(false);
        mediaTab = false;

        tweetLabel.setOpacity(0);
        tweetTextField.setOpacity(0);
        Platform.runLater(() -> timeLineContainer.setVisible(false));
        viewPost.get(0).setOpacity(.5);
        viewPost.get(1).setOpacity(.5);

        youtubeVideoContainer.setOpacity(0);

        // resets time and date
        timeDateActivated = true;
        showTime();

        // pause video and hide it
        if (youtubeActivated) {
            playPause();
            youtubeActivated = false;
            youtubeVideoContainer.setOpacity(0);
            click();
        }
        else {
            click();
        }
        // hides the weather
        weatherActivated = true;
        showWeather();

    }

    private void grabVideo() throws IOException {
        //takes the textfield string and searches for the video
        youtubeClass vid = new youtubeClass(tweetTextField.getText());
        if (youtubeActivated) {
            Platform.runLater(
                    () -> youtubeVideoContainer.getEngine().load(vid.send));
        }

    }

    private void playPause() throws AWTException {
        Robot bot = new Robot();
        
        // simulate mouse click on location where the video will be at all times       
        bot.mouseMove(800, 450);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }

    private void click() throws AWTException {
        Robot bot = new Robot();
        
        // simulate mouse click to help reset the application
        bot.mouseMove(800, 250);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
