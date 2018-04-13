/**
 * TODO:  Описание класса
 */
public class Palindrome {
    /**
     * TODO:  Описание метода
     */
    public static boolean isPalindrome(String s){
        String s2=reverseString(s);
        return s.equals(s2);
    }

    public static String reverseString(String s){

        int l = s.length();
        String s2="";
        for (int i=l-1;i>=0;i--)
            s2= s2+ s.charAt(i);
        return s2;
    }
    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (isPalindrome(s)==true)
            System.out.println("Слово "+s+" является палиндромом");
            else System.out.println("Слово "+s+" не является палиндромом");
        }

    }
}
