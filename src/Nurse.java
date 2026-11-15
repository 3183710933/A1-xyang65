/**
 * Represents a nurse working in the service. A nurse records their
 * registration type and whether they are able to take blood samples.
 */
public class Nurse extends HealthProfessional {

    /** Registration type, for example "Registered Nurse" or "Enrolled Nurse". */
    private final String nurseType;

    /** True when this nurse is permitted to take blood samples. */
    private final boolean canTakeBloodSamples;

    /**
     * Full constructor with a custom working week.
     *
     * @param id                  unique numeric ID
     * @param name                full name
     * @param workingDays         days the nurse works each week
     * @param nurseType           registration type of the nurse
     * @param canTakeBloodSamples whether the nurse can take blood samples
     */
    public Nurse(int id, String name, String[] workingDays,
                 String nurseType, boolean canTakeBloodSamples) {
        super(id, name, workingDays);
        if (nurseType == null || nurseType.isBlank()) {
            throw new IllegalArgumentException("Nurse type must not be blank.");
        }
        this.nurseType = nurseType.trim();
        this.canTakeBloodSamples = canTakeBloodSamples;
    }

    /**
     * Convenience constructor using the default Monday-Friday working week.
     *
     * @param id                  unique numeric ID
     * @param name                full name
     * @param nurseType           registration type of the nurse
     * @param canTakeBloodSamples whether the nurse can take blood samples
     */
    public Nurse(int id, String name, String nurseType, boolean canTakeBloodSamples) {
        super(id, name);
        if (nurseType == null || nurseType.isBlank()) {
            throw new IllegalArgumentException("Nurse type must not be blank.");
        }
        this.nurseType = nurseType.trim();
        this.canTakeBloodSamples = canTakeBloodSamples;
    }

    /**
     * Returns the nurse's registration type.
     *
     * @return nurse type
     */
    public String getNurseType() {
        return nurseType;
    }

    /**
     * Returns whether this nurse can take blood samples.
     *
     * @return true if blood sampling is allowed
     */
    public boolean canTakeBloodSamples() {
        return canTakeBloodSamples;
    }

    /**
     * Performs a blood sample if the nurse is authorised to do so.
     *
     * @return a confirmation or authorisation message
     */
    public String takeBloodSample() {
        if (canTakeBloodSamples) {
            return getName() + " took a blood sample.";
        }
        return getName() + " is not authorised to take blood samples.";
    }

    @Override
    public String getSpecialty() {
        return "Nurse - " + nurseType;
    }

    /**
     * Nurse core task: nursing care, with blood sampling where authorised.
     *
     * @return description of this nurse's core task
     */
    @Override
    public String performCoreTask() {
        String sample = canTakeBloodSamples ? " and can take blood samples" : "";
        return "Provides nursing care" + sample + ".";
    }

    /**
     * Combines the shared details with the nurse's role, type and
     * blood-sampling ability.
     *
     * @return readable summary of this nurse
     */
    @Override
    public String toString() {
        return super.toString()
                + " | Role: " + getSpecialty()
                + " | Blood sampling: " + (canTakeBloodSamples ? "Yes" : "No");
    }
}
