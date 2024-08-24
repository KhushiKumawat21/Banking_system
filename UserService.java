package transaction;

public interface UserService {
    boolean createUser(String accountNumber, String name, int pin, String address, long aadhar, String pan, long phone_number);
    boolean userExists(String accountNumber);
}
