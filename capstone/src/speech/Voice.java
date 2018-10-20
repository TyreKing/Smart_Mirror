package speech;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class Voice {
    private String speechResult;
    private LiveSpeechRecognizer recognizer;
    private Logger logger =Logger.getLogger(getClass().getName()); 
    
    private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

    public Voice() {
        Configuration configs = new Configuration();
        configs.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configs.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        
        
        configs.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        
        try {
            recognizer = new LiveSpeechRecognizer(configs);
                    
        }catch (IOException e) {
           logger.log(Level.SEVERE, "Reconizer with mic did not set Properly", e);
        }
        
        
        
        
        
        
        
        
        
    }

}
