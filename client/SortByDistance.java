import java.util.Comparator;

public class SortByDistance implements Comparator<Pixel>{

    public int compare(Pixel a, Pixel b){
        /*if(a.distance - b.distance > 0){
            return 1;
        } 
        if(a.distance - b.distance < 0){
            return -1;
        }
        if(a.distance - b.distance == 0){
            return 1;
        }
        return 1;*/
        if((int) (a.distance - b.distance) == 0){
            return -1;
        }
        return (int) (a.distance - b.distance);
    }
}
