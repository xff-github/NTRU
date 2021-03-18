package com.ntru.demo.test;
import java.util.*;

public class Polynomial_test {
    public static void main(String[] args) {

        List<Integer> a =  Arrays.asList(1, 2, 3);
        List<Integer> b =  Arrays.asList(1, 2, 3);

        List<Integer> result = getResult(a, b);
        System.out.println(result);

    }



    private static List<Integer> getResult(List<Integer> listA, List<Integer> listB) {
        //把list转成Map,Map的key表示多项式的阶数,Map的value表示多项式的系数
        Map<Integer, Integer> resultA = assembler(listA);//调用assembler方法以key-value的方式赋值
        Map<Integer, Integer> resultB = assembler(listB);//调用assembler方法以key-value的方式赋值
        //获取初步处理Map的结果
        Map<Integer, Integer> map = assemblerJoinerMap(resultA, resultB);//调用assemblerJoinerMap方法,map存储的是合并同类项的值
        //返回最终结果

        return assemblerFinalResult(map, resultA.size());//map为合并同类项后的map集合.resultA.size为3

    }

    private static Map<Integer, Integer> assembler(List<Integer> source) {
        Map<Integer, Integer> targets = new HashMap<>();
        for (int i = 0; i < source.size(); i++) {
            //向Map集合赋值,get(0)=1,get(1)=2,get(2)=3
            targets.put(i, source.get(i));
        }
        return targets;
    }

    //降阶处理
    private static List<Integer> assemblerFinalResult(Map<Integer, Integer> resultMap, int size) {
        //resultMap存储的合并同类项的值
        for (int i = 0; i < resultMap.entrySet().size(); i++) {
            if (i < size) {//resultA.size == 3
                continue;//跳出循环,size为初始多项式的长度(0 1 2跳出循环,不需降阶)
            }
            //降阶
            int degree = i - size + 1;//3-3+1=1 4-3+1=2 加到x, x2项(3 4 将阶) degree代表key值
            //合并同类项
            upPut(degree, resultMap.get(i), resultMap);//调用upPut方法,进行降阶
        }
        //删除高阶
        removeIfKeyGreater(resultMap, size);//get(3) get(4)删除,key > size删除
        return new ArrayList<>(resultMap.values());
    }

    //错位相乘,取多项式相乘的结果
    private static Map<Integer, Integer> assemblerJoinerMap(Map<Integer, Integer> resultA, Map<Integer, Integer> resultB) {
        Map<Integer, Integer> resultMap = new HashMap<>();//定义new HashMap集合为空
//        if (resultA.size() == resultB.size()) {//实现两个多项式项数相同的多项式相乘
        //获取resultA,resultB集合中的数
        for (int i = 0; i < resultA.entrySet().size(); i++) {
            for (int j = 0; j < resultB.entrySet().size(); j++) {
                int degree = i + j;//degree相当于key值(key最大为4.得到的多项式最高指数为x的4次方)
                Integer value = resultA.get(i) * resultB.get(j);//错位相乘
                upPut(degree, value, resultMap);//调用upPut方法,进行合并同类项.resultMap = [1,4,10,12,9]

            }
//            }
        }
        return resultMap;
    }

    //更新插入Map,如果Key为空则直接放入,如果Key有值则直接相加.(index相当于key值)
    private static void upPut(Integer index, Integer value, Map<Integer, Integer> resultMap) {
        //合并同类项,key值相同,value值相加
        value = value + resultMap.getOrDefault(index, 0);
        resultMap.put(index, value);
    }

    //删除Map中key大于某一个值的所有value,size == 3
    private static void removeIfKeyGreater(Map<Integer, Integer> map, int size) {
        int initSize = map.size();
        for (int i = size; i <= initSize; i++) {
            map.remove(i);//最后执行
        }
    }
}

