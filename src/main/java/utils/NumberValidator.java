package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidator {

    public boolean validateNumber(String str) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
