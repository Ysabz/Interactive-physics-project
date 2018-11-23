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

public class AssetManagerBomb {
    static private Background backgroundImage = null;
    static private ImagePattern planeImage = null ;
    static private ImagePattern bombImage = null ;
    static private ImagePattern targetImage = null;
    static private Media planeNoiseSound = null;
    static private AudioClip shootingSound = null;
    static private AudioClip blastSound = null;
    
    
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets()
    {
        // Preload all images
       
        
        planeImage = new ImagePattern(new Image(fileURL("./assets/art/plane2.png")));
        bombImage = new ImagePattern(new Image(fileURL("./assets/art/bomb2.png")));
        targetImage = new ImagePattern (new Image(fileURL("./assets/art/target.png")));
    }
    
    static public ImagePattern getPlaneImage()
    {
        return planeImage;        
    }
    static public ImagePattern getBombImage()
    {
        return bombImage;        
    }
    static public ImagePattern getTargetImage()
    {
        return targetImage;        
    }
    static public Media getplaneNoiseSound()
    {
        return planeNoiseSound;
    }
    static public AudioClip getBlastSound()
    {
        return blastSound;
    }
}
