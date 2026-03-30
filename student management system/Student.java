import java.io.Serializable;
public class Student implements Serializable {

    private static final long serialVersionUID = 3L;
    private String uemRollNo;
    private String enrollmentNo;
    private String name;
    private String gender;         
    private String dateOfBirth;     
    private String email;           
    private String contactNumber;   
    private String permanentAddress;
    private int    semester;
    private String section;
    private String batch;
    private String cgpa;
    private String bloodGroup;
    private String hostelOrDay;
    private String guardianName;
    private String guardianRelation;
    private String guardianContact;
    private String subject1Name;  private double sub1Internal; private double sub1External;
    private String subject2Name;  private double sub2Internal; private double sub2External;
    private String subject3Name;  private double sub3Internal; private double sub3External;
    private String subject4Name;  private double sub4Internal; private double sub4External;
    private String subject5Name;  private double sub5Internal; private double sub5External;
    private String subject6Name;  private double sub6Internal; private double sub6External;
    private double attendancePercent;
    public static final String[][] SEMESTER_SUBJECTS = {
        {},
        {"Mathematics I (Calculus & Linear Algebra)",
         "Physics",
         "Chemistry",
         "English & Communication Skills",
         "Basic Electrical & Electronics Engg",
         "Engineering Graphics & Design"},
        // SEM 2
        {"Mathematics II (Probability & Statistics)",
         "Programming Fundamentals (C)",
         "Data Structures & Algorithms",
         "Digital Logic Design",
         "Environmental Science",
         "Engineering / IT Workshop"},
        // SEM 3
        {"Discrete Mathematics",
         "Object-Oriented Programming (Java)",
         "Computer Organisation & Architecture",
         "Operating Systems",
         "Database Management Systems",
         "Numerical Methods & Scientific Computing"},
        // SEM 4
        {"Design & Analysis of Algorithms",
         "Theory of Computation",
         "Computer Networks",
         "Software Engineering & Project Management",
         "Microprocessors & Embedded Systems",
         "Elective I"},
        // SEM 5
        {"Compiler Design",
         "Artificial Intelligence & Expert Systems",
         "Machine Learning",
         "Information & Cyber Security",
         "Cloud Computing & Virtualisation",
         "Elective II"},
        // SEM 6
        {"Deep Learning & Neural Networks",
         "Big Data Analytics",
         "Internet of Things (IoT)",
         "Distributed Systems",
         "Open Elective",
         "Minor Project"},
        // SEM 7  (5 real components + 1 placeholder)
        {"Advanced Topics in CSE (Elective III)",
         "Elective IV",
         "Internship / Industrial Training",
         "Research Methodology & Tech Writing",
         "Seminar & Technical Presentation",
         "N/A"},
        // SEM 8  (4 real components + 2 placeholders)
        {"Major Project",
         "Entrepreneurship & Business Development",
         "Professional Ethics & IPR",
         "Comprehensive Viva Voce",
         "N/A",
         "N/A"}
    };
    public Student() {}

