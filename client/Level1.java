class Level1 extends Level {
    public Level1() {
        float[] a = {-100, -100};
        float[] b = {-100, -25};
        float[] c = {-100, 25};
        float[] d = {-100, 100};
        float[] e = {100, 100};
        float[] f = {100, -100};
        createWall(a, b);
        createWall(c, d);
        createWall(d, e);
        createWall(a, f);
        createWall(e, f);

        float[] b1 = {-200, -25};
        float[] c1 = {-200, +25};
        createWall(b, b1);
        createWall(c, c1);

        float[] b2 = {-200, -300};
        float[] c2 = {-200, +300};
        float[] g = {-500, -300};
        float[] g1 = {-500, -100};
        float[] h = {-500, +300};
        float[] h1 = {-500, -75};
        createWall(b1, b2);
        createWall(c1, c2);
        createWall(b2, g);
        createWall(c2, h);
        createWall(b1, b2);
        createWall(g, g1);
        createWall(h, h1);

        float[] i = {-300, -25};
        float[] j = {-300, +25};
        float[] k = {-350, -25};
        float[] l = {-350, +25};
        createWall(i, j);
        createWall(i, k);
        createWall(j, l);
        createWall(k, l);

        float[] m = {-280, 125};
        float[] n = {-310, 125};
        float[] o = {-280, 155};
        float[] p = {-310, 155};
        createWall(m, n);
        createWall(n, p);
        createWall(m, o);
        createWall(o, p);

        float[] g2 = {-550, -100};
        float[] h2 = {-600, -75};
        float[] g3 = {-550, -300};
        float[] h3 = {-600, -275};
        float[] g4 = {-700, -300};
        float[] h4 = {-700, -275};
        createWall(g1, g2);
        createWall(h1, h2);
        createWall(g2, g3);
        createWall(h2, h3);
        createWall(g3, g4);
        createWall(h3, h4);

        float[] q = {-700, -350};
        float[] r = {-700, -150};
        float[] s = {-850, -150};
        float[] t = {-850, -350};
        float[] s1 = {-750, -150};
        float[] r1 = {-725, -150};
        createWall(g4, q);
        createWall(h4, r);
        createWall(q, t);
        createWall(r, r1);
        createWall(s, t);
        createWall(s, s1);

        float[] r2 = {-725, 0};
        float[] s2 = {-750, 0};
        createWall(r1, r2);
        createWall(s1, s2);

        float[] u = {-700, 0};
        float[] v = {-675, 0};
        createWall(s1, u);
        createWall(r1, v);
    }
}
