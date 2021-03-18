package com.ntru.demo.test;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

        public class Ntru {
        //随机选取环多项式
            public static Random getRandom(int q) {
            Random random = new Random();
            random.nextInt(q);//从区间[0,q-1]选择一个随机数
            return random;
        }

        public static void polyomial(int n, int q, int[][] ri, Random random) {
            //为选取的环多项式赋值
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    ri[i][j] = random.nextInt(q);
                    //System.out.print("ri" + "[" + i + "]" + "[" + j + "]" + "=" + ri[i][j]);
                }
                //System.out.println();
            }
        }

        public static void Gaussian(int n, int x, int[][] s1) {
            //随机选取多项式向量(高斯采样)
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    if (x <= 10 && x >= -10) {
                        s1[i][j] = x;
                        //System.out.print("s1"+"["+i+"]"+"["+j+"]="+s1[i][j]+"  ");
                    }
                    //System.out.println();
                }
            }
        }

        public static void getpk(int n, int q, int[][] ri, int[][] ti, int x1, int[][] y1) {
            //获取环用户公钥
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    y1[i][j] = (ti[i][j] * x1 + ri[i][j]) % q;
                    //System.out.print("y1" + "[" + i + "]" + "[" + j + "]=" + y1[i][j] + " ");
                }
                //System.out.println();
            }
        }

        public static int[][] mulit(int n, int q, int[][] s24, int[][] f) {
            //多项式乘法
            int[][] pp = new int[1][2 * n - 1];
            for (int i = 0; i < 1; i++) {
                for (int t = 0; t < 1; t++) {
                    for (int j = 0; j < 2 * n - 1; j++) {
                        for (int k = 0; k < n; k++) {
                            for (int l = 0; l < n; l++) {
                                if (j == k + l) {
                                    pp[i][j] += s24[t][k] * f[i][l] % q;
                                }
                            }
                        }
                        //System.out.println("pp"+"["+i+"]"+"["+j+"]="+pp[i][j]+"     ");
                    }
                }
            }
            return pp;
        }

        public static int[][] mod(int n, int q, int[][] pp) {
            //多项式除法
            int[][] p2 = new int[1][n];
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n - 1; j++) {
                    p2[i][j] = (pp[i][j] - pp[i][n + j]) % q;
                    p2[i][j] = p2[i][j] % q;
                    //System.out.println("p2"+"["+i+"]"+"["+j+"]="+p2[i][j]+"     ");
                }
                p2[i][n - 1] = (pp[i][n - 1]) % q;
                p2[i][n - 1] = p2[i][n - 1] % q;
                //System.out.println("p2[0][255]="+p2[i][n - 1]);
            }
            return p2;
        }

        public static void main(String[] args) throws IOException {

            int n = 256;
            int q = 536870912;

            int[][] ri = new int[1][n];//定义多项式ri
            int[][] ti = new int[1][n];//定义多项式ti
            int[][] hk = new int[1][n];//定义多项式hk

            Random random = getRandom(q);
            polyomial(n, q, hk, random);

            int[][] y1 = new int[1][n];//定义多项式y1

            int[][] f1 = new int[1][n];//定义多项式f1
            int[][] f2 = new int[1][n];//定义多项式f2
            int[][] f3 = new int[1][n];//定义多项式f3
            int[][] f4 = new int[1][n];//定义多项式f4
            int[][] f5 = new int[1][n];//定义多项式f5
            int[][] f6 = new int[1][n];//定义多项式f6
            int[][] f7 = new int[1][n];//定义多项式f7
            int[][] f8 = new int[1][n];//定义多项式f8

            polyomial(n, q, f1, random);
            polyomial(n, q, f2, random);
            polyomial(n, q, f3, random);
            polyomial(n, q, f4, random);
            polyomial(n, q, f5, random);
            polyomial(n, q, f6, random);
            polyomial(n, q, f7, random);
            polyomial(n, q, f8, random);

            //密钥生成
            long startTime = System.nanoTime(); //获取密钥生成开始时间

            //ri的赋值
            polyomial(n, q, ri, random);

            //ti的赋值
            polyomial(n, q, ti, random);

            //用户输入身份ID信息
            Scanner sc = new Scanner(System.in);
            System.out.println("请您输入身份信息:");
            String s = sc.nextLine();
            int x1 = s.hashCode();
            System.out.println(x1);

            //获取公钥
            getpk(n, q, ri, ti, x1, y1);

            long endTime = System.nanoTime(); //获取密钥生成结束时间
            int count2 = 0;
            int count3 = 0;
            for (int i = 0; i < 1000; i++) {
                count2 += (endTime - startTime);
                count3 = count2 / 1000;
            }
            System.out.println("密钥生成程序运行时间：" + count3 + "ns"); //输出程序运行时间

            //将二维数组中的写入到txt文件中来查看公钥的大小
            File file = new File("d:\\pk.txt"); //存放数组数据的文件
            FileWriter out = new FileWriter(file); //文件写入流
            //将数组中的数据写入到文件中。每行各数据之间TAB间隔
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    out.write(y1[i][j] + "\t");
                }
                out.write("\r\n");
            }
            out.close();

            file = new File("d:\\sk1.txt"); //存放数组数据的文件
            out = new FileWriter(file); //文件写入流
            //将数组中的数据写入到文件中。每行各数据之间TAB间隔
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    out.write(ri[i][j] + "\t");
                }
                out.write("\r\n");
            }
            out.close();

            file = new File("d:\\sk2.txt"); //存放数组数据的文件
            out = new FileWriter(file); //文件写入流
            //将数组中的数据写入到文件中。每行各数据之间TAB间隔
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    out.write(ti[i][j] + "\t");
                }
                out.write("\r\n");
            }
            out.close();

            //签名生成
            long startTime1 = System.currentTimeMillis(); //获取签名生成开始时间
            int u = 0;
            int v = 100;
            int x = (int) (Math.sqrt(v) * random.nextGaussian() + u);//x为正态分布选取的随机数.
            //高斯算法采取多项式向量
            int[][] s1 = new int[1][n];
            Gaussian(n, x, s1);
            int[][] s2 = new int[1][n];
            Gaussian(n, x, s2);

            int[][] s11 = new int[1][n];
            Gaussian(n, x, s11);
            int[][] s21 = new int[1][n];
            Gaussian(n, x, s21);

            int[][] s12 = new int[1][n];
            Gaussian(n, x, s12);
            int[][] s22 = new int[1][n];
            Gaussian(n, x, s22);

            int[][] s13 = new int[1][n];
            Gaussian(n, x, s13);
            int[][] s23 = new int[1][n];
            Gaussian(n, x, s23);

            int[][] s14 = new int[1][n];
            Gaussian(n, x, s14);
            int[][] s24 = new int[1][n];
            Gaussian(n, x, s24);

            int[][] s16 = new int[1][n];
            Gaussian(n, x, s16);
            int[][] s26 = new int[1][n];
            Gaussian(n, x, s26);

            int[][] s17 = new int[1][n];
            Gaussian(n, x, s17);
            int[][] s27 = new int[1][n];
            Gaussian(n, x, s27);

            Scanner sc1 = new Scanner(System.in);
            System.out.println("请您输入消息串:");
            String ss = sc1.nextLine();
            String b = ss + x1;//将身份ID信息、待签消息ss进行哈希
            int a = b.hashCode();
            System.out.println(a);

            //计算-1的u次方
            int c = -1;
            int d = (int) Math.pow(c, a);
            //System.out.println(d);

            int[][] f = new int[1][n];
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    f[i][j] = (y1[i][j] + d * (f1[i][j] + f2[i][j] + f3[i][j] + f4[i][j] + f5[i][j] + f6[i][j] + f7[i][j] + f8[i][j])) % q;
                    //System.out.print("f"+"["+i+"]"+"["+j+"]="+f[i][j]+"     ");
                }
                //System.out.println();
            }

            //多项式乘法
            int[][] h8 = mulit(n, q, s27, f);

            //多项式取模运算
            int[][] ph = mod(n, q, h8);

            int[][] p1 = new int[1][n];
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    p1[i][j] = (s17[i][j] + ph[i][j]) % q;
                    //System.out.print("p1"+"["+i+"]"+"["+j+"]="+p1[i][j]+"     ");
                }
                //System.out.println();
            }

            int[][] h1 = mulit(n, q, s2, hk);
            int[][] pa = mod(n, q, h1);

            int[][] h2 = mulit(n, q, s21, hk);
            int[][] pb = mod(n, q, h2);

            int[][] h3 = mulit(n, q, s22, hk);
            int[][] pc = mod(n, q, h3);

            int[][] h4 = mulit(n, q, s23, hk);
            int[][] pd = mod(n, q, h4);

            int[][] h5 = mulit(n, q, s24, hk);
            int[][] pe = mod(n, q, h5);

            int[][] h7 = mulit(n, q, s26, hk);
            int[][] pg = mod(n, q, h7);

            int[][] p = new int[1][n];
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    p[i][j] = (p1[i][j] - s1[i][j] - s11[i][j] - s12[i][j] - s13[i][j] - s14[i][j] - s16[i][j] -  pa[i][j] - pb[i][j] - pc[i][j] - pd[i][j] - pe[i][j] - pg[i][j]) % q;
                    //System.out.print("p"+"["+i+"]"+"["+j+"]="+p[i][j]+"     ");
                }
                //System.out.println();
            }

            long endTime1 = System.currentTimeMillis(); //获取签名生成结束时间
            int count = 0;
            int count1 = 0;
            for (int i = 0; i < 1000; i++) {
                count +=(endTime1 - startTime1);
                count1 = count/1000;
            }
            System.out.println("生成签名签名程序运行时间：" + count1 + "ms"); //输出程序运行时间

            file = new File("d:\\s1.txt"); //存放数组数据的文件
            out = new FileWriter(file); //文件写入流
            //将数组中的数据写入到文件中。每行各数据之间TAB间隔
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    out.write(ri[i][j] + "\t");
                }
                out.write("\r\n");
            }
            out.close();

            file = new File("d:\\s2.txt"); //存放数组数据的文件
            out = new FileWriter(file); //文件写入流
            //将数组中的数据写入到文件中。每行各数据之间TAB间隔
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    out.write(ri[i][j] + "\t");
                }
                out.write("\r\n");
            }
            out.close();

            //签名验证
            long startTime2 = System.currentTimeMillis(); //获取签名验证开始时间

            Scanner sc2 = new Scanner(System.in);
            System.out.println("请您输入消息串:");
            String sss = sc2.nextLine();
            String b1 = sss + x1;//将身份ID信息、待签消息sss进行哈希(sss = ss)
            int aa = b1.hashCode();
            System.out.println(aa);

            int c1 = -1;
            int d1 = (int) Math.pow(c1, aa);// d1 = d
            //System.out.println(d1);

            int[][] ff = new int[1][n];// ff = f
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    ff[i][j] = (y1[i][j] + d1 * (f1[i][j] + f2[i][j] + f3[i][j] + f4[i][j] + f5[i][j] + f6[i][j] + f7[i][j] + f8[i][j])) % q;
                    //System.out.print("ff"+"["+i+"]"+"["+j+"]="+ff[i][j]+"     ");
                }
                //System.out.println();
            }

            //多项式乘法
            int[][] h81 = mulit(n, q, s27, f);

            //多项式取模运算
            int[][] ph1 = mod(n, q, h81);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print("ph1" + "[" + i + "]" + "[" + j + "]=" + ph1[i][j] + "     ");
                }
                System.out.println();
            }

            //等式右边
            int[][] p11 = new int[1][n];
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    p11[i][j] = (s17[i][j] + ph1[i][j]) % q;
                    //System.out.print("p11"+"["+i+"]"+"["+j+"]="+p11[i][j]+"     ");
                }
                //System.out.println();
            }

            int[][] h11 = mulit(n, q, s2, hk);
            int[][] pa1 = mod(n, q, h11);

            int[][] h22 = mulit(n, q, s21, hk);
            int[][] pb1 = mod(n, q, h22);

            int[][] h33 = mulit(n, q, s22, hk);
            int[][] pc1 = mod(n, q, h33);

            int[][] h44 = mulit(n, q, s23, hk);
            int[][] pd1 = mod(n, q, h44);

            int[][] h55 = mulit(n, q, s24, hk);
            int[][] pe1 = mod(n, q, h55);

            int[][] h77 = mulit(n, q, s26, hk);
            int[][] pg1 = mod(n, q, h77);

            //等式左边
            int[][] p22 = new int[1][n];
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < n; j++) {
                    p22[i][j] = ( s1[i][j] + s11[i][j] + s12[i][j] + s13[i][j] + s14[i][j] + s16[i][j] + pa1[i][j] + pb1[i][j] + pc1[i][j] + pd1[i][j] + pe1[i][j] + pg1[i][j]) % q;
                    //System.out.print("p22"+"["+i+"]"+"["+j+"]="+p22[i][j]+"     ");
                }
                //System.out.println();
            }

            System.out.println(Arrays.equals(p22 , p11));

            long endTime2 = System.currentTimeMillis(); //获取签名验证生成结束时间
            int count4 = 0;
            int count5 = 0;
            for (int i = 0; i < 1000; i++) {
                count4 += (endTime2 - startTime2);
                count5 = count4/1000;
            }
            System.out.println("签名验证程序运行时间：" + count5 + "ms"); //输出程序运行时间
        }

    }


