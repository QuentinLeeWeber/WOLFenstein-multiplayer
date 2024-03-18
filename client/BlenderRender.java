import java.awt.*;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Color;


class BlenderRender {
    //level und player wird von Game aus übergeben
    public Level level;
    public Player player;

    private boolean renderIn3d;
    public static final boolean debug = false;
    private final int viewDistance = 10000;
    public static final int resolution = 800;
    public static final int fov = 60;
    private final float wallHeight = 8;
    public static final int spriteHeight = 20000;
    private float wallDarkness = 1.6f;

    private Color floorColor;

    private BufferedImage skybox;
    private BufferedImage Sprite1;
    public BufferedImage wallImage;


    //liste der objecte welche es sich lohnt zu zeichen (optimierung)
    private ArrayList<Wall> minWalls = new ArrayList<Wall>();
    private ArrayList<Pixel> pixels = new ArrayList<Pixel>();
    //auch die liste ist veraltet, aber so ist es eben
    private ArrayList<Image> wallTex = new ArrayList<Image>();

    public BlenderRender(boolean _3d) {
        renderIn3d = _3d;
        loadTexures();
        floorColor = avarageColor(wallImage);
    }

    public void draw(Graphics g) {
        if(renderIn3d){
            g.setColor(floorColor);
            g.fillRect(0, 0, 800, 600);
            drawSkybox(g);
            checkMinCollision(g);
            raycast(g);
            calcGraphicObjekte(g);
            renderPixels(g);
        } else { 
            for (Wall wall : level.walls) {
                g.setColor(new Color(0, 0, 0));
                g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
            }
            for (Graphikobjekt gr : level.graphikobjekte) {
                g.drawOval(gr.x, gr.y, 5, 5);
            }
            player.draw2D(g);    
        }
        if(debug){
            g.setColor(new Color(255, 0, 0, 50));
            g.drawRect(player.x - viewDistance / 2, player.y - viewDistance / 2, viewDistance, viewDistance);
            g.setColor(new Color(0, 0, 0));
            for (Wall wall : level.walls) {
                g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
            }
            g.setColor(Color.red);
            int maxShotDistance = Game.getGame().getWidth();
            g.drawLine(player.x, player.y, player.x-(int) (Math.sin(Math.toRadians(-player.direction))*maxShotDistance), player.y-(int) (Math.cos(Math.toRadians(-player.direction))*maxShotDistance));
            g.setColor(new Color(0, 0, 0));
            for(Graphikobjekt gr : level.graphikobjekte){
                g.drawOval(gr.x, gr.y, 4, 4);
            }
        }
    }

