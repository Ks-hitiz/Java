import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

public class Checking {
    public static void main (String[] args){
        String input = " ";
        boolean isEmpty = StringUtils.isBlank(input);
        System.out.println("Is input blank? " + isEmpty);

        ImmutableList<String> fruits = ImmutableList.of("Apple", "Banana", "Cherry");
        fruits.forEach(fruit -> System.out.println("Fruit: " + fruit));
    }
}
