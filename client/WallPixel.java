package client;

import java.awt.*;

public class WallPixel extends Pixel{

    public Color darker;
    public float height;

    public WallPixel(int _x, float _distance, Image _texture, Color _darker, int _height){
        super(_x, _distance, _texture);
        darker = _darker;
        height = _height;
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(texture, x, (int) (Game.frameHeight / 2 - height), 1, (int) (height * 2), null);     
        g.setColor(darker);
        g.drawLine(x, (int) (Game.frameHeight / 2 - height), x, (int) (Game.frameHeight / 2 + height));
    }
}
