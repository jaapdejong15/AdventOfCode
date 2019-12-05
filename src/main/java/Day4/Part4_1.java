package Day4;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Part4_1 {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        List<Integer> passwords = new ArrayList<>();
        int lowerRange = 152085;
        int upperRange = 670283;
        for (int i = 0; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                for (int k = j; k < 10; k++) {
                    for (int l = k; l < 10; l++) {
                        for (int m = l; m < 10; m++) {
                            for (int n = m; n < 10; n++) {
                                int password = n + 10 * m + 100 * l + 1000 * k + 10000 * j + 100000 * i;
                                if (password < upperRange && password > lowerRange) {
                                    int[] passwordArr = {-1, i, j, k, l, m, n, -1};
                                    if(checkPassword(passwordArr)) {
                                        passwords.add(password);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(passwords.size());
        long endTime = System.nanoTime();
        System.out.println("Solved in " + ((endTime - startTime) / 10000000.0f) + " seconds");
    }

    private static boolean checkPassword(int[] passwordArr) {
        for (int o = 1; o < 6; o++) {
            if (passwordArr[o] == passwordArr[o+1]) {
                return true;
            }
        }
        return false;
    }
}
