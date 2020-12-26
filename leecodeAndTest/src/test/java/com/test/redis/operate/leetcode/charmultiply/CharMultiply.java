package com.test.redis.operate.leetcode.charmultiply;

import org.testng.annotations.Test;

import java.util.Arrays;

public class CharMultiply {


    @Test
    public void testStr() {
        System.out.println(NumArray.multiply(new NumArray("2925"),
                new NumArray("4787")).toString());

    }
/*
   "498828660196"
           "840477629533"*/
/*"2925"
        "4787"*/

}


/**
 * 字符串数字
 */
class NumArray {

    private int[] values;//用来存储10进制数值的数组。"123"->[3][2][1] ,索引为0处是3

    private int basicTen = 0;//标记数是左移几位,不能被pulic 修改,只供计算的时候private 修改。value=[3][2][1] ，basicTen=2，那么 这个数NumArray
//    指代的值其实是12300

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public void setNum(int index, int num) {
        this.values[index] = num;

    }

    public int getBasicTen() {
        return basicTen;
    }

    //位移数,不允许对外被调用。仅供在计算中被设置值
    private void setBasicTen(int basicTen) {
        this.basicTen = basicTen;

    }


    //把数字字符串转为NumArray
    public NumArray(String str) {
        char[] chars = str.toCharArray();

        int startIndex = 0;
        for (int i = 0; i <= chars.length - 1; i++) {

            if (chars[i] - '0' > 0 && chars[i] - '0' <= '9' - '0') {
                startIndex = i;
                break;
            }
        }
        if (startIndex == str.length() - 1 && chars[startIndex] == '0') {
            this.values = new int[1];
            return;
        } else {

            this.values = new int[str.length() - startIndex];
            for (int j = str.length() - 1; j >= startIndex; j--) {
                values[values.length - 1 - j] = chars[j - startIndex] - '0';

            }
        }


    }

    @Override
    /**
     * 这个必须被覆盖,题目要用
     */
    public String toString() {
        StringBuilder builder = null;

        if (values != null && values.length > 0) {

            builder = new StringBuilder(values.length);
            int startIndex = 0;
            for (int i = values.length - 1; i >= 0; i--) {
                if (values[i] != 0) {
                    startIndex = i;
                    break;
                }
            }
            for (int j = startIndex; j >= 0; j--) {
                builder.append(values[j]);
            }

            for (int i = 0; i < basicTen; i++) {
                builder.append(0);

            }


        } else {
            builder = new StringBuilder("0");
        }
        return builder.toString();
    }

    /**
     * 把数组转成NumArray
     *
     * @param values
     */
    public NumArray(int[] values) {
        this.values = values;
    }

    /**
     * 两数想乘得到新的NumArray
     *
     * @param arr1
     * @param arr2
     */
    public static NumArray multiply(NumArray arr1, NumArray arr2) {


        NumArray result = new NumArray(new int[arr1.getValues().length + arr2.getValues().length]);
        NumArray temp = null;
        int valueTemp = 0;
        int arr2ElementTemp = 0;
        int multiplyOfChildren = 0;//两个一位数想乘得到的数的一个缓存变量
        NumArray[] numArrays = new NumArray[arr2.getValues().length];


        for (int arr2Index = 0; arr2Index <= arr2.getValues().length - 1; arr2Index++)
        //1辗转想乘arr2.length次 得到arr2.length个NumArray元素的数组numArrays
        {
            valueTemp = arr2.getValues()[arr2Index];
            temp = new NumArray(new int[arr1.getValues().length + 1]);
            temp.setBasicTen(arr2Index);
            arr2ElementTemp = arr2.getValues()[arr2Index];
            int last = 0;
            for (int j = 0; j <= temp.getValues().length - 2; j++) {
                multiplyOfChildren = arr1.getValues()[j] * arr2ElementTemp;
                temp.getValues()[j] = (multiplyOfChildren + last) % 10;
                last = (multiplyOfChildren + last) / 10;

            }
            temp.getValues()[temp.getValues().length - 1] = last;
            numArrays[arr2Index] = temp;

        }
        //2把numArrays数组拆开逐个数字加法运算,形成result
        for (int k = 0; k <= numArrays.length - 1; k++) {
            //System.out.println("第" + k + "次:" + result + "加" + numArrays[k]);
            result = plus(result, numArrays[k]);
            //System.out.println("结果为" + result);

        }

        return result;

    }

    //两个NumArray相加,得出新的NumArray。因为参数两个数组长度要求设置好,所以这个方法是private的

    /**
     * @param num1 num1是用于存储最后结果的NumArray，其values数组长度应该被设置好，与最后结果长度一致。
     * @param childNum childNum是用于存储被加的NumArray，其values数组长度应该被设置好，不应该大于最后结果NumArray的values的长度。
     */
    private static NumArray plus(NumArray num1, NumArray childNum) {
        //1判断长度,设定num1为较长位(因为已经协定好数组长度进来是正确的,所以这段省略即可)

      /*  if (num1.getValues().length <= childNum.getValues().length + childNum.getBasicTen()) {

            int[] num1Temp = new int[childNum.getValues().length + 2 + childNum.getBasicTen()];//扩容
            for (int j = 0; j <= num1.getValues().length - 1; j++) {
                num1Temp[j] = num1.getValues()[j];

            }
        }*/
        int[] values1 = num1.getValues();//(因为已经协定好数组长度进来是正确的,所以这段省略即可)
        NumArray result = null;
        if (values1[values1.length - 1] != 0) {
            result = new NumArray(new int[num1.getValues().length + 1]);
        } else {
            result = num1;
        }

        //2调整后相加
        int plusTemp = 0;
        int lastTemp = 0;//补多少
        int basicTenTemp = childNum.getBasicTen();
        int lastIndex = basicTenTemp + childNum.getValues().length;
        for (int g = basicTenTemp;
             g <= basicTenTemp + childNum.getValues().length - 1;
             g++) {

            plusTemp = childNum.values[g - basicTenTemp] + result.getValues()[g];
            result.getValues()[g] = (plusTemp + lastTemp) % 10;
            lastTemp = (plusTemp + lastTemp) / 10;


        }
        if (lastTemp != 0) {
            result.getValues()[lastIndex] = (result.getValues()[lastIndex] + lastTemp) % 10;
            plusForward(result, lastIndex);
        }
       /* try {
        }
        catch (Exception e)
        {
            System.out.println("result:"+result);
            System.out.println("lastIndex:"+lastIndex);
            System.out.println("lastTemp:"+lastTemp);
        }*/


        return result;


    }

    /**
     * 在NumArray中其中一位已经超过了10,需要递归进位。[0][10][1] 进位后 [0][0][1+1]即[0][0][2]
     *
     * @param result
     */
    private static void plusForward(NumArray result, int index) {
        if (result.getValues()[index] >= 10 && result.getValues()[index] <= 18) {
            int[] arr = result.getValues();
            int startNum = result.getValues()[index];
            int startIndex = index;
            for (int i = index; startNum >= 10 && i <= arr.length - 1; i++) {
                arr[i] = startNum % 10;
                startNum = startNum / 10 + arr[i + 1];


            }

        }

    }
}