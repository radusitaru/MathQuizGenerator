package BackEnd;

import java.util.Random;

public class GenerateExpression {

    /**
     * --------------------------------------------------------------------------------
     * Declaring & initializing global variables and arrays
     * --------------------------------------------------------------------------------
     */
    static Random randomNr = new Random();
    static int[] nrArray = new int[5];
    static String[] opArray = new String[4];
    static String[] exArray = new String[9];

    /**
     * -------------------------------------------------------------------------------------
     * Generating array with numbers based on nrLimit, which is the max number in the array.
     * -------------------------------------------------------------------------------------
     */
    static void nrArray(int nrLimit) {
        for (int currentNr = 0; currentNr < nrArray.length; currentNr++) {
            nrArray[currentNr] = randomNr.nextInt(nrLimit) + 1;
        }
    }

    /**
     * --------------------------------------------------------------------------------
     * Generating a random array based on two operators
     * --------------------------------------------------------------------------------
     */
    static void twoOpArray(String op1, String op2) {
        for (int currentNr = 0; currentNr < opArray.length; currentNr++) {
            int operator = randomNr.nextInt(2);
            if (operator == 1) {
                opArray[currentNr] = op1;
            } else {
                opArray[currentNr] = op2;
            }
        }
    }

    /**
     * --------------------------------------------------------------------------------
     * Generating a random array based on four operators
     * --------------------------------------------------------------------------------
     */
    static void fourOpArray(String op1, String op2, String op3, String op4) {
        for (int currentNr = 0; currentNr < opArray.length; currentNr++) {
            switch (randomNr.nextInt(4)) {
                case 0:
                    opArray[currentNr] = op1;
                    break;
                case 1:
                    opArray[currentNr] = op2;
                    break;
                case 2:
                    opArray[currentNr] = op3;
                    break;
                case 3:
                    opArray[currentNr] = op4;
                    break;
                default:
                    opArray[currentNr] = "ERROR";
            }

        }
    }

    /**
     * ----------------------------------------------------------------------------------------------
     * Generating an array in which the operators and numbers generated earlier are randomly ordered
     * ----------------------------------------------------------------------------------------------
     */
    static void exArray() {
        int nrCounter = 0;
        int opCounter = 0;
        for (int currentNr = 0; currentNr < exArray.length; currentNr++) {

            if (currentNr % 2 == 0) {
                exArray[currentNr] = String.valueOf(nrArray[nrCounter]);
                nrCounter++;
            } else {
                exArray[currentNr] = opArray[opCounter];
                opCounter++;
            }
        }
    }

    /**
     * --------------------------------------------------------------------------------------
     * Convert expression to String
     * --------------------------------------------------------------------------------------
     */
    static String printStringArray(String[] exArray) {
        String mathString = exArray[0] + exArray[1] + exArray[2] + exArray[3] + exArray[4] + exArray[5] + exArray[6] + exArray[7] + exArray[8];
        return mathString;
    }

}
