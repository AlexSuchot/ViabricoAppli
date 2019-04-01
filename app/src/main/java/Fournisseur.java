public class Fournisseur {
    private String Name;
    private String Description;
    private String Address;
    private Integer Number;
    private String Email;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Fournisseur(String name, String description, String address, Integer number, String email) {
        Name = name;
        Description = description;
        Address = address;
        Number = number;
        Email = email;
    }
}
