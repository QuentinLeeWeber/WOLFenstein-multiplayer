class Level1 extends Level {
    public Level1() {   
        super();
        int[] a = {200, 200};
        int[] b = {300, 300};
        int[] c = {500, 500};
        int[] d = {400, 400};
        int[] j = {240, 330};
        int[] i = {340, 220};

        int[] e = {300, 500};
        int[] f = {200, 400};
        int[] g = {0, 0};
        int[] h = {100, 100};
        int[] x = {100, 100};
        int[] y = {600, 100};
        int[] v = {300, 100};
        int[] w = {301, 500};
        createWall(c, d);
        createWall(e, f);
        createWall(a, b);
        createWall(h, g);
        createWall(i, j);
        createWall(x, y);
        createWall(v, w);
        createEnemy(100, 200);
    }
}
