import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.print;

public class ooc1_4 {
    public static void main(String[] args) throws IOException {
        int count = 0;
        String initPage = "https://cs.muic.mahidol.ac.th/courses/ooc/docs/index.html";
        ArrayList<String> HtmlList = getHtmlContent(initPage);
        ArrayList<String> nonHtmlList = getNonHtmlContent(initPage);
        HashSet visited = new HashSet();
        while (!HtmlList.isEmpty()) {
            if (!visited.contains(HtmlList.get(0))) {
                ArrayList<String> nonHtmlContent = getNonHtmlContent(HtmlList.get(0));
                visited.add(HtmlList.get(0));
                HtmlList.addAll(getHtmlContent(HtmlList.get(0)));

                for (String each : nonHtmlContent) {
                    if (!nonHtmlList.contains(each)) {
                        nonHtmlList.add(each);
                    }
                }
            }
            HtmlList.remove(0);
            count++;
            if (count%100 == 0) {
                System.out.println(visited.size());
                System.out.println("This is html: " + HtmlList.size());
            }
        }

        System.out.println("HTML all loaded");

        for (String content : nonHtmlList) {
            try {
                String contentPath = content.substring(30);
                String path = "/Users/JarHan/Desktop" + contentPath;
                FileUtils.copyURLToFile(new URL(content), new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Object html : visited) {
            try {
                int words = 0;
                String htmlPath = html.toString().substring(30);
                String path = "/Users/JarHan/Desktop" + htmlPath;
                FileUtils.copyURLToFile(new URL(html.toString()), new File(path));
                words = wordCount(path);
                System.out.println(words + " words for " + html.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int wordCount(String path) throws IOException {
        int cWord = 0;
        FileReader cReader = new FileReader(path);
        BufferedReader cBuffer = new BufferedReader(cReader);
        cBuffer = new BufferedReader(new FileReader(path));
        String cCurrentLine;

        while ((cCurrentLine = cBuffer.readLine()) != null) {
            cWord += cCurrentLine.split(" ").length;
        }
        return cWord;
    }

    public static ArrayList<String> getHtmlContent(String chtml) throws IOException {
        ArrayList<String> htmlList = new ArrayList<String>();
        Document cDoc = Jsoup.connect(chtml)
                .ignoreHttpErrors(true)
                .get();
        Elements links = cDoc.select("a[href]");
        for (Element link : links) {
            if (link.attr("abs:href").startsWith("https://cs.muic.mahidol.ac.th/") && link.attr("abs:href").endsWith("html")) {
                htmlList.add(link.attr("abs:href"));
            }
        }
        return htmlList;
    }

    public static ArrayList<String> getNonHtmlContent(String chtml) throws IOException {
        ArrayList<String> nonHtmlList = new ArrayList<String>();
        Document cDoc = Jsoup.connect(chtml)
                .ignoreHttpErrors(true)
                .get();
        Elements imgs = cDoc.select("img[src]");
        Elements styles = cDoc.select("link[href]");
        Elements iframes = cDoc.select("iframe[src]");
        for (Element img : imgs) {
            if (img.attr("abs:src").startsWith("https://cs.muic.mahidol.ac.th/courses/ooc/docs/")) {
                nonHtmlList.add(img.attr("abs:src"));
            }
        }
        for (Element style : styles) {
            if (style.attr("abs:href").startsWith("https://cs.muic.mahidol.ac.th/courses/ooc/docs/")) {
                nonHtmlList.add(style.attr(("abs:href")));
            }
        }
        for (Element iframe : iframes) {
            if (iframe.attr("abs:src").startsWith("https://cs.muic.mahidol.ac.th/courses/ooc/docs/")) {
                nonHtmlList.add(iframe.attr("abs:src"));
            }
        }
        return nonHtmlList;
    }
}