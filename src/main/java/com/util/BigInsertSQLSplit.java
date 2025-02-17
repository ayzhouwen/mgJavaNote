package com.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BigInsertSQLSplit {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\ayzho\\Desktop\\新建文件夹\\sql1.txt"; // 输入文件路径
        String outputDir = "C:\\Users\\ayzho\\Desktop\\新建文件夹\\distsql\\";      // 输出目录
        int chunkSize = 10000;                              // 每个文件的行数

        try {
            splitFile(inputFilePath, outputDir, chunkSize);
            System.out.println("文件切割完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 切割文件
     *
     * @param inputFilePath 输入文件路径
     * @param outputDir     输出目录
     * @param chunkSize     每个文件的行数
     * @throws IOException 文件读写异常
     */
    public static void splitFile(String inputFilePath, String outputDir, int chunkSize) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        List<String> chunk = new ArrayList<>(chunkSize);
        String line;
        int fileCount = 1;
        int lineCount = 0;

        while ((line = reader.readLine()) != null) {
            // 如果是 INSERT 语句，加入当前块
            if (line.startsWith("INSERT INTO")) {
                chunk.add(line);
                lineCount++;

                // 如果达到 chunkSize，写入文件
                if (lineCount >= chunkSize) {
                    writeChunkToFile(chunk, outputDir, fileCount);
                    fileCount++;
                    lineCount = 0;
                    chunk.clear();
                }
            }
        }

        // 处理剩余的数据
        if (!chunk.isEmpty()) {
            writeChunkToFile(chunk, outputDir, fileCount);
        }

        reader.close();
    }

    /**
     * 将当前块写入文件
     *
     * @param chunk     当前块的数据
     * @param outputDir 输出目录
     * @param fileCount 文件编号
     * @throws IOException 文件读写异常
     */
    private static void writeChunkToFile(List<String> chunk, String outputDir, int fileCount) throws IOException {
        String outputFilePath = outputDir + "output_part_" + fileCount + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        for (String line : chunk) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
        System.out.println("生成文件: " + outputFilePath);
    }
}
