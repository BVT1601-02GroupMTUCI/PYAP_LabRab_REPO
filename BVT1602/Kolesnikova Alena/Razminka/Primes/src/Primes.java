//* TODO:  Комментарий, описывающий класс
public class Primes {
   // public static boolean isPrime;
    public static boolean isPrime(int n) {
        // TODO:  Реализация
        for (int i = 2; i < n; i++)
            if (n % i == 0) return false;
                return true;
    }

      //    * TODO:  Комментарий, описывающий метод
    public static void main(String[] args) {


        // TODO:  Реализация программы
        for (int n=2; n<=100;n++) {
            if  ((isPrime(n)) == true)
            {

                System.out.print (n+ ", ");
        }}



    }
}
