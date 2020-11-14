package examsystem.exsys.Views.ViewComponents;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utils {
    final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    final Random rand = new Random();

    public Utils() {
    }

    public String generateEnterExamCode() {
        int i = 6;
        String uid = "";
        while (i-- > 0) {
            uid += alpha.charAt(rand.nextInt(35));
        }
        return uid;
    }
}


