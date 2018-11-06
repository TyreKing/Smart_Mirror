package twitteraApp;




import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.restfb.types.Payment.Item;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;



public class Permissions {

    
//    public static void main(String[]args) throws TwitterException {
//        Permissions permissions = new Permissions("test3");
//    }
//    public Permissions() throws TwitterException {
//       String tweeted = createTweet("test 2");
//       System.out.println(tweeted);
//    }
//    
//    public Permissions(String tw) throws TwitterException{
//        String tweeted = createTweet(tw);
//        System.out.println(tweeted);
//        
//         
//    }

    public String createTweet(String tweet) throws TwitterException {
        Twitter twitter = getTwitterinstance();
        Status status = twitter.updateStatus(tweet);
        return status.getText();
    }

 
    public String getScreenName() throws TwitterException{
        Twitter twitter = getTwitterinstance();
        twitter.getHomeTimeline().stream().map(item -> item.getCreatedAt()).collect(Collectors.toList());
        return twitter.getScreenName();
    }
    
    public List<Date> getTweetTime() throws TwitterException{
        Twitter twitter = getTwitterinstance();
       return twitter.getHomeTimeline().stream().map(item -> item.getCreatedAt()).collect(Collectors.toList());
        
    }
   
    public List<String> getTimeLine() throws TwitterException {
        Twitter twitter = getTwitterinstance();
        
        return twitter.getHomeTimeline().stream()
          .map(item -> item.getText())
          .collect(Collectors.toList());
    }
    
    


       
           private Twitter getTwitterinstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true);
//        cb.setOAuthConsumerKey(System.getenv("CK"));
//        cb.setOAuthConsumerSecret(System.getenv("CKS"));
//        cb.setOAuthAccessToken(System.getenv("AT"));
//        cb.setOAuthAccessTokenSecret(System.getenv("ATS"));
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }
        
}

