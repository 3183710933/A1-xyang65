/**
 * Intermediate abstract subtype for doctors in the service.
 * <p>
 * Both general practitioners and specialists are doctors, so the ability to
 * prescribe medication is captured once here and reused by every doctor
 * subtype, instead of being duplicated in each one. Concrete doctor types
 * still supply their own specialty and core-task description.
 */
public abstract class Doctor extends HealthProfessional {

    /**
     * Constructs a doctor with a custom working week.
     *
     * @param id          unique numeric ID
     * @param name        full name
     * @param workingDays weekdays the doctor works each week
     */
    protected Doctor(int id, String name, String[] workingDays) {
        super(id, name, workingDays);
    }

    /**
     * Constructs a doctor with the default Monday-Friday working week.
     *
     * @param id   unique numeric ID
     * @param name full name
     */
    protected Doctor(int id, String name) {
        super(id, name);
    }

    /**
     * Records a medication prescription for this doctor.
     *
     * @param medication name of the prescribed medication (must not be blank)
     * @return a confirmation message describing the prescription
     * @throws IllegalArgumentException if the medication name is blank
     */
    public String prescribeMedication(String medication) {
        if (medication == null || medication.isBlank()) {
            throw new IllegalArgumentException("Medication name must not be blank.");
        }
        return getName() + " prescribed: " + medication.trim();
    }
}
