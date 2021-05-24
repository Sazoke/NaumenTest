import java.awt.*;
import java.util.*;

public class MyRouteFinder implements RouteFinder {
    private static final char startSymbol = '@';
    private static final char endSymbol = 'X';
    private static final char wallSymbol = '#';
    private static final char roadSymbol = '+';

    @Override
    public char[][] findRoute(char[][] map) {
        ArrayList<Point> minWay = findWay(map);
        if(minWay == null)
            return null;

        for (Point point: minWay){
            map[point.y][point.x] = roadSymbol;
        }

        return map;
    }

    private ArrayList<Point> findWay(char[][] map){
        Point startPoint = getStartedPoint(map);
        if(startPoint == null)
            return null;

        HashSet<Point> visitedPoints = new HashSet<>();
        visitedPoints.add(startPoint);
        ArrayDeque<ArrayList<Point>> queue = new ArrayDeque<>();
        for (Point point: getNearPoints(startPoint, map, visitedPoints)) {
            ArrayList<Point> way = new ArrayList<>();
            way.add(point);
            queue.add(way);
        }

        while (!queue.isEmpty()){
            ArrayList<Point> nowWay = queue.pop();
            for (Point point: getNearPoints(nowWay.get(nowWay.size() - 1), map, visitedPoints)) {
                ArrayList<Point> newWay = new ArrayList<>(nowWay);
                newWay.add(point);
                if(map[point.y][point.x] == endSymbol) {
                    return nowWay;
                }
                queue.addLast(newWay);
            }
        }
        return null;
    }

    private Point getStartedPoint(char[][] map){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == startSymbol)
                    return new Point(j, i);
            }
        }
        return null;
    }

    private ArrayList<Point> getNearPoints(Point currentPoint, char[][] map, HashSet<Point> checkedPoints){
        ArrayList<Point> result = new ArrayList<>();
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                Point point = new Point(currentPoint.x + i, currentPoint.y + j);
                if(i != j && i != -j && isCorrectPoint(point, map, checkedPoints)) {
                    result.add(point);
                    checkedPoints.add(point);
                }
            }
        }

        return result;
    }

    private boolean isCorrectPoint(Point point, char[][] map, HashSet<Point> checkedPoints){
        if(point.x < 0 || point.y < 0 || point.x >= map[0].length || point.y >= map.length)
            return false;
        return !checkedPoints.contains(point) && map[point.y][point.x] != wallSymbol;
    }
}
