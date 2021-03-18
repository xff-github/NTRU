package com.ntru.demo.test;

import java.util.Random;

public class Matrix {
    public static void main(String[] args) {

        int k = 8;
        int n = 256;
        int m = 1536;
        int q = 1073741824;

        Random random = new Random();
        random.nextInt(q);//从区间[0,q-1]选择一个随机数

        int[][] a = new int[n][m];//自己定义矩阵a
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = random.nextInt(q);
                //System.out.println("a"+"["+i+"]"+"["+j+"]="+a[i][j]+"  ");
            }
        }

        int[][] b = new int[m][n];//自己定义矩阵b
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = random.nextInt(q);
                //System.out.println("b"+"["+i+"]"+"["+j+"]="+b[i][j]+"  ");
            }
        }

        int[][] c = new int[n][n];//存放矩阵相乘后的结果

        long startTime = System.currentTimeMillis();

        for(k = 0; k < a.length; k++)
            {
                for(n = 0; n < b[0].length; n++)
                {
                    int temp = 0;
                    for(m = 0; m < b.length; m++)
                    {
                        temp += a[n][m] * b[m][n];

                    }
                c[k][n] = temp;

            }
        }
        //System.out.println("矩阵相乘后结果为");

        for(int i = 0; i<a.length; i++)
        {
            for(int j = 0; j<b[0].length; j++)
            {
                //System.out.print(c[i][j]%q+"\t");
            }
            //System.out.println();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("矩阵乘法：" + (endTime - startTime) + "ms");
    }
}
