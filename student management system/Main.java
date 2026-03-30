import java.util.*;
public class Main {

    private static final Scanner sc  = new Scanner(System.in);
    private static final StudentManagementSystem sms = new StudentManagementSystem();
    private static final String RST  = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYN  = "\u001B[36m";
    private static final String GRN  = "\u001B[32m";
    private static final String YLW  = "\u001B[33m";
    private static final String RED  = "\u001B[31m";
    private static final String BLU  = "\u001B[34m";
    private static final String MAG  = "\u001B[35m";
    public static void main(String[] args) {
        banner();
        boolean on = true;
        while (on) {
            menu();
            int c = readInt("  ► Choice: ");
            System.out.println();
            switch (c) {
                case 1  -> addFlow();
                case 2  -> editFlow();
                case 3  -> removeFlow();
                case 4  -> searchFlow();
                case 5  -> allStudentsFlow();
                case 6  -> semSectionFlow();
                case 7  -> batchFlow();
                case 8  -> topFlow();
                case 9  -> atRiskFlow();
                case 10 -> attendanceFlow();
                case 11 -> exportResultFlow();
                case 12 -> exportAttendFlow();
                case 13 -> syllabusFlow();
                case 0  -> { on = false; bye(); }
                default -> warn("Invalid option — please try again.");
            }
        }
        sc.close();
    }

    private static void banner() {
        System.out.println(BOLD + CYN);
        System.out.println("  ╔═════════════════════════════════════════════════════════════════════╗");
        System.out.println("  ║   UNIVERSITY OF ENGINEERING AND MANAGEMENT, KOLKATA  (UEM)         ║");
        System.out.println("  ║   Department of Computer Science & Engineering                     ║");
        System.out.println("  ║   B.Tech CSE — Student Management System  v3.0                    ║");
        System.out.println("  ║   ─────────────────────────────────────────────────────────────    ║");
        System.out.println("  ║   STATUS   : AUTONOMOUS UNIVERSITY  (UGC Recognised)               ║");
        System.out.println("  ║   ADDRESS  : Plot III-B/5, New Town, Action Area III, Kol-160      ║");
        System.out.println("  ║   WEBSITE  : www.uem.edu.in                                        ║");
        System.out.println("  ╚═════════════════════════════════════════════════════════════════════╝");
        System.out.println(RST);
        System.out.println(GRN + "  Records loaded: " + sms.getTotalCount() + RST + "\n");
    }

