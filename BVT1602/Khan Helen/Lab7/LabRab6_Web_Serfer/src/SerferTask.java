import java.util.*;

/** SerferTask реализует интерфейс Runnable. Каждый экземпляр имеет
 ссылку на экземпляр класса URLPool. Получает пару глубина- ссылка из пула (останавливает поток, если нет ссылок),
 извлекает веб-страницу, получает все URL-адреса со страницы и добавляет
 новую пару ссылка-глубина(URLDepth) в пул URL-адресов для каждого найденного URL-адреса.
 */
public class SerferTask implements Runnable {

     public URLDepthPair depthPair;

    public URLPool myPool;

    /** конструктор, устанавливающий пул, переданный методу, в качестве основного(myPool) */
    public SerferTask(URLPool pool) {
        myPool = pool;
    }

    /** метод, содержащий код для выполнения в отдельном потоке */
    public void run() {

        // получить следующую пару глубина-ссылка из пула.
        depthPair = myPool.get();

        // полчаем глубину.
        int myDepth = depthPair.getDepth();

        // Получаем все ссылки с сайта и сохраняем их в новом связанном списке.
        LinkedList<String> linksList = new LinkedList<String>();
        linksList = Serfer.getLinks(depthPair);

        // цикл по всем полученным с сайта ссылкам
        for (int i=0;i<linksList.size();i++) {
            String newURL = linksList.get(i);

            // для каждой найденой ссылки пишем ее глубину, и добавляем пару глубина-ссылка в пул.
            URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepth + 1);
            myPool.add(newDepthPair);
        }
    }
}