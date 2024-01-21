class Level1 extends Level {
    public Level1() {
        int[] a = {200, 200};
        int[] b = {500, 200};
        int[] c = {200, 100};
        createWall(a, b);
        createWall(a, c);
        createEnemy(100, 200);
    }
}
