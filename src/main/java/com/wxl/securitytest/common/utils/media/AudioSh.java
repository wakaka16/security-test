package com.wxl.securitytest.common.utils.media;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 音频统一转换工具
 * @author wxl
 */
@Component
public class AudioSh implements Multimedia {

	/**
	 * 日志
	 */
	protected static final Logger LOG = LoggerFactory.getLogger(AudioSh.class);
	/**
	 * 音频采样率
	 */
	final static int SAMPLERATE = 11025;
	/**
	 * 音频比特率
	 */
	final static int BITRATE = 16000;

	@Override
	public boolean convert(String sourceFile, String targetFile) {
		boolean flag = true;
		String cmd = "ffmpeg  -i " + sourceFile + " -ar " + SAMPLERATE + " -ab " + BITRATE + " -f mp3 "
				+ targetFile;
		System.out.println(cmd);
		try {
			// 执行命令
			Process proc = Runtime.getRuntime().exec(cmd);
			//打印错误信息
//			InputStream stderr = proc.getErrorStream();
//            InputStreamReader isr = new InputStreamReader(stderr);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            while ((line = br.readLine()) != null)
//                System.out.println(line);
            int exitVal = proc.waitFor();
            flag = (exitVal == 0);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new IllegalArgumentException("音频转换失败");
		}		
		return flag;
	}

}
