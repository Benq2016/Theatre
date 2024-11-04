package src.Domain;

public interface Person {
    Integer id = 0;

    String getName();
    void setName(String name);
    EMail getEmail();
    void setEmail(EMail email);
    int getAge();
    void setAge(int age);
}