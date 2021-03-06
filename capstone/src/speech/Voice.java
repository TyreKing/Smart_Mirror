package speech;


import java.awt.AWTException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import org.json.JSONException;

import application.HomeController;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import twitter4j.TwitterException;


public class Voice {

    // Necessary
    private LiveSpeechRecognizer recognizer;
    
    // Logger
    private Logger logger = Logger.getLogger(getClass().getName());
     
    /**
     * This String contains the Result that is coming back from SpeechRecognizer
     */
    private String speechRecognitionResult;
    
    //-----------------Lock Variables-----------------------------
    
    /**
     * This variable is used to ignore the results of speech recognition cause actually it can't be stopped...
     * 
     * <br>
     * Check this link for more information: <a href=
     * "https://sourceforge.net/p/cmusphinx/discussion/sphinx4/thread/3875fc39/">https://sourceforge.net/p/cmusphinx/discussion/sphinx4/thread/3875fc39/</a>
     */
    private boolean ignoreSpeechRecognitionResults = false;
    
    /**
     * Checks if the speech recognise is already running
     */
    private boolean speechRecognizerThreadRunning = false;
    
    /**
     * Checks if the resources Thread is already running
     */
    private boolean resourcesThreadRunning;
    
    //---
    
    /**
     * This executor service is used in order the playerState events to be executed in an order
     */
    private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(3);
    
    //------------------------------------------------------------------------------------
    private HomeController homeController;

    /**
     * Constructor
     */
    public Voice(HomeController homeController) {
        this.homeController = homeController;
        // Loading Message
        logger.log(Level.INFO, "Loading Speech Recognizer...\n");
        
        // Configuration
        Configuration configuration = new Configuration();
        
        // Load model from the jar
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        
        //====================================================================================
        //=====================READ THIS!!!===============================================
        //Uncomment this line of code if you want the recognizer to recognize every word of the language 
        //you are using , here it is English for example    
        //====================================================================================
        //configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        
        //====================================================================================
        //=====================READ THIS!!!===============================================
        //If you don't want to use a grammar file comment below 3 lines and uncomment the above line for language model 
        //====================================================================================
        
        // Grammar
        configuration.setGrammarPath("resource:/application");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);
        
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        // Start recognition process pruning previously cached data.
        // recognizer.startRecognition(true);
        
        //Check if needed resources are available
        startResourcesThread();
        //Start speech recognition thread
       
