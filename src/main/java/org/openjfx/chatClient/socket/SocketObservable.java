package org.openjfx.chatClient.socket;

public interface SocketObservable {
	public void subscribeStatus(SocketStatusObserver observer);
	public void unsubscribeStatus(SocketStatusObserver observer);
	public void subscribeMsg(SocketMsgObserver observer);
	public void unsubscribeMsg(SocketMsgObserver observer);
	public void notifyStatus(int sigValue);
	public void notifyMsg(String msg);
}
