class Level1 extends Level {
    public Level1() {
        float[] a = {200, 200};
        float[] b = {300, 300};
        float[] c = {500, 500};
        float[] d = {400, 400};
        float[] j = {240, 330};
        float[] i = {340, 220};

        float[] e = {300, 500};
        float[] f = {200, 400};
        float[] g = {0, 0};
        float[] h = {100, 100};
        createWall(c, d);
        createWall(e, f);
        createWall(a, b);
        createWall(h, g);
        createWall(i, j);
        createEnemy(100, 200);
    }
}
