package com.util;

import java.nio.ByteBuffer;

/**
 * �ֽ���عܾ�
 */
public class ByteUtil
{

	/**
	 * intתС�˴洢
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] num2LittleEndian(int a, int len)
	{
		byte[] data = new byte[len];
		for(int i = 0;i<len;i++)
		{
			data[i] =(byte) (a >>(8*i) & 0xFF );
		}

		return data;
	}

	/**
	 * intת��˴洢
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] num2BigEndian(int a, int len)
	{
		byte[] data = new byte[len];
		for(int i = 0;i<len;i++)
		{
			data[len-i-1] =(byte) (a >>(8*i) & 0xFF );
		}

		return data;
	}

	/**
	 * ByteBuffeתByteArray
	 * @return
	 */
	public static byte[] ByteBuffe2ByteArray(ByteBuffer buffer){
		buffer.flip();
		byte [] array=new byte[buffer.remaining()];
		buffer.get(array, 0, array.length);
		return array;
	}

	/**
	 * ���ڽ���ʮ�������ַ�������Ĵ�д�ַ�����
	 */
	private static final char[] toDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static String encodeHex(byte[] data) {
		final int len = data.length;
		final char[] out = new char[3*len];//len*3
		// two characters from the hex value.
		for (int i = 0, j = 0; i < len; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];// ��λ
			out[j++] = toDigits[0x0F & data[i]];// ��λ
			out[j++]=32; //��ӿո�


		}
		return new String(out);
	}

	public static void main(String[] args) {
		int b=2300;
		System.out.println((byte)230);
		System.out.println(encodeHex(num2LittleEndian(b,1)));

	}
}
