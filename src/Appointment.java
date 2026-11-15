import java.time.LocalTime;

/**
 * Represents a single appointment on the current day: a patient, a chosen
 * health professional of any type, and a time slot.
 * <p>
 * Design decisions:
 * <ul>
 *     <li>The professional field is declared as the parent type
 *         {@link HealthProfessional}, so one appointment class can accept any
 *         GP, specialist, nurse or dietitian subtype. This is polymorphism in
 *         composition: the appointment does not need to know the concrete
 *         subtype.</li>
 *     <li>The time is stored as {@link LocalTime} rather than a String. A
 *         time value is type-safe, carries no formatting ambiguity, and can be
 *         compared directly, which keeps chronological sorting correct.</li>
 *     <li>The constructor rejects null patient, professional and time
 *         values, so an incomplete appointment object can never be created
 *         (the object's invariants are enforced at construction).</li>
 *     <li>The class implements {@link Comparable} so a whole collection can
 *         be sorted from earliest to latest. Only the time is compared;
 *         appointments at the same time with different professionals may
 *         appear in any order.</li>
 * </ul>
 */
public class Appointment implements Comparable<Appointment> {

    /** The patient booked into this appointment. */
    private final Patient patient;

    /** The professional the patient will see (any subclass of HealthProfessional). */
    private final HealthProfessional professional;

    /** Time of day the appointment starts. */
    private final LocalTime time;

    /**
     * Constructs an appointment. Every required detail must be supplied;
     * a missing detail prevents the appointment from being created at all.
     *
     * @param patient     patient being seen
     * @param professional chosen health professional
     * @param time        appointment start time
     * @throws IllegalArgumentException if any argument is null
     */
    public Appointment(Patient patient, HealthProfessional professional, LocalTime time) {
        if (patient == null) {
            throw new IllegalArgumentException("Appointment must have a patient.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("Appointment must have a health professional.");
        }
        if (time == null) {
            throw new IllegalArgumentException("Appointment must have a time.");
        }
        this.patient = patient;
        this.professional = professional;
        this.time = time;
    }

    /**
     * Returns the booked patient.
     *
     * @return patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the booked professional.
     *
     * @return health professional
     */
    public HealthProfessional getProfessional() {
        return professional;
    }

    /**
     * Returns the appointment time.
     *
     * @return start time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Orders appointments chronologically: an earlier appointment comes
     * before a later one. Appointments at the same time may stay in
     * whatever order the collection places them in.
     *
     * @param other appointment to compare against
     * @return negative if this appointment is earlier, positive if later, zero if equal
     */
    @Override
    public int compareTo(Appointment other) {
        return this.time.compareTo(other.time);
    }

    /**
     * Returns the appointment details, including the full details of the
     * booked professional, in a readable form.
     *
     * @return readable summary of this appointment
     */
    @Override
    public String toString() {
        return "Time: " + time
                + " | " + patient
                + " | " + professional;
    }
}
