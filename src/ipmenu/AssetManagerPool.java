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

public class AssetManagerPool {
    static private BackgroundImage backgroundImage = null;
    static private ImagePattern cueBallImage = null ;
    //static private ImagePattern bombImage = null ;
    //static private ImagePattern targetImage = null;
    //static private Media planeNoiseSound = null;
    //static private AudioClip shootingSound = null;
    //static private AudioClip blastSound = null;
    
    
    
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets()
    {
        // Preload all images
        backgroundImage = new BackgroundImage(new Image(fileURL("./assets/poolTable.jpg")), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        cueBallImage = new ImagePattern(new Image(fileURL("./assets/cueBall.png")));
        //(new ImagePattern(new Image(fileURL("./assets/poolTable.gif"))));
        //planeImage = new ImagePattern(new Image(fileURL("./assets/art/plane2.png")));
        //bombImage = new ImagePattern(new Image(fileURL("./assets/art/bomb2.png")));
        //targetImage = new ImagePattern (new Image(fileURL("./assets/art/target.png")));
        
        // Preload all music tracks
        //planeNoiseSound = new Media(fileURL("./assets/sound/plane_noise.wav"));

        // Preload all sound effects
        //shootingSound =  new AudioClip(fileURL("./assets/sound/bomb_drop.wav"));
        //blastSound = new AudioClip(fileURL("./assets/sound/blast.wav"));
        
    }
    static public BackgroundImage getBackground(){
        return backgroundImage;   
    }
    static public ImagePattern getcueBallImage()
    {
        return cueBallImage;
    }
    //static public ImagePattern getBombImage()
    //{
    //    return bombImage;        
    //}
    //static public ImagePattern getTargetImage()
    //{
    //    return targetImage;        
    //}
    //static public Media getplaneNoiseSound()
    //{
    //    return planeNoiseSound;
    //}
    //static public AudioClip getShootingSounnd()
    //{
    //    return shootingSound;
    //}
    //static public AudioClip getBlastSound()
    //{
    //    return blastSound;
    //}
}
