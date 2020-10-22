package idv.lingerkptor.GoodsManager.core.api.request;

import idv.lingerkptor.GoodsManager.core.Message.Message;

//json封包
	// {
	// category:err info warn
	// }

	/**
	 * 請求物件類別
	 *
	 * @author lingerkptor
	 */
	public class GetMessageRequest  implements Request{
		private Message.Category category;

		GetMessageRequest() {
		}

		public String getCategory() {
			return category.toString();
		}
	}