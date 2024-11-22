import java.util.Scanner;
import java.util.Random;

public class MasterMind{
    public static void main(String[] args){
        Scanner scann = new Scanner(System.in);

        System.out.println("Witaj w grze MasterMind!");
        String username = null;
        String difficultyName = null;
        int difficultyLevel = 0;
        boolean isUsernameValid = false;
        boolean isDifficultyValid = false;
        int maxAttempts = Integer.MAX_VALUE;
        int attempts = 0;

        while (!isUsernameValid) {
            System.out.print("Podaj swoje imię (musi być dłuższe niż 3 znaki): ");
            username = scann.nextLine().trim();//without white spaces

            if (username.length() > 3) {
                System.out.println("Cześć, " + username + "!");
                isUsernameValid = true;
            } else {
                System.out.println("Imię jest za krótkie. Spróbuj ponownie.");
            }
        }

        System.out.println("Zgadnij 4 cyfrowy kod składający się z liczb od 1 do 6.");
        System.out.println("");
        System.out.println("Wybierz poziom trudności:");
        System.out.println("Poziom łatwy - nieograniczona liczba strzałów - naciśnij '1'");
        System.out.println("Poziom średni - 8 możliwych strzałów - naciśnij '2'");
        System.out.println("Poziom trudny - 5 możliwych strzałów - naciśnij '3'");

        while (!isDifficultyValid) {
            difficultyLevel = scann.nextInt();
            scann.nextLine();

            switch (difficultyLevel) {
                case 1:
                    difficultyName = "Łatwy";
                    maxAttempts = Integer.MAX_VALUE;
                    break;
                case 2:
                    difficultyName = "Średni";
                    maxAttempts = 8;
                    break;
                case 3:
                    difficultyName = "Trudny";
                    maxAttempts = 5;
                    break;
                default:
                    System.out.println("Podaj właściwy numer poziomu trudności. Spróbuj ponownie.");
                    continue;
            }
            System.out.println("Wybrałeś poziom: " + difficultyName);
            isDifficultyValid = true;
        }

        System.out.println("");

        Random rand = new Random();
        int codeLength = 4;
        int maxDigit = 6;
        int[] secretCode = new int[codeLength];
        int[] userCode = new int[codeLength];
        boolean guessed = false;

        for(int i = 0; i < codeLength; i++){
            secretCode[i] = rand.nextInt(maxDigit) + 1;
        }

        while(!guessed && attempts < maxAttempts) {
            System.out.println("Wprowadź swoją próbę: ");
            String guess = scann.nextLine();
            attempts++;
            try {
                if(guess.length() != codeLength){
                    throw new NumberFormatException();
                }
                for(int i = 0; i < codeLength; i++){
                    userCode[i] = Character.getNumericValue(guess.charAt(i));
                    if(userCode[i] < 1 || userCode[i] > maxDigit){
                        throw new NumberFormatException();
                    }
                }
            } catch(NumberFormatException e){
                System.out.println("Wprowadź liczbę od 1 do " + maxDigit + ".");
            }
            int identicalButNotInPlace = 0;
            int IdenticalAndInPlace = 0;
            boolean[] countedInUserCode = new boolean[codeLength];
            boolean[] countedInSecretCode = new boolean[codeLength];

            for (int i = 0; i < codeLength; i++){
                if (userCode[i] == secretCode[i]){
                    IdenticalAndInPlace++;
                    countedInUserCode[i] = true;
                    countedInSecretCode[i] = true;
                }
            }

            for (int i = 0; i < codeLength; i++){
                if(countedInUserCode[i] != true){
                    for(int j = 0; j < codeLength; j++) {
                        if(countedInSecretCode[j] == false && userCode[i] == secretCode[j]){
                            identicalButNotInPlace++;
                            countedInUserCode[i] = true;
                            countedInSecretCode[j] = true;
                        }
                    }
                }
            }
            if (IdenticalAndInPlace == codeLength){
                System.out.println("Gratulacje " + username + "! - kod został odgadnięty!");
                guessed = true;
            } else {
                System.out.println("Poprawne cyfry na poprawnej pozycji: " + IdenticalAndInPlace);
                System.out.println("Poprawne cyfry na niepoprawnych pozycjach: " + identicalButNotInPlace);
                if (attempts >= maxAttempts) {
                    System.out.println("Przekroczyłeś maksymalną liczbę prób!");
                    break;
                }
            }
        }
        scann.close();
    }
}