    private void raycast(Graphics g){
        for(int i = 0; i < resolution;i++){
            float renderDistance = 0;
            float hitX = 99999;
            float hitY = 99999;
            float hitDistance = 99999;
            Wall hitWall = null;
            for(Wall wall : minWalls){
                float dirDegree = (player.direction - fov / 2) + ((float) fov / (float) resolution) * (i + 1) - 90;
                if(dirDegree % 90 == 0.0f || dirDegree % 270 == 0.0f){
                    dirDegree = dirDegree + 0.0001f;
                }
                float dir = (float) (dirDegree * (Math.PI / 180));
                float mL = 0;float nL = 0;float mR = 0;float nullX = player.x;float nullY = player.y;float nullSchnittX = 0;float X = 0;float Y = 0;
                try {
                    mL = (wall.a[1] - wall.b[1]) / (wall.a[0] - wall.b[0]); 
                } catch (Exception e){ 
                    mL = 1999999999;
                }
                try {
                    mR = (float) (Math.sin(dir) / Math.cos(dir));
                } catch(Exception e){
                    mR = 1999999999;
                }
                nL = -((wall.a[0] - nullX) * mL - (wall.a[1] - nullY));
                nullSchnittX = (nL / (mR - mL));
                X = nullSchnittX + nullX;
                Y = (nullSchnittX * mR + nullY);
                if(X <= wall.a[0] && X >= wall.b[0] || X <= wall.b[0] && X >= wall.a[0]){
                    if(nullSchnittX * mR  >= 0 && Math.sin(dir) >= 0 || nullSchnittX * mR  <= 0 && Math.sin(dir) <= 0){
                        if(Math.sqrt(Math.pow(X - player.x, 2) + Math.pow(Y - player.y, 2)) < hitDistance) {
                            hitDistance = (float) Math.sqrt(Math.pow(X - player.x, 2) + Math.pow(Y - player.y, 2));
                            hitX = X;
                            hitY = Y;
                            hitWall = wall;
                        }
                    }
                }
                renderDistance = (float) (hitDistance * Math.cos(((i - resolution / 2) * ((float) fov / (float) resolution)) * (Math.PI / 180)));
            }
            int drawX = (int) (((float) i) * (float) (Game.frameWidth) / (float) (resolution));
            int drawY = (int) (((float) (Game.frameHeight) / renderDistance) * 2 * wallHeight);

            if(hitDistance <= 1000){
                float wallAngle = (float) Math.acos(Math.abs(
                    (Math.cos(Math.toRadians(player.direction)) * (hitWall.a[0] - hitWall.b[0]) + Math.sin(Math.toRadians(player.direction)) * (hitWall.a[1] - hitWall.b[1]))
                    / (Math.sqrt(Math.pow((hitWall.a[0] - hitWall.b[0]), 2) + Math.pow((hitWall.a[1] - hitWall.b[1]), 2)))
                ));

                Color angleColor = new Color(0, 0, 0, Math.min(10000000, (int) ((wallAngle / 1.5708f) * 100 * wallDarkness)));

                float lenght = (float) Math.sqrt(Math.pow(hitWall.a[0] - hitX, 2) + Math.pow(hitWall.a[1] - hitY, 2));
                float wallIndex = ((lenght * wallTex.size() * 0.350f * (1 / wallHeight)) % wallTex.size());

                pixels.add(new WallPixel(drawX, renderDistance, angleColor, drawY, wallIndex));
            }
            if(debug){
                g.setColor(new Color(255, 0, 0));
                g.fillOval((int) hitX - 2, (int) hitY - 2, 4, 4);
                g.setColor(new Color(0, 100, 100));
                g.drawLine((int) hitX, (int) hitY, player.x, player.y);
            }
        }
    }

    //checkt durch eine versimplete collisionsberechnung, ob der raycast sich für das object lohnt 
    private void checkMinCollision(Graphics g){
        minWalls.clear();
        int checkBoxX = player.x - viewDistance / 2;
        int checkBoxY = player.y - viewDistance / 2; 

        for(Wall wall : level.walls){
            if(debug){
                g.setColor(new Color(200, 200, 200));
                g.fillRect((int) wall.physicA[0], (int) wall.physicA[1], (int) (wall.physicB[0] - wall.physicA[0]), (int) (wall.physicB[1] - wall.physicA[1]));
                g.setColor(new Color(0, 0, 255));
                g.drawOval((int) wall.physicA[0], (int) wall.physicA[1], 4, 4);
                g.drawOval((int) wall.physicB[0], (int) wall.physicB[1], 4, 4);
            }
            if((checkBoxX <= wall.physicB[0] && checkBoxX >= wall.physicA[0] - viewDistance) && (checkBoxY <= wall.physicB[1] && checkBoxY >= wall.physicA[1] - viewDistance)){
                if(debug){
                    g.setColor(new Color(255, 0, 0, 50));
                    g.fillRect((int) wall.physicA[0], (int) wall.physicA[1], (int) (wall.physicB[0] - wall.physicA[0]), (int) (wall.physicB[1] - wall.physicA[1]));
                }
                minWalls.add(wall); 
            }      
        }
    }

