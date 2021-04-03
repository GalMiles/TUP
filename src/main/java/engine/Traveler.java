package engine;

public class Traveler {
    private String Name;
    private String Password;
    private int Age;
    private String EmailAddress;

    public Traveler(String name, String password, int age, String emailAddress)
    {
        this.Age = age;
        this.Name = name;
        this.Password = password;
        this.EmailAddress = emailAddress;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }
}
