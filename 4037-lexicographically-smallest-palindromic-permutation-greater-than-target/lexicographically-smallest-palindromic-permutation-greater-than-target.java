class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int half = n / 2;

        int[] freq = new int[26];

        // Count characters
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check whether palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Characters available for left half
        int[] cnt = new int[26];

        for (int i = 0; i < 26; i++) {
            cnt[i] = freq[i] / 2;
        }

        char[] left = new char[half];

        /*
         * Try to make left half equal to target's left half.
         */
        int i = 0;

        while (i < half) {

            int c = target.charAt(i) - 'a';

            if (cnt[c] == 0) {
                break;
            }

            left[i] = target.charAt(i);
            cnt[c]--;
            i++;
        }

        /*
         * Case 1:
         * We matched the complete left half.
         */
        if (i == half) {

            String candidate = build(left, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Case 2:
         * Find the rightmost position that can be increased.
         *
         * Increasing the rightmost possible position gives
         * the smallest palindrome greater than target.
         */
        for (int pos = i; pos >= 0; pos--) {

            /*
             * Restore the character at pos if it was already
             * taken while matching the target.
             */
            if (pos < i) {
                cnt[left[pos] - 'a']++;
            }

            int targetChar = target.charAt(pos) - 'a';

            /*
             * Try the smallest character greater than target[pos].
             */
            for (int c = targetChar + 1; c < 26; c++) {

                if (cnt[c] == 0) {
                    continue;
                }

                left[pos] = (char) ('a' + c);
                cnt[c]--;

                /*
                 * Fill everything after pos with the smallest
                 * possible characters.
                 */
                int k = pos + 1;

                for (int x = 0; x < 26; x++) {
                    while (cnt[x] > 0) {
                        left[k++] = (char) ('a' + x);
                        cnt[x]--;
                    }
                }

                return build(left, middle);
            }
        }

        return "";
    }

    private String build(char[] left, char middle) {

        StringBuilder sb = new StringBuilder(left.length * 2 + 1);

        // Left half
        for (char c : left) {
            sb.append(c);
        }

        // Middle
        if (middle != 0) {
            sb.append(middle);
        }

        // Right half
        for (int i = left.length - 1; i >= 0; i--) {
            sb.append(left[i]);
        }

        return sb.toString();
    }
}