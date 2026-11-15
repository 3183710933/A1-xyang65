/**
 * Represents a general practitioner (GP), the primary-care doctor of the
 * service. A GP inherits all shared professional details from
 * {@link HealthProfessional} through the {@link Doctor} layer, and adds
 * whether the practice offers bulk billing.
 */
public class GeneralPractitioner extends Doctor {

    /** True when this GP bills through bulk billing (no out-of-pocket fee). */
    private final boolean bulkBilling;

    /**
     * Full constructor with a custom working week.
     *
     * @param id          unique numeric ID
     * @param name        full name
     * @param workingDays days the GP works each week
     * @param bulkBilling whether bulk billing is enabled
     */
    public GeneralPractitioner(int id, String name, String[] workingDays, boolean bulkBilling) {
        super(id, name, workingDays);
        this.bulkBilling = bulkBilling;
    }

    /**
     * Convenience constructor using the default Monday-Friday working week.
     *
     * @param id          unique numeric ID
     * @param name        full name
     * @param bulkBilling whether bulk billing is enabled
     */
    public GeneralPractitioner(int id, String name, boolean bulkBilling) {
        super(id, name);
        this.bulkBilling = bulkBilling;
    }

    /**
     * Returns whether this GP uses bulk billing.
     *
     * @return true if bulk billing is enabled
     */
    public boolean isBulkBilling() {
        return bulkBilling;
    }

    @Override
    public String getSpecialty() {
        return "General Practitioner";
    }

    /**
     * GP core task: primary-care consultation and medication prescribing.
     *
     * @return description of this GP's core task
     */
    @Override
    public String performCoreTask() {
        return "Provides primary-care consultations and prescribes medication.";
    }

    /**
     * Combines the shared details from the parent class with the GP's role
     * and bulk billing status.
     *
     * @return readable summary of this GP
     */
    @Override
    public String toString() {
        return super.toString()
                + " | Role: " + getSpecialty()
                + " | Bulk billing: " + (bulkBilling ? "Yes" : "No");
    }
}
