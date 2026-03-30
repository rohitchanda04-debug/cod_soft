import java.io.*;
import java.util.*;
import java.util.stream.*;
public class StudentManagementSystem {

    private static final String DATA_FILE = "uem_cse_students.dat";
    private final Map<String, Student> store = new LinkedHashMap<>();

    public StudentManagementSystem() {
        loadFromFile();
    }
    public boolean addStudent(Student s) {
        String key = key(s.getUemRollNo());
        if (store.containsKey(key)) return false;
        store.put(key, s);
        saveToFile();
        return true;
    }
    public boolean removeStudent(String rollNo) {
        String k = key(rollNo);
        if (!store.containsKey(k)) return false;
        store.remove(k);
        saveToFile();
        return true;
    }
    public boolean updateStudent(Student s) {
        String k = key(s.getUemRollNo());
        if (!store.containsKey(k)) return false;
        store.put(k, s);
        saveToFile();
        return true;
    }
    public Student findByRoll(String rollNo) {
        return store.get(key(rollNo));
    }
    public Student findByEnrollment(String enrollNo) {
        String target = enrollNo.trim().toUpperCase();
        return store.values().stream()
                .filter(s -> s.getEnrollmentNo().toUpperCase().equals(target))
                .findFirst().orElse(null);
    }
    public List<Student> searchByName(String query) {
        String q = query.toLowerCase().trim();
        return store.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(q))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }
    public List<Student> filterBySemSection(int sem, String section) {
        return store.values().stream()
                .filter(s -> s.getSemester() == sem
                          && s.getSection().equalsIgnoreCase(section))
                .sorted(Comparator.comparing(Student::getUemRollNo))
                .collect(Collectors.toList());
    }
    public List<Student> filterByBatch(String batch) {
        return store.values().stream()
                .filter(s -> s.getBatch().equalsIgnoreCase(batch.trim()))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }
    public List<Student> getAttendanceShortfall(int sem, double threshold) {
        return store.values().stream()
                .filter(s -> (sem == 0 || s.getSemester() == sem)
                          && s.getAttendancePercent() < threshold)
                .sorted(Comparator.comparingDouble(Student::getAttendancePercent))
                .collect(Collectors.toList());
    }
    public List<Student> getAtRiskStudents(int sem) {
        return store.values().stream()
                .filter(s -> s.getSemester() == sem
                          && !s.getResult().equals("PASS"))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }
    public List<Student> getTopStudents(int sem, int n) {
        return store.values().stream()
                .filter(s -> s.getSemester() == sem)
                .sorted(Comparator.comparingDouble(Student::getSGPA).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
    public List<Student> getAllStudents() {
        return store.values().stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public int getTotalCount() { return store.size(); }
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(DATA_FILE), "UTF-8"))) {
            for (Student s : store.values()) pw.println(s.toCSV());
        } catch (IOException e) {
            System.err.println("[ERROR] Save failed: " + e.getMessage());
        }
    }
    public void loadFromFile() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), "UTF-8"))) {
            String line; int ln = 0;
            while ((line = br.readLine()) != null) {
                ln++;
                line = line.trim();
                if (line.isEmpty()) continue;
                Student s = Student.fromCSV(line);
                if (s != null) store.put(key(s.getUemRollNo()), s);
                else System.err.println("[WARN] Skipping malformed record at line " + ln);
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Load failed: " + e.getMessage());
        }
    }
    public void exportSemesterReport(int sem, String section) {
        List<Student> list = filterBySemSection(sem, section);
        String filename = "UEM_CSE_Sem" + sem + "_Sec"
                        + section.toUpperCase() + "_Result.txt";

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "UTF-8"))) {

            pw.println("═══════════════════════════════════════════════════════════════════════════");
            pw.println("  UNIVERSITY OF ENGINEERING AND MANAGEMENT, KOLKATA  (AUTONOMOUS)");
            pw.println("  Department of Computer Science & Engineering");
            pw.println("  B.Tech CSE — Semester " + sem + "  |  Section "
                       + section.toUpperCase() + "  |  End-Semester Result Sheet");
            pw.println("  Marks Pattern: Internal /40  +  External /60  =  Total /100");
            pw.println("  Pass Criteria: Total ≥ 40  AND  External ≥ 24  AND  Attendance ≥ 75%");
            pw.println("═══════════════════════════════════════════════════════════════════════════");
            pw.println();

            String[] subjects = Student.SEMESTER_SUBJECTS[sem];

            for (Student s : list) {
                double[] internals = {s.getSub1Internal(), s.getSub2Internal(),
                                      s.getSub3Internal(), s.getSub4Internal(),
                                      s.getSub5Internal(), s.getSub6Internal()};
                double[] externals = {s.getSub1External(), s.getSub2External(),
                                      s.getSub3External(), s.getSub4External(),
                                      s.getSub5External(), s.getSub6External()};

                pw.printf("  %-20s  %-22s  Sem: %d-%s  Batch: %s%n",
                          s.getUemRollNo(), s.getName(),
                          s.getSemester(), s.getSection(), s.getBatch());
                pw.println("  " + "─".repeat(70));
                pw.printf("  %-42s %6s %6s %7s  %-20s%n",
                          "Subject", "Int/40", "Ext/60", "Tot/100", "Grade");
                pw.println("  " + "─".repeat(70));

                for (int i = 0; i < 6; i++) {
                    if (subjects[i].equalsIgnoreCase("N/A")) continue;
                    double tot = internals[i] + externals[i];
                    pw.printf("  %-42s %6.1f %6.1f %7.1f  %-20s%n",
                              subjects[i], internals[i], externals[i],
                              tot, Student.marksToLetterGrade(tot));
                }

                pw.println("  " + "─".repeat(70));
                pw.printf("  SGPA: %-6.2f  |  Attendance: %.1f%% (%s)  |  Result: %s%n%n",
                          s.getSGPA(), s.getAttendancePercent(),
                          s.getAttendanceStatus(), s.getResult());
            }

            pw.println("═══════════════════════════════════════════════════════════════════════════");
            pw.println("  Total Students: " + list.size());

            if (!list.isEmpty()) {
                double avgSGPA = list.stream().mapToDouble(Student::getSGPA).average().orElse(0);
                long pass = list.stream().filter(s -> s.getResult().equals("PASS")).count();
                pw.printf("  Average SGPA : %.2f / 10.00%n", avgSGPA);
                pw.printf("  Pass Count   : %d / %d  (%.1f%%)%n",
                          pass, list.size(), (pass * 100.0 / list.size()));
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Export failed: " + e.getMessage());
            return;
        }
        System.out.println("[INFO] Result sheet exported → " + filename);
    }
    public void exportAttendanceNotice(int sem, double threshold) {
        List<Student> list = getAttendanceShortfall(sem, threshold);
        String filename = "UEM_CSE_Attendance_Warning_Sem" + sem + ".txt";

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "UTF-8"))) {
            pw.println("═══════════════════════════════════════════════════════════════");
            pw.println("  UEM KOLKATA (AUTONOMOUS) — ATTENDANCE WARNING NOTICE");
            pw.println("  Department: CSE  |  Semester: " + sem);
            pw.printf ("  Students with attendance below %.1f%%%n", threshold);
            pw.println("═══════════════════════════════════════════════════════════════");
            pw.printf("%-22s %-22s %-5s %-10s %-15s %-13s%n",
                      "UEM Roll No", "Name", "Sec", "Attend%", "Status", "Contact");
            pw.println("─".repeat(90));
            for (Student s : list) {
                pw.printf("%-22s %-22s %-5s %-10.1f %-15s %-13s%n",
                          s.getUemRollNo(), s.getName(), s.getSection(),
                          s.getAttendancePercent(), s.getAttendanceStatus(),
                          s.getContactNumber());
            }
            pw.println("═══════════════════════════════════════════════════════════════");
            pw.println("Total: " + list.size() + " student(s)");
        } catch (IOException e) {
            System.err.println("[ERROR] Export failed: " + e.getMessage());
            return;
        }
        System.out.println("[INFO] Attendance notice exported → " + filename);
    }
    private String key(String roll) {
        return roll == null ? "" : roll.trim().toUpperCase();
    }
}