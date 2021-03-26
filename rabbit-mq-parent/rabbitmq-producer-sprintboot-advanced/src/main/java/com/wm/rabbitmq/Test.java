package com.wm.rabbitmq;

/**
 * @author wangm
 * @title: Test
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2423:24
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        String[] ssd = new String[1];
        ssd[0] = "a";
//        System.out.println(test.threeSumClosest());
    }
    public int threeSumClosest(int[] nums, int target) {
        int result = nums[0] + nums[1] + nums[2];
        int sum = nums[0] + nums[1] + nums[2];

        int min = sum > target ? sum - target: target-sum;
        int mins = sum > target ? sum - target: target-sum;
        for(int i = 0;i<nums.length - 2; i++){
            for(int j = 1; j < nums.length -1;j++) {
                for (int w = 3; w< nums.length;w++){
                    sum = nums[i] + nums[j] + nums[w];
                    mins = sum > target ? sum - target: target-sum;
                    if(mins < min){
                        result = sum;
                        min = mins;
                    }
                }
            }
        }

        return result;
    }

}
