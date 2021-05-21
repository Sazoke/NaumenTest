import java.awt.*;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Stack;

public class MyRouteFinder implements RouteFinder {
    private static final char startSymbol = '@';
    private static final char endSymbol = 'X';
    private static final char wallSymbol = '#';
    private static final char roadSymbol = '+';

    @Override
    public char[][] findRoute(char[][] map) {
        Point startPoint = getStartedPoint(map);
        if(startPoint == null)
            return null;
        Route minRoute = new Route();
        HashSet<Point> visitedPoints = new HashSet<>();
        visitedPoints.add(startPoint);
        ArrayList<Point> startList = new ArrayList<>();
        startList.add(startPoint);
        Stack<Route> stack = new Stack<>();
        Route firstRoute = new Route();
        firstRoute.VisitedPoints = visitedPoints;
        firstRoute.Way = startList;
        stack.add(firstRoute);

        while (!stack.empty()){
            Route nowRoute = stack.pop();
            for (Point point: getNearPoints(nowRoute.Way.get(nowRoute.Way.size() - 1), map, nowRoute.VisitedPoints)) {
                ArrayList<Point> newWay = new ArrayList<>(nowRoute.Way);
                newWay.add(point);
                if(minRoute.Way != null && minRoute.Way.size() < newWay.size())
                    break;
                if(map[point.y][point.x] == endSymbol) {
                    minRoute = nowRoute;
                }
                nowRoute.VisitedPoints.add(point);
                Route newRoute = new Route();
                newRoute.Way = newWay;
                newRoute.VisitedPoints = new HashSet<>(nowRoute.VisitedPoints);
                stack.push(newRoute);
            }
        }

        if(minRoute.Way == null)
            return null;
        for (Point road: minRoute.Way.subList(1, minRoute.Way.size())){
            map[road.y][road.x] = roadSymbol;
        }

        return map;
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
                Point point = new Point(currentPoint.x + j, currentPoint.y + i);
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

    private class Route{
        public ArrayList<Point> Way;
        public HashSet<Point> VisitedPoints;
    }
}
