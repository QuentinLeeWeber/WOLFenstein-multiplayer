package client;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpritePixel extends Pixel{

    public SpritePixel(int _x, float _distance, BufferedImage _texture){
        super(_x, _distance, _texture);
    }

    @Override
    public void draw(Graphics g){

        int textureWidth = (int) (BlenderRender.spriteHeight / distance);
        int textureHeight = (int) (BlenderRender.spriteHeight / distance);
        
        g.drawImage(texture, x - (int) ((float) textureWidth / 2), 300 - (int) ((float) textureHeight / 2), textureWidth, textureHeight, null);

        if(BlenderRender.debug){
            g.setColor(new Color(255, 0, 0));
            g.drawOval(x, 300, 10, 10);
        }
    }
}