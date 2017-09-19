package com.io.file;
//2017年4月7日22:05:51,用于测试 RandomAccessFile的使用

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {
	/** 
     * 读的方法 
     * @param path 文件路径 
     * @param pointe 指针位置 
	 * @throws IOException 
     * **/  
	public static void randomRed(String path,int pointe) throws IOException{
		 //RandomAccessFile raf=new RandomAccessFile(new File("D:\\3\\test.txt"), "r");  
        /** 
         * model各个参数详解 
         * r 代表以只读方式打开指定文件 
         * rw 以读写方式打开指定文件 
         * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备 
         * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备 
         *  
         * **/  
		RandomAccessFile raf=new RandomAccessFile(path, "r");
		//获取RandomAccessFile对象文件指针的位置，初始位置是0  .
		System.out.println("RandomAccessFile文件指针的初始位置:"+raf.getFilePointer());
		raf.seek(0);//移动文件指针位置
		byte[] buff=new byte[1024];
		//用于保存实际读取的字节数
		int hasRead=0;
		//循环读取
		while((hasRead=raf.read(buff))>0){
			//打印读取的内容,并将字节转化为字符串输入
			String s=new String(buff,0,hasRead); //注意文件的编码格式必须是utf-8,否则在new String需要手动指定编码格式
			System.out.println(s);
		}
		
	}
	
	/** 
	 * 追加方式 
	 * 写的方法 
	 * @param path 文件路径 
	 * @throws IOException 
	 * ***/  
	public static void randomWrite(String path) throws IOException{
		 /**以读写的方式建立一个RandomAccessFile对象**/  
		RandomAccessFile raf=new RandomAccessFile(path,"rw");
		//将记录指针移动到文件的最后
		raf.seek(raf.length());
		raf.write("我是追加的\r\n".getBytes());
	} 
	
	/** 
     * 实现向指定位置 
     * 插入数据 
     * @param fileName 文件名 
     * @param points 指针位置 
     * @param insertContent 插入内容 
     * **/  
	
	public static void insert(String fileName,long points,String conent){
			try {
				File tmp=File.createTempFile("tmp", null);
				tmp.deleteOnExit(); //在jvm退出时删除
				
				RandomAccessFile raf=new RandomAccessFile(fileName,"rws"); //rws立即写入磁盘,虽然性能差,方便调试
				//创建一个临时文件夹来保存插入点后的数据
				FileOutputStream tmpOut=new FileOutputStream(tmp);
				FileInputStream tmpIn=new FileInputStream(tmp);
				raf.seek(points);
				//将插入点的后的内容读入到临时文件夹
				byte [] buff=new byte[1024];
				//用于保存临时读取的字节数
				int hasRead=0;
				//循环读取插入点后的内容
				while ((hasRead=raf.read(buff))>0) {
					//将读取的数据写入临时文件中
					tmpOut.write(buff, 0, hasRead);
				}
				//插入需要制定添加的数据
				raf.seek(points);//返回原来的插入处
				//追加需要追加的内容
				raf.write(conent.getBytes());
				//最后追加临时文件中的内容
				while ((hasRead=tmpIn.read(buff))>0) {
					raf.write(buff,0,hasRead);
				}
				
				
			} catch (Exception e) {
					e.printStackTrace();
			}
	}
	public static void main(String[] args) throws IOException {
		String path="D:"+File.separator+"zw"+File.separator+"HelloFile.txt";
		int seekPoint=20;
		//RandomAccessFileTest.randomRed(path, seekPoint);
	//	RandomAccessFileTest.randomWrite(path);
		RandomAccessFileTest.insert(path, 0, "神的质疑"); //注意在指定points时,要小心,避免半个汉字的byte的下标,否则会乱码
		
	}
}
