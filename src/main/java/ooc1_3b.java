import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ooc1_3b {

    public static void main(String[] args) throws Exception {
        ooc1_3b client = new ooc1_3b();
        client.download();
        System.out.println("Finish downloaded");
    }

    public void download() {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://www.muic.mahidol.ac.th/eng/");

            HttpResponse response = client.execute(request);
            InputStream is = response.getEntity().getContent();

            FileOutputStream fos = new FileOutputStream(new File("/Users/JarHan/Desktop/OOC/homework/1/test_b"));

            int inByte;
            while ((inByte = is.read()) != -1) {
                fos.write(inByte);
            }

            is.close();
            fos.close();
            client.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    http://www.technicalkeeda.com/java-tutorials/httpclient-download-file-from-url
}