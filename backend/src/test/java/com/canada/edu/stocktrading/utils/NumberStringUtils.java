package com.canada.edu.stocktrading.utils;

import org.springframework.stereotype.Component;

@Component
public class NumberStringUtils {
    public static String generateRandomString(int limit,boolean isWithNumber, boolean isWithUpperCase, boolean isWithLowerCase,boolean hasWhiteSpace) {
        String NUMBERS = "0123456789";
        String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LOWERCASE =  "abcdefghijklmnopqrstuvxyz";

        StringBuilder requiredType = new StringBuilder();

        if(isWithNumber) {
            requiredType.append(NUMBERS);
        }

        if(isWithUpperCase) {
            requiredType.append(UPPERCASE);
        }

        if(isWithLowerCase) {
            requiredType.append(LOWERCASE);
        }

        String prototypeString = requiredType.toString();

        StringBuilder generatedSb = new StringBuilder();

        for(int i = 0; i < limit; i++) {
            if(hasWhiteSpace && i==limit/2) {
                generatedSb.append(" ");
            }
            int index = (int)prototypeString.length()*((int)(Math.random()*10))/10;
            generatedSb.append(prototypeString.charAt(index));
        }

        return generatedSb.toString();
    }

    public static Integer generateRandomNumber(Integer min, Integer max) {
        return (int) (Math.random()*(max-min)+min);
    }
}
