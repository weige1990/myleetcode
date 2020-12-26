package com.test.redis.operate;

public class TestNumSerial {




    private static int  getFromCharSerial(int n){
    int temp=0;
    int count=0;
    outter:
    for(int i=1;i<=n;i++)
    {
        inner:
        for(int j=1;j<=i;j++)
        {
            temp=i;
            count++;
            if(count==n)
            {
                break outter;
            }

        }

    }


       return temp;
    }

    /*public static int climbOnece(int nth ,int costOfI)
    {

    }

    public static int climbAll(int ... cost)
    {
        int price=0;
        for (int i=0;i<=cost.length-1;i++)
        {

        climbOnece(i,cost[i]);


        }
    }
    */


    public static void main(String[] args) {

        System.out.println(getFromCharSerial(3));
        System.out.println(getFromCharSerial(5));
        System.out.println(getFromCharSerial(16));

    }
}
