package algorithm;

import org.junit.Test;

import java.util.*;

public class Question42085172 {
    private static HashSet<String> permutation(String prefix, String str) {
        HashSet<String> permutations = new HashSet<>();
        int n = str.length();
        if (n == 0) {
            permutations.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permutations.addAll(permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n)));
//                permutations.addAll(permutation(str.charAt(i) + prefix, str.substring(0, i) + str.substring(i + 1, n)));
            }
        }
        return permutations;
    }

    @Test
    public void paddTest() {
        String header = "**************************BILL***************************";
        int[] qty = new int[]{1, 22};
        String[] O = new String[]{"Fried Raviolli", "Potato Fries with Jalapeno and Cheese Dip"};
        int[] l = new int[]{400, 1800};

        int maxLength = header.length();
        System.out.println(header);
        for (int r = 0; r < 2; r++) {
            String position = String.format("%s X %s", qty[r], O[r]);
            String price = String.valueOf(l[r]);
            System.out.println(String.format("%s%" + (maxLength - position.length()) + "s", position, price));
        }
    }

    @Test
    public void testReplaceallGroup() {
        String ss = "A64=201hghfg70210^A117=rtryxcvbrth^A192=53rtyrty012519.72^A193^A117=rtryxcvbrth^";
        ss = ss.replaceAll("A117=[^&]*?\\^", "A117=GJHK^");
        System.out.println(ss);

        Map<String, List<?>> map = new HashMap<>();
        map.put("nazivK", new ArrayList<>());
        map.put("adresaK", new ArrayList<>());

//        map.replaceAll((s, list) -> list.isEmpty() ? null : list);


        for(Iterator<Map.Entry<String, List<?>>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, List<?>> entry = it.next();
            if(entry.getValue().isEmpty()) {
                it.remove();
            }
        }
        System.out.println(ss);

    }


    private static Set<String> permutation2(String str) {
        Set<String> permutations = new HashSet<>();
        int n = str.length();
        if (n == 2) {
            permutations.add("" + str.charAt(0) + str.charAt(1));
            permutations.add("" + str.charAt(1) + str.charAt(0));
        } else {
            String prefix = str.substring(0, 1);
            Set<String> subset = permutation2(str.substring(1));
            for (String word : subset) {
                for (int i = 0; i < n; i++) {
                    permutations.add(word.substring(0, i) + prefix + word.substring(i));
                }
            }
        }
        return permutations;
    }

    private static Set<char[]> permutationCharArr(char[] str) {
        Set<char[]> permutations = new HashSet<>();
        int n = str.length;
        if (n == 2) {
            permutations.add(new char[]{str[0], str[1]});
            permutations.add(new char[]{str[1], str[0]});
        } else {
            char head = str[0];
            char[] tail = new char[str.length - 1];
            System.arraycopy(str, 1, tail, 0, str.length - 1);
            Set<char[]> subset = permutationCharArr(tail);
            for (char[] word : subset) {
                for (int i = 0; i < n; i++) {
                    char[] newsubset = new char[str.length];
                    System.arraycopy(word, 0, newsubset, 0, i);
                    newsubset[i] = head;
                    System.arraycopy(word, i, newsubset, i + 1, word.length - i);
                    permutations.add(newsubset);
                }
            }
        }
        return permutations;


    }

    @Test
    public void testPermutation() {
        Set<String> perms = permutation("", "1234567890");
        System.out.println(perms.size());
    }

    @Test
    public void testPermutation2() {
        Set<String> perms = permutation2("1234567890");
        System.out.println(perms.size());
    }

    @Test
    public void testPermutationsCharArr() {
        Set<char[]> perms = permutationCharArr("1234567890a".toCharArray());
        System.out.println(perms.size());
    }

}
