import java.util.*;

/**
 Класс пула URL для хранения списка URL-адресов для поиска с глубиной.
 */
public class URLPool {
    
    /** список не обработанных ссылок */
    private LinkedList<URLDepthPair> awaitURLs;
    
    /** список обработанных ссылок. */
    public LinkedList<URLDepthPair> processedURLs;
    
    /** arraylist хранит "ссылки", обнаруженные на указанном сайте и просмотренные.
     * Нужна для отображения текста просмотренных ссылок на экране. */
    private ArrayList<String> seenURLs = new ArrayList<String>();
    
    /** счетчик ожидающих потоков */
    public int waitingThreads;
    
    /** Конструктор для инициализации ожидающих потоков, обработанных URL-адресов и ожидающих
     * URL-адресов
     */
    public URLPool() {
        waitingThreads = 0;
        awaitURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
    }
    
    /** Synchronized метод для получения количества ожидающих потоков. */
    public synchronized int getWaitThreads() {
        return waitingThreads;
    }
    
    /** Synchronized метод для получения размеров пула */
    public synchronized int size() {
        return awaitURLs.size();
    }
    
    /** Synchronized метод для добавления глубины в пул */
    public synchronized boolean add(URLDepthPair depthPair) {
        
        // флаг для отслеживания добавлено/не добавлено
        boolean added = false;

        // Если глубина меньше максимальной глубины, добавить пару глубина-ссылка в пул
        if (depthPair.getDepth() < depthPair.getDepth()) {
            awaitURLs.addLast(depthPair);
            added = true; //изменяем состояние флага
            waitingThreads--; //уменьшаем кол-во ожидающих потоков
            this.notify(); //запускаем/продолжаем работу потока
        }
        else { //иначе просто добавляем ссылку в список просмотренных
            seenURLs.add(depthPair.getURL());
        }
        // возвращаем состояние флага добавления в пул ожидания
        return added;
        }

    /**
     * A synchronized метод для получения следующей ссылки из пула.
     */
    public synchronized URLDepthPair get() {
        
        // сбрасываем глубину
        URLDepthPair myDepthPair = null;
        
        // если пул пуст, потоки ждут
        if (awaitURLs.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            }
            // обработка исключений при нахождении ссылки на email
            catch (InterruptedException e) {
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        } 
        //удаляем первую пару глубина-ссылка из пула/списка ожидающих ссылок
        myDepthPair = awaitURLs.removeFirst();
        //добавляем ее ссылку в список просмотренных и обработанных ссылок
        seenURLs.add(myDepthPair.getURL());
        processedURLs.add(myDepthPair);
        return myDepthPair;
    }
    /** 
     * A synchronized method to get the list of seen URLs.
     */
    public synchronized ArrayList<String> getSeenList() {
        return seenURLs;
    }
}