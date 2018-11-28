package com.lim.xyyutil.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qinhao
 */
public class FileCopyNIO {


    public static void copy(String src, String dst) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(src));
        FileOutputStream outputStream = new FileOutputStream(new File(dst));
        FileChannel readChannel = inputStream.getChannel();
        FileChannel writeChannel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            // 从输入通道中读取数据到缓冲区中
            int r = readChannel.read(buffer);
            // read() 返回 -1 表示 EOF
            if (r == -1) {
                break;
            }
            // 切换读写
            buffer.flip();
            // 把缓冲区的内容写入输出文件中
            writeChannel.write(buffer);
            // 清空缓冲区
            buffer.clear();
        }
        // 先开后关,针对流关闭顺序可了解try()
        writeChannel.close();
        readChannel.close();
        outputStream.close();
        inputStream.close();
    }

    public static void copyAutoCloseRes(String src, String dst) throws IOException {
        /**
         * try-with-resource
         * 关闭的资源需要实现AutoCloseable接口
         */
        try (
                FileInputStream inputStream = new FileInputStream(new File(src));
                FileOutputStream outputStream = new FileOutputStream(new File(dst));
                FileChannel readChannel = inputStream.getChannel();
                FileChannel writeChannel = outputStream.getChannel();
                MyAutoCloseClass autoCloseClass = new MyAutoCloseClass()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                int r = readChannel.read(buffer);
                if (r == -1) {
                    break;
                }
                buffer.flip();
                writeChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    static class MyAutoCloseClass implements AutoCloseable {

        @Override
        public void close() throws IOException {
            System.out.println("MyAutoCloseClass is closed ^.^");
        }
    }

    public static void main(String[] args) throws IOException {
        //copy("C:\\Users\\dell\\Desktop\\initial.sql", "C:\\Users\\dell\\Desktop\\copy.sql");
        copyAutoCloseRes("C:\\Users\\dell\\Desktop\\initial.sql", "C:\\Users\\dell\\Desktop\\copyAutoCloseRes.sql");
    }
}
