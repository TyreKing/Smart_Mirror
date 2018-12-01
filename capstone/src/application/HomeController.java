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

    @FXML
    private ImageView dailyWeatherIcon;

    @FXML
    private Text temp;

    @FXML
    private Text chanceOfRain;

    @FXML
    private Text description;

    @FXML
    private WebView youtubeVideoContainer;

    @FXML
    private VBox weatherContainer;

    List<ImageView> pics = new ArrayList<>();
    List<Button> viewPost = new ArrayList<>();

    private int position = 0;
    private int mediaPosition = 0;
    private boolean twitterActivated = false;
    private boolean weatherActivated = false;
    private boolean timeDateActivated = false;
    private boolean youtubeActivated = false;
    private boolean videoPlaying = false;
    private boolean mediaTab = false;
    private WeatherClass weather;
    private Image dailyweatherImage;

    public HomeController getController() throws IOException, JSONException {

        pics.add(exit);
        pics.add(weatherIcon);
        pics.add(timeDateIcon);
        pics.add(twitterIcon);
        pics.add(youtubeIcon);
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

    public void highlighted(String move)
            throws TwitterException, IOException, JSONException, AWTException {

        if (move.equals("left") || move.equals("mirror left")
                || move.equals("mirror up")) {
            // if no other feature is activated
//            if (!twitterActivated && !weatherActivated) {
//                Platform.runLater(() -> showCommand.setText(move));
//                showCommand.setVisible(true);
//                Platform.runLater(() -> fadein(showCommand));
//                prev();
//            }
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
        if (move.equals("right") || move.equals("mirror right")
                || move.equals("mirror down")) {

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
        if (move.equals("up") || move.equals("mirror select")) {
            if (mediaTab == true) {
                selectedMediaTab();
            }
            else {
                selected();
            }

        }
        if (move.equals("down") || move.equals("mirror confirm")
                || move.equals("mirror send")
                || move.equals("mirror Search")) {
            complete();
        }
        if (move.equals("mirror cancel")) {
            cancel();
        }
        if (move.equals("mirror play") || move.equals("mirror pause")) {
            playPause();
        }
        if (move.equals("mirror mirror")) {
            Platform.runLater(() -> showCommand.setText(":)"));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));
        }
        if (move.equals("mirror stop listening")) {
            Platform.runLater(() -> showCommand.setText("Zzz..."));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));
        }
    }

    private void complete()
            throws TwitterException, IOException, AWTException {
        if (twitterActivated) {
            twitterActivated = false;

            Platform.runLater(() -> mediaChoiceBox.setVisible(false));
            mediaLabel.setVisible(false);
            mediaTab = false;

            tweetLabel.setVisible(false);
            tweetTextField.setOpacity(0);

            // new Permissions(tweetTextField.getText());
            new Permissions().createTweet(tweetTextField.getText());

            Platform.runLater(() -> showCommand.setText("SENT"));
            showCommand.setVisible(true);
            Platform.runLater(() -> fadein(showCommand));

            System.out.println("Tweet [ " + tweetTextField.getText()
                    + " ] was sucessfully sent out.");
            tweetTextField.clear();
        }
        if (youtubeActivated) {
            tweetLabel.setVisible(false);
            tweetTextField.setOpacity(0);
            youtubeVideoContainer.setOpacity(1);
            grabVideo();
        }

    }

    private void selectedMediaTab() throws TwitterException {
        if (viewPost.get(mediaPosition).equals(writePostButton)) {
            // tweetLabel.setVisible(true);
            tweetTextField.setOpacity(1);
        }
        else {
            Platform.runLater(() -> timeLineContainer.setVisible(true));
            List<Date> time = new Permissions().getTweetTime();
            List<String> post = new Permissions().getTimeLine();
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            Platform.runLater(() -> {
                try {
                    TweetFour.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(3) + "\r\n" + time.get(3));
                    tweetFive.setText("@" + new Permissions().getScreenName()
                            + "\r\n" + post.get(4) + "\r\n" + time.get(4));
                }
                catch (TwitterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
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
        if (pics.get(position).equals(weatherIcon)) {
            showWeather();
        }
        if (pics.get(position).equals(youtubeIcon)) {
            tweetTextField.setOpacity(1);
            Platform.runLater(() -> tweetLabel.setVisible(true));
            Platform.runLater(() -> tweetLabel.setText("Youtube"));
            youtubeActivated = true;

        }
        if(pics.get(position).equals(exit)){
            Platform.exit();
        }
    }

    private void showWeather() {
        if (weatherActivated) {
            weatherActivated = false;
            weatherContainer.setOpacity(0);
        }
        else {
            weatherActivated= true;

            
            Platform.runLater(
                    () -> temp.setText(Double.toString(Math.floor(weather.getTemp()))+" Degrees"));

            Platform.runLater(
                    () -> description.setText(weather.getDescription()));
            weatherContainer.setOpacity(1);
            if (weather.getWeatherType().equals("Clear")) {
                dailyweatherImage= new Image("icons/icons8-partly-cloudy-day-64.png");
                Platform.runLater(()->dailyWeatherIcon.setImage(dailyweatherImage));
               
            }
            if (weather.getWeatherType().equals("Clouds")) {
                dailyweatherImage= new Image("icons/icons8-sun-64.png");
                Platform.runLater(()->dailyWeatherIcon.setImage(dailyweatherImage));
            }
            if (weather.getWeatherType().equals("Drizzle")) {
                dailyweatherImage= new Image("icons/icons8-rain-64.png");
                Platform.runLater(()->dailyWeatherIcon.setImage(dailyweatherImage));
            }
            if (weather.getWeatherType().equals("Rain")) {
               
                dailyweatherImage= new Image("icons/icons8-rain-64.png");
                Platform.runLater(()->dailyWeatherIcon.setImage(dailyweatherImage));
            }

        }
    }

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

        // timeDateActivted = true;
        // Platform.runLater(
        // () -> timeDate.setText(weather.getTime().toString()));
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

    public void cancel() throws IOException, JSONException, AWTException {
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

        tweetLabel.setVisible(false);
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
        //hides the weather
        weatherActivated = true;
        showWeather();

    }

    public void grabVideo() throws IOException {
        youtubeClass vid = new youtubeClass(tweetTextField.getText());
        if (youtubeActivated) {
            Platform.runLater(
                    () -> youtubeVideoContainer.getEngine().load(vid.send));
        }

    }

    public void playPause() throws AWTException {
        Robot bot = new Robot();
        // simulate mouse click on location where the video will be at all times
        // TODO on final build calibrate to click

        bot.mouseMove(800, 450);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }

    public void click() throws AWTException {
        Robot bot = new Robot();
        // simulate mouse click on location where the video will be at all times
        // TODO on final build calibrate to click

        bot.mouseMove(800, 250);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
