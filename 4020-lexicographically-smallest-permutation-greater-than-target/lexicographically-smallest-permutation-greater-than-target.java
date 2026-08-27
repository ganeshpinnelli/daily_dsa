class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] cnt = new int[26];

        for (char c : s.toCharArray())
            cnt[c - 'a']++;

        char[] a = target.toCharArray();

        if (dfs(a, 0, cnt))
            return new String(a);

        return "";
    }

    private boolean dfs(char[] a, int i, int[] cnt) {
        if (i == a.length)
            return false;

        int x = a[i] - 'a';

        // Keep the same character
        if (cnt[x] > 0) {
            cnt[x]--;
            if (dfs(a, i + 1, cnt))
                return true;
            cnt[x]++;
        }

        // Choose the smallest greater character
        for (int c = x + 1; c < 26; c++) {
            if (cnt[c] > 0) {
                cnt[c]--;
                a[i] = (char)('a' + c);

                // Fill the rest in sorted order
                int k = i + 1;
                for (int j = 0; j < 26; j++)
                    while (cnt[j]-- > 0)
                        a[k++] = (char)('a' + j);

                return true;
            }
        }

        return false;
    }
}