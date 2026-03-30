public class Validator {
    private static final String UEM_ROLL_PATTERN =
            "BTECH/CSE/\\d{4}/\\d{3}";
    private static final String UEM_ENROLL_PATTERN =
            "UEM/\\d{4}/CSE/\\d{5}";

    private static final String DATE_PATTERN  = "\\d{2}/\\d{2}/\\d{4}";
    private static final String PHONE_PATTERN = "[6-9]\\d{9}";
    private static final String EMAIL_PATTERN = "^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final String BATCH_PATTERN = "\\d{4}-\\d{4}";
    public static boolean isNonEmpty(String v) {
        return v != null && !v.trim().isEmpty();
    }

    public static boolean isValidName(String name) {
        return isNonEmpty(name) && name.matches("[A-Za-z .\\-]+") && name.trim().length() >= 2;
    }
    public static boolean isValidUemRollNo(String roll) {
        if (!isNonEmpty(roll)) return false;
        return roll.trim().toUpperCase().matches(UEM_ROLL_PATTERN.toUpperCase());
    }
    public static boolean isValidEnrollmentNo(String enroll) {
        if (!isNonEmpty(enroll)) return false;
        return enroll.trim().toUpperCase().matches(UEM_ENROLL_PATTERN.toUpperCase());
    }

    public static boolean isValidSemester(int sem) {
        return sem >= 1 && sem <= 8;
    }
    public static boolean isValidSection(String sec) {
        return isNonEmpty(sec) && sec.matches("[A-Za-z]");
    }
    public static boolean isValidBatch(String batch) {
        return isNonEmpty(batch) && batch.matches(BATCH_PATTERN);
    }
    public static boolean isValidDate(String date) {
        if (!isNonEmpty(date) || !date.matches(DATE_PATTERN)) return false;
        String[] p = date.split("/");
        int day = Integer.parseInt(p[0]), month = Integer.parseInt(p[1]), year = Integer.parseInt(p[2]);
        return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1995 && year <= 2008;
    }
    public static boolean isValidPhone(String phone) {
        return isNonEmpty(phone) && phone.matches(PHONE_PATTERN);
    }
    public static boolean isValidEmail(String email) {
        return isNonEmpty(email) && email.matches(EMAIL_PATTERN);
    }
    public static boolean isValidInternalMarks(double marks) {
        return marks >= 0.0 && marks <= 40.0;
    }
    public static boolean isValidExternalMarks(double marks) {
        return marks >= 0.0 && marks <= 60.0;
    }
    public static boolean isValidAttendance(double att) {
        return att >= 0.0 && att <= 100.0;
    }
    public static boolean isValidGender(String g) {
        return isNonEmpty(g) && (g.equalsIgnoreCase("Male")
                              || g.equalsIgnoreCase("Female")
                              || g.equalsIgnoreCase("Other"));
    }
    public static boolean isValidBloodGroup(String bg) {
        if (!isNonEmpty(bg)) return false;
        for (String v : new String[]{"A+","A-","B+","B-","O+","O-","AB+","AB-"})
            if (v.equalsIgnoreCase(bg.trim())) return true;
        return false;
    }
    public static boolean isValidHostelStatus(String hs) {
        return isNonEmpty(hs) && (hs.equalsIgnoreCase("Hosteller")
                               || hs.equalsIgnoreCase("Day Scholar"));
    }
    public static boolean isValidRelation(String rel) {
        return isNonEmpty(rel) && (rel.equalsIgnoreCase("Father")
                                || rel.equalsIgnoreCase("Mother")
                                || rel.equalsIgnoreCase("Guardian"));
    }
    public static boolean isValidCGPA(String cgpa) {
        if (!isNonEmpty(cgpa)) return false;
        if (cgpa.equalsIgnoreCase("N/A")) return true;
        try {
            double v = Double.parseDouble(cgpa);
            return v >= 0.0 && v <= 10.0;
        } catch (NumberFormatException e) { return false; }
    }
}
