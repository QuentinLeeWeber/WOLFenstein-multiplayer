import java.awt.*;

public class WallPixel extends Pixel{

    public Color color;

    public WallPixel(int _x, float _distance, Color _color, int _height){
        super(_x, _distance);
        color = _color;
        height = _height;
    }

//int drawY = (int) (((float) (Game.frameHeight) / renderDistance) * 2 * wallHeight);

    @Override
    public void draw(Graphics g){
        g.setColor(color);

        //float angle = (float) Math.asin(Math.abs(x - 400) / 400);
        g.drawLine(x, (int) (Game.frameHeight / 2 - height), x, (int) (Game.frameHeight / 2 + height));
    }
}
