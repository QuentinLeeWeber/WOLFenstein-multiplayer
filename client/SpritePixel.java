import java.awt.*;
import java.awt.image.BufferedImage;

public class SpritePixel extends Pixel{

    String nameTag = "shaa";

    public SpritePixel(int _x, float _distance, BufferedImage _texture, String _nameTag){
        super(_x, _distance, _texture);
        nameTag = _nameTag;
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
        g.setFont(new Font("DialogInput", Font.PLAIN, (int) (0.14f * (float) textureHeight)));
        g.setColor(Color.gray);
        g.drawString(nameTag, (int) (x - 0.7f * textureHeight * ((float) nameTag.length() / 18.0f)+ 2), (int) (300.0f - 0.3f * textureHeight) + 2);
        g.setColor(Color.green);
        g.drawString(nameTag, (int) (x - 0.7f * textureHeight * ((float) nameTag.length() / 18.0f)), (int) (300.0f - 0.3f * textureHeight));
    }
}