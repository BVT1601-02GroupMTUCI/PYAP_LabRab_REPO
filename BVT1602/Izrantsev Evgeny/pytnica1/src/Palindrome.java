import java.util.Scanner;

public class Palindrome {
    public static void main(String[] args) {
       // for (int i = 0; i < args.length; i++) {
        //    String s = args[i];
        Scanner scan = new Scanner(System.in);
        String s= scan.next();
        isPalindrome(s);
        }

    public static String reverseString(String s){
        String s1 = "";
        for ( int i = s.length()-1;  i >= 0 ; --i) {
         s1+=s.charAt(i);
        }
        return s1;
    }


    public static boolean isPalindrome(String s)
    {
        if (s.equals(reverseString(s))){
            System.out.println("Полиндром");
        }else{
            System.out.println("Не полиндром");
        }

        return s.equals(reverseString(s));
    }
}
