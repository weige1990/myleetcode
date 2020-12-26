package com.test.redis;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple RedisOperateStarter.
 */
public class RedisOperateStarterTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {


        System.out.println(-5+8*6);
        System.out.println((55+9)%9);
        System.out.println(20+-3*5 /8);
        System.out.println(5+15/3*2-8%3);
    }

    @Test
    public void test2()
    {
        int num=0;


       for(int i=1;i<=4;i++)
       {
           for(int j=1;j<=4;j++){

               for(int k=1;k<=4;k++){
                   if(k!=i&&k!=j&&i!=j)
                   {

                   num++;
                   }

               }
           }

       }
        System.out.println(num);
    }


        @Test
        public void test3() {

            int devide =19/7;

            int result= 19-(devide*7);
            System.out.println(result);
        }
        //25 16+8+1=11001

    @Test
    public void test4() {

        int n =8;


        System.out.println((n*(n+1)/2)*(1<<(n-1)));
    }




}