    private static void menu() {
        System.out.println(BOLD + BLU +
            "\n  ╔══ MAIN MENU ═══════════════════════════════════════════════╗" + RST);
        System.out.println("  ║  STUDENT RECORDS                                             ║");
        System.out.println("  ║   1.  Register New Student                                   ║");
        System.out.println("  ║   2.  Edit Student Information                               ║");
        System.out.println("  ║   3.  Remove Student Record                                  ║");
        System.out.println("  ║   4.  Search  (Roll / Name / Enrollment No)                  ║");
        System.out.println("  ║   5.  Display All Students                                   ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════════╣");
        System.out.println("  ║  REPORTS & ANALYTICS                                         ║");
        System.out.println("  ║   6.  Semester / Section Result Sheet                        ║");
        System.out.println("  ║   7.  Batch-wise Student List                                ║");
        System.out.println("  ║   8.  Top Performers (by Semester)                           ║");
        System.out.println("  ║   9.  At-Risk Students (Fail / Detained)                     ║");
        System.out.println("  ║  10.  Attendance Shortfall Report                            ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════════╣");
        System.out.println("  ║  EXPORTS                                                     ║");
        System.out.println("  ║  11.  Export Result Sheet (.txt)                             ║");
        System.out.println("  ║  12.  Export Attendance Warning Notice (.txt)                ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════════╣");
        System.out.println("  ║  ACADEMICS                                                   ║");
        System.out.println("  ║  13.  UEM CSE Syllabus + Grading Scale                      ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════════╣");
        System.out.println("  ║   0.  Exit                                                   ║");
        System.out.println(BOLD + BLU +
            "  ╚══════════════════════════════════════════════════════════════╝" + RST);
    }
    private static void addFlow() {
        hdr("REGISTER NEW STUDENT — UEM KOLKATA (Autonomous)");

        // ── Identity ──────────────────────────────────────────────────────────
        System.out.println(YLW + "  UEM Roll No format: BTECH/CSE/YYYY/NNN  (e.g. BTECH/CSE/2022/001)" + RST);
        String roll = readStr("UEM Roll No: ",
            Validator::isValidUemRollNo,
            "Format must be BTECH/CSE/YYYY/NNN").toUpperCase();

        if (sms.findByRoll(roll) != null) {
            warn("Roll number " + roll + " is already registered."); return;
        }

        System.out.println(YLW + "  Enrollment No format: UEM/YYYY/CSE/NNNNN  (e.g. UEM/2022/CSE/00001)" + RST);
        String enroll = readStr("Enrollment No: ",
            Validator::isValidEnrollmentNo,
            "Format must be UEM/YYYY/CSE/NNNNN").toUpperCase();

        String name   = readStr("Full Name: ", Validator::isValidName, "Letters and spaces only (min 2 chars).");
        String gender = readStr("Gender (Male / Female / Other): ", Validator::isValidGender, "Enter Male, Female, or Other.");
        String dob    = readStr("Date of Birth (DD/MM/YYYY): ", Validator::isValidDate, "DD/MM/YYYY, born 1995-2008.");

        System.out.println(YLW + "  UEM student email format: firstname.lastname@uem.edu.in" + RST);
        String email  = readStr("UEM Email: ", Validator::isValidEmail, "Invalid email format.");
        String phone  = readStr("Mobile (10 digits): ", Validator::isValidPhone, "Indian 10-digit mobile.");

        System.out.print("  Permanent Address: ");
        String addr = sc.nextLine().trim();
        if (addr.isEmpty()) addr = "N/A";
        System.out.println(BOLD + "\n  ── ACADEMIC DETAILS ────────────────────────────────" + RST);
        int sem     = readInt2("Current Semester (1-8): ", Validator::isValidSemester, "Must be 1 to 8.");
        String sec  = readStr("Section (A / B / C): ", Validator::isValidSection, "Single letter A-Z.");
        String batch= readStr("Batch (e.g. 2022-2026): ", Validator::isValidBatch, "Format YYYY-YYYY.");
        String cgpa = readStr("Cumulative CGPA (0.0-10.0 or N/A): ", Validator::isValidCGPA, "Enter 0.0-10.0 or N/A.");
        String blood= readStr("Blood Group (e.g. O+ / AB-): ", Validator::isValidBloodGroup, "Valid: A+/A-/B+/B-/O+/O-/AB+/AB-.");
        String stay = readStr("Hostel Status (Hosteller / Day Scholar): ", Validator::isValidHostelStatus, "Enter Hosteller or Day Scholar.");
        System.out.println(BOLD + "\n  ── GUARDIAN DETAILS ────────────────────────────────" + RST);
        String gName = readStr("Guardian Name: ", Validator::isValidName, "Letters and spaces only.");
        String gRel  = readStr("Relation (Father / Mother / Guardian): ", Validator::isValidRelation, "Enter Father, Mother, or Guardian.");
        String gPh   = readStr("Guardian Mobile: ", Validator::isValidPhone, "10-digit Indian mobile.");
        String[] subjects = Student.SEMESTER_SUBJECTS[sem];
        System.out.println(BOLD + "\n  ── MARKS — SEMESTER " + sem + " ───────────────────────────" + RST);
        System.out.println(YLW + "  Internal Assessment: max 40 marks  |  End-Semester Exam: max 60 marks" + RST);
        System.out.println(YLW + "  Pass criteria: Total ≥ 40  AND  External ≥ 24" + RST);
        double[] internals = new double[6];
        double[] externals = new double[6];
        for (int i = 0; i < 6; i++) {
            if (subjects[i].equalsIgnoreCase("N/A")) {
                internals[i] = 0; externals[i] = 0;
                continue;
            }
            System.out.println(BOLD + "\n  [" + (i+1) + "] " + subjects[i] + RST);
            internals[i] = readDbl("      Internal (/40): ", Validator::isValidInternalMarks, "Enter 0.0 to 40.0.");
            externals[i] = readDbl("      External (/60): ", Validator::isValidExternalMarks, "Enter 0.0 to 60.0.");
            System.out.printf("      Total: %.1f/100  →  %s%n",
                internals[i] + externals[i],
                Student.marksToLetterGrade(internals[i] + externals[i]));
        }

        double att = readDbl("Attendance % (0-100): ", Validator::isValidAttendance, "Enter 0.0 to 100.0.");
        Student s = new Student(
            roll, enroll, name, gender, dob, email, phone, addr,
            sem, sec.toUpperCase(), batch, cgpa, blood, stay,
            gName, gRel, gPh,
            subjects[0], internals[0], externals[0],
            subjects[1], internals[1], externals[1],
            subjects[2], internals[2], externals[2],
            subjects[3], internals[3], externals[3],
            subjects[4], internals[4], externals[4],
            subjects[5], internals[5], externals[5],
            att
        );

        if (sms.addStudent(s)) {
            ok("Student '" + name + "' registered successfully!");
            printCard(s);
        } else {
            warn("Registration failed — roll number conflict.");
        }
    }
    private static void editFlow() {
        hdr("EDIT STUDENT INFORMATION");
        String roll = prompt("UEM Roll No: ");
        Student s = sms.findByRoll(roll);
        if (s == null) { warn("Student not found."); return; }

        ok("Found: " + s);
        System.out.println(YLW + "  Press ENTER to keep the current value." + RST + "\n");

        String v;
        v = pOpt("Name [" + s.getName() + "]: ");
        if (!v.isEmpty() && Validator.isValidName(v)) s.setName(v);

        v = pOpt("Email [" + s.getEmail() + "]: ");
        if (!v.isEmpty() && Validator.isValidEmail(v)) s.setEmail(v);

        v = pOpt("Mobile [" + s.getContactNumber() + "]: ");
        if (!v.isEmpty() && Validator.isValidPhone(v)) s.setContactNumber(v);

        v = pOpt("Address [" + s.getPermanentAddress() + "]: ");
        if (!v.isEmpty()) s.setPermanentAddress(v);

        v = pOpt("Semester [" + s.getSemester() + "]: ");
        if (!v.isEmpty()) { try { int i = Integer.parseInt(v); if (Validator.isValidSemester(i)) s.setSemester(i); else warn("Invalid — kept."); } catch (Exception e) { warn("Not a number — kept."); } }

        v = pOpt("Section [" + s.getSection() + "]: ");
        if (!v.isEmpty() && Validator.isValidSection(v)) s.setSection(v.toUpperCase());

        v = pOpt("CGPA [" + s.getCgpa() + "]: ");
        if (!v.isEmpty() && Validator.isValidCGPA(v)) s.setCgpa(v);

        v = pOpt("Attendance % [" + s.getAttendancePercent() + "]: ");
        if (!v.isEmpty()) { try { double a = Double.parseDouble(v); if (Validator.isValidAttendance(a)) s.setAttendancePercent(a); } catch (Exception ignored) {} }

        System.out.println(BOLD + "\n  ── UPDATE MARKS (ENTER to skip) ─────────────────────" + RST);
        System.out.println(YLW + "  Internal /40  |  External /60" + RST);
        editMarks(s);

        if (sms.updateStudent(s)) { ok("Record updated!"); printCard(s); }
        else warn("Update failed.");
    }

