import java.util.Scanner;

public class Three {
    public static int max(int a, int b){
        if(a>b) return a;
        else return b;
    }
    public static float calculate(int a, int b){
        return max(a,b) + (float) a/b;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter length of array : ");
        int n = sc.nextInt();
        int[] a = new int[n];
        System.out.println("Enter Array A : ");
        for(int i=0; i<n ; i++){
            a[i] = sc.nextInt();
        }
        int[] b = new int[n];
        System.out.println("Enter Array B : ");
        for(int i=0; i<n ; i++){
            b[i] = sc.nextInt();
        }
        float[] c = new float[n];
        for(int i=0; i<n ; i++){
            c[i] = calculate(a[i],b[i]);
        }
        System.out.println("Resulted Array C : ");
        for(float num : c){
            System.out.print(num +" ");
        }
    }
}
