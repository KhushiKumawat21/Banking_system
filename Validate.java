package businesslogic;

public interface Validate {
    boolean validate(String value, ValidationType type);
    boolean validate(long value, ValidationType type);

    enum ValidationType {
        aadhar,
        pan,
        address,
        pin, 
        id
    }
}


