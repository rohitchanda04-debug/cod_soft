import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
public class GuessingGame {
    static final int MIN_NUMBER     = 1;
    static final int MAX_NUMBER     = 100;
    static final int MAX_ATTEMPTS   = 8;
    static int calculatePoints(int attemptsUsed) {
        return Math.max(10, 100 - (attemptsUsed - 1) * 12);
    }
    static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║        NUMBER GUESSING GAME          ║");
        System.out.println("  ║     Guess the number: 1 to 100       ║");
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println();
    }
    static void printRoundHeader(int round, int totalScore) {
        System.out.println();
        System.out.println("  ┌──────────────────────────────────────┐");
        System.out.printf ("  │  Round %-5d           Score: %-6d │%n", round, totalScore);
        System.out.println("  └──────────────────────────────────────┘");
    }
    static void printDivider() {
        System.out.println("  ─────────────────────────────────────────");
    }
    static void printAttemptsBar(int used, int max) {
        System.out.print("  Attempts: [");
        for (int i = 1; i <= max; i++) {
            if (i <= used) System.out.print("■");
            else           System.out.print("□");
        }
        System.out.println("] " + used + "/" + max);
    }
    static class RoundResult {
        int    roundNumber;
        int    secretNumber;
        int    attemptsUsed;
        boolean won;
        int    pointsEarned;
        RoundResult(int r, int s, int a, boolean w, int p) {
            roundNumber  = r;
            secretNumber = s;
            attemptsUsed = a;
            won          = w;
            pointsEarned = p;
        }
    }
    public static void main(String[] args) {
        Scanner  scanner  = new Scanner(System.in);
        Random   random   = new Random();
        ArrayList<RoundResult> history = new ArrayList<>();
        int  totalScore  = 0;
        int  roundNumber = 1;
        int  roundsWon   = 0;
        boolean keepPlaying = true;
        printBanner();
        System.out.println("  Welcome! Guess the secret number between "
                           + MIN_NUMBER + " and " + MAX_NUMBER + ".");
        System.out.println("  You get " + MAX_ATTEMPTS
                           + " attempts per round. Score high by guessing fast!\n");
        while (keepPlaying) {
            printRoundHeader(roundNumber, totalScore);
            int secretNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;

            int     attemptsUsed     = 0;
            boolean guessedCorrectly = false;

            System.out.println("  A number between " + MIN_NUMBER
                               + " and " + MAX_NUMBER + " has been chosen.");
            System.out.println("  You have " + MAX_ATTEMPTS + " attempts. Good luck!\n");
            while (attemptsUsed < MAX_ATTEMPTS) {
                int attemptsLeft = MAX_ATTEMPTS - attemptsUsed;
                printAttemptsBar(attemptsUsed, MAX_ATTEMPTS);
                System.out.println("  Attempts left : " + attemptsLeft);
                System.out.print("  Your guess    : ");
                String input = scanner.nextLine().trim();
                int guess;

                try {
                    guess = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("  ⚠  Please enter a valid whole number.\n");
                    continue;   // don't count as an attempt
                }

                if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                    System.out.println("  ⚠  Number must be between "
                                       + MIN_NUMBER + " and " + MAX_NUMBER + ".\n");
                    continue;   // don't count as an attempt
                }
                attemptsUsed++;
                System.out.println();
                if (guess == secretNumber) {
                    guessedCorrectly = true;
                    int pts = calculatePoints(attemptsUsed);
                    totalScore += pts;
                    roundsWon++;
                    System.out.println("  ✔  CORRECT! The number was " + secretNumber + ".");
                    System.out.println("  Guessed in " + attemptsUsed
                                       + " attempt(s).  +" + pts + " points!");
                    history.add(new RoundResult(roundNumber, secretNumber,
                                                attemptsUsed, true, pts));
                    break;

                } else if (guess < secretNumber) {
                    System.out.println("  ↑  Too LOW!  Try a higher number.");
                    if (attemptsLeft - 1 <= MAX_ATTEMPTS / 2) {
                        System.out.println("     (Hint: the number is between "
                                           + (guess + 1) + " and " + MAX_NUMBER + ")");
                    }

                } else {
                    System.out.println("  ↓  Too HIGH! Try a lower number.");

                    if (attemptsLeft - 1 <= MAX_ATTEMPTS / 2) {
                        System.out.println("     (Hint: the number is between "
                                           + MIN_NUMBER + " and " + (guess - 1) + ")");
                    }
                }
                System.out.println();
            }
            printDivider();
            if (!guessedCorrectly) {
                System.out.println("  ✘  Out of attempts! The number was " + secretNumber + ".");
                history.add(new RoundResult(roundNumber, secretNumber,
                                            MAX_ATTEMPTS, false, 0));
            }
            System.out.println();
            System.out.printf("  Total Score : %d%n", totalScore);
            System.out.printf("  Rounds Won  : %d / %d%n", roundsWon, roundNumber);
            printDivider();
            System.out.print("\n  Play another round? (yes / no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("y")) {
                roundNumber++;
                keepPlaying = true;
            } else {
                keepPlaying = false;
            }
        }
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║             FINAL RESULTS            ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.printf ("  ║  Rounds Played : %-20d║%n", roundNumber);
        System.out.printf ("  ║  Rounds Won    : %-20d║%n", roundsWon);
        System.out.printf ("  ║  Rounds Lost   : %-20d║%n", roundNumber - roundsWon);
        System.out.printf ("  ║  Final Score   : %-20d║%n", totalScore);
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║  Round-by-Round Breakdown            ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        for (RoundResult r : history) {
            String status  = r.won ? "WON " : "LOST";
            String attempts = r.won ? r.attemptsUsed + " guess(es)" : "no guesses left";
            System.out.printf("  ║  Round %-2d │ %-4s │ Ans: %-3d │ %-12s║%n",
                              r.roundNumber, status, r.secretNumber, attempts);
        }

        System.out.println("  ╠══════════════════════════════════════╣");
        String rating;
        double winRate = (roundNumber > 0) ? (double) roundsWon / roundNumber * 100 : 0;
        if      (winRate == 100) rating = "Perfect - Undefeated!";
        else if (winRate >= 75)  rating = "Excellent performance!";
        else if (winRate >= 50)  rating = "Good effort!";
        else if (winRate >= 25)  rating = "Keep practicing!";
        else                     rating = "Better luck next time!";
        System.out.printf ("  ║  Win Rate : %.0f%% - %-22s║%n", winRate, rating);
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println("\n  Thanks for playing! Goodbye.\n");

        scanner.close();
    }
}
