import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ooc1_3c {
    public static void main(String[] args) throws IOException {
        FileUtils.copyURLToFile(
                new URL("http://www.muic.mahidol.ac.th/eng/"),
                new File("/Users/JarHan/Desktop/OOC/homework/1/test_c"));
        System.out.println("Finish downloaded");
    }

//    http://commons.apache.org/proper/commons-io/javadocs/api-2.4/org/apache/commons/io/FileUtils.html#copyURLToFile(java.net.URL,%20java.io.File)
}