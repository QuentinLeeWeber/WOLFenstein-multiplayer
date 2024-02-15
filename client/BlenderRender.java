import java.awt.*;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.io.File;

class BlenderRender {
    //level und player wird von Game aus übergeben
    public Level level;
    public Player player;

    private boolean renderIn3d;
    private boolean debug = true;
    private final int viewDistance = 1000;
    private final int resolution = 800;
    private final int fov = 60;
    private final float wallHeight = 10;
    private boolean renderBoundingBoxes = false;

    private Image felix;
    private Image background;
    private Image skybox;

    //liste der objecte welche es sich lohnt zu zeichen (optimierung)
    private ArrayList<Wall> minWalls = new ArrayList<Wall>();
    private ArrayList<Pixel> pixels = new ArrayList<Pixel>();

    public BlenderRender(boolean _3d) {
        renderIn3d = _3d;
        loadTexures();
    }

    public void draw(Graphics g) { 
        if(renderIn3d){
            g.drawImage(background, 0, 0, null);
            drawSkybox(g);
            checkMinCollision(g);
            raycast(g);
            //calcGraphicObjekte(g);
            renderPixels(g);

            calcGraphicObjekte(g);


        } else { 
            for (Wall wall : level.walls) {
                g.setColor(new Color(0, 0, 0));
                g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
            }
            for (Graphikobjekt gr : level.graphikobjekte) {
                g.drawOval(gr.x, gr.y, 5, 5);
            }
            player.draw(g);    
        }
        if(debug){
            g.setColor(new Color(255, 0, 0, 50));
            g.drawRect(player.x - viewDistance / 2, player.y - viewDistance / 2, viewDistance, viewDistance);
            g.setColor(new Color(0, 0, 0));
            for (Wall wall : level.walls) {
                g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
            }
            for(Graphikobjekt gr : level.graphikobjekte){
                g.drawOval(gr.x, gr.y, 4, 4);
            }
        }
        if (renderBoundingBoxes) {
            Color prevColor = g.getColor();
            g.setColor(Color.green);

            for (BoundingBox boundingBox : level.getBoundingBoxes()) {
                if (boundingBox != null) {
                    //BoundingBoxes mit einer minimalen Größe von 3 gemalt, damit sie immer sichtbar sind
                    g.fillRect(boundingBox.x, boundingBox.y, Math.max(3, boundingBox.width), Math.max(3, boundingBox.height));
                }
            }
            g.setColor(Color.orange);
            g.fillRect(Game.getGame().player.boundingBox.x, Game.getGame().player.boundingBox.y, Game.getGame().player.boundingBox.width, Game.getGame().player.boundingBox.height);
            g.setColor(prevColor);
        }
    }

