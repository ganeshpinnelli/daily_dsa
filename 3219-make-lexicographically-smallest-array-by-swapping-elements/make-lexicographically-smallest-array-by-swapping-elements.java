class Solution {
    public int[] lexicographicallySmallestArray(int[] nums,int limit){
    int n=nums.length;
    Integer[] idx=new Integer[n];
    for(int i=0;i<n;i++)idx[i]=i;
    Arrays.sort(idx,(a,b)->Integer.compare(nums[a],nums[b]));
    int[] ans=new int[n];
    int i=0;
    while(i<n){
        int j=i+1;
        while(j<n&&nums[idx[j]]-nums[idx[j-1]]<=limit)j++;
        Integer[] group=Arrays.copyOfRange(idx,i,j);
        Arrays.sort(group);
        for(int k=0;k<group.length;k++)ans[group[k]]=nums[idx[i+k]];
        i=j;
    }
    return ans;
}
}