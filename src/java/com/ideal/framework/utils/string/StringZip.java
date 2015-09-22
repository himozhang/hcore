package com.ideal.framework.utils.string;

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * 进行字符串数据的压缩
 */
public class StringZip {

	/**
	 * 压缩数据以UTF-8编码
	 * 
	 * @param data
	 * @return 编码后的字符串
	 */
	public static String compressData(String data) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DeflaterOutputStream zos = new DeflaterOutputStream(bos);
			zos.write(data.getBytes("UTF-8"));
			zos.close();
			return new String(getenBASE64inCodec(bos.toByteArray()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return "ZIP_ERR";
		}
	}

	/**
	 * 解压缩数据以UTF-8编码
	 * 
	 * @param encdata
	 * @return 解码后的字符串
	 */
	public static String decompressData(String encdata) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			InflaterOutputStream zos = new InflaterOutputStream(bos);
			zos.write(getdeBASE64inCodec(encdata));
			zos.close();
			return new String(bos.toByteArray(), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "UNZIP_ERR";
		}
	}

	/**
	 * 调用apache的编码方法
	 */
	private static String getenBASE64inCodec(byte[] b) {
		if (b == null)
			return null;
		return new String((new Base64()).encode(b));
	}

	/**
	 * 调用apache的解码方法
	 */
	private static byte[] getdeBASE64inCodec(String s) {
		if (s == null)
			return null;
		return new Base64().decode(s.getBytes());
	}
	
	public static void main(String[] args){
		String data = StringZip.compressData(data1);
		//System.out.println(data1.getBytes().length);
		//System.out.println(data.getBytes().length);
		System.out.println(StringZip.decompressData(data));
	}
}