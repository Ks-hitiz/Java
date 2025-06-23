import java.util.Scanner;

public class Four {

    public static int len(String str){
        int l = 0;
        while(true){
            try{
                str.charAt(l);
                l++;
            }
            catch(StringIndexOutOfBoundsException e){
                break ;
            }
        }
        return l;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the String : ");
        String str = sc.nextLine();
        System.out.println("Length of  the String : "+ len(str));
    }
}