    private static void editMarks(Student s) {
        record SubInfo(String name, double internal, double external) {}
        List<SubInfo> subs = List.of(
            new SubInfo(s.getSubject1Name(), s.getSub1Internal(), s.getSub1External()),
            new SubInfo(s.getSubject2Name(), s.getSub2Internal(), s.getSub2External()),
            new SubInfo(s.getSubject3Name(), s.getSub3Internal(), s.getSub3External()),
            new SubInfo(s.getSubject4Name(), s.getSub4Internal(), s.getSub4External()),
            new SubInfo(s.getSubject5Name(), s.getSub5Internal(), s.getSub5External()),
            new SubInfo(s.getSubject6Name(), s.getSub6Internal(), s.getSub6External())
        );

        for (int i = 0; i < 6; i++) {
            SubInfo si = subs.get(i);
            if (si.name().equalsIgnoreCase("N/A")) continue;
            System.out.println(BOLD + "  [" + (i+1) + "] " + si.name() + RST);

            String inStr = pOpt("      Internal [" + si.internal() + "/40]: ");
            String exStr = pOpt("      External [" + si.external() + "/60]: ");

            try {
                double newIn = inStr.isEmpty() ? si.internal() : Double.parseDouble(inStr);
                double newEx = exStr.isEmpty() ? si.external() : Double.parseDouble(exStr);
                if (!Validator.isValidInternalMarks(newIn)) { warn("Internal out of range — kept."); newIn = si.internal(); }
                if (!Validator.isValidExternalMarks(newEx)) { warn("External out of range — kept."); newEx = si.external(); }
                switch (i) {
                    case 0 -> { s.setSub1Internal(newIn); s.setSub1External(newEx); }
                    case 1 -> { s.setSub2Internal(newIn); s.setSub2External(newEx); }
                    case 2 -> { s.setSub3Internal(newIn); s.setSub3External(newEx); }
                    case 3 -> { s.setSub4Internal(newIn); s.setSub4External(newEx); }
                    case 4 -> { s.setSub5Internal(newIn); s.setSub5External(newEx); }
                    case 5 -> { s.setSub6Internal(newIn); s.setSub6External(newEx); }
                }
            } catch (NumberFormatException ignored) { warn("Invalid input — kept original."); }
        }
    }
    private static void removeFlow() {
        hdr("REMOVE STUDENT RECORD");
        String roll = prompt("UEM Roll No: ");
        Student s = sms.findByRoll(roll);
        if (s == null) { warn("Student not found."); return; }
        System.out.println(YLW + "  About to permanently remove: " + s + RST);
        String c = prompt("Confirm? (yes / no): ");
        if (c.equalsIgnoreCase("yes")) {
            if (sms.removeStudent(roll)) ok("Record deleted.");
            else warn("Deletion failed.");
        } else System.out.println("  Cancelled.");
    }
    private static void searchFlow() {
        hdr("SEARCH STUDENT");
        System.out.println("  1. By UEM Roll Number");
        System.out.println("  2. By Name (partial)");
        System.out.println("  3. By Enrollment Number");
        int opt = readInt("  Option: ");

        switch (opt) {
            case 1 -> { Student s = sms.findByRoll(prompt("Roll No: ")); if (s!=null) printCard(s); else warn("Not found."); }
            case 2 -> { List<Student> r = sms.searchByName(prompt("Name: ")); if (r.isEmpty()) warn("No match."); else { ok(r.size()+" result(s):"); r.forEach(Main::printCard); } }
            case 3 -> { Student s = sms.findByEnrollment(prompt("Enrollment No: ")); if (s!=null) printCard(s); else warn("Not found."); }
            default -> warn("Invalid option.");
        }
    }
    private static void allStudentsFlow() {
        hdr("ALL REGISTERED STUDENTS  (Total: " + sms.getTotalCount() + ")");
        List<Student> all = sms.getAllStudents();
        if (all.isEmpty()) { warn("No records found."); return; }
        printTable(all);
    }
    private static void semSectionFlow() {
        hdr("SEMESTER / SECTION RESULT SHEET");
        int sem    = readInt2("Semester (1-8): ", Validator::isValidSemester, "Must be 1-8.");
        String sec = readStr("Section: ", Validator::isValidSection, "Single letter.");
        List<Student> list = sms.filterBySemSection(sem, sec);
        if (list.isEmpty()) { warn("No students for Sem " + sem + "-" + sec.toUpperCase()); return; }
        printDetailedTable(list, sem);
    }
    private static void batchFlow() {
        hdr("BATCH-WISE STUDENT LIST");
        String batch = readStr("Batch (e.g. 2022-2026): ", Validator::isValidBatch, "Format YYYY-YYYY.");
        List<Student> list = sms.filterByBatch(batch);
        if (list.isEmpty()) { warn("No students for batch " + batch); return; }
        ok("Batch " + batch + " — " + list.size() + " student(s):");
        printTable(list);
    }
    private static void topFlow() {
        hdr("TOP PERFORMERS");
        int sem = readInt2("Semester (1-8): ", Validator::isValidSemester, "Must be 1-8.");
        int n   = readInt("How many students? ");
        if (n <= 0) { warn("Invalid number."); return; }
        List<Student> top = sms.getTopStudents(sem, n);
        if (top.isEmpty()) { warn("No students in Semester " + sem); return; }

        System.out.printf("%n  %-4s %-22s %-22s %-5s %-7s %-6s %-10s %-9s%n",
                "Rank","UEM Roll No","Name","Sec","Attend%","SGPA","Percentage","Result");
        System.out.println("  " + "─".repeat(90));
        int rank = 1;
        for (Student s : top) {
            System.out.printf("  %-4d %-22s %-22s %-5s %-7.1f %-6.2f %-10.2f %-9s%n",
                rank++, s.getUemRollNo(), s.getName(), s.getSection(),
                s.getAttendancePercent(), s.getSGPA(),
                s.getPercentage(), s.getResult());
        }
    }
    private static void atRiskFlow() {
        hdr("AT-RISK STUDENTS (Fail / Detained / Not Eligible)");
        int sem = readInt2("Semester (1-8): ", Validator::isValidSemester, "Must be 1-8.");
        List<Student> list = sms.getAtRiskStudents(sem);
        if (list.isEmpty()) { ok("No at-risk students in Semester " + sem + ". ✓"); return; }

        warn(list.size() + " student(s) at risk in Semester " + sem + ":");
        System.out.printf("%n  %-22s %-22s %-5s %-7s %-6s %-25s%n",
                "UEM Roll No","Name","Sec","Attend%","SGPA","Status");
        System.out.println("  " + "─".repeat(90));
        for (Student s : list) {
            System.out.printf("  %-22s %-22s %-5s %-7.1f %-6.2f %-25s%n",
                s.getUemRollNo(), s.getName(), s.getSection(),
                s.getAttendancePercent(), s.getSGPA(), s.getResult());
        }
    }
    private static void attendanceFlow() {
        hdr("ATTENDANCE SHORTFALL REPORT");
        int sem   = readInt2("Semester (1-8, or 0 for all): ",
                v -> v >= 0 && v <= 8, "Must be 0-8.");
        double th = readDbl("Below what % (default 75): ",
                Validator::isValidAttendance, "0-100.");

        List<Student> list = sms.getAttendanceShortfall(sem, th);
        if (list.isEmpty()) {
            ok("No student below " + th + "% attendance" +
               (sem > 0 ? " in Semester " + sem : "") + ". ✓");
            return;
        }

        warn(list.size() + " student(s) below " + th + "% attendance:");
        System.out.printf("%n  %-22s %-22s %-4s %-5s %-10s %-15s %-13s%n",
                "UEM Roll No","Name","Sem","Sec","Attend%","Status","Contact");
        System.out.println("  " + "─".repeat(95));
        for (Student s : list) {
            System.out.printf("  %-22s %-22s %-4d %-5s %-10.1f %-15s %-13s%n",
                s.getUemRollNo(), s.getName(),
                s.getSemester(), s.getSection(),
                s.getAttendancePercent(), s.getAttendanceStatus(),
                s.getContactNumber());
        }
    }

