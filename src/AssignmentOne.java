import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Main demonstration class for the PROG2004 Assessment 1 appointment system.
 * <p>
 * Part 3 creates a realistic mix of health professional objects and prints
 * their details through a single polymorphic collection. Part 6 drives the
 * {@link AppointmentManager} through its normal operations (adding,
 * filtering, sorting, cancelling) and through the error cases where an
 * operation cannot be performed.
 */
public class AssignmentOne {

    /**
     * Entry point for the demonstration.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=== PROG2004 Assessment 1: Health Service Appointment System ===");
        System.out.println();

        demonstrateProfessionals();
        demonstrateAppointments();

        System.out.println("=== Demonstration complete ===");
    }

    /**
     * Part 3: create several professionals of each type, store them together
     * through their common parent type, and print all of their details.
     * <p>
     * The single loop below processes every subtype as a
     * {@link HealthProfessional}. Adding a new professional type later would
     * only require a new subclass; this loop would not need to change.
     */
    private static void demonstrateProfessionals() {
        System.out.println("----- Part 3: Health professionals -----");
        System.out.println();

        GeneralPractitioner gp1 = new GeneralPractitioner(101, "Dr Alice Smith", true);
        GeneralPractitioner gp2 = new GeneralPractitioner(102, "Dr Bob Jones",
                new String[]{"Monday", "Wednesday", "Friday"}, false);
        GeneralPractitioner gp3 = new GeneralPractitioner(103, "Dr Charlie Brown", true);

        Specialist specialist1 = new Specialist(201, "Dr Diana Prince", "Paediatrics");
        Specialist specialist2 = new Specialist(202, "Dr Edward Nigma",
                new String[]{"Tuesday", "Thursday"}, "Radiology");

        Nurse nurse1 = new Nurse(301, "Nurse Fiona Green", "Registered Nurse", true);
        Nurse nurse2 = new Nurse(302, "Nurse George Hill",
                new String[]{"Monday", "Tuesday", "Wednesday"}, "Enrolled Nurse", false);

        Dietitian dietitian1 = new Dietitian(401, "Ms Hannah White", "Sports Nutrition");

        // Polymorphic collection: every subtype object is held as a HealthProfessional.
        ArrayList<HealthProfessional> professionals = new ArrayList<>();
        professionals.add(gp1);
        professionals.add(gp2);
        professionals.add(gp3);
        professionals.add(specialist1);
        professionals.add(specialist2);
        professionals.add(nurse1);
        professionals.add(nurse2);
        professionals.add(dietitian1);

        System.out.println("1. All registered professionals:");
        for (HealthProfessional professional : professionals) {
            System.out.println("  " + professional);
        }
        System.out.println();

        // Polymorphic behaviour: the same method call resolves to each subtype's
        // own implementation at runtime.
        System.out.println("2. Core task of each professional (dynamic dispatch):");
        for (HealthProfessional professional : professionals) {
            System.out.println("  - " + professional.getName() + " ("
                    + professional.getSpecialty() + "): " + professional.performCoreTask());
        }
        System.out.println();

        // Subtype-specific actions, called on their concrete types.
        System.out.println("3. Role-specific actions:");
        System.out.println("  - " + gp1.prescribeMedication("Amoxicillin"));
        System.out.println("  - " + specialist1.prescribeMedication("Paracetamol"));
        System.out.println("  - " + nurse1.takeBloodSample());
        System.out.println("  - " + nurse2.takeBloodSample());
        System.out.println("  - " + dietitian1.createDietPlan("Iron deficiency"));
        System.out.println();
    }

