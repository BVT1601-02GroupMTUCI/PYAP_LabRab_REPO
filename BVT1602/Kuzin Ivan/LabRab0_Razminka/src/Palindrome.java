public class Palindrome  {
    public Palindrome() {
    }

    public static void main(String[] args) {
        for(int i = 0; i < args.length; ++i) {
            String s = args[i];
            System.out.println("There Is: "+ReverseString(s));
        }

    }

    public static String ReverseString(String s) {
        String reverse = "";

        for(int i = s.length() - 1; i >= 0; --i) {
            reverse = reverse + s.charAt(i);
        }

        System.out.println("Input String: "+s);
        System.out.println("Reverse String: "+reverse);
        return isPalindrome(s, reverse) ? "пaлиндром" : "не пaлиндром";
    }

    public static boolean isPalindrome(String s1, String s2) {
        return s1.equals(s2);
    }
}