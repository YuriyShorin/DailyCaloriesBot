package coursework.bot.dailycaloriesbot.utills;

public class NumbersUtil {

    public static Double parseDouble(String numberToParse) {
        if (numberToParse.contains(",")) {
            numberToParse = numberToParse.replace(",", ".");
        }
        double number;
        try {
            number = Double.parseDouble(numberToParse);
        } catch (NumberFormatException e) {
            return null;
        }
        return number;
    }

    public static Integer parseInt(String numberToParse) {
        int number;
        try {
            number = Integer.parseInt(numberToParse);
        } catch (NumberFormatException e) {
            return null;
        }
        return number;
    }
}