    private void raycast(Graphics g){
        for(int i = 0; i < resolution;i++){
            float renderDistance = 0;
            float hitX = 99999;
            float hitY = 99999;
            float hitDistance = 99999;
            Color renderColor = new Color(0, 0, 0);
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
                            renderColor = wall.renderColor;
                        }
                    }
                }     
                renderDistance = (float) (hitDistance * Math.cos(((i - resolution / 2) * ((float) fov / (float) resolution)) * (Math.PI / 180)));
            }
            int drawX = (int) (((float) i) * (float) (Game.frameWidth) / (float) (resolution));
            int drawY = (int) (((float) (Game.frameHeight) / renderDistance) * 2 * wallHeight);
            if(hitDistance <= 1000){
                g.setColor(renderColor);
                
                //g.drawLine(drawX, Game.frameHeight / 2 - drawY, drawX, Game.frameHeight / 2 + drawY);
                
                pixels.add(new WallPixel(drawX, renderDistance, renderColor, drawY));
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
        for(Graphikobjekt gr : level.graphikobjekte){

            //System.out.println(Game.getGame().player.direction);

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
            //float relativeY = (float) ((gr.x -player.x) * Math.sin(Math.toRadians(360 - player.direction)) + (gr.y -player.y) * Math.cos(Math.toRadians(360 - player.direction)));

            int drawX;
            if (relativeX <= 0) {
                drawX = (int) (400 + (Math.toDegrees(angle) / (fov / 2) * 400));
            } else {
                drawX = (int) (400 - (Math.toDegrees(angle) / (fov / 2) * 400));
            }



            if(debug){
                g.setColor(new Color(0, 255, 255));
                g.drawOval((int) X, (int) Y, 4, 4);
                //g.setColor(new Color(0, 0, 255));
                //g.drawOval((int) relativeX + player.x, (int) relativeY + player.y, 4, 4);
                System.out.println(relativeX + "   " + Math.toDegrees(angle));
                g.drawOval(drawX, 300, 10, 10);
            }
            
            /*float X = gr.x - player.x;
            float Y = gr.y - player.y;
            float distance = (float) Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
            float relativeX = (float) (X * Math.cos(Math.toRadians(player.direction)) - Y * Math.sin(Math.toRadians(player.direction)));
            float relativeY = (float) (X * Math.sin(Math.toRadians(player.direction)) + Y * Math.cos(Math.toRadians(player.direction)));
            float rotation = 0;
            if(relativeY >= 0){
                rotation =  (float) Math.asin(relativeX / distance);
            } else {
                rotation =  (float) (Math.PI - Math.asin(relativeX / distance));
            }

            if(debug){
                g.setColor(new Color(0, 255, 0));
                g.fillOval((int) (player.x + X), (int) (player.y + Y), 8, 8);
                g.setColor(new Color(0, 255, 255));
                g.drawOval((int) relativeX + player.x, (int) relativeY + player.y, 4, 4);
                System.out.println(X + "    " + Y + "   " +  relativeX + "   " + relativeY + "   " + rotation);
            }*/
            
            
            //g.drawOval((int) (Math.cos(relativeX) * 20 + player.x), (int) (Math.sin(relativeY) * 20 + player.y), 4, 4);
            
            /*int x = Game.frameWidth / 2;
            if(gr.texture.equals("felix")){
                //g.drawImage(felix, x - felix.getWidth(null), Game.frameHeight / 2 - felix.getHeight(null), null);
                g.drawImage(felix, 0, 0, null);
            }*/
        }
    }

    //function to calculate the the radial position of a Graphicobject (wir haben wirklich sehr komische konventionenen) 
    //relative to player
    //
    //helpfunction for calcGraphicsObjekte
    /*private float calcRelativeRotation(int x, int y, int r){
        if(y >= 0){
            return (float) Math.asin(x / r);
        } else {
            return (float) (Math.PI - Math.asin(x / r));
        }
    }*/

    //wichtiger Link https://stackoverflow.com/questions/10545300/sorting-an-array-list-of-objects-based-on-a-variable-in-object?rq=3
    //noch wichtiger https://www.javatpoint.com/java-list-sort-method
    private void renderPixels(Graphics g){
        //der try block ist sehr evil, i know
        try{
            Collections.sort(pixels, new SortByDistance());
        } catch(Exception e){
            System.err.println("error while sorting pixels");
        }
        for(Pixel pixel : pixels){
            pixel.draw(g);
        }
        pixels.clear();
    }

    private void loadTexures(){
        System.out.println("loading textures!");
        boolean loadSuccess = true;
        try{
            skybox = ImageIO.read(new File("resources/skybox_blue_sky_3.png"));
            background = ImageIO.read(new File("resources/blue_background.png"));
            felix = ImageIO.read(new File("resources/felix.png"));
        } catch(IOException e){
            e.printStackTrace();
            System.err.println("failed to load textures");
            loadSuccess = false;
        }
        if(loadSuccess){
            System.out.println("succesfully loaded textures!");
        }
    }

    public void drawSkybox(Graphics g){
        g.drawImage(skybox, (int) (-800 * 4 * ((float) (player.direction) / 360)), 0, null);
        g.drawImage(skybox, 800 * 4 + (int) (-800 * 4 * ((float) (player.direction) / 360)), 0, null);
    }
}