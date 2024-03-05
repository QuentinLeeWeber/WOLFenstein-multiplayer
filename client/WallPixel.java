package client;

import java.awt.*;
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
        BufferedImage subImage = null;
        try {
            subImage = texture.getSubimage((int) ((wallIndex) * (800.0f / (float) BlenderRender.resolution)), 0, 1, texture.getHeight(null));
        } catch (Exception e){
            subImage = null;
            System.out.println("wallIndex error");
        } 

        g.drawImage(subImage, x, (int) (Game.frameHeight / 2 - height), (int) (800.0f / (float) BlenderRender.resolution) , (int) (height * 2), null);
        g.setColor(darker);
        g.fillRect(x, (int) (Game.frameHeight / 2 - height), (int) (800.0f / (float) BlenderRender.resolution), (int) height * 2);
    }
}
