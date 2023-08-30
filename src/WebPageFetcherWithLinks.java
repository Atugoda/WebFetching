
import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.*;

public class WebPageFetcherWithLinks {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WebPageFetcherWithLinks <url>");
            return;
        }

        String urlStr = args[0];
        try {
            // Set proxy information (Update with your proxy details)
            System.setProperty("http.proxyHost", "");
            System.setProperty("http.proxyPort", "8080");

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();

                // Extract links using regular expression
                String regex = "<a\\s+href\\s*=\\s*\"([^\"]+)\"";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(content.toString());

                Set<String> links = new HashSet<>();
                while (matcher.find()) {
                    links.add(matcher.group(1));
                }

                // Download linked web pages
                String basePath = "downloaded_pages/";
                for (String link : links) {
                    downloadWebPage(link, basePath);
                }

                System.out.println("Downloaded " + links.size() + " linked web pages.");
            } else {
                System.out.println("Failed to fetch the web page. Response code: " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void downloadWebPage(String link, String basePath) throws IOException {
        URL url = new URL(link);
        String fileName = link.replaceAll("[^a-zA-Z0-9.-]", "_");
        File file = new File(basePath + fileName + ".html");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            // Save content to file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content.toString());
            fileWriter.close();

            System.out.println("Downloaded: " + fileName + ".html");
        } else {
            System.out.println("Failed to download: " + fileName + ".html. Response code: " + responseCode);
        }
    }
}