    private static void exportResultFlow() {
        hdr("EXPORT RESULT SHEET");
        int sem    = readInt2("Semester (1-8): ", Validator::isValidSemester, "Must be 1-8.");
        String sec = readStr("Section: ", Validator::isValidSection, "Single letter.");
        sms.exportSemesterReport(sem, sec);
    }
    private static void exportAttendFlow() {
        hdr("EXPORT ATTENDANCE WARNING NOTICE");
        int sem   = readInt2("Semester (1-8): ", Validator::isValidSemester, "Must be 1-8.");
        double th = readDbl("Threshold % (default 75): ",
                Validator::isValidAttendance, "0-100.");
        sms.exportAttendanceNotice(sem, th);
    }
    private static void syllabusFlow() {
        hdr("UEM KOLKATA — B.Tech CSE AUTONOMOUS SYLLABUS");
        for (int sem = 1; sem <= 8; sem++) {
            System.out.println(BOLD + MAG + "\n  ── SEMESTER " + sem
                + " ───────────────────────────────────────────" + RST);
            String[] subs = Student.SEMESTER_SUBJECTS[sem];
            int no = 1;
            for (String sub : subs) {
                if (!sub.equalsIgnoreCase("N/A"))
                    System.out.printf("     %d.  %s%n", no++, sub);
            }
        }

        System.out.println(BOLD + "\n\n  UEM AUTONOMOUS GRADING SCALE  (10-point)" + RST);
        System.out.println("  ┌──────────────┬──────┬──────────────────────────────────────┐");
        System.out.println("  │ Total (/100) │ Grd  │ Description          │ Grade Points  │");
        System.out.println("  ├──────────────┼──────┼──────────────────────┼───────────────┤");
        System.out.println("  │ 90 – 100     │  O   │ Outstanding          │     10        │");
        System.out.println("  │ 80 –  89     │  A+  │ Excellent            │      9        │");
        System.out.println("  │ 70 –  79     │  A   │ Very Good            │      8        │");
        System.out.println("  │ 60 –  69     │  B+  │ Good                 │      7        │");
        System.out.println("  │ 50 –  59     │  B   │ Above Average        │      6        │");
        System.out.println("  │ 40 –  49     │  C   │ Pass                 │      5        │");
        System.out.println("  │  0 –  39     │  F   │ Fail                 │      0        │");
        System.out.println("  └──────────────┴──────┴──────────────────────┴───────────────┘");
        System.out.println();
        System.out.println(BOLD + "  MARKS PATTERN (per subject):" + RST);
        System.out.println("    • Internal Assessment (IA) : 40 marks  (Assignments, Mid-Sems, Practicals)");
        System.out.println("    • End-Semester Exam (ESE)  : 60 marks  (Annual University Exam)");
        System.out.println("    • Total                    : 100 marks");
        System.out.println();
        System.out.println(BOLD + "  PASS CRITERIA (UEM Autonomous — per subject):" + RST);
        System.out.println("    ✔  Total marks  ≥ 40 / 100");
        System.out.println("    ✔  External     ≥ 24 / 60   (minimum in ESE)");
        System.out.println("    ✔  Attendance   ≥ 75%       (eligibility for ESE)");
        System.out.println();
        System.out.println(BOLD + "  NOTE:" + RST);
        System.out.println("    UEM Kolkata is an AUTONOMOUS UNIVERSITY (UGC recognised).");
        System.out.println("    Degrees are awarded by UEM itself — not by MAKAUT.");
        System.out.println("    UEM has its own examination board, syllabus, and grading system.");
    }
    private static void printCard(Student s) {
        System.out.println(BOLD +
            "\n  ┌───────────────────────────────────────────────────────────┐" + RST);
        System.out.printf("  │  %-57s│%n", "UEM KOLKATA (AUTONOMOUS) — STUDENT PROFILE");
        System.out.println(BOLD +
            "  ├─────────────────────────────┬─────────────────────────────┤" + RST);
        r2("UEM Roll No",    s.getUemRollNo(),        "Enrollment No",  s.getEnrollmentNo());
        r2("Name",           s.getName(),              "Gender",         s.getGender());
        r2("Date of Birth",  s.getDateOfBirth(),       "Blood Group",    s.getBloodGroup());
        r2("Email",          s.getEmail(),             "Mobile",         s.getContactNumber());
        r2("Semester",       "Sem " + s.getSemester(), "Section",        s.getSection());
        r2("Batch",          s.getBatch(),             "Hostel Status",  s.getHostelOrDay());
        r2("Cumul. CGPA",    s.getCgpa(),              "Attendance",
           String.format("%.1f%%  [%s]", s.getAttendancePercent(), s.getAttendanceStatus()));
        r2("Guardian",       s.getGuardianName(),      "Relation",       s.getGuardianRelation());
        r2("Gdn. Contact",   s.getGuardianContact(),   "Address",        trunc(s.getPermanentAddress(), 26));

        System.out.println(BOLD +
            "  ├─────────────────────────────┴─────────────────────────────┤" + RST);
        System.out.printf("  │  %-57s│%n",
            "MARKS — SEM " + s.getSemester() + "  [Internal /40 + External /60 = Total /100]");
        System.out.println(BOLD +
            "  ├──────────────────────────────────────┬────┬────┬─────┬────┤" + RST);
        System.out.printf("  │  %-38s│IA  │ESE │ Tot │Grd │%n", "Subject");
        System.out.println(BOLD +
            "  ├──────────────────────────────────────┼────┼────┼─────┼────┤" + RST);

        String[]  names   = {s.getSubject1Name(), s.getSubject2Name(), s.getSubject3Name(),
                             s.getSubject4Name(), s.getSubject5Name(), s.getSubject6Name()};
        double[]  intArr  = {s.getSub1Internal(), s.getSub2Internal(), s.getSub3Internal(),
                             s.getSub4Internal(), s.getSub5Internal(), s.getSub6Internal()};
        double[]  extArr  = {s.getSub1External(), s.getSub2External(), s.getSub3External(),
                             s.getSub4External(), s.getSub5External(), s.getSub6External()};

        for (int i = 0; i < 6; i++) {
            if (names[i].equalsIgnoreCase("N/A")) continue;
            double tot = intArr[i] + extArr[i];
            String grd = tot >= 90 ? "O" : tot >= 80 ? "A+" : tot >= 70 ? "A"
                       : tot >= 60 ? "B+" : tot >= 50 ? "B" : tot >= 40 ? "C" : "F";
            System.out.printf("  │  %-38s│%3.0f │%3.0f │%4.1f │%-4s│%n",
                trunc(names[i], 38), intArr[i], extArr[i], tot, grd);
        }

        System.out.println(BOLD +
            "  ├──────────────────────────────────────┴────┴────┴─────┴────┤" + RST);
        System.out.printf("  │  %-28s : %-25s │%n", "SGPA (this semester)",
            String.format("%.2f / 10.00", s.getSGPA()));
        System.out.printf("  │  %-28s : %-25s │%n", "Percentage",
            String.format("%.2f %%", s.getPercentage()));

        String res = s.getResult();
        String col = res.equals("PASS") ? GRN : RED;
        System.out.printf("  │  %-28s : " + col + BOLD + "%-25s" + RST + " │%n", "Result", res);
        System.out.println(BOLD +
            "  └───────────────────────────────────────────────────────────┘" + RST);
    }

