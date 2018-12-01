package youtubeApp;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;
import com.google.api.services.youtube.YouTube;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class youtubeClass {

    /** Application name. */
    private static final String APPLICATION_NAME = "Smart Mirror";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"),
            ".credentials/youtube-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory
            .getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials at
     * ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES = Arrays
            .asList(YouTubeScopes.YOUTUBE_READONLY);

    public String send;
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        }
        catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create an authorized Credential object.
     * 
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(
                "capstone/src/youtubeApp/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets
                .load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow,
                new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service, such as a YouTube Data
     * API client service.
     * 
     * @return an authorized API client service
     * @throws IOException
     */
    public static YouTube getYouTubeService() throws IOException {
        Credential credential = authorize();
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    
    /**
     * Searches through youTube and chooses the top result 
     * @param search the string that is to be looked up on youTube
     * @throws IOException
     */
    public youtubeClass(String search) throws IOException {
        YouTube youtube = getYouTubeService();
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet");
            parameters.put("maxResults", "1");
            parameters.put("q", search);
            parameters.put("type", "video");

          
            YouTube.Search.List searchListByKeywordRequest = youtube.search()
                    .list(parameters.get("part").toString());
            if (parameters.containsKey("maxResults")) {
                searchListByKeywordRequest.setMaxResults(Long
                        .parseLong(parameters.get("maxResults").toString()));
            }

            if (parameters.containsKey("q") && parameters.get("q") != "") {
                searchListByKeywordRequest
                        .setQ(parameters.get("q").toString());
            }

            if (parameters.containsKey("type")
                    && parameters.get("type") != "") {
                searchListByKeywordRequest
                        .setType(parameters.get("type").toString());
            }

            SearchListResponse response = searchListByKeywordRequest.execute();
            System.out.println(response.getItems().get(0).getId());
            String id =  response.getItems().get(0).getId().getVideoId();

            //this give me the video ID          
            send="https://www.youtube.com/embed/"+id;
            


        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Sends the embedded URL
     * @return embedded video URL
     */
    public String sender() {
        return send;
    }
}