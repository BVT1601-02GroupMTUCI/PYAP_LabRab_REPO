   public class Palindrome {
         public static String reverseString(String s)
         {
        	  String revers ="";
        	  for(int i=s.length()-1; i>=0;i--)
        	  {
        		  revers=revers+s.charAt(i);
        	  }
        	  return revers;

         }
         public static void main(String[] args) {
             for (int i = 0; i < args.length; i++) {
                String s = args[i];
                if (isPalindrome(s))
                System.out.println("���������"); 
                else System.out.println(" �� ���������");
         
             }
         }
         
         public static boolean isPalindrome(String s) {
        	if (s.equals( reverseString(s))) return true;
        	else return false;
         }
}