    private static void r2(String l1, String v1, String l2, String v2) {
        System.out.printf("  │  %-13s: %-13s│  %-13s: %-13s│%n",
            l1, trunc(v1, 13), l2, trunc(v2, 13));
    }

    private static void printTable(List<Student> list) {
        System.out.printf("%n  %-22s %-22s %-10s %-5s %-5s %-7s %-6s %-8s%n",
            "UEM Roll No","Name","Batch","Sec","Sem","Attend%","SGPA","Result");
        System.out.println("  " + "─".repeat(90));
        for (Student s : list) {
            System.out.printf("  %-22s %-22s %-10s %-5s %-5d %-7.1f %-6.2f %-8s%n",
                s.getUemRollNo(), s.getName(), s.getBatch(), s.getSection(),
                s.getSemester(), s.getAttendancePercent(), s.getSGPA(), s.getResult());
        }
        System.out.println("  " + "─".repeat(90) + "\n  Total: " + list.size() + " record(s)");
    }

    private static void printDetailedTable(List<Student> list, int sem) {
        String[] subs = Student.SEMESTER_SUBJECTS[sem];
        System.out.println();
        System.out.printf("  %-22s %-20s ", "Roll No", "Name");
        for (String sub : subs)
            if (!sub.equalsIgnoreCase("N/A"))
                System.out.printf("%-10s", trunc(sub, 9));
        System.out.printf("%-7s %-5s %-10s %-25s%n", "Total", "SGPA", "Attend%", "Result");
        System.out.println("  " + "─".repeat(130));

        for (Student s : list) {
            System.out.printf("  %-22s %-20s ", s.getUemRollNo(), s.getName());
            double[] totals = s.getAllSubjectTotals();
            String[] names  = {s.getSubject1Name(), s.getSubject2Name(), s.getSubject3Name(),
                               s.getSubject4Name(), s.getSubject5Name(), s.getSubject6Name()};
            for (int i = 0; i < 6; i++)
                if (!names[i].equalsIgnoreCase("N/A"))
                    System.out.printf("%-10.1f", totals[i]);
            System.out.printf("%-7.1f %-5.2f %-10.1f %-25s%n",
                s.getTotalMarks(), s.getSGPA(),
                s.getAttendancePercent(), s.getResult());
        }
        System.out.println("  " + "─".repeat(130) + "\n  Total: " + list.size() + " record(s)");
    }
    @FunctionalInterface interface Chk<T> { boolean ok(T t); }