    public Student(
            String uemRollNo, String enrollmentNo,
            String name, String gender, String dateOfBirth,
            String email, String contactNumber, String permanentAddress,
            int semester, String section, String batch,
            String cgpa, String bloodGroup, String hostelOrDay,
            String guardianName, String guardianRelation, String guardianContact,
            String s1, double i1, double e1,
            String s2, double i2, double e2,
            String s3, double i3, double e3,
            String s4, double i4, double e4,
            String s5, double i5, double e5,
            String s6, double i6, double e6,
            double attendancePercent) {

        this.uemRollNo       = uemRollNo;
        this.enrollmentNo    = enrollmentNo;
        this.name            = name;
        this.gender          = gender;
        this.dateOfBirth     = dateOfBirth;
        this.email           = email;
        this.contactNumber   = contactNumber;
        this.permanentAddress= permanentAddress;
        this.semester        = semester;
        this.section         = section;
        this.batch           = batch;
        this.cgpa            = cgpa;
        this.bloodGroup      = bloodGroup;
        this.hostelOrDay     = hostelOrDay;
        this.guardianName    = guardianName;
        this.guardianRelation= guardianRelation;
        this.guardianContact = guardianContact;

        this.subject1Name = s1; this.sub1Internal = i1; this.sub1External = e1;
        this.subject2Name = s2; this.sub2Internal = i2; this.sub2External = e2;
        this.subject3Name = s3; this.sub3Internal = i3; this.sub3External = e3;
        this.subject4Name = s4; this.sub4Internal = i4; this.sub4External = e4;
        this.subject5Name = s5; this.sub5Internal = i5; this.sub5External = e5;
        this.subject6Name = s6; this.sub6Internal = i6; this.sub6External = e6;

        this.attendancePercent = attendancePercent;
    }
    public static double subjectTotal(double internal, double external) {
        return internal + external;
    }
    public double[] getAllSubjectTotals() {
        return new double[]{
            sub1Internal + sub1External,
            sub2Internal + sub2External,
            sub3Internal + sub3External,
            sub4Internal + sub4External,
            sub5Internal + sub5External,
            sub6Internal + sub6External
        };
    }
    public double getTotalMarks() {
        double sum = 0;
        double[] totals = getAllSubjectTotals();
        String[] names  = {subject1Name, subject2Name, subject3Name,
                           subject4Name, subject5Name, subject6Name};
        for (int i = 0; i < 6; i++) {
            if (!names[i].equalsIgnoreCase("N/A")) sum += totals[i];
        }
        return sum;
    }
    public int getActiveSubjectCount() {
        int count = 0;
        String[] names = {subject1Name, subject2Name, subject3Name,
                          subject4Name, subject5Name, subject6Name};
        for (String n : names) if (!n.equalsIgnoreCase("N/A")) count++;
        return count;
    }
    public double getPercentage() {
        int active = getActiveSubjectCount();
        return active == 0 ? 0 : (getTotalMarks() / (active * 100.0)) * 100.0;
    }
    public double getSGPA() {
        double[] totals = getAllSubjectTotals();
        String[] names  = {subject1Name, subject2Name, subject3Name,
                           subject4Name, subject5Name, subject6Name};
        double gpSum = 0; int count = 0;
        for (int i = 0; i < 6; i++) {
            if (!names[i].equalsIgnoreCase("N/A")) {
                gpSum += marksToGradePoint(totals[i]);
                count++;
            }
        }
        return count == 0 ? 0 : gpSum / count;
    }
    public static double marksToGradePoint(double total) {
        if      (total >= 90) return 10.0;  // O  — Outstanding
        else if (total >= 80) return 9.0;   // A+ — Excellent
        else if (total >= 70) return 8.0;   // A  — Very Good
        else if (total >= 60) return 7.0;   // B+ — Good
        else if (total >= 50) return 6.0;   // B  — Above Average
        else if (total >= 40) return 5.0;   // C  — Average (Pass)
        else                  return 0.0;   // F  — Fail
    }
    public static String marksToLetterGrade(double total) {
        if      (total >= 90) return "O   (Outstanding)";
        else if (total >= 80) return "A+  (Excellent)";
        else if (total >= 70) return "A   (Very Good)";
        else if (total >= 60) return "B+  (Good)";
        else if (total >= 50) return "B   (Above Average)";
        else if (total >= 40) return "C   (Pass)";
        else                  return "F   (Fail)";
    }
    public String getResult() {
        if (attendancePercent < 75.0) return "NOT ELIGIBLE (Attendance)";

        double[] internals = {sub1Internal, sub2Internal, sub3Internal,
                              sub4Internal, sub5Internal, sub6Internal};
        double[] externals = {sub1External, sub2External, sub3External,
                              sub4External, sub5External, sub6External};
        String[] names     = {subject1Name, subject2Name, subject3Name,
                              subject4Name, subject5Name, subject6Name};

        for (int i = 0; i < 6; i++) {
            if (names[i].equalsIgnoreCase("N/A")) continue;
            double total = internals[i] + externals[i];
            if (total < 40)         return "FAIL (Marks < 40)";
            if (externals[i] < 24)  return "FAIL (External < 24)";
        }
        return "PASS";
    }
    public String getAttendanceStatus() {
        if      (attendancePercent >= 90) return "Excellent";
        else if (attendancePercent >= 75) return "Eligible";
        else if (attendancePercent >= 65) return "Warning";
        else                              return "NOT ELIGIBLE";
    }
    public String toCSV() {
        return String.join("|",
            uemRollNo, enrollmentNo, name, gender, dateOfBirth,
            email, contactNumber, permanentAddress,
            String.valueOf(semester), section, batch, cgpa, bloodGroup, hostelOrDay,
            guardianName, guardianRelation, guardianContact,
            subject1Name, String.valueOf(sub1Internal), String.valueOf(sub1External),
            subject2Name, String.valueOf(sub2Internal), String.valueOf(sub2External),
            subject3Name, String.valueOf(sub3Internal), String.valueOf(sub3External),
            subject4Name, String.valueOf(sub4Internal), String.valueOf(sub4External),
            subject5Name, String.valueOf(sub5Internal), String.valueOf(sub5External),
            subject6Name, String.valueOf(sub6Internal), String.valueOf(sub6External),
            String.valueOf(attendancePercent)
        );
    }

