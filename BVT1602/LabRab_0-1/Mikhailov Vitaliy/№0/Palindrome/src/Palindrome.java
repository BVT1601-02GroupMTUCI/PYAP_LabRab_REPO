public class Palindrome {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i=0; i< args.length; i++){
            String s = args[i];
            if (isPalindrome(s))
            {
                System.out.println(s+" is a palindrome ");
            }
            else
            {
                System.out.println(s+" isn't a palindrome ");
            
            } 
        }
        
    }
    public static String reverseString(String s)
    {
        String p = "";
        for (int j=1;j<=(s.length());j++)
        {
            p+=s.charAt((s.length())-j);
        }
        //System.out.println(p);
        return p;
    }
    
    public static boolean isPalindrome(String s)
    {
        String p = reverseString(s);
        return s.equals(p);
    }
}
