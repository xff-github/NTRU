package com.ntru.demo.test;


import java.util.Random;

public class NtruTest {
    public static void main(String[] args) {
        int γ;
        int λ;
        int k = 55;
        int m = 512;
        int q = 100000;
        //int[] Rq = new int[(int) (q-1)];//Rq的系数
        int[] a = new int[k * m];//向量a的系数
        int[] b = new int[k * m];//向量b的系数
        int[] ri = new int[m];
        int c[][] =new int[m][m];//存放多项式乘法的中间结果
        int d[] =new int[1023];//存放多项式乘法的中间结果
        int e[] =new int[m];//存放多项式乘法的最终结果
        //ri的选取
        Random random = new Random();
        random.nextInt(q);//从区间[0,q-1]选择一个随机数

        for (int i : ri) {
            i =  random.nextInt(q);
            System.out.println(i);
        }
        //a向量和b向量的取值范围也为0到q-1;
        for (int i : a) {
            i = random.nextInt(q);
        }
        for (int n = 0; n < b.length; n++) {
            n = random.nextInt(q);
        }

        /**
         * 多项式乘法的子函数
         */
        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < m - 1; j++) {
                c[i][j] += b[i]* ri[j];//c[i][j]存放的是b中x的系数和ri中x的系数的结果
                System.out.println(c[i][j]);
            }
        }

        for (int s = 0; s < 1022; s++) {
            for (int i = 0; i < m - 1; i++) {
                for (int j = 0; j < m-1 ; j++) {
                    if (s == i + j){
                        d[s] += c[i][j];//d[s]中存放的是ri,b多项式想乘以后生后才能多项式d中xs的系数
                }
            }
        }
            for (int i = 0; i < m - 2; i++) {
                e[i] = d[i] - d[m+i];
            }

        }

        
        //普通正态随机分布
        //参数 u 均值
        //参数 v 方差
        int u = 0;
        int v = 10;
        int x = (int) (Math.sqrt(v) * random.nextGaussian() + u);//x的取值范围是[-10,10],x为正态分布选取的随机数.
        //x>=-2&&x<=2;

        //定义一个k行m列的向量ei
        int[][] ei = new int[k][m];
        //将二维数组遍历
        for (int[] ints : ei) {
            for (int i : ints) {
                if (x>=-2&&x<=2){
                    i=x;
                }
            }
        }

    }
}
