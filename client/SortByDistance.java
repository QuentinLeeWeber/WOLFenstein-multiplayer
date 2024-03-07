import java.util.Comparator;

public class SortByDistance implements Comparator<Pixel>{

    public int compare(Pixel a, Pixel b){
        float read = a.distance - b.distance;
        return read == 0.0f ? 0 : read > 0? -1:1;
    }
}
