package idv.lingerkptor.GoodsManager.core.Listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import idv.lingerkptor.GoodsManager.core.Message.Message;



public class UserListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession user = se.getSession();
		// 註冊訊息服務
		System.out.println("註冊訊息服務");
		MessageInit.getMsgManager().register(user);
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, user.getId() + "已登入"));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession user = se.getSession();
		// 登出訊息服務
		System.out.println("登出訊息服務");
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, user.getId() + "已登出"));
		MessageInit.getMsgManager().logout(user);

	}
}
