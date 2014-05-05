package com.elephant.framework.network.socket.mima;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import com.elephant.framework.network.codec.IFEncoder;
import com.elephant.framework.network.msg.IFSendableMessage;

public class MinaSocketMsgEncoder extends IoFilterAdapter {
	
	private IFEncoder encoder;
	public MinaSocketMsgEncoder(IFEncoder encoder){
		this.encoder = encoder;
	}
	
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,Object message) throws Exception {
		if(message instanceof IFSendableMessage){
			IFSendableMessage msg =(IFSendableMessage) message;
			
			//写入bytebuf
			byte[] data = encoder.encoder(msg);
			IoBuffer buf = IoBuffer.allocate(data.length).setAutoExpand(true);
			buf.put(data);
			nextFilter.messageReceived(session, buf);
		}else{
			nextFilter.messageReceived(session, message);
		}
		
	}

}
