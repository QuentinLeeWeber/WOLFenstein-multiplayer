import java.awt.*;
import java.lang.Math;

class BlenderRender {
    public Level level;

    private boolean renderBoundingBoxes = false;

    public BlenderRender() {

    }

    public void draw(Graphics g, Player p) {
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
            g.fillRect(p.boundingBox.x, p.boundingBox.y, p.boundingBox.width, p.boundingBox.height);
            g.setColor(prevColor);
        }

        for (Wall wall : level.walls) {
            g.drawLine(wall.a[0], wall.a[1], wall.b[0], wall.b[1]);
        }
        for (Graphikobjekt gr : level.graphikobjekte) {
            g.drawOval(gr.getX(), gr.getY(), 5, 5);
        }
        p.draw(g);
    }
}
