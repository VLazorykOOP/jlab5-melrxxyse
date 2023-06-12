import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

abstract class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract void show();

    @Override
    public int compareTo(Person person) {
        return name.compareTo(person.getName());
    }
}

class Student extends Person {
    private String studentId;

    public Student(String name, int age, String studentId) {
        super(name, age);
        this.studentId = studentId;
    }

    @Override
    public void show() {
        System.out.println("Ім'я: " + getName());
        System.out.println("Вік: " + getAge());
        System.out.println("ID Студента: " + studentId);
    }

    public String getStudentId() {
        return studentId;
    }
}

class Teacher extends Person {
    private String teacherId;

    public Teacher(String name, int age, String teacherId) {
        super(name, age);
        this.teacherId = teacherId;
    }

    @Override
    public void show() {
        System.out.println("Ім'я: " + getName());
        System.out.println("Вік: " + getAge());
        System.out.println("ID Викладача: " + teacherId);
    }

    public String getTeacherId() {
        return teacherId;
    }
}

class DepartmentHead extends Person {
    private String department;

    public DepartmentHead(String name, int age, String department) {
        super(name, age);
        this.department = department;
    }

    @Override
    public void show() {
        System.out.println("Ім'я: " + getName());
        System.out.println("Вік: " + getAge());
        System.out.println("Спеціальність: " + department);
    }

    public String getDepartment() {
        return department;
    }
}

public class Main {
    public static void main(String[] args) {
        Stack<Integer> recordStack = new Stack<>();

        // Додавання номерів записів до стеку
        recordStack.push(1);
        recordStack.push(2);
        recordStack.push(3);
        recordStack.push(4);
        recordStack.push(5);

        // Прямий доступ до елементів запису




        ArrayList<Person> people = new ArrayList<>();
        readDataFromFile(people, "students.txt", "teachers.txt", "department_heads.txt");

        System.out.println("Прочитані дані:");
        for (Person person : people) {
            person.show();
            System.out.println();
        }

        System.out.println("Сортування за ім'ям:");
        Collections.sort(people);
        for (Person person : people) {
            person.show();
            System.out.println();
        }

        addRecordsFromKeyboard(people);

        System.out.println("Після додавання з клавіатури:");
        for (Person person : people) {
            person.show();
            System.out.println();
        }

        System.out.println("Сортування за віком:");
        Collections.sort(people, new PersonComparator());
        for (Person person : people) {
            person.show();
            System.out.println();
        }

        ArrayList<Person> sortedList = new ArrayList<>(people);
        Collections.sort(sortedList, new PersonComparator());

        writeDataToFile(sortedList, "sortedData.txt");
    }

    public static void readDataFromFile(ArrayList<Person> people, String... filenames) {
        for (String filename : filenames) {
            try {
                File file = new File(filename);
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(";");

                    String type = parts[0];
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String id = parts[3];

                    if (type.equals("Student")) {
                        people.add(new Student(name, age, id));
                    } else if (type.equals("Teacher")) {
                        people.add(new Teacher(name, age, id));
                    } else if (type.equals("DepartmentHead")) {
                        people.add(new DepartmentHead(name, age, id));
                    }
                }

                scanner.close();
                System.out.println("Дані з файлу " + filename + " успішно прочитані.");
            } catch (IOException e) {
                System.out.println("Помилка при зчитуванні з файлу " + filename);
            }
        }
    }

    public static void addRecordsFromKeyboard(ArrayList<Person> people) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть дані студента:");
        System.out.print("Ім'я: ");
        String studentName = scanner.nextLine();
        System.out.print("Вік: ");
        int studentAge = Integer.parseInt(scanner.nextLine());
        System.out.print("ID Студента: ");
        String studentId = scanner.nextLine();
        people.add(new Student(studentName, studentAge, studentId));

        System.out.println("Введіть дані викладача:");
        System.out.print("Ім'я: ");
        String teacherName = scanner.nextLine();
        System.out.print("Вік: ");
        int teacherAge = Integer.parseInt(scanner.nextLine());
        System.out.print("ID Викладача: ");
        String teacherId = scanner.nextLine();
        people.add(new Teacher(teacherName, teacherAge, teacherId));

        System.out.println("Введіть дані керівника відділу:");
        System.out.print("Ім'я: ");
        String departmentHeadName = scanner.nextLine();
        System.out.print("Вік: ");
        int departmentHeadAge = Integer.parseInt(scanner.nextLine());
        System.out.print("Спеціальність: ");
        String department = scanner.nextLine();
        people.add(new DepartmentHead(departmentHeadName, departmentHeadAge, department));

        scanner.close();
    }

    public static void writeDataToFile(ArrayList<Person> people, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);

            for (Person person : people) {
                if (person instanceof Student) {
                    writer.write("Student;" + person.getName() + ";" + person.getAge() + ";" + ((Student) person).getStudentId() + "\n");
                } else if (person instanceof Teacher) {
                    writer.write("Teacher;" + person.getName() + ";" + person.getAge() + ";" + ((Teacher) person).getTeacherId() + "\n");
                } else if (person instanceof DepartmentHead) {
                    writer.write("DepartmentHead;" + person.getName() + ";" + person.getAge() + ";" + ((DepartmentHead) person).getDepartment() + "\n");
                }
            }

            writer.close();
            System.out.println("Дані успішно записані у файл: " + filename);
        } catch (IOException e) {
            System.out.println("Помилка при записі даних у файл: " + filename);
        }
    }

    public static class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person person1, Person person2) {
            return Integer.compare(person1.getAge(), person2.getAge());
        }
    }
}
