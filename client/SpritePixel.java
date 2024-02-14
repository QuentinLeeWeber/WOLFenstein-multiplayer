import java.awt.*;

public class SpritePixel extends Pixel{

    public String texture;

    public SpritePixel(int _x, float _distance, String _texture){
        super(_x, _distance);
        texture = _texture;
    }

    @Override
    public void draw(Graphics g){

    }
}
