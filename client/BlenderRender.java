import java.awt.*;
import java.util.ArrayList;

class BlenderRender {
    public Level level;

    public BlenderRender() {

    }

    public void draw(Graphics g, Player p) {

        for (Wall wall : level.walls) {
            g.drawLine(wall.a[0], wall.a[1], wall.b[0], wall.b[1]);
        }
        for (Graphikobjekt gr : level.graphikobjekte) {
            g.drawOval(gr.x, gr.y, 5, 5);
        }

        p.draw(g);
    }
}
