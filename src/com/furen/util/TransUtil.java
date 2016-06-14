package com.furen.util;

/**
 * 卡号加密、解密处理
 * @author 夏建军
 *
 */
public class TransUtil {
	
	/**
	 * 卡号加密
	 * @param pan
	 * @return
	 */
  	public static String panEncryption(String pan){
  		//调用加密算法加密
  		pan = com.sssoft.common.util.SSDes.encHexStr3(pan,"85a3256c8cabcdeffedcbac8c6523a58");
  		//将返回的结果转大写
  		pan = pan.toUpperCase();
  		/**
  		 * 特殊字符替换
  		 *  :	A	a
		 *	;	B	b
		 *	<	C	c
		 *	=	D	d
		 *	>	E	e
		 *	?	F	f
  		 */
  		return pan.replaceAll("A", ":").replaceAll("B", ";").replaceAll("C", "<")
  				.replaceAll("D", "=").replaceAll("E", ">").replaceAll("F", "?");
  	}
  	
  	/**
  	 * 卡号解密，并只限制指定区域，不显示的区域用*号替换
  	 * @param pan      卡号密文
  	 * @param front    解密的卡号显示前面几位
  	 * @param behind   解密的卡号显示后面几位
  	 * @return
  	 */
  	public static String panDecryptHideCenter(String pan,int front,int behind) {
  		//先解密出卡号
  		//pan = com.sssoft.common.util.SSDes.decHexStr3(pan, "85a3256c8cabcdeffedcbac8c6523a58");
  		pan = com.sssoft.common.util.CryptUtil.decPan(pan);
  		String asterisk = "";
  		if(front > 0 && behind > 0) {
  			//显示前后，中间用*号替换
  			for(int i = 0; i < pan.length()-front-behind; i++ ) {
  				asterisk += "*";
  			}
  			return pan.substring(0,front) + asterisk + pan.substring(pan.length()-behind,pan.length());
  		} else if(front > 0 && behind <= 0) {
  			//只显示前面指定位数
  			for(int i = 0; i < pan.length()-front; i++ ) {
  				asterisk += "*";
  			}
  			return pan.substring(0,front) + asterisk;
  		} else if(front <= 0 && behind > 0) {
  			//只显示后面指定位数
  			for(int i = 0; i < pan.length()-behind; i++ ) {
  				asterisk += "*";
  			}
  			return asterisk + pan.substring(asterisk.length(),pan.length());
  		} else {
  			//直接返回解密后的卡号
  			return pan;
  		}
  	}
}
