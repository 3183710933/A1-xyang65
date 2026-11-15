/**
 * Represents a patient with basic contact details: a name and a mobile
 * phone number. The patient is kept as its own class so appointment objects
 * can refer to a complete person rather than loose strings.
 */
public class Patient {

    /** Patient's full name. */
    private final String name;

    /** Patient's mobile phone number used for contact and lookups. */
    private final String phoneNumber;

    /**
     * Constructs a patient with validated contact details.
     *
     * @param name        patient's full name (must not be blank)
     * @param phoneNumber patient's mobile number (must not be blank)
     * @throws IllegalArgumentException if either detail is missing
     */
    public Patient(String name, String phoneNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Patient name must not be blank.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Patient phone number must not be blank.");
        }
        this.name = name.trim();
        this.phoneNumber = phoneNumber.trim();
    }

    /**
     * Returns the patient's name.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patient's phone number.
     *
     * @return mobile number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Two patients are treated as the same person when they share the
     * contact phone number.
     *
     * @param o object to compare against
     * @return true if the phone numbers match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) o;
        return phoneNumber.equals(other.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }

    /**
     * Returns the patient's details in a readable form.
     *
     * @return readable summary of this patient
     */
    @Override
    public String toString() {
        return "Patient: " + name + " (phone: " + phoneNumber + ")";
    }
}
