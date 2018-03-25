public class Palindrome {
    public static void main(String[] args){
        for (int i = 0; i<args.length;i++){
            String s = args[i];
            if (isPalindrome(s)) System.out.println(s+" is palindrome");
            else System.out.println(s+" is not palindrome");
        }
    }

    public static String reverseString(String s){
        String rs = "";
        for (int i=s.length()-1;i>=0;i--){
            rs+=s.charAt(i);
        }
        return rs;
    }

    public static boolean isPalindrome(String s){
        if (s.equals(reverseString(s))) return true;
        else return false;
    }
}
