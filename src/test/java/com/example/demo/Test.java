package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: com.example.demo
 * @Author: BYM
 * @Date: 2019/6/25
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */
public class Test {


    private static void test1() {
        String a = "hello";
        long time = new Date().getTime();
        for (int i = 0; i < 10000; i++) {
            a += i;
        }
        long time1 = new Date().getTime();

        System.out.println(time1 - time);

        //377
    }


    private static void test2() {
        StringBuilder a = new StringBuilder("hello");
        long time = new Date().getTime();
        for (int i = 0; i < 10000; i++) {
            a.append(i);
        }
        long time1 = new Date().getTime();

        System.out.println(time1 - time);
        //3
    }


    private static void test3() {
        StringBuffer a = new StringBuffer("hello");
        long time = new Date().getTime();
        for (int i = 0; i < 10000; i++) {
            a.append(i);
        }
        long time1 = new Date().getTime();

        System.out.println(time1 - time);
    }

    private static void test4() {
        ArrayList<Object> objects = new ArrayList<>(5);
        HashMap<Object, Object> map = new HashMap<>(1);
        map.put(1, 2);
        map.put("2", "3");
        Object o = map.get("2");
        System.out.println(o);


    }

    private static void test5() {
        // 此方位为native方法。
        // public static native void arraycopy(Object src, int srcPos, Object dest,int destPos, int length);
        // 初始化
        int[] ids = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(ids)); // [1, 2, 3, 4, 5]
        // 测试自我复制
        // 把从索引0开始的2个数字复制到索引为3的位置上
        System.arraycopy(ids, 0, ids, 3, 2);
        System.out.println(Arrays.toString(ids)); // [1, 2, 3, 1, 2]
        // 测试复制到别的数组上
        // 将数据的索引1开始的3个数据复制到目标的索引为0的位置上
        int[] ids2 = new int[6];
        System.arraycopy(ids, 1, ids2, 0, 3);
        System.out.println(Arrays.toString(ids2)); // [2, 3, 1, 0, 0, 0]
        // 此功能要求
        // 源的起始位置+长度不能超过末尾
        // 目标起始位置+长度不能超过末尾
        // 且所有的参数不能为负数
        try {
            System.arraycopy(ids, 0, ids2, 0, ids.length + 1);
        } catch (IndexOutOfBoundsException ex) {
            // 发生越界异常，数据不会改变
            System.out.println("拷贝发生异常：数据越界。");
        }
        System.out.println(Arrays.toString(ids2)); // [2, 3, 1, 0, 0, 0]
        // 如果是类型转换问题
        Object[] o1 = {1, 2, 3, 4.5, 6.7};
        Integer[] o2 = new Integer[5];
        System.out.println(Arrays.toString(o2)); // [null, null, null, null, null]
        try {
            System.arraycopy(o1, 0, o2, 0, o1.length);
        } catch (ArrayStoreException ex) {
            // 发生存储转换，部分成功的数据会被复制过去
            System.out.println("拷贝发生异常：数据转换错误，无法存储。");
        }
        // 从结果看，前面3个可以复制的数据已经被存储了。剩下的则没有
        System.out.println(Arrays.toString(o2)); // [1, 2, 3, null, null]
    }

    private static void test6() {
        long a = 1001234;
        double v = a >> 1;
        System.out.println(v);
    }

    private static void test7() {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 5; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第" + index + "个线程" + Thread.currentThread().getName());
                }
            });
            /**
             * 第1个线程pool-1-thread-1
             * 第2个线程pool-1-thread-1
             * 第3个线程pool-1-thread-1
             * 第4个线程pool-1-thread-1
             * 第5个线程pool-1-thread-1
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第" + index + "个线程" + Thread.currentThread().getName());

                }
            }).start();
            /**
             * 第1个线程Thread-0
             * 第2个线程Thread-1
             * 第3个线程Thread-2
             * 第4个线程Thread-3
             * 第5个线程Thread-4
             */

        }
    }


    public static void main(String[] args) {
        test7();
    }

}
