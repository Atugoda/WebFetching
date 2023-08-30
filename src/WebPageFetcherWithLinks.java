import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPageFetcherWithLinks {
    private static final Pattern LINK_PATTERN = Pattern.compile("href=\"(http[^\"]+)\"");

    private static Set<String> visitedUrls = new HashSet<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WebSpiderEnhanced <url>");
            return;
        }

        String startingUrl = args[0];

        crawlAndDownload(startingUrl);
    }

    private static void crawlAndDownload(String url) {
        if (visitedUrls.contains(url)) {
            return;
        }

        visitedUrls.add(url);

        try {
            URL webpageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) webpageUrl.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());

                Matcher matcher = LINK_PATTERN.matcher(line);
                while (matcher.find()) {
                    String linkedUrl = matcher.group(1);
                    System.out.println("Found link: " + linkedUrl);
                    downloadAndSave(linkedUrl);
                    crawlAndDownload(linkedUrl);
                }
            }
            reader.close();

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadAndSave(String url) {
        try {
            URL webpageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) webpageUrl.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            reader.close();

            connection.disconnect();

            String fileName = getFileNameFromUrl(url);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}

