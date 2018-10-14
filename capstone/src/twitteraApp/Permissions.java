package twitteraApp;



import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Permissions {

    
    public static void main(String[]args) throws TwitterException {
        Permissions permissions = new Permissions();
    }
    public Permissions() throws TwitterException {
       String tweeted = createTweet("tweeting from my application");
       System.out.println(tweeted);
    }
    

    public String createTweet(String tweet) throws TwitterException {
        Twitter twitter = getTwitterinstance();
        Status status = twitter.updateStatus(tweet);
        return status.getText();
    }

   

       
        
        
}

