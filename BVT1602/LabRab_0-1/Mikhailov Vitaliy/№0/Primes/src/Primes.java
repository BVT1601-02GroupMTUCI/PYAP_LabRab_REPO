public class Primes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int n;
        for (n=2;n<101;n++)
        {
            if (isPrime(n)==true) {
                System.out.print(n+" ");
            };
        }
        
    }
    public static boolean isPrime(int n){
        for (int j=2;j<n;j++)
        {
            if (n%j==0) return false;
        }   
        return true;
    }
    
}
