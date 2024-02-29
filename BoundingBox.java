class BoundingBox {
      //x und y sind die Koordinaten der oberen linken Ecke
      public int x;   
      public int y;
      public int width;
      public int height;
  
  public BoundingBox(int _x, int _y, int _width, int _height) {
       x = _x;
       y = _y;
       width = _width;
       height = _height;
  }
  
  public boolean isColliding(BoundingBox a) {
    return BoundingBox.isColliding(this, a);
  }

  public static boolean isColliding(BoundingBox a, BoundingBox b) {
    boolean xOverlap = false;
    boolean yOverlap = false;
    if (a.x < b.x) {
        if (a.x + a.width > b.x) {
          xOverlap = true;
        } 
    }
    else if (a.x < b.x + b.width) {
      xOverlap = true;
    }
    if (a.y < b.y) {
      if (a.y + a.height > b.y) {
        yOverlap = true;
      } 
    }
    else if (a.y < b.y + b.height) {
      yOverlap = true;
    }
    return xOverlap && yOverlap;   
  }
}