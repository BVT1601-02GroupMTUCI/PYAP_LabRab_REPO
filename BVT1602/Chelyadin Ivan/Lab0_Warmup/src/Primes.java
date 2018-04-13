
/*
    Класс описывающий программу, производящую поиск простых чисел
 */
public class Primes {

    /* Функция, выполняющаяся при запуске
       Выводит на экран все простые числа в диапазоне [2;100]
     */
    public static void main(String[] args){
        for (int i = 2; i<101;i++){
            if (isPrime(i)) System.out.print(i+",");
            /* expected output
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
             */
        }
    }
    // Функция проверяет является ли число простым
    public static boolean isPrime(int n){
        for (int i = 2; i<n;i++){
            if (n%i == 0) return false;
        }
        return true;
    }
}