        startSpeechRecognition();
    }
    
    //-----------------------------------------------------------------------------------------------
    
    /**
     * Starts the Speech Recognition Thread
     */
    public synchronized void startSpeechRecognition() {
        
        //Check lock
        if (speechRecognizerThreadRunning)
            logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
        else
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {
                
                //locks
                speechRecognizerThreadRunning = true;
                ignoreSpeechRecognitionResults = false;
                
                //Start Recognition
                recognizer.startRecognition(true);
                //Information           
                logger.log(Level.INFO, "You can start to speak...\n");
                
                try {
                    while (speechRecognizerThreadRunning) {
                        /*
                         * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
                         */
                        SpeechResult speechResult = recognizer.getResult();
                        
                        //Check if we ignore the speech recognition results
                        if (!ignoreSpeechRecognitionResults) {
                            
                            //Check the result
                            if (speechResult == null)
                                logger.log(Level.INFO, "I can't understand what you said.\n");
                            else {
                                
                                //Get the hypothesis
                                speechRecognitionResult = speechResult.getHypothesis();
                                
                                //You said?
                                System.out.println("You said: [" + speechRecognitionResult + "]\n");
                                
                                //Call the appropriate method 
                                makeDecision(speechRecognitionResult, speechResult.getWords());
                                
                            }
                        } else
                            logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
                      //Get the hypothesis
                        speechRecognitionResult = speechResult.getHypothesis(); 
                        //switch to show animation of awake and sleep
                        if (ignoreSpeechRecognitionResults && speechRecognitionResult.equals("mirror stop listening")) {
                            
                            homeController.getController().highlighted(speechRecognitionResult);
                        }
                        // check if user is trying to wake the mirror
                        if(speechRecognitionResult.equals("mirror mirror") ){
                            stopIgnoreSpeechRecognitionResults();
                            homeController.getController().highlighted(speechRecognitionResult);
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.WARNING, null, ex);
                    speechRecognizerThreadRunning = false;
                }
                
                logger.log(Level.INFO, "SpeechThread has exited...");
                
            });
    }
    
    /**
     * Stops ignoring the results of SpeechRecognition
     */
    public synchronized void stopIgnoreSpeechRecognitionResults() {
        
        //Stop ignoring speech recognition results
        ignoreSpeechRecognitionResults = false;
    }
    
    /**
     * Ignores the results of SpeechRecognition
     */
    public synchronized void ignoreSpeechRecognitionResults() {
        
        //Instead of stopping the speech recognition we are ignoring it's results
        ignoreSpeechRecognitionResults = true;
        
    }
    
    //-----------------------------------------------------------------------------------------------
    
    /**
     * Starting a Thread that checks if the resources needed to the SpeechRecognition library are available
     */
    public void startResourcesThread() {
        
        //Check lock
        if (resourcesThreadRunning)
            logger.log(Level.INFO, "Resources Thread already running...\n");
        else
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {
                try {
             
                    //Lock
                    resourcesThreadRunning = true;
                    
                    // Detect if the microphone is available
                    while (true) {
                        
                        //Is the Microphone Available
                        if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
                            logger.log(Level.INFO, "Microphone is not available.\n");
                        
                        // Sleep some period
                        Thread.sleep(350);
                     
                    }
                    
                } catch (InterruptedException ex) {
                    logger.log(Level.WARNING, null, ex);
                    resourcesThreadRunning = false;
                }
            });
    }
    
    /**
     * sends the result to the Homecontroller to be used as a command
     * 
     * @param speechWords   list of words recognized
     * @throws JSONException    
     * @throws IOException 
     * @throws TwitterException 
     * @throws AWTException 
     */
    public void makeDecision(String speech , List<WordResult> speechWords) throws TwitterException, IOException, JSONException, AWTException {
        
        System.out.println(speech);

        switch(speech) {
            
            case "mirror mirror":
                stopIgnoreSpeechRecognitionResults();
                break;
            
            case "mirror stop listening":
                ignoreSpeechRecognitionResults();
                break;
            
            case "mirror select": 
                homeController.getController().highlighted(speech);
                break;
                
            case "mirror choose":   
                homeController.getController().highlighted(speech);
                break;
                
            case "mirror right":
                homeController.getController().highlighted(speech);
                break;
            case "mirror left":
                homeController.getController().highlighted(speech);
                break;
            case "mirror up":
                homeController.getController().highlighted(speech);
                break;
            case "mirror down":
                homeController.getController().highlighted(speech);
                break;
            case "mirror confirm":
                homeController.getController().highlighted(speech);
                break;
            case "mirror cancel":
                homeController.getController().highlighted(speech);
                break;
            case "mirror play":
                homeController.getController().highlighted(speech);
                break;
            case "mirror pause":
                homeController.getController().highlighted(speech);
                break;
            case "mirror search":
                homeController.getController().highlighted(speech);
                break;
            case "mirror open youtube":
                homeController.getController().highlighted(speech);
                break;
            case "mirror show twitter feed":
                homeController.getController().highlighted(speech);
                break;
            case "mirror open twitter":
                homeController.getController().highlighted(speech);
                break;
                
            case "mirror create tweet":
                homeController.getController().highlighted(speech);
                break;
                
            case "mirror post tweet":
                homeController.getController().highlighted(speech);
                break;
                
            case "mirror show weather":
                homeController.getController().highlighted(speech);
                break;
            case "mirror show time":
                homeController.getController().highlighted(speech);
                break;
                
            case "mirror sleep":
                homeController.getController().highlighted(speech);
                break;
               
        }
        
    }
    /**
     * 
     * @return  boolean on whether speech is being ignored or not
     */
    public boolean getIgnoreSpeechRecognitionResults() {
        return ignoreSpeechRecognitionResults;
    }
    
    /**
     * 
     * @return  boolean on whether thread is running 
     */
    public boolean getSpeechRecognizerThreadRunning() {
        return speechRecognizerThreadRunning;
    }
}
