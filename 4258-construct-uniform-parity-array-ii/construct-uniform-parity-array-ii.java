class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = Integer.MAX_VALUE;
        boolean allEven = true;

        for (int x : nums1) {
            min = Math.min(min, x);
            if ((x & 1) == 1) allEven = false;
        }

        return allEven || (min & 1) == 1;
    }
}