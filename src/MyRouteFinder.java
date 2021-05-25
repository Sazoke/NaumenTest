import java.awt.*;
import java.util.*;

public class MyRouteFinder implements RouteFinder {
    private static final char startSymbol = '@';
    private static final char endSymbol = 'X';
    private static final char wallSymbol = '#';
    private static final char roadSymbol = '+';
    private Point start;
    private Point end;

    @Override
    public char[][] findRoute(char[][] map) {
        ArrayList<Point> minWay = findWay(map);
        if(minWay == null)
            return null;
        for (Point point: minWay)
            map[point.y][point.x] = roadSymbol;

        return map;
    }

    private ArrayList<Point> findWay(char[][] map){
        setStartAndEnd(map);
        if(start == null || end == null)
            return null;

        HashMap<Point, Integer> costs = new HashMap<>();
        costs.put(start, 0);

        PriorityQueue<ArrayList<Point>> queue = new PriorityQueue<>(new PointComparator(end));
        for (Point point: getNearPoints(start, 1, map, costs)) {
            ArrayList<Point> way = new ArrayList<>();
            way.add(point);
            queue.add(way);
        }

        while (!queue.isEmpty()){
            ArrayList<Point> nowWay = queue.poll();
            for (Point point: getNearPoints(nowWay.get(nowWay.size() - 1), nowWay.size() + 1, map, costs)) {
                if(point.equals(end))
                    return nowWay;

                ArrayList<Point> newWay = new ArrayList<>(nowWay);
                newWay.add(point);
                queue.add(newWay);
            }
        }
        return null;
    }

    private void setStartAndEnd(char[][] map){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == startSymbol)
                    start = new Point(j, i);
                else if(map[i][j] == endSymbol)
                    end = new Point(j, i);
                if(start != null && end != null)
                    return;
            }
        }
    }

    private ArrayList<Point> getNearPoints(Point currentPoint, int weight, char[][] map, HashMap<Point, Integer> costs){
        ArrayList<Point> result = new ArrayList<>();
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                Point point = new Point(currentPoint.x + i, currentPoint.y + j);
                if(i != j && i != -j && isCorrectPoint(point, weight, map, costs)) {
                    result.add(point);
                    costs.put(point, weight);
                }
            }
        }

        return result;
    }

    private boolean isCorrectPoint(Point point, int weight, char[][] map, HashMap<Point, Integer> costs){
        if(point.x < 0 || point.y < 0 || point.x >= map[0].length || point.y >= map.length)
            return false;
        return (!costs.containsKey(point) || costs.get(point) > weight) && map[point.y][point.x] != wallSymbol;
    }
}
