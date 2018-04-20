/*
 *  Primes outputs prime numbers from 0 to 100
 */
public class Primes {
    /*
     *  main begins this program
     */
    public static void main(String[] args){
        for(int i = 2; i < 100 + 1; i++){
            if(isPrime(i))System.out.println(""+i);
        }
    }
    /*
     *  isPrime return true if number is prime
     *  else return false
     */
    public static boolean isPrime(int n){
        for( int i = 2; i < n; i++){
            if(n%i == 0)return false;
        }
        return true;
    }
}
