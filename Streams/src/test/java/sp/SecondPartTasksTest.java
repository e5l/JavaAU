package sp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws FileNotFoundException {
        ArrayList<String> paths = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();

        URL path = classLoader.getResource("text1.txt");
        if (path == null)
            throw new FileNotFoundException("Couldn't find resource: text1.txt");

        paths.add(path.getPath());
        paths.add(path.getPath());

        List<String> result = SecondPartTasks.findQuotes(paths, "внедрения");
        List<String> expected = Arrays.asList("внедрения и", "внедрения и модернизации", "внедрения и", "внедрения и модернизации");

        assertEquals(expected, result);
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(SecondPartTasks.piDividedBy4(), Math.PI / 4.0, 0.001);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> data = ImmutableMap.of(
            "Vasya", ImmutableList.of("Abc", "Cde", "DoReMe", "Muhahahaha"),
            "Petya", ImmutableList.of("Elephant", "Code"),
            "None", ImmutableList.of()
        );

        assertEquals("Vasya", SecondPartTasks.findPrinter(data));
    }

    @Test
    public void testCalculateGlobalOrder() {
        List<Map<String, Integer>> orders = ImmutableList.of(
                ImmutableMap.of(
                        "A", 100,
                        "B", 50,
                        "C", 1
                ),
                ImmutableMap.of(),
                ImmutableMap.of(
                        "A", 50,
                        "D", 10
                )
        );

        Map<String, Integer> expected = ImmutableMap.of(
                "A", 150,
                "B", 50,
                "C", 1,
                "D", 10
        );

        assertEquals(expected, SecondPartTasks.calculateGlobalOrder(orders));
    }
}