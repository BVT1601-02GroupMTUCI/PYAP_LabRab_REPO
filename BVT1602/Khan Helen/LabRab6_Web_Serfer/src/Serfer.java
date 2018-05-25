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

        // счетчик.
        int depth = 0;

        // проверка на кол-во введенных параметров cmd,если не верный ввод
        if (args.length != 2) {
            System.out.println(" Повторите ввод! Входная строка должна иметь вид: java Serfer <URL> <depth>");
            System.exit(1);
        }
        // если все норм
        else {
            try {
                // преобразуем второй параметр входной строки в инт.
                depth = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException nfe) {
                // ежели второй аргумент не конвертится в инт(т.е. не число)
                System.out.println("Повторите ввод! Входная строка должна иметь вид: java Serfer <URL> <depth>");
                System.exit(1);
            }
        }

        // список не обработанных ссылок
        LinkedList<URLDepthPair> awaitURLs = new LinkedList<URLDepthPair>();

        // список обработанных ссылок.
        LinkedList<URLDepthPair> processedURLs = new LinkedList<URLDepthPair>();

        // добавляем урл, введенный пользователем в список урлов, ожидающих обработки.
        awaitURLs.add(new URLDepthPair(args[0], 0));

        // arraylist хранит "имена ссылок", обнаруженные на указанном сайте. Нужна для отображения текста просмотренных ссылок на экране
        //добавляем сайт введенный пользователем в список просмотренных сылок
        ArrayList<String> seenURLs = new ArrayList<String>();
        seenURLs.add(awaitURLs.getFirst().getURL());

        // если список не обработанных ссылок не пуст, берем последний элемент этого списка
        // и добавляем его в список обработанных ссылок.
        //также вычисляем глубину вхождения по ссылкам
        //int i=0;
        while (awaitURLs.size() != 0) {

            URLDepthPair depthPair = awaitURLs.pop();
            processedURLs.add(depthPair);
            int myDepth = depthPair.getDepth();

            // Получаем все ссылки, которые есть на исходном сайте и записываем его в связный список
            LinkedList<String> linksList = new LinkedList<String>();
            linksList = Serfer.getLinks(depthPair);

            // Если мы не достигли максимальной глубины обхода (которую задали вторым параметром cmd)
            // добавляем в список ссылки, находящиеся на сайте, проверяя их на повторение
            if (myDepth < depth) {

                for (int i=0;i<linksList.size();i++) {
                    String newURL = linksList.get(i);
                    // если такая ссылка уже есть в нашем списке, пропускаем.
                    if (seenURLs.contains(newURL)) {
                        continue;
                    }
                    // если данная ссылка еще не была нами просмотрена, добавляем ее в списки ождающих обработки и просмотренных ссылок
                    //и увеличиваем счетчик глубины просмотра
                    else {
                        awaitURLs.add(new URLDepthPair(newURL, myDepth + 1));
                        seenURLs.add(newURL);
                    }
                }
            }
        }
        // Выводим все обработанные ссылки на эеран
        Iterator<URLDepthPair> iter = processedURLs.iterator();//счетчик кол-ва записей в списке
        //пока есть следующий элемент списка
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
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


    private static LinkedList<String> getLinks(URLDepthPair myDepthPair) {

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
