public class Palindrome {
    public static void main(String[] args) {
        String s = "java Palindrome madam racecar apple kayak song noon";
        for(int i=0; i < s.split(" ").length; i++)
            System.out.println( ReverseString(s.split(" ")[i]) );
    }

    public static String ReverseString(String s){
        String reverse="";
        for(int i=s.length()-1;i>=0;i--)
            reverse+=(s.charAt(i));
        System.out.println(s);
        System.out.println(reverse);
        if(isPalindrome(s,reverse))
            return "Палиндром";
        else return "Не палиндром";
    }

    public static boolean isPalindrome(String s1,String s2){
        if(s1.equals(s2))
            return true;
        return false;
    }
}