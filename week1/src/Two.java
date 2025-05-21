import java.util.Scanner;

public class Two {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int[] arr = new int[9];
        int sum = 0;
        System.out.print("Enter the Array : ");
        for(int i=0; i<arr.length; i++){
            arr[i] = s.nextInt();
            sum += arr[i];
        }
        System.out.print("Average Sum the Array : "+(float) sum/arr.length );
    }
}
