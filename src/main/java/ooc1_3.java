import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ooc1_3 {
    public static void main(String[] args) {
        ooc1_3 client = new ooc1_3();
        client.download();
        System.out.println("Finish downloaded");
    }

    public void download(){
        try {
            URL cURL = new URL("http://www.muic.mahidol.ac.th/eng/");;
            StringBuffer cFile = new StringBuffer();

            URLConnection urlConnection = cURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String cLine;

            while ((cLine = reader.readLine()) != null) {
                cFile.append(cLine + "\n");
            }

            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/JarHan/Desktop/test_a")));
            writer.write(cFile.toString());
            writer.close();

        } catch (IOException e) {
        // Do something here
        }
    }
}