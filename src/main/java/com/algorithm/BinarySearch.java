package com.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 二分查找又称折半查找，它是一种效率较高的查找方法。 
　　【二分查找要求】：1.必须采用顺序存储结构 2.必须按关键字大小有序排列。
 * @author Administrator
 *
 */
public class BinarySearch { 
	public static void main(String[] args) {
		//int[] src = new int[] {1, 3, 5, 7, 8, 9}; 
		//List<Integer> list=new ArrayList<>();
		int[] src=new  int[100000]; 
		Random rand = new Random();
		for(int i=0;i<src.length;i++){
			src[i]= rand.nextInt(100000)+3;
		}
		Arrays.sort(src);
		src[5000]=1000;
		System.out.println(src);
		
		System.out.println(binarySearch(src, 103));
		System.out.println(binarySearch(src,3,0,src.length-1));
	}

	/**
	 * * 二分查找<a href="http://lib.csdn.net/base/datastructure" class='replace_word' title="算法与数据结构知识库" target='_blank' style='color:#df3434; font-weight:bold;'>算法</a> * *
	 * 
	 * @param srcArray
	 *            有序数组 *
	 * @param des
	 *            查找元素 *
	 * @return des的数组下标，没找到返回-1
	 */ 
   public static int binarySearch(int[] srcArray, int des){ 
	
		int low = 0; 
		int high = srcArray.length-1; 
		int count=0;
		while(low <= high) { 
			count++;
			System.out.println("执行"+count+"次");
			int middle = (low + high)/2; 
			if(des == srcArray[middle]) { 
			
			    return middle; 
			}else if(des <srcArray[middle]) { 
			    high = middle - 1; 
			}else { 
			    low = middle + 1; 
			}
		}
		
		return -1;
   }
      
	  /**  
	 *二分查找特定整数在整型数组中的位置(递归)  
	 *@paramdataset  
	 *@paramdata  
	 *@parambeginIndex  
	 *@paramendIndex  
	 *@returnindex  
	 */
	public static int binarySearch(int[] dataset,int data,int beginIndex,int endIndex){  
	   int midIndex = (beginIndex+endIndex)/2;  
	   if(data <dataset[beginIndex]||data>dataset[endIndex]||beginIndex>endIndex){
		   return -1;  
	   }
	   if(data <dataset[midIndex]){  
		   return binarySearch(dataset,data,beginIndex,midIndex-1);  
	   }else if(data>dataset[midIndex]){  
	       return binarySearch(dataset,data,midIndex+1,endIndex);  
	   }else {  
		   return midIndex;  
	   }  
   } 

}