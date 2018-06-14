import java.net.*;

public class URLDepthPair {


    private int currentDepth; //текущая глубина просмотра
    private String currentURL;  //переменная для хранения текущей ссылки

    /**
     * Конструктор для установки глубину просмотра и активную ссылку.
     */
    public URLDepthPair(String URL, int depth) {
        currentDepth = depth;
        currentURL = URL;
    }
    /**
     * Геттер для предоставления URl
     */
    public String getURL() {
        return currentURL;
    }
    /**
     * Геттер возвращающий глубину просмотра.
     */
    public int getDepth() {
        return currentDepth;
    }
    /**
     * метод, возвращающий текущий адрес и текущую глубину в виде строки
     */
    public String toString() {
        String stringDepth = Integer.toString(currentDepth);
        return stringDepth + '\t' + currentURL;
    }

    public String getDocPath() {
        try {
            URL url = new URL(currentURL);
            return url.getPath();
        }
        //обработка исключений на случай не верного адреса:
        // MalformedURLException является исключением, которое возникает, когда клиент не может правильно разобрать URL-адрес.
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
    /**
     *Метод возвращает Хост данного URL
     */
    public String getWebHost() {
        try {
            URL url = new URL(currentURL);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }


}