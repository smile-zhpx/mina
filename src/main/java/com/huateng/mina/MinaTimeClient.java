package com.huateng.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector();

		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("logger", new LoggingFilter());
		chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("UTF-8"))));

		connector.setHandler(new MinaTimeClientHandler());
		connector.setConnectTimeoutMillis(2000L);

		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",
				9123));

		cf.awaitUninterruptibly();
		for (int i = 0; i < 5; i++) {
			cf.getSession().write("hello, mina! 你好");
		}
		CloseFuture clf = cf.getSession().getCloseFuture().awaitUninterruptibly();
		System.out.println("-----------------" + clf.isDone());
		cf.getSession().close(true);
		connector.dispose();
	}

}
