abstract class Kreatur extends Graphikobjekt{
  private int leben;
  public int angriffsstaerke;
  
  public Kreatur(int x, int y) {
    super(x, y);
  }
  
  public int getLeben() {
    return leben;
    }
  
  
  public void turn(float angle) {
    }
  public void move(float speed) {
    }
}
