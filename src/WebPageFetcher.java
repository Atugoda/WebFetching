import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;

public class WebPageFetcher {
    public static void main(String[] args) {
                if(args.length!=1){
                    System.out.println("Usage:java webpage fetch <http://www.w3schools.com/php/default.asp>");
                    return;
                }
                String urlStr = args[0];
                try{
                    System.setProperty("http.proxyHost" , "45.201.134.38");
                    System.setProperty("http.proxyPort","8080");

                    URL url = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                        reader.close();
                    } else {
                        System.out.println("Failed to fetch the web page. Response code: " + responseCode);
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }


                }
    }

