/**
 * Represents a dietitian who specialises in nutrition and diet planning.
 */
public class Dietitian extends HealthProfessional {

    /** Area of nutrition the dietitian focuses on, for example sports nutrition. */
    private final String focusArea;

    /**
     * Full constructor with a custom working week.
     *
     * @param id          unique numeric ID
     * @param name        full name
     * @param workingDays days the dietitian works each week
     * @param focusArea   nutrition focus area
     */
    public Dietitian(int id, String name, String[] workingDays, String focusArea) {
        super(id, name, workingDays);
        if (focusArea == null || focusArea.isBlank()) {
            throw new IllegalArgumentException("Focus area must not be blank.");
        }
        this.focusArea = focusArea.trim();
    }

    /**
     * Convenience constructor using the default Monday-Friday working week.
     *
     * @param id          unique numeric ID
     * @param name        full name
     * @param focusArea   nutrition focus area
     */
    public Dietitian(int id, String name, String focusArea) {
        super(id, name);
        if (focusArea == null || focusArea.isBlank()) {
            throw new IllegalArgumentException("Focus area must not be blank.");
        }
        this.focusArea = focusArea.trim();
    }

    /**
     * Returns the dietitian's focus area.
     *
     * @return focus area
     */
    public String getFocusArea() {
        return focusArea;
    }

    /**
     * Creates a diet plan for a stated condition or goal.
     *
     * @param condition patient condition or goal the plan targets
     * @return a description of the prepared diet plan
     * @throws IllegalArgumentException if the condition is blank
     */
    public String createDietPlan(String condition) {
        if (condition == null || condition.isBlank()) {
            throw new IllegalArgumentException("Diet target must not be blank.");
        }
        return getName() + " prepared a " + focusArea
                + " plan for: " + condition.trim();
    }

    @Override
    public String getSpecialty() {
        return "Dietitian - " + focusArea;
    }

    /**
     * Dietitian core task: nutrition counselling and diet planning.
     *
     * @return description of this dietitian's core task
     */
    @Override
    public String performCoreTask() {
        return "Provides nutrition counselling and creates diet plans focused on " + focusArea + ".";
    }

    /**
     * Combines the shared details with the dietitian's role and focus area.
     *
     * @return readable summary of this dietitian
     */
    @Override
    public String toString() {
        return super.toString()
                + " | Role: " + getSpecialty()
                + " | Focus: " + focusArea;
    }
}
