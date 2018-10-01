package application;

import java.util.ArrayList;
import java.util.List;

import com.sun.glass.ui.TouchInputSupport;

import javafx.fxml.FXML;

import javafx.scene.image.ImageView;

public class HomeController {

    @FXML
    private ImageView exit;

    @FXML
    private ImageView Logo;
    
    List<ImageView> pics = new ArrayList<>();
   

    public  HomeController getController() {
        return this;
    }
    
    
    public void highlighted(String move) {
        
            pics.add(exit);
            pics.add(Logo);
        
            
        
        if(move.equals("left")) {
            exit.setOpacity(1);
            Logo.setOpacity(.5);
        }
        if (move.equals("right")) {
            exit.setOpacity(.5);
            Logo.setOpacity(1);
        }
        
        
    }
   
}