    public static Student fromCSV(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 37) return null;
        try {
            return new Student(
                p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7],
                Integer.parseInt(p[8]), p[9], p[10], p[11], p[12], p[13],
                p[14], p[15], p[16],
                p[17], Double.parseDouble(p[18]), Double.parseDouble(p[19]),
                p[20], Double.parseDouble(p[21]), Double.parseDouble(p[22]),
                p[23], Double.parseDouble(p[24]), Double.parseDouble(p[25]),
                p[26], Double.parseDouble(p[27]), Double.parseDouble(p[28]),
                p[29], Double.parseDouble(p[30]), Double.parseDouble(p[31]),
                p[32], Double.parseDouble(p[33]), Double.parseDouble(p[34]),
                Double.parseDouble(p[35])
            );
        } catch (Exception e) {
            return null;
        }
    }
    public String getUemRollNo()                  { return uemRollNo; }
    public void   setUemRollNo(String v)          { this.uemRollNo = v; }

    public String getEnrollmentNo()               { return enrollmentNo; }
    public void   setEnrollmentNo(String v)       { this.enrollmentNo = v; }

    public String getName()                       { return name; }
    public void   setName(String v)               { this.name = v; }

    public String getGender()                     { return gender; }
    public void   setGender(String v)             { this.gender = v; }

    public String getDateOfBirth()                { return dateOfBirth; }
    public void   setDateOfBirth(String v)        { this.dateOfBirth = v; }

    public String getEmail()                      { return email; }
    public void   setEmail(String v)              { this.email = v; }

    public String getContactNumber()              { return contactNumber; }
    public void   setContactNumber(String v)      { this.contactNumber = v; }

    public String getPermanentAddress()           { return permanentAddress; }
    public void   setPermanentAddress(String v)   { this.permanentAddress = v; }

    public int    getSemester()                   { return semester; }
    public void   setSemester(int v)              { this.semester = v; }

    public String getSection()                    { return section; }
    public void   setSection(String v)            { this.section = v; }

    public String getBatch()                      { return batch; }
    public void   setBatch(String v)              { this.batch = v; }

    public String getCgpa()                       { return cgpa; }
    public void   setCgpa(String v)               { this.cgpa = v; }

    public String getBloodGroup()                 { return bloodGroup; }
    public void   setBloodGroup(String v)         { this.bloodGroup = v; }

    public String getHostelOrDay()                { return hostelOrDay; }
    public void   setHostelOrDay(String v)        { this.hostelOrDay = v; }

    public String getGuardianName()               { return guardianName; }
    public void   setGuardianName(String v)       { this.guardianName = v; }

    public String getGuardianRelation()           { return guardianRelation; }
    public void   setGuardianRelation(String v)   { this.guardianRelation = v; }

    public String getGuardianContact()            { return guardianContact; }
    public void   setGuardianContact(String v)    { this.guardianContact = v; }

    public double getAttendancePercent()          { return attendancePercent; }
    public void   setAttendancePercent(double v)  { this.attendancePercent = v; }

    // Subject 1
    public String getSubject1Name()               { return subject1Name; }
    public void   setSubject1Name(String v)       { this.subject1Name = v; }
    public double getSub1Internal()               { return sub1Internal; }
    public void   setSub1Internal(double v)       { this.sub1Internal = v; }
    public double getSub1External()               { return sub1External; }
    public void   setSub1External(double v)       { this.sub1External = v; }

    // Subject 2
    public String getSubject2Name()               { return subject2Name; }
    public void   setSubject2Name(String v)       { this.subject2Name = v; }
    public double getSub2Internal()               { return sub2Internal; }
    public void   setSub2Internal(double v)       { this.sub2Internal = v; }
    public double getSub2External()               { return sub2External; }
    public void   setSub2External(double v)       { this.sub2External = v; }

    // Subject 3
    public String getSubject3Name()               { return subject3Name; }
    public void   setSubject3Name(String v)       { this.subject3Name = v; }
    public double getSub3Internal()               { return sub3Internal; }
    public void   setSub3Internal(double v)       { this.sub3Internal = v; }
    public double getSub3External()               { return sub3External; }
    public void   setSub3External(double v)       { this.sub3External = v; }

    // Subject 4
    public String getSubject4Name()               { return subject4Name; }
    public void   setSubject4Name(String v)       { this.subject4Name = v; }
    public double getSub4Internal()               { return sub4Internal; }
    public void   setSub4Internal(double v)       { this.sub4Internal = v; }
    public double getSub4External()               { return sub4External; }
    public void   setSub4External(double v)       { this.sub4External = v; }

    // Subject 5
    public String getSubject5Name()               { return subject5Name; }
    public void   setSubject5Name(String v)       { this.subject5Name = v; }
    public double getSub5Internal()               { return sub5Internal; }
    public void   setSub5Internal(double v)       { this.sub5Internal = v; }
    public double getSub5External()               { return sub5External; }
    public void   setSub5External(double v)       { this.sub5External = v; }

    // Subject 6
    public String getSubject6Name()               { return subject6Name; }
    public void   setSubject6Name(String v)       { this.subject6Name = v; }
    public double getSub6Internal()               { return sub6Internal; }
    public void   setSub6Internal(double v)       { this.sub6Internal = v; }
    public double getSub6External()               { return sub6External; }
    public void   setSub6External(double v)       { this.sub6External = v; }

    @Override
    public String toString() {
        return "Roll: " + uemRollNo + " | " + name
             + " | Sem " + semester + "-" + section + " | Batch: " + batch;
    }
}
