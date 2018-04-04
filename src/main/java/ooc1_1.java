import org.apache.commons.io.DirectoryWalker;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ooc1_1 extends DirectoryWalker {

    public static int countFiles = 0;
    public static int countDir = 0;
    public static Map<String, Integer> fileExt = new HashMap<String, Integer>();

    public ooc1_1() {
        super();
    }

    public Map<String, Integer> count(File startDirectory) throws IOException {
        List results = new ArrayList();
        walk(startDirectory, results);
        return fileExt;
    }

    protected boolean handleDirectory(File directory, int depth, Collection results) {
        countDir++;
        return true;
    }

    protected void handleFile(File file, int depth, Collection results) {
        countFiles++;
        String ext = getExtension(file.getName());

        if (!fileExt.keySet().contains(ext)) {
            fileExt.put(ext, 1);
        }
        else {
            Integer tmp = fileExt.get(ext);
            fileExt.put(ext, ++tmp);
        }
        results.add(file);
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        File fileName = new File(args[0]);
        ooc1_1 counter = new ooc1_1();
        counter.count(fileName);
        System.out.println("Total number of files: " + countFiles);
        System.out.println("Total number of directory: " + countDir);
        System.out.println("Total number of unique file extensions: " + fileExt.size());
        System.out.println("List all unique file extensions: " + fileExt.keySet());
        System.out.println("Total number of files for each extension: " + fileExt);
    }
}
