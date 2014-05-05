package com.elephant.framework.network.socket.mima;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import com.elephant.framework.network.codec.FDefaultDecoder;

public class MinaSocketBytesDecoder extends IoFilterAdapter {
	
	private FDefaultDecoder decoder;
	
	public MinaSocketBytesDecoder(FDefaultDecoder decoder){
		this.decoder = decoder;
	}
	
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,Object message) throws Exception {
		//super.messageReceived(nextFilter, session, message);
		if(message instanceof IoBuffer){
			IoBuffer buff = (IoBuffer)message;
			while(buff.remaining()>= 4 ){
				//记录当前缓存区索引位置
				buff.mark();
				
				//2 (msg+data)
				short len = buff.getShort();
				//2 
				short msgId = buff.getShort();
				
				if(buff.remaining() >= len -2 ){
					byte[] data = new byte[len-2];
					buff.get(data);
					nextFilter.messageReceived(session, decoder.decoder(data, msgId));				
				}else{ //数据不够一条消息  重新设置等待下次读取
					buff.reset();
					break;
				}
			}
			
		}else{
			nextFilter.messageReceived(session, message);
		}
	}

}