    private static String readStr(String msg, Chk<String> chk, String err) {
        while (true) { String v = prompt(msg); if (chk.ok(v)) return v; warn(err); }
    }

    private static int readInt2(String msg, Chk<Integer> chk, String err) {
        while (true) { int v = readInt(msg); if (chk.ok(v)) return v; warn(err); }
    }

    private static double readDbl(String msg, Chk<Double> chk, String err) {
        while (true) {
            System.out.print("  " + msg);
            try { double v = Double.parseDouble(sc.nextLine().trim()); if (chk.ok(v)) return v; warn(err); }
            catch (NumberFormatException e) { warn("Enter a valid number."); }
        }
    }

    private static int readInt(String msg) {
        while (true) {
            System.out.print("  " + msg);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { warn("Enter a whole number."); }
        }
    }

    private static String prompt(String msg) { System.out.print("  " + msg); return sc.nextLine().trim(); }
    private static String pOpt(String msg)   { System.out.print("  " + msg); return sc.nextLine().trim(); }
    private static String trunc(String s, int max) {
        if (s==null) return ""; return s.length()<=max ? s : s.substring(0,max-1)+"…";
    }

    private static void hdr(String t) {
        System.out.println("\n" + BOLD + CYN
            + "  ╔═══ " + t + " " + "═".repeat(Math.max(1, 58 - t.length())) + "╗" + RST);
    }
    private static void ok(String m)   { System.out.println(GRN + BOLD + "  ✔  " + m + RST); }
    private static void warn(String m) { System.out.println(RED        + "  ✘  " + m + RST); }

    private static void bye() {
        System.out.println(BOLD + CYN +
            "\n  ╔═══════════════════════════════════════════════════════╗" + RST);
        System.out.println(BOLD + CYN +
            "  ║  UEM Kolkata SMS — All data saved. See you next time! ║" + RST);
        System.out.println(BOLD + CYN +
            "  ╚═══════════════════════════════════════════════════════╝\n" + RST);
    }
}
