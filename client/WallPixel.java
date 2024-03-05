import java.awt.*;
import java.awt.*;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class WallPixel extends Pixel{

    public Color darker;
    public float height;
    private float wallIndex;

    public WallPixel(int _x, float _distance, Color _darker, int _height, float _wallIndex){
        super(_x, _distance, Game.getGame().renderer.wallImage);
        darker = _darker;
        height = _height;
        wallIndex = _wallIndex;
    }

    @Override
    public void draw(Graphics g){
        //g.drawImage(texture, x, (int) (Game.frameHeight / 2 - height), (int) (800.0f / (float) BlenderRender.resolution), (int) (height * 2), null);
        //System.out.println(texture.getWidth(null));
        BufferedImage subImage = null;
        try {
            subImage = texture.getSubimage((int) (texture.getWidth(null) * (wallIndex / 1.0f) ), 0, 1, texture.getHeight(null));
        } catch (Exception e){
            subImage = null;
        }

        /*for(int i = 0; i < (int) (800.0f / (float) BlenderRender.resolution);i++) {
            g.drawImage(subImage, x + i, (int) (Game.frameHeight / 2 - height), 1, (int) (height * 2), null);
            System.out.println((int) (800.0f / (float) BlenderRender.resolution));
        }*/

        g.drawImage(subImage, x, (int) (Game.frameHeight / 2 - height), (int) (800.0f / (float) BlenderRender.resolution) , (int) (height * 2), null);

        g.setColor(darker);
        g.fillRect(x, (int) (Game.frameHeight / 2 - height), (int) (800.0f / (float) BlenderRender.resolution), (int) height * 2);
    }
}
