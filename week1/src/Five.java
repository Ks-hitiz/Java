import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Five {

    private static final Pattern p = Pattern.compile("^[A-Za-z0-9._%+-]+@clarivate\\.com$");

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/Data.csv"))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length < 2) continue;

                String mentees = columns[0].trim().toLowerCase();
                String mentor = columns[1].trim().toLowerCase();

                if (!isValidEmail(mentor)) {
                    System.out.println("Invalid mentor email: " + mentor);
                    continue;
                }

                String[] menteeList = mentees.split("\\|");

                for (String mentee : menteeList) {
                    mentee = mentee.trim();
                    if (isValidEmail(mentee)) {
                        data.put(mentee, mentor);
                    }
                    else {
                        System.out.println("Invalid mentee email: " + mentee);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter to search : ");
        String src = scanner.nextLine().toLowerCase();

        Set<String> result = data.entrySet().stream()
                .filter(entry -> entry.getKey().contains(src))
                .map(Map.Entry::getValue).collect(Collectors.toSet());

        if (result.isEmpty()) {
            System.out.println("Not found");
        }
        else {
            System.out.println("Mentors associated with matching mentees:");
            result.forEach(System.out::println);
        }
    }

    private static boolean isValidEmail(String email) {
        return p.matcher(email).matches();
    }
}
