class Level2 extends Level {
  public Level2() {
    super();
    createVector(200, 200, 200, 400);
    createVector(200, 200, 400, 200);
    createVector(200, 400, 400, 400);
    createVector(400, 400, 400, 200);
      generateWallsFromVectors();
  }
}