    private void calcGraphicObjekte(Graphics g){
        ArrayList<Graphikobjekt> allGraphikobjekte = new ArrayList<>(level.graphikobjekte);
        allGraphikobjekte.addAll(Game.getGame().remotePlayers.values());

        for(Graphikobjekt gr : allGraphikobjekte){

            float X = (float) (player.x + Math.cos(Math.toRadians(90 - player.direction)) * 69);
            float Y = (float) (player.y - Math.sin(Math.toRadians(90 - player.direction)) * 69);

            float distancePlayerObject = (float) Math.sqrt(Math.pow(player.x - gr.x, 2) + Math.pow(player.y - gr.y, 2));
            float distancePlayerPoint = 69;
            float distancePointObject = (float) Math.sqrt(Math.pow(gr.x - X, 2) + Math.pow(gr.y - Y, 2));


            //sinussatz
            float angle = (float) Math.acos((Math.pow(distancePlayerPoint, 2) + Math.pow(distancePlayerObject, 2) - Math.pow(distancePointObject, 2))
                                            /
                                            (2 * distancePlayerPoint * distancePlayerObject));

            float relativeX = (float) ((gr.x -player.x) * Math.cos(Math.toRadians(360 - player.direction)) - (gr.y -player.y) * Math.sin(Math.toRadians(360 - player.direction)));

            int drawX;
            if (relativeX <= 0) {
                drawX = (int) (400 - (Math.toDegrees(angle) / (fov / 2) * 400));
            } else {
                drawX = (int) (400 + (Math.toDegrees(angle) / (fov / 2) * 400));
            }

            pixels.add(new SpritePixel(drawX, distancePlayerObject, Sprite1));

            if(debug){
                g.setColor(new Color(0, 255, 255));
                g.drawOval((int) X, (int) Y, 4, 4);
                g.drawOval(drawX, 300, 10, 10);
            }
        }
    }

    private void renderPixels(Graphics g){ 
        Collections.sort(pixels, new SortByDistance());     
        for(Pixel pixel : pixels){
            pixel.draw(g);
        }
        pixels.clear();
    }

    private void loadTexures(){
        System.out.println("loading textures...");
        boolean loadSuccess = true;
        try{
            skybox = ImageIO.read(new File("resources/skybox_blue_sky_3.png"));
            Sprite1 = ImageIO.read(new File("resources/sprite_3.png"));
            wallImage = ImageIO.read(new File("resources/wall_sandstone.png"));
            preCalcWallTexture();
        }
        catch(IOException e){
            try{
                skybox = ImageIO.read(new File("client/resources/skybox_blue_sky_3.png"));
                Sprite1 = ImageIO.read(new File("client/resources/sprite_3.png"));
                wallImage = ImageIO.read(new File("client/resources/wall_sandstone_lowRes.png"));
                preCalcWallTexture();
            }
            catch (IOException e2) {
                System.err.println("failed to load textures");
                loadSuccess = false;
                e.printStackTrace();
            }
        }
        if(loadSuccess){
            System.out.println("succesfully loaded textures!");
        }
    }

    //funktion ist veraltet, ohne gibt es allerdings ueble bugs, daher bleibt das jetzt hier noch ein weilchen
    public void preCalcWallTexture(){
        System.out.println(("pre calculating wall texture..."));
        int width = (int) ( (float) wallImage.getWidth(null) / (800.0f / (float) resolution) );
        int height = wallImage.getHeight(null);
        for(int i = 0; i < width; i++){
            if((i != 0) && (i != width)){
                wallTex.add(wallImage.getSubimage(i * (int) (800.0f / (float) resolution), 0, (int) (800.0f / (float) resolution), height));
            }
        }
    }

    public void drawSkybox(Graphics g){
        g.drawImage(skybox, (int) (-800 * 4 * (player.direction / 360)), 0, null);
        g.drawImage(skybox, 800 * 4 + (int) (-800 * 4 * (player.direction / 360)), 0, null);
    }

    public Color avarageColor(BufferedImage image){
        long redBucket = 0;
        long greenBucket = 0;
        long blueBucket = 0;
        long pixelCount = 0;

        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                int p = image.getRGB(3,3);
                pixelCount++;
                redBucket += (p>>16) & 255;
                greenBucket += (p>>8) & 255;
                blueBucket += p & 255;
            }
        }
        return new Color((int) (redBucket / pixelCount), (int) (greenBucket / pixelCount), (int) (blueBucket / pixelCount));
    }
}
