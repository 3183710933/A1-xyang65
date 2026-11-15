/**
 * Represents a specialist doctor who works in a particular field of medicine,
 * for example paediatrics, radiology or cardiology. As a {@link Doctor}, the
 * specialist also inherits the shared prescribing capability.
 */
public class Specialist extends Doctor {

    /** Medical field this specialist practices in. */
    private final String specialtyField;

    /**
     * Full constructor with a custom working week.
     *
     * @param id            unique numeric ID
     * @param name          full name
     * @param workingDays   days the specialist works each week
     * @param specialtyField medical field of expertise
     */
    public Specialist(int id, String name, String[] workingDays, String specialtyField) {
        super(id, name, workingDays);
        if (specialtyField == null || specialtyField.isBlank()) {
            throw new IllegalArgumentException("Specialty field must not be blank.");
        }
        this.specialtyField = specialtyField.trim();
    }

    /**
     * Convenience constructor using the default Monday-Friday working week.
     *
     * @param id            unique numeric ID
     * @param name          full name
     * @param specialtyField medical field of expertise
     */
    public Specialist(int id, String name, String specialtyField) {
        super(id, name);
        if (specialtyField == null || specialtyField.isBlank()) {
            throw new IllegalArgumentException("Specialty field must not be blank.");
        }
        this.specialtyField = specialtyField.trim();
    }

    /**
     * Returns the specialist's field of practice.
     *
     * @return specialty field
     */
    public String getSpecialtyField() {
        return specialtyField;
    }

    @Override
    public String getSpecialty() {
        return "Specialist - " + specialtyField;
    }

    /**
     * Specialist core task: field-specific consultation and prescribing.
     *
     * @return description of this specialist's core task
     */
    @Override
    public String performCoreTask() {
        return "Provides " + specialtyField + " consultations and prescribes medication when required.";
    }

    /**
     * Combines the shared details with the specialist's role and field.
     *
     * @return readable summary of this specialist
     */
    @Override
    public String toString() {
        return super.toString()
                + " | Role: " + getSpecialty()
                + " | Field: " + specialtyField;
    }
}
