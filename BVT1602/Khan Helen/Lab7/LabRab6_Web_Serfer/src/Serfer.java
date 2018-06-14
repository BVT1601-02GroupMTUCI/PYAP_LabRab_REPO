import java.net.*;
import java.util.*;
import java.io.*;


public class Serfer {
    /**
     * константа для определения что найденная строка евляется подходящей нам ссылкой.
     */
   static String URL_Preffix = "a href=\"";

    /**
     * константный символ? определяющий конец WebHost и начало пути к документу на хостинге(Docpath)
     */
   static String END_URL = "\"";

    public static void main(String[] args) {

        // счетчик глубины.
        int depth = 0;
        //кол-во активных потоков
        int numThreads = 0;

        // проверка на кол-во введенных параметров cmd,если не верный ввод
        if (args.length != 3) {
            System.out.println(" Повторите ввод! Входная строка должна иметь вид: java Serfer <URL> <depth> <number tread>");
            System.exit(1);
        }
        // если все норм
        else {
            try {
                // преобразуем второй параметр входной строки в инт.
                depth = Integer.parseInt(args[1]);
                numThreads = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException nfe) {
                // ежели второй аргумент не конвертится в инт(т.е. не число)
                System.out.println("Повторите ввод! Входная строка должна иметь вид: java Serfer <URL> <depth>");
                System.exit(1);
            }
        }

        //ставим глубину для ссылки, введенной пользователем(той что будем обыскивать), равной нулю
        URLDepthPair currentDepthPair = new URLDepthPair(args[0], 0);

        // создаем пул и добавляем введенную пользователем ссылку.
        URLPool pool = new URLPool();
        pool.add(currentDepthPair);


        // переменные для хранения общего кол-ва потоков и кол-ва активных потоков.
        int initialActive = Thread.activeCount();

        /*если количество активных или ожидающих потоков меньше, чем указал пользователь, создаем еще потоки,
        * иначе ждем (sleep)*/
        while (pool.getWaitThreads() != numThreads) {
            if (Thread.activeCount() - initialActive < numThreads) {//тут вычитаем основной поток(main), его не считаем
                //создаем новый объект типа SerferTask и запускаем поток для текущей ссылки
                SerferTask crawler = new SerferTask(pool);
                new Thread(crawler).start();
            }
            else {
                try {
                    Thread.sleep(100);  // 0.1 second
                }
                    //ислючение прерывания работы потока
                catch (InterruptedException ie) {
                    System.out.println("Обработано исключение" +
                            "InterruptedException, игнорировано...");
                }

            }
        }

        // если все потоки ожидают(т.е. нет ссылок в пуле), распечатаем те, что уже обработали
        Iterator<URLDepthPair> iter = pool.processedURLs.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.exit(0);
    }
    //переменная для хранения сокета
    static Socket sock;
    /**
     * Инициализируем сокет и устанавливаем таймаут для него*/
    private static int IntialSocket(URLDepthPair myDepthPair){
        //создаем новый сокет с URL-адресом, переданным методу в
        //URLDepthPair и порт 80.
        try {
            sock = new Socket(myDepthPair.getWebHost(), 80);
        }
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
            return 1;
        }
        catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
            return 2;
        }

        // устанавиваем время задержки сокеиа в 3 сек.
        try {
            sock.setSoTimeout(3000);
        }
        // Если ошибка сокета.
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return 3;
        }
        return 0;
    }
    private static OutputStream GetOutputStr(){

        OutputStream outStream;

        // получаем выходной поток с сокета
        try {
            outStream = sock.getOutputStream();
        }
        // если ошибка ввода/вывода потока, возвращаем список ссылок
        // (он будет пустым т.к. мы ничего еще даже не запросили и не прочитали).
        catch (IOException exce) {
            System.err.println("IOException: " + exce.getMessage());
            return null;
        }
        return outStream;
    }
    private static InputStream GetInputStr(){

        InputStream inStream;

        // получаем поток из socketа.
        try {
            inStream = sock.getInputStream();
        }
        // обработка исключений в случае, если поток пришел пустой или не правильный
        catch (IOException excep){
            System.err.println("IOException: " + excep.getMessage());
            return null;
        }
        return inStream;
    }


    public static LinkedList<String> getLinks(URLDepthPair myDepthPair) {

        // Инициализируем связанный список строк, которые будут хранить найденные ссылки
        LinkedList<String> URLs = new LinkedList<String>();

            if(IntialSocket(myDepthPair)!=0){
                return URLs;
            }

        // строковые преременные для хранения пути файла и webHost
        String docPath = myDepthPair.getDocPath();
        String webHost = myDepthPair.getWebHost();
        /*инициализирунм переменную выходног потока*/
        OutputStream outStream=GetOutputStr();
        if(outStream==null){return URLs;}

           /*все действия myWriter пишем в первый параметр (OutStream), т.е. записываем в выходной поток,
           * направленный к серверу*/
        PrintWriter myWriter = new PrintWriter(outStream, true);

        //отправляем запрос на сервер.
        myWriter.println("GET " + docPath + " HTTP/1.1");//получаем путь к файлу
        myWriter.println("Host: " + webHost); //получаем хост
        myWriter.println("Connection: close");
        myWriter.println();
                /*Конец отправки данных на сервер*/

                /*Читаем ответ с сервера*/

        // инициализируем InputStream.
        //InputStream представляет классы, которые получают данные, в нашем случае входной поток с сокета
        InputStream inStream=GetInputStr();
        if(inStream==null){return URLs;}
        // создаем новый InputStreamReader И Buffer для буфферизации потока, полученного в InputStream
        //Класс Java BufferedReader используется для чтения текста из входного потока на основе символов.
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader BuffReader = new BufferedReader(inStreamReader);

        //  чтения данных по строкам методом readLine ().
        while (true) {
            String line;
            try {
                line = BuffReader.readLine();
            }
            // обработка исключений.
            catch (IOException except) {
                System.err.println("IOException: " + except.getMessage());
                return URLs;
            }
            // если прочитали документ до конца
            if (line == null)
                break;
            //индексы начала, конца и текущего символа строки
            int beginIndex = 0;
            int endIndex = 0;
            // поиск ссылки(вернее последовательности "a href") в текущей строке начиная с первого символа.
            int index = line.indexOf(URL_Preffix, 0);
            // если в этой строке нет a href, то в цикл не входим
            while (index!=-1) {


                // если это ссылка, двигаем индекс начала ссылки на длину строки "a href", чтобы получить саму ссылку.
                index += URL_Preffix.length();
                beginIndex = index;

                // Ищем конец ссылки (т.е. символ /).
                endIndex = line.indexOf(END_URL, index);
                index = endIndex;

                //выделяем подстроку между нашим символом начала ссылки и символом конца ссылки,
                // (т.е. получаем строку, содержащую саму ссылку), и записываем сылку в список
                String newLink = line.substring(beginIndex, endIndex);
                URLs.add(newLink);
                index = line.indexOf(URL_Preffix, index);
            }

        }
        // Возвращаем список ссылок.
        return URLs;
    }
}
