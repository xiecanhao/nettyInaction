package cn.gz.xchao.nettyInaction.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 传统阻塞网络编程
 * 
 * @since 2018年12月12日 上午10:42:06
 * @author xchao
 */
public class PlainOioServer {
	public void serve(int port) throws IOException {
		final ServerSocket socket = new ServerSocket(port);
		try {
			for (;;) {
				final Socket clientSocket = socket.accept();
				System.out.println("Accepted connection from " + clientSocket);
				new Thread(new Runnable() {

					public void run() {
						OutputStream out;
						try {
							out = clientSocket.getOutputStream();
							out.write("Hi!\r\n"
									.getBytes(Charset.forName("UTF-8")));
							out.flush();
							clientSocket.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								clientSocket.close();
							} catch (Exception e2) {
								// ignore on close
							}
						}
					}

				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