    /**
     * Part 6: run the appointment manager end to end, covering successful
     * bookings, filters, sorting, cancellations and the warning cases.
     */
    private static void demonstrateAppointments() {
        System.out.println("----- Part 6: Appointment system demonstration -----");
        System.out.println();

        AppointmentManager manager = new AppointmentManager();

        // Professionals used in the bookings below.
        HealthProfessional gp1 = new GeneralPractitioner(101, "Dr Alice Smith", true);
        HealthProfessional gp2 = new GeneralPractitioner(102, "Dr Bob Jones",
                new String[]{"Monday", "Wednesday", "Friday"}, false);
        HealthProfessional specialist1 = new Specialist(201, "Dr Diana Prince", "Paediatrics");
        HealthProfessional nurse1 = new Nurse(301, "Nurse Fiona Green",
                "Registered Nurse", true);
        HealthProfessional dietitian1 = new Dietitian(401, "Ms Hannah White", "Sports Nutrition");

        // Patients used in the demonstration.
        Patient p1 = new Patient("John Doe", "0412345678");
        Patient p2 = new Patient("Jane Smith", "0423456789");
        Patient p3 = new Patient("Mike Wilson", "0434567890");
        Patient p4 = new Patient("Sarah Lee", "0445678901");

        // 1. Add valid appointments across the different professional types.
        System.out.println("1. Adding appointments:");
        System.out.println("  " + manager.addAppointment(new Appointment(p1, gp1, LocalTime.of(9, 0))));
        System.out.println("  " + manager.addAppointment(new Appointment(p2, gp1, LocalTime.of(10, 30))));
        System.out.println("  " + manager.addAppointment(new Appointment(p3, specialist1, LocalTime.of(9, 30))));
        System.out.println("  " + manager.addAppointment(new Appointment(p4, nurse1, LocalTime.of(11, 0))));
        System.out.println("  " + manager.addAppointment(new Appointment(p1, dietitian1, LocalTime.of(14, 0))));
        System.out.println("  " + manager.addAppointment(new Appointment(p2, gp2, LocalTime.of(8, 30))));
        System.out.println();

        // 2. Error case: double booking the same professional in the same slot.
        System.out.println("2. Trying a double booking:");
        System.out.println("  " + manager.addAppointment(new Appointment(p3, gp1, LocalTime.of(9, 0))));
        System.out.println();

        // 3. Error cases: slots outside the clinic schedule or not on 30 minutes.
        System.out.println("3. Trying invalid time slots:");
        System.out.println("  " + manager.addAppointment(new Appointment(p4, gp2, LocalTime.of(7, 45))));
        System.out.println("  " + manager.addAppointment(new Appointment(p4, gp2, LocalTime.of(16, 30))));
        System.out.println();

        // 4. Display all current appointments sorted by time.
        System.out.println("4. All appointments (earliest to latest):");
        printAppointments(manager.getAllAppointmentsSorted());
        System.out.println();

        // 5. Filter by professional ID, then sort the filtered subset.
        System.out.println("5. Filter by professional ID 101 (subset sorted):");
        printAppointments(manager.sortByTime(manager.filterByProfessionalId(101)));
        System.out.println();

        // 6. Filter by patient phone number, then sort the filtered subset.
        System.out.println("6. Filter by phone number 0412345678 (subset sorted):");
        printAppointments(manager.sortByTime(manager.filterByPatientPhone("0412345678")));
        System.out.println();

        // 7. Error cases: filters that match nothing.
        System.out.println("7. Filters with no match:");
        System.out.println("  By professional ID 999:");
        printAppointments(manager.filterByProfessionalId(999));
        System.out.println("  By phone number 0488888888:");
        printAppointments(manager.filterByPatientPhone("0488888888"));
        System.out.println();

        // 8. Cancel one appointment by professional ID and time.
        System.out.println("8. Cancelling the 09:00 appointment with professional ID 101:");
        System.out.println("  " + manager.cancelByProfessionalAndTime(101, LocalTime.of(9, 0)));
        System.out.println();

        // 9. Error case: cancelling an appointment that does not exist.
        System.out.println("9. Trying to cancel a non-existent appointment:");
        System.out.println("  " + manager.cancelByProfessionalAndTime(101, LocalTime.of(15, 0)));
        System.out.println();

        // 10. Cancel a single appointment by patient phone number and time.
        System.out.println("10. Cancelling the 11:00 appointment for phone 0445678901:");
        System.out.println("  " + manager.cancelByPatientPhoneAndTime("0445678901", LocalTime.of(11, 0)));
        System.out.println();

        // 11. Cancel every appointment for one patient by phone number.
        System.out.println("11. Cancelling all appointments for phone number 0423456789:");
        System.out.println("  " + manager.cancelByPatientPhone("0423456789"));
        System.out.println();

        // 12. Error case: cancelling appointments for an unknown phone number.
        System.out.println("12. Trying to cancel appointments for an unknown phone number:");
        System.out.println("  " + manager.cancelByPatientPhone("0499999999"));
        System.out.println();

        // 13. Show the updated collection after the cancellations.
        System.out.println("13. Updated appointments (earliest to latest):");
        printAppointments(manager.getAllAppointmentsSorted());
        System.out.println();
    }

    /**
     * Prints a list of appointments, or a notice when the list is empty.
     *
     * @param appointments appointments to print
     */
    private static void printAppointments(ArrayList<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("  (no appointments)");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println("  " + appointment);
        }
    }
}
