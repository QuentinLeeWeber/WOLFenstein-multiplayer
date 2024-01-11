abstract class Graphikobjekt  {
  
      public int x;
      public int y;
      public String texture;
      
      public abstract void update();
  
      public Graphikobjekt(int _x, int _y) {
             x = _x;
             y = _y; 
      }
}
