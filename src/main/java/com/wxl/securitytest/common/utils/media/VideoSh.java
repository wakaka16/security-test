package com.wxl.securitytest.common.utils.media;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 视频统一转换工具
 */
@Component
public class VideoSh implements Multimedia{
	@Value("${video.convert-config}")
  private String convertConfig;

	/**
	 * 日志
	 */
	protected static final Logger LOG = LoggerFactory.getLogger(VideoSh.class);


	@Override
	public boolean convert(String sourceFile, String targetFile) {
		boolean flag = true;
		try {
			// 执行命令
			String cmd = convertConfig+" "+ sourceFile + " " + targetFile;
			System.out.println(cmd);
			Process proc = Runtime.getRuntime().exec(cmd);
			//打印错误信息
//			InputStream stderr = proc.getErrorStream();
//			InputStreamReader isr = new InputStreamReader(stderr);
//			BufferedReader br = new BufferedReader(isr);
//			String line = null;
//			while ((line = br.readLine()) != null)
//				System.out.println(line);
			int exitVal = proc.waitFor();
			flag = (exitVal == 0);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new IllegalArgumentException("视频转换失败");
		}
		return flag;
	}
}
