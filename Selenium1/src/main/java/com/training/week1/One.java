public class One {
    public static void main(String[] args){
        int[] arr = {5,7,6,9,8,1,-15,2,6};
        int sum  = 0;
        for (int num : arr) {
            sum += num;
        }
        System.out.print("Sum of the array : "+sum);
    }
}
