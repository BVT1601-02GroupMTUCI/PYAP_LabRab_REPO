public class prime {

    public static void main(String[] args) { //main class
        for(int i=2;i<=100;i++){
            if(isPrime(i)){
                System.out.println(i);}
        }
    }
    public static boolean isPrime(int n){ //bool class
        for(int i =2; i < n; i++){
            if(n%i==0)
                return false;

        }
        return true;
    }

}
