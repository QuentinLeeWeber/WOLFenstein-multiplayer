class Level1 extends Level {
    public Level1() {
        float[] a = {100, 100};
        float[] b = {100, 25};
        float[] c = {100, -25};
        float[] d = {100, -100};
        float[] e = {-100, -100};
        float[] f = {-100, 100};
        createWall(a, b);
        createWall(c, d);
        createWall(d, e);
        createWall(a, f);
        createWall(e, f);

        float[] b1 = {200, 25};
        float[] c1 = {200, -25};
        createWall(b, b1);
        createWall(c, c1);

        float[] b2 = {200, 300};
        float[] c2 = {200, -300};
        float[] c3 = {300, -300};
        float[] g = {500, 300};
        float[] g1 = {500, 100};
        float[] h = {500, -300};
        float[] h0 = {325, -300};
        float[] h1 = {500, 75};
        createWall(b1, b2);
        createWall(c1, c2);
        createWall(b2, g);
        createWall(c2, c3);
        createWall(h, h0);
        createWall(b1, b2);
        createWall(g, g1);
        createWall(h, h1);

        float[] i = {300, 25};
        float[] j = {300, -25};
        float[] k = {350, 25};
        float[] l = {350, -25};
        createWall(i, j);
        createWall(i, k);
        createWall(j, l);
        createWall(k, l);

        float[] m = {280, -125};
        float[] n = {310, -125};
        float[] o = {280, -155};
        float[] p = {310, -155};
        createWall(m, n);
        createWall(n, p);
        createWall(m, o);
        createWall(o, p);

        float[] g2 = {550, 100};
        float[] h2 = {600, 75};
        float[] g3 = {550, 300};
        float[] h3 = {600, 275};
        float[] g4 = {700, 300};
        float[] h4 = {700, 275};
        createWall(g1, g2);
        createWall(h1, h2);
        createWall(g2, g3);
        createWall(h2, h3);
        createWall(g3, g4);
        createWall(h3, h4);

        float[] q = {700, 350};
        float[] r = {700, 150};
        float[] s = {850, 150};
        float[] t = {850, 350};
        float[] s1 = {750, 150};
        float[] r1 = {725, 150};
        createWall(g4, q);
        createWall(h4, r);
        createWall(q, t);
        createWall(r, r1);
        createWall(s, t);
        createWall(s, s1);

        float[] r2 = {725, 0};
        float[] s2 = {750, 0};
        createWall(r1, r2);
        createWall(s1, s2);
       
        float[] u = {875, 0};
        float[] v = {700, 0};
        float[] w = {875, -100};
        float[] x = {700, -100};
        float[] w1 = {850, -100};
        float[] x1 = {825, -100};
        createWall(r2, v);
        createWall(s2, u);
        createWall(u, w);
        createWall(v, x);
        createWall(w, w1);
        createWall(x, x1);

        float[] w2 = {850, -200};
        float[] x2 = {825, -200};
        createWall(w1, w2);
        createWall(x1, x2);

        float[] y = {1050, -200};
        float[] z = {625, -200};
        float[] z1 = {625, -400};
        float[] aa = {1050, -500};
        float[] aa0 = {800, -500};
        float[] bb = {625, -500};
        float[] bb1 = {625, -450};
        float[] bb0 = {775, -500};
        createWall(w2, y);
        createWall(x2, z);
        createWall(y, aa);
        createWall(z, z1);
        createWall(bb, bb1);
        createWall(aa, aa0);
        createWall(bb, bb0);

        float[] gg = {725, -380};
        float[] hh = {750, -380};
        float[] ii = {750, -405};
        float[] jj = {725, -405};
        createWall(gg, hh);
        createWall(gg, jj);
        createWall(ii, hh);
        createWall(ii, jj);

        float[] kk = {970, -330};
        float[] ll = {950, -330};
        float[] mm = {950, -310};
        float[] nn = {970, -310};
        createWall(kk, ll);
        createWall(kk, nn);
        createWall(mm, ll);
        createWall(mm, nn);

        float[] z2 = {500, -400};
        float[] bb2 = {500, -450};
        createWall(z1, z2);
        createWall(bb1, bb2);

        float[] cc = {500, -370};
        float[] cc1 = {325, -370};
        float[] dd = {500, -520};
        float[] ee = {270, -520};
        float[] ff = {270, -370};
        float[] ff1 = {300, -370};
        createWall(z2, cc);
        createWall(bb2, dd);
        createWall(dd, ee);
        createWall(ee, ff);
        createWall(ff, ff1);
        createWall(c3, ff1);
        createWall(cc, cc1);
        createWall(cc1, h0);

        float[] aa01 = {800, -675};
        float[] bb01 = {775, -675};
        createWall(aa0, aa01);
        createWall(bb0, bb01);

        float[] oo = {900, -675};
        float[] pp = {675, -675};
        float[] qq = {900, -825};
        float[] rr = {675, -825};
        createWall(aa01, oo);
        createWall(bb01, pp);
        createWall(oo, qq);
        createWall(pp, rr);
        createWall(qq, rr);
    }
}