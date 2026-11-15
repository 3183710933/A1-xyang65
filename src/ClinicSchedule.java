import java.time.LocalTime;

/**
 * Encapsulates the clinic's fixed daily booking schedule: opening time,
 * closing time and the length of each slot.
 * <p>
 * Keeping the schedule in its own class means the appointment manager depends
 * on this schedule object rather than on hard-coded times. If the opening
 * hours or slot length ever change, only this class (or the schedule object
 * passed to the manager) needs to be updated; the booking and filtering
 * logic stays the same.
 */
public class ClinicSchedule {

    /** First appointment slot of the day. */
    private final LocalTime start;

    /** Clinic closing time; the last slot starts one slot before this. */
    private final LocalTime end;

    /** Length of each booking slot in minutes. */
    private final int slotMinutes;

    /**
     * Creates a schedule with the given opening time, closing time and slot
     * length.
     *
     * @param start       first slot of the day
     * @param end         closing time of the day
     * @param slotMinutes length of each slot in minutes (must divide 60 evenly)
     * @throws IllegalArgumentException if any value is invalid
     */
    public ClinicSchedule(LocalTime start, LocalTime end, int slotMinutes) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Schedule start and end must not be null.");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Schedule start must be before end.");
        }
        if (slotMinutes <= 0 || 60 % slotMinutes != 0) {
            throw new IllegalArgumentException("Slot minutes must be a positive divisor of 60.");
        }
        this.start = start;
        this.end = end;
        this.slotMinutes = slotMinutes;
    }

    /**
     * Returns the first slot time.
     *
     * @return opening time
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * Returns the closing time.
     *
     * @return closing time
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * Returns the slot length in minutes.
     *
     * @return slot minutes
     */
    public int getSlotMinutes() {
        return slotMinutes;
    }

    /**
     * Returns the latest possible start time for a booking.
     *
     * @return last slot of the day
     */
    public LocalTime getLastSlot() {
        return end.minusMinutes(slotMinutes);
    }

    /**
     * Checks whether a time is a valid booking slot: within clinic hours, on
     * a slot boundary, and with no seconds or nanoseconds attached (so a
     * time such as 09:00:30 is not accepted by mistake).
     *
     * @param time time to check
     * @return true if the time is a valid slot
     */
    public boolean isValidSlot(LocalTime time) {
        if (time == null) {
            return false;
        }
        if (time.isBefore(start) || time.isAfter(getLastSlot())) {
            return false;
        }
        if (time.getSecond() != 0 || time.getNano() != 0) {
            return false;
        }
        return time.getMinute() % slotMinutes == 0;
    }

    /**
     * Returns a human-readable description of the schedule.
     *
     * @return schedule description
     */
    public String getScheduleDescription() {
        return start + " to " + getLastSlot() + ", " + slotMinutes + "-minute slots";
    }
}
