public class Exercise6 {
    public static void main(String[] args){
        Employee manager = new Manager("Alice", 80000);
        Employee developer = new Developer("Bob", 60000);

        System.out.println(manager.getName() + "'s total salary: $" + manager.calculateSalary());
        System.out.println(developer.getName() + "'s total salary: $" + developer.calculateSalary());
    }
}

abstract class Employee{
    String name;
    double salary;
    public Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
    }
    abstract double calculateSalary();

    public String getName() {
        return name;
    }
}

class Manager extends Employee{
    public Manager(String name, double salary){
        super(name,salary);
    }
    double calculateSalary(){
        return (salary * 12);
    }
}

class Developer extends Employee{
    public Developer(String name, double salary){
        super(name,salary);
    }
    double calculateSalary(){
        return (salary * 12);
    }
}