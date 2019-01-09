package cn.gz.xchao.nettyInaction.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 复合编码、解码器。将已有的编码器和解码器放在一起
 * 
 * @since 2018年12月27日 下午4:28:47
 * @author xchao
 */
public class CombinedByteCharCodec extends
		CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
	public CombinedByteCharCodec() {
		super(new ByteToCharDecoder(), new CharToByteEncoder());
	}
}
