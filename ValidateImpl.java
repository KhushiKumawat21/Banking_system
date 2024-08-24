package businesslogic;

public class ValidateImpl implements Validate {

    @Override
    public boolean validate(String value, ValidationType type) {
        if (value == null || value.isEmpty()) return false;

        switch (type) {
            case pan:
                return value.matches("[A-Z]{5}[0-9]{4}[A-Z]");  // 5 letters, 4 digits, 1 letter

            case address:
                return value.length() <= 200;  // Ensure the address length is reasonable

            default:
                throw new IllegalArgumentException("Unknown validation type: " + type);
        }
    }

    @Override
    public boolean validate(long value, ValidationType type) {
    	String stringValue = String.valueOf(value);
        switch (type) {
            case aadhar:
                // Aadhar should be a 12-digit number
                return stringValue.matches("\\d{12}");

            case pin:
                // PIN should be a 4-digit number
                return stringValue.matches("\\d{4}");
                
            case id:
                // ID should be a positive integer
                return value > 0;

            default:
                throw new IllegalArgumentException("Validation type does not support long values: " + type);
        }
    }
}



