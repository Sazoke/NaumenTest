import junit.framework.TestCase;

import java.util.ArrayList;

public class Tests extends TestCase {
    public void testEasyMapFinder(){
        assertTrue(isEqualsMaps(
                "...@.\n" +
                ".####\n" +
                ".....\n" +
                "####.\n" +
                ".X...\n",
                        "+++@.\n" +
                        "+####\n" +
                        "+++++\n" +
                        "####+\n" +
                        ".X+++"));
    }

    public void testNoWayMapFinder(){
        assertTrue(isEqualsMaps("..X..\n" +
                        "#####\n" +
                        ".....\n" +
                        ".@...\n" +
                        ".....\n",
                                null));
    }

    public void testHardMapFinder(){
        assertTrue(isEqualsMaps("....@\n" +
                        "#.###\n" +
                        ".....\n" +
                        "....X\n" +
                        ".....",
                                ".+++@\n" +
                                "#+###\n" +
                                ".+...\n" +
                                ".+++X\n" +
                                "....."));
    }

    private static boolean isEqualsMaps(String firstMap, String secondMap){
        char[][] map = parseStringMap(firstMap);
        MyRouteFinder finder = new MyRouteFinder();
        map = finder.findRoute(map);
        char[][] correctMap = parseStringMap(secondMap);

        if(map == null || correctMap == null)
            return map == null && correctMap == null;

        if(map.length != correctMap.length || map[0].length != correctMap[0].length)
            return false;

        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                if(map[i][j] != correctMap[i][j])
                    return false;
            }
        }

        return true;
    }

    private static char[][] parseStringMap(String str){
        if (str == null)
            return null;
        ArrayList<char[]> arrayList = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '\n' || i == str.length() - 1){
                if(i == str.length() - 1 && str.charAt(i) != '\n')
                    i++;
                char[] newArr = new char[i - startIndex];
                for (int j = startIndex; j < i; j++)
                    newArr[j - startIndex] = str.charAt(j);
                startIndex = i + 1;
                arrayList.add(newArr);
            }

        }

        return arrayList.toArray(new char[][]{});
    }
}
