package com.kqds.util.base.util;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

public class Wav2Mp3 {

	/**
	 * 执行转化过程
	 * 
	 * @param source
	 *            源文件
	 * @param desFileName
	 *            目标文件名
	 * @return 转化后的文件
	 */
	public static File execute(File source, String desFileName) throws Exception {
		File target = new File(desFileName);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(36000)); // 音频比率 MP3默认是1280000
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);
		return target;
	}

	public static void main(String[] args) throws Exception {
		File file = new File("c://11.wav");
		execute(file, "c://11.mp3");
	}

}
