import java.awt.*;

public class SpritePixel extends Pixel{

    private Image texture;

    public SpritePixel(int _x, float _distance, Image _texture){
        super(_x, _distance);
        texture = _texture;
    }

    @Override
    public void draw(Graphics g){

        int textureWidth = (int) (float) ((texture.getWidth(null) / distance) * BlenderRender.spriteHeight);
        int textureHeight = (int) (float) ((texture.getHeight(null) / distance) * BlenderRender.spriteHeight);
        
        g.drawImage(texture, x - (int) ((float) textureWidth / 2), 300 - (int) ((float) textureHeight / 2), textureWidth, textureHeight, null);

        if(BlenderRender.debug){
            g.setColor(new Color(255, 0, 0));
            g.drawOval(x, 300, 10, 10);
        }
    }
}