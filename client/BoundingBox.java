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
  
  public static boolean isColliding(BoundingBox a, BoundingBox b) {
    //funktioniert zunaechst nur in x-Richtung
      if (a.x < b.x) {
         if (a.x + a.width > b.x) {
           return true;
         } 
      }
      else if (a.x < b.x + b.width) {
          return true;
        }
    return false;   
  }
}
