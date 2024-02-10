import java.util.Scanner;
import java.util.regex.Pattern;

public class StrinCalc {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String expression = scanner.nextLine();

        String result = calc(expression);
        System.out.println("Результат: " + "\"" + result + "\"");
    }

    public static String calc(String expression) {
        String[] tokens = expression.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Неверное количество операндов. Введите выражение в формате 'строка оператор число'.");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];
        if (isNumeric(operand1)) {
            throw new IllegalArgumentException("Первым операндом должна быть строка");
        }
        if (isNotString(operand2) && !isNumeric(operand2))
            throw new IllegalArgumentException("Строка должжна быть в кавычках");


        if (operand1.length() > 10 || operand2.length() > 10) {
            throw new IllegalArgumentException("Длина операндов должна быть не более 10 символов.");
        }


        switch (operator) {
            case "+":
                return addStrings(operand1, operand2);
            case "-":
                return subtractStrings(operand1, operand2);
            case "*":
                return multiplyString(operand1, operand2);
            case "/":
                return divideString(operand1, operand2);
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция. Допустимые операции: +, -, *, /");
        }
    }

    private static String addStrings(String operand1, String operand2) {
        if (isNotString(operand2))
            throw new IllegalArgumentException("Строка должжна быть в кавычках");

        operand1 = operand1.replaceAll("\"", "");
        operand2 = operand2.replaceAll("\"", "");

        String result = operand1 + operand2;
        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }
        return result;
    }

    private static String subtractStrings(String operand1, String operand2) {
        if (!operand1.contains(operand2)) {
            return operand1;
        }
        if (isNotString(operand2))
            throw new IllegalArgumentException("Строка должжна быть в кавычках");

        operand1 = operand1.replaceAll("\"", "");
        operand2 = operand2.replaceAll("\"", "");

        String result = operand1.replace(operand2, "");
        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }
        return result;
    }

    private static String multiplyString(String operand1, String operand2) {
        if (isString(operand2)) {
            throw new IllegalArgumentException("Вторым операндом должно быть число");
        }

        operand1 = operand1.replaceAll("\"", "");
        operand2 = operand2.replaceAll("\"", "");

        int multiplier = parseInteger(operand2);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < multiplier; i++) {
            result.append(operand1);
        }
        if (result.length() > 40) {
            result = new StringBuilder(result.substring(0, 40) + "...");
        }
        return result.toString();
    }

    private static String divideString(String operand1, String operand2) {
        if (isString(operand2)) {
            throw new IllegalArgumentException("Вторым операндом должно быть число");
        }

        operand1 = operand1.replaceAll("\"", "");
        operand2 = operand2.replaceAll("\"", "");

        int divisor = parseInteger(operand2);
        int chunkSize = operand1.length() / divisor;
        String result = operand1.substring(0, chunkSize);
        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }
        return result;
    }

    private static int parseInteger(String operand) {
        try {
            int value = Integer.parseInt(operand);
            if (value < 1 || value > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа.");
        }

    }

    public static boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches() && !isString(strNum);
    }

    public static boolean isString(String input) {
        Pattern pattern = Pattern.compile("^\".*\"$");
        return pattern.matcher(input).matches();
    }

    private static boolean isNotString(String input) {
        return !isString(input);
    }

}
