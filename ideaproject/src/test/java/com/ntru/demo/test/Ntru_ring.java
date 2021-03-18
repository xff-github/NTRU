package com.ntru.demo.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;

public class Ntru_ring {

    //环多项式赋值
    public static void fuzhi(int q, Random random, int[][] h) {
        for (int i = 0; i < h.length; i++) {
            for (int j = 0; j < h[i].length; j++) {
                h[i][j] = random.nextInt(q);//从区间[0,q-1]选择一个随机数
                //System.out.println("h" + "["+i+"]"+"["+j+"]=" + h[i][j]);
            }
        }
    }

    //多项式乘法运算
    public static int[][] getInts(int n, int q, int[][] h, int[][] s4) {
        int[][] c = new int[1][2 * n - 1];//存放两个多项式系数相乘的结果
        for (int i = 0; i < 1; i++) {
            for (int s = 0; s < 1; s++) {
                for (int t = 0; t < 2 * n - 1; t++) {
                    for (int p = 0; p < n; p++) {
                        for (int j = 0; j < n; j++) {
                            if (t == p + j) {
                                c[i][t] += s4[s][p] * h[i][j] % q;
                                //System.out.println("c" + "["+i+"]"+"["+j+"]=" + c[i][j]);
                            }
                        }
                    }
                }
            }
        }
        return c;
    }

    //合并同类项
    public static void add(int n, int[][] c) {
        int[] d = new int[2 * n - 1];
        for (int s = 0; s < 2 * n - 1; s++) {
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    if (s == i + j) {
                        d[s] = d[s] + c[i][j];
                        d[s] += c[i][j];
                    }
                }
            }
        }
    }

    //取模运算
    public static int[][] mod(int n, int q, int[][] c) {
        int[][] f = new int[1][n];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                f[i][j] = (c[i][j] - c[i][n + j]) % q;
                f[i][j] = f[i][j] % q;

                //System.out.println("f" + "["+i+"]"+"["+j+"]=" + f[i][j]);
            }
            f[i][n - 1] = (c[i][n - 1]) % q;
            f[i][n - 1] = f[i][n - 1] % q;
        }
        return f;
    }

    //高斯采样(多项式)
    public static void Gaussian(int n, int x, int[][] y) {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                if (x <= 2 && x >= -2) {
                    y[i][j] = x;
                    //System.out.println("y" + "["+i+"]"+"["+j+"]=" + y[i][j]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int n = 256;
        int q = 536870912;

        Random random = new Random();

        //定义主公钥
        int[][] h = new int[1][n];
        fuzhi(q, random, h);

        //定义私钥s1 s2 s3 s4
        int[][] s1 = new int[1][n];
        int[][] s2 = new int[1][n];
        int[][] s3 = new int[1][n];
        int[][] s4 = new int[1][n];
        fuzhi(q, random, s1);
        fuzhi(q, random, s2);
        fuzhi(q, random, s3);
        fuzhi(q, random, s4);

        long startTime = System.nanoTime(); //获取开始时间

        //多项式相乘运算
        int[][] c = getInts(n, q, h, s4);

        //合并同类项
        add(n, c);

        //取模运算
        int[][] f = mod(n, q, c);

        //计算可链接标签
        int[][] I = new int[1][n];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                I[i][j] = (s3[i][j] + f[i][j] + s1[i][j]) % q;
                //System.out.println("I" + "["+i+"]"+"["+j+"]=" + I[i][j]);
            }
        }

        //随机选取多项式
        int u = 0;
        int v = 100;
        int x = (int) (Math.sqrt(v) * random.nextGaussian() + u);//x为正态分布选取的随机数.

        //定义一个1行n列的向量y
        int[][] y = new int[1][n];
        Gaussian(n, x, y);

        //定义一个1行n列的向量yy
        int[][] yy = new int[1][n];
        Gaussian(n, x, yy);

        getInts(n, q, yy, h);
        add(n, c);
        int[][] v1 = mod(n, q, c);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                //System.out.println("v1" + "["+i+"]"+"["+j+"]=" + v1[i][j]);
            }
        }

        int[][] v11 = new int[1][n];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                v11[i][j] = (y[i][j] + v1[i][j]) % q;
                //System.out.println("v11" + "[" + i + "]" + "[" + j + "]=" + v11[i][j]);
            }
        }

        Scanner sc = new Scanner(System.in);
        out.println("请您输入消息串:");
        String m = sc.nextLine();
        String ss = m + I + v11;//将参数、消息m、可链接标签进行哈希
        int x1 = ss.hashCode();
        System.out.println(x1);

        int[][] z1 = new int[1][n];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                z1[i][j] = ((s1[i][j] + s3[i][j])*v + y[i][j])%q;
            }
        }

        int[][] z2 = new int[1][n];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                z2[i][j] = ((s2[i][j] + s4[i][j])*v + yy[i][j])%q;
            }
        }

        long endTime = System.nanoTime(); //获取结束时间
        int count2 = 0;
        int count3 = 0;
        for (int i = 0; i < 1000; i++) {
            count2 += (endTime - startTime);
            count3 = count2 / 1000;
        }
        System.out.println("签名生成程序运行时间：" + count3 + "ns"); //输出程序运行时间

        //签名验证

        //签名内容
        File file = new File("d:\\y.txt"); //存放数组数据的文件
        FileWriter out = new FileWriter(file); //文件写入流
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                out.write(y[i][j] + "\t");
            }
            out.write("\r\n");
        }
        out.close();

        file = new File("d:\\I.txt"); //存放数组数据的文件
        out = new FileWriter(file); //文件写入流
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                out.write(I[i][j] + "\t");
            }
            out.write("\r\n");
        }
        out.close();


        file = new File("d:\\z1.txt"); //存放数组数据的文件
        out = new FileWriter(file); //文件写入流
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                out.write(z1[i][j] + "\t");
            }
            out.write("\r\n");
        }
        out.close();
    }


}
