import java.util.Arrays;

/**
 * Represents a generic health professional.
 * <p>
 * This abstract class holds the information and behaviour shared by every
 * type of health professional in the service: a numeric ID, a name and the
 * fixed set of weekdays they work each week.
 * <p>
 * Design decisions:
 * <ul>
 *     <li>Fields are {@code private final}: the internal state cannot be
 *         changed from outside the class, and once an object is constructed
 *         it stays in a valid state (encapsulation / immutability).</li>
 *     <li>Setters are deliberately not provided; callers read details
 *         through getters instead.</li>
 *     <li>The working-days array is defensively copied both on the way in
 *         and on the way out, so an outside reference cannot mutate the
 *         internal array.</li>
 *     <li>The class is {@code abstract}: a generic "plain" professional with
 *         no specialty makes no sense, so it cannot be instantiated directly.
 *         Every concrete subtype must supply {@link #getSpecialty()} and
 *         {@link #performCoreTask()}.</li>
 * </ul>
 */
public abstract class HealthProfessional {

    /** Valid weekday names accepted by the working-days validation. */
    private static final String[] VALID_DAYS = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    /** Numeric identifier unique to this professional. */
    private final int id;

    /** Full name of the professional. */
    private final String name;

    /** Fixed set of weekdays this professional works (for example Monday, Wednesday). */
    private final String[] workingDays;

    /**
     * Full constructor. Validates every argument and defensive-copies the
     * working days array so the object can never be put into an invalid state.
     *
     * @param id          unique numeric ID of the professional (must be positive)
     * @param name        full name of the professional (must not be blank)
     * @param workingDays weekdays the professional works (must not be empty,
     *                    must contain valid, non-repeated weekday names)
     * @throws IllegalArgumentException if any argument is missing or invalid
     */
    protected HealthProfessional(int id, String name, String[] workingDays) {
        if (id <= 0) {
            throw new IllegalArgumentException("Professional ID must be a positive number.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Professional name must not be blank.");
        }
        if (workingDays == null || workingDays.length == 0) {
            throw new IllegalArgumentException("Working days must not be empty.");
        }
        validateWorkingDays(workingDays);

        this.id = id;
        this.name = name.trim();
        this.workingDays = Arrays.copyOf(workingDays, workingDays.length);
    }

    /**
     * Convenience constructor that defaults the working week to Monday to
     * Friday. It chains to the full constructor through
     * {@link #defaultWorkingDays()} so the default array is created fresh
     * each time and shared validation is not duplicated.
     *
     * @param id   unique numeric ID of the professional
     * @param name full name of the professional
     */
    protected HealthProfessional(int id, String name) {
        this(id, name, defaultWorkingDays());
    }

    /**
     * Returns a fresh copy of the standard Monday-Friday working week.
     * A new array is created on every call so no caller can share or mutate
     * an internal default.
     *
     * @return default working week
     */
    private static String[] defaultWorkingDays() {
        return new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    }

    /**
     * Checks that every supplied day is a recognised weekday and that no day
     * appears twice.
     *
     * @param days working days to validate
     * @throws IllegalArgumentException if a day is unknown or repeated
     */
    private static void validateWorkingDays(String[] days) {
        for (String day : days) {
            boolean known = false;
            for (String valid : VALID_DAYS) {
                if (valid.equals(day)) {
                    known = true;
                    break;
                }
            }
            if (!known) {
                throw new IllegalArgumentException("Invalid working day: " + day);
            }
        }
        for (int i = 0; i < days.length; i++) {
            for (int j = i + 1; j < days.length; j++) {
                if (days[i].equals(days[j])) {
                    throw new IllegalArgumentException("Duplicate working day: " + days[i]);
                }
            }
        }
    }

    /**
     * Returns a short description of what kind of professional this is.
     * Each concrete subtype supplies its own description.
     *
     * @return the professional's specialty or role
     */
    public abstract String getSpecialty();

    /**
     * Describes the core clinical task this type of professional performs.
     * Subtypes respond differently to the same call, which is the
     * polymorphism used when a mixed list of professionals is processed.
     *
     * @return description of the professional's core task
     */
    public abstract String performCoreTask();

    /**
     * Returns the professional's ID.
     *
     * @return numeric ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the professional's name.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a copy of the working days array. The copy protects the
     * internal collection: modifying the returned array cannot change this
     * professional's state.
     *
     * @return copy of the working days
     */
    public String[] getWorkingDays() {
        return Arrays.copyOf(workingDays, workingDays.length);
    }

    /**
     * Two professionals are considered equal when they share the same ID,
     * because the ID is the unique identifier used across the system.
     * {@code hashCode()} is overridden together with {@code equals()} so the
     * contract holds when professionals are stored in hash-based collections.
     *
     * @param o object to compare against
     * @return true if the objects have the same ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional other = (HealthProfessional) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Returns the shared details in a readable form. Subclasses call this
     * method and append their role and type-specific information, so the
     * common formatting is written only once.
     *
     * @return readable summary of the shared professional details
     */
    @Override
    public String toString() {
        return "ID: " + id
                + " | Name: " + name
                + " | Working days: " + String.join(", ", workingDays);
    }
}
