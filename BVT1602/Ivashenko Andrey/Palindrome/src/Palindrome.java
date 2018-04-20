/*
 *  Palindrome checks the arguments for palindrome
 */
public class Palindrome {
    /*
     *  main begins this program
     */
    public static void main(String[] args){

        for(int i = 0; i < args.length; i++){
            System.out.println(args[i] + " - " +
                    (isPali(args[i]) ? "is palindrome" : "is not palindrome"));
        }

    }
    /*
     *  isPali return true if string word is palindrome
     *  else return false
     */
    public static boolean isPali(String word){
        if(word.equals(reverseString(word)))return true;
        return false;
    }
    /*
     *  reverseString return reversed string s
     */
    public static String reverseString(String s){
        String res = "";
        for(int i = 0; i < s.length(); i++){
            res += s.charAt(s.length()-i-1);
        }
        return res;
    }
}
