package ipmenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;

public class AssetManager {
    static private Background backgroundImage = null;
    static private ImagePattern posParticles;
    static private ImagePattern negParticles;
    //static private ImagePattern blackHoleImage = null;
    
    static private Media backgroundMusic = null;
    static private AudioClip newPlanetSound = null;
    static private AudioClip shootingSound = null;
    
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets()
    {
        // Preload all images
        Image background = new Image(fileURL("./assets/images/background.png"));
        backgroundImage = new Background(
                            new BackgroundImage(background, 
                                                BackgroundRepeat.NO_REPEAT, 
                                                BackgroundRepeat.NO_REPEAT, 
                                                BackgroundPosition.DEFAULT,
                                                BackgroundSize.DEFAULT));
        posParticles = new ImagePattern(new Image(fileURL("./assets/images/proton.png")));
        negParticles = new ImagePattern(new Image(fileURL("./assets/images/electron.png")));        
        
       
    }
    
    static public Background getBackgroundImage()
    {
        return backgroundImage;        
    }
    static public ImagePattern getPosPartImg(){
        return posParticles;
    }
    
    static public ImagePattern getNegPartImg(){
        return negParticles;
    }
    
    static public Media getBackgroundMusic()
    {
        return backgroundMusic;
    }
    
    static public AudioClip getNewPlanetSound()
    {
        return newPlanetSound;
    }
}
