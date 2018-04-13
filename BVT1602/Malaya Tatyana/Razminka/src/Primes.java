public class Primes{
	public static void main(String[] args){
		System.out.print("Простые числа: ");
	for (int n=2;n<=100;n++) {
		 if (isPrime(n))
			 System.out.print(n+",");
		 
	}
	}
	public static  boolean  isPrime(int n){
		
		for(int i=2; i<n;i++) {
			if (n%i==0)
				return false;			 
		}
		return true;
}}
