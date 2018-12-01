package twitteraApp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Permissions {

    /**
     * this creates and post the tweet to twitter
     * 
     * @param tweet the string use to set the tweet
     * @return the tweet
     * @throws TwitterException
     */
    public String createTweet(String tweet) throws TwitterException {
        Twitter twitter = getTwitterinstance();
        Status status = twitter.updateStatus(tweet);
        return status.getText();
    }

    /**
     * This get the twitter handle
     * 
     * @return user name
     * @throws TwitterException
     */
    public String getScreenName() throws TwitterException {
        Twitter twitter = getTwitterinstance();
        twitter.getHomeTimeline().stream().map(item -> item.getCreatedAt())
                .collect(Collectors.toList());
        return twitter.getScreenName();
    }

    /**
     * gets the 5 most recent tweets from your personal timeline
     * 
     * @return list of tweets in twitter feed
     * @throws TwitterException
     */
    public List<Date> getTweetTime() throws TwitterException {
        Twitter twitter = getTwitterinstance();
        return twitter.getHomeTimeline().stream()
                .map(item -> item.getCreatedAt()).collect(Collectors.toList());

    }

    /**
     * gets the time for user tweets
     * 
     * @return List of times for tweets in twitter feed
     * @throws TwitterException
     */
    public List<String> getTimeLine() throws TwitterException {
        Twitter twitter = getTwitterinstance();

        return twitter.getHomeTimeline().stream().map(item -> item.getText())
                .collect(Collectors.toList());
    }

    /**
     * Must have your own consumer key, consumer secret, Access token, and
     * AccessTokenSecret use those credentials for the configurationBuilder
     * 
     * @return twitter object used to get user data
     */
    private Twitter getTwitterinstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        // ====================================================================================
        // =====================READ THIS!!!===============================================
        // Uncomment these line of code if it is the first
        // time you are trying to sign into twitter
        // ====================================================================================
        // cb.setDebugEnabled(true);
        // cb.setOAuthConsumerKey(System.getenv("CK"));
        // cb.setOAuthConsumerSecret(System.getenv("CKS"));
        // cb.setOAuthAccessToken(System.getenv("AT"));
        // cb.setOAuthAccessTokenSecret(System.getenv("ATS"));

        // ====================================================================================
        // =====================READ THIS!!!===============================================
        // once you set up your configurations
        // comment the above lines and you will be signed into your own twitter
        // account
        // ====================================================================================

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

}
