import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the full collection of appointments for the clinic day.
 * <p>
 * Responsibilities: adding appointments (with slot and double-booking
 * validation), filtering by professional ID or patient phone number,
 * sorting appointments by time, and cancelling appointments.
 * <p>
 * An {@link ArrayList} is used because appointments are added and removed
 * over the day, and the collection needs to be traversed, filtered, sorted
 * and removed from. Lookups are by scanning the list rather than by unique
 * key, so a Map is not required. The clinic timetable is held in a separate
 * {@link ClinicSchedule} object through composition, so changing the schedule
 * does not require changes to this class.
 */
public class AppointmentManager {

    /** All appointments booked for the current day. */
    private final ArrayList<Appointment> appointments;

    /** The clinic timetable used to validate booking slots. */
    private final ClinicSchedule schedule;

    /**
     * Creates an empty manager with the standard clinic schedule
     * (08:00-16:00, 30-minute slots).
     */
    public AppointmentManager() {
        this(new ClinicSchedule(LocalTime.of(8, 0), LocalTime.of(16, 0), 30));
    }

    /**
     * Creates an empty manager with a supplied schedule. This constructor
     * makes the timetable easy to change without touching the booking logic.
     *
     * @param schedule clinic timetable to use
     */
    public AppointmentManager(ClinicSchedule schedule) {
        this.appointments = new ArrayList<>();
        this.schedule = schedule;
    }

    /**
     * Attempts to add a new appointment. The booking is rejected with a
     * warning message when the appointment is missing, the slot is outside
     * the clinic schedule, or the professional is already booked at that
     * time.
     * <p>
     * Double-booking detection compares professionals with
     * {@code equals()}, which HealthProfessional defines as "same ID", so two
     * references to the same professional are recognised as equal.
     *
     * @param appointment appointment to add
     * @return a {@code [SUCCESS]} or {@code [WARNING]} message describing the result
     */
    public String addAppointment(Appointment appointment) {
        if (appointment == null) {
            return "[WARNING] Appointment could not be added because no details were provided.";
        }

        LocalTime time = appointment.getTime();
        if (!schedule.isValidSlot(time)) {
            return "[WARNING] " + time + " is not a valid booking slot ("
                    + schedule.getScheduleDescription() + ").";
        }

        for (Appointment existing : appointments) {
            if (existing.getProfessional().equals(appointment.getProfessional())
                    && existing.getTime().equals(time)) {
                return "[WARNING] Professional ID " + appointment.getProfessional().getId()
                        + " is already booked at " + time + ".";
            }
        }

        appointments.add(appointment);
        return "[SUCCESS] Appointment booked for " + appointment.getPatient().getName()
                + " with " + appointment.getProfessional().getName() + " at " + time + ".";
    }

    /**
     * Returns all appointments booked with the professional matching the
     * given ID. The result may be an empty list.
     *
     * @param professionalId ID of the professional to look up
     * @return matching appointments
     */
    public ArrayList<Appointment> filterByProfessionalId(int professionalId) {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId() == professionalId) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Returns all appointments booked by the patient with the given phone
     * number. The result may be an empty list.
     *
     * @param phoneNumber patient mobile number to look up
     * @return matching appointments
     */
    public ArrayList<Appointment> filterByPatientPhone(String phoneNumber) {
        ArrayList<Appointment> result = new ArrayList<>();
        if (phoneNumber == null) {
            return result;
        }
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getPhoneNumber().equals(phoneNumber)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Returns a copy of the given appointment list sorted from earliest to
     * latest. The original list is not changed.
     *
     * @param appointmentList list to sort
     * @return sorted copy of the list
     */
    public ArrayList<Appointment> sortByTime(ArrayList<Appointment> appointmentList) {
        ArrayList<Appointment> sorted = new ArrayList<>(appointmentList);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Returns all current appointments sorted from earliest to latest.
     *
     * @return all appointments in time order
     */
    public ArrayList<Appointment> getAllAppointmentsSorted() {
        return sortByTime(appointments);
    }

    /**
     * Cancels the appointment booked with the given professional at the
     * given time.
     *
     * @param professionalId ID of the booked professional
     * @param time           appointment time to cancel
     * @return a {@code [SUCCESS]} or {@code [WARNING]} message describing the result
     */
    public String cancelByProfessionalAndTime(int professionalId, LocalTime time) {
        Appointment toRemove = null;
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId() == professionalId
                    && appointment.getTime().equals(time)) {
                toRemove = appointment;
                break;
            }
        }

        if (toRemove == null) {
            return "[WARNING] No appointment found for professional ID " + professionalId
                    + " at " + time + ".";
        }

        appointments.remove(toRemove);
        return "[SUCCESS] Cancelled appointment for " + toRemove.getPatient().getName()
                + " with " + toRemove.getProfessional().getName() + " at " + time + ".";
    }

    /**
     * Cancels the single appointment booked by the patient with the given
     * phone number at the given time.
     *
     * @param phoneNumber patient mobile number
     * @param time         appointment time to cancel
     * @return a {@code [SUCCESS]} or {@code [WARNING]} message describing the result
     */
    public String cancelByPatientPhoneAndTime(String phoneNumber, LocalTime time) {
        Appointment toRemove = null;
        for (Appointment appointment : appointments) {
            if (phoneNumber != null
                    && appointment.getPatient().getPhoneNumber().equals(phoneNumber)
                    && appointment.getTime().equals(time)) {
                toRemove = appointment;
                break;
            }
        }

        if (toRemove == null) {
            return "[WARNING] No appointment found for phone number " + phoneNumber
                    + " at " + time + ".";
        }

        appointments.remove(toRemove);
        return "[SUCCESS] Cancelled appointment for " + toRemove.getPatient().getName()
                + " at " + time + " (phone " + phoneNumber + ").";
    }

    /**
     * Cancels every appointment booked by the patient with the given phone
     * number.
     *
     * @param phoneNumber patient mobile number
     * @return a {@code [SUCCESS]} or {@code [WARNING]} message describing the result
     */
    public String cancelByPatientPhone(String phoneNumber) {
        ArrayList<Appointment> remaining = new ArrayList<>();
        int cancelled = 0;

        for (Appointment appointment : appointments) {
            if (phoneNumber != null
                    && appointment.getPatient().getPhoneNumber().equals(phoneNumber)) {
                cancelled++;
            } else {
                remaining.add(appointment);
            }
        }

        if (cancelled == 0) {
            return "[WARNING] No appointments found for phone number " + phoneNumber + ".";
        }

        appointments.clear();
        appointments.addAll(remaining);
        return "[SUCCESS] Cancelled " + cancelled + " appointment(s) for phone number "
                + phoneNumber + ".";
    }
}
