public class polindrom {
    public static void main(String[] args) {
for(int i=0;i<args.length;i++){
    String s=args[i];
    System.out.println( ReverseString(s));

}
    }
    public static String ReverseString(String s){
        String reverse="";
        for(int i=s.length()-1;i>=0;i--){
            reverse+=(s.charAt(i));

        }System.out.println(s);
        System.out.println(reverse);
        if(isPalindrome(s,reverse))
        return "полиндром";
        else return "не полиндром";


    }
    public static boolean isPalindrome(String s1,String s2){
        if(s1.equals(s2)){
            return true;
        }return false;

    }
}
