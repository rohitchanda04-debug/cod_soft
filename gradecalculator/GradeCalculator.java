import java.util.Scanner;
public class GradeCalculator {
    private static final double MIN_MARK = 0.0;
    private static final double MAX_MARK = 100.0;
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        printBanner();
        int numberOfSubjects = getNumberOfSubjects(scanner);
        String[] subjectNames = new String[numberOfSubjects];
        double[] marks        = new double[numberOfSubjects];

        System.out.println();
        System.out.println("Enter the name and marks (out of 100) for each subject:");
        System.out.println("─".repeat(50));

        for (int i = 0; i < numberOfSubjects; i++) 
        {
            System.out.print("  Subject " + (i + 1) + " name  : ");
            subjectNames[i] = scanner.nextLine().trim();
            marks[i] = getValidMark(scanner, subjectNames[i]);
        }
        double totalMarks = calculateTotal(marks);
        double averagePercentage = calculateAverage(totalMarks, numberOfSubjects);
        String grade   = determineGrade(averagePercentage);
        String remark  = getRemark(averagePercentage);
        displayResults(subjectNames, marks, numberOfSubjects,
                       totalMarks, averagePercentage, grade, remark);

        scanner.close();
    }
    private static int getNumberOfSubjects(Scanner scanner) 
    /**
     * @param args
     */
    {
        int n = 0;
        while (true) {
            System.out.print("Enter the number of subjects: ");
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                scanner.nextLine();
                if (n >= 1) {
                    break;
                } else {
                    System.out.println("  ✗  Please enter at least 1 subject.\n");
                }
            } else {
                System.out.println("  ✗  Invalid input. Please enter a whole number.\n");
                scanner.nextLine();
            }
        }
        return n;
    }
    private static double getValidMark(Scanner scanner, String subjectName) {
        double mark = 0;
        while (true) {
            System.out.print("  Marks for " + subjectName + "      : ");
            if (scanner.hasNextDouble()) {
                mark = scanner.nextDouble();
                scanner.nextLine();
                if (mark >= MIN_MARK && mark <= MAX_MARK) {
                    break;
                } else {
                    System.out.printf(
                        "  ✗  Marks must be between %.0f and %.0f. Try again.%n%n",
                        MIN_MARK, MAX_MARK
                    );
                }
            } else {
                System.out.println("  ✗  Invalid input. Please enter a number.\n");
                scanner.nextLine();
            }
    /**
     * @param marks
     * @return
     */
        }
        return mark;
    }
    private static double calculateTotal(double[] marks) {
        double total = 0;
        for (double mark : marks) {
            total += mark;
        }
        return total;
    }
    private static double calculateAverage(double totalMarks, int numberOfSubjects) {
        return totalMarks / numberOfSubjects;
    }
    private static String determineGrade(double average) {
        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B+";
        } else if (average >= 60) {
            return "B";
        } else if (average >= 50) {
            return "C";
        } else if (average >= 40) {
            return "D";
        } else {
            return "F";
        }
    }
    private static String getRemark(double average) {
        if (average >= 90) return "Outstanding!";
        if (average >= 80) return "Excellent!";
        if (average >= 70) return "Very Good";
        if (average >= 60) return "Good";
        if (average >= 50) return "Average";
        if (average >= 40) return "Below Average";
        return "Fail – needs improvement";
    }
    private static void printBanner() {
        System.out.println("═".repeat(52));
        System.out.println("        STUDENT GRADE CALCULATOR");
        System.out.println("═".repeat(52));
        System.out.println();
    }
    private static void displayResults(
            String[] subjectNames,
            double[] marks,
            int numberOfSubjects,
            double totalMarks,
            double averagePercentage,
            String grade,
            String remark) {
        System.out.println();
        System.out.println("═".repeat(52));
        System.out.println("                  RESULT CARD");
        System.out.println("═".repeat(52));
        System.out.printf("  %-22s  %10s  %10s%n", "Subject", "Marks", "Out of");
        System.out.println("  " + "─".repeat(48));
        for (int i = 0; i < numberOfSubjects; i++) {
            System.out.printf(
                "  %-22s  %10.2f  %10.0f%n",
                subjectNames[i], marks[i], MAX_MARK
            );
        }
        System.out.println("  " + "─".repeat(48));
        double maxPossibleTotal = numberOfSubjects * MAX_MARK;
        System.out.printf(
            "  %-22s  %10.2f  %10.0f%n",
            "TOTAL", totalMarks, maxPossibleTotal
        );
        System.out.println("═".repeat(52));
        System.out.printf("  Average Percentage : %8.2f %%%n", averagePercentage);
        System.out.printf("  Letter Grade       : %8s%n",   grade);
        System.out.printf("  Remark             : %8s%n",   remark);

        System.out.println("═".repeat(52));
        System.out.println();
    }
}