import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class PointComparator implements Comparator<ArrayList<Point>> {
    private Point end;
    public PointComparator(Point end){
        this.end = end;
    }

    @Override
    public int compare(ArrayList<Point> points, ArrayList<Point> t1) {
        return getWeight(points) - getWeight(t1);
    }

    private int getWeight(ArrayList<Point> points){
        int size = points.size();
        Point last = points.get(size - 1);
        return Math.abs(end.x - last.x) + Math.abs(end.y - last.y) + size;
    }
}
