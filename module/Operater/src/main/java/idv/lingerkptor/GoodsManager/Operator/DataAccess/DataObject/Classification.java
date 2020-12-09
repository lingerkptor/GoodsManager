package idv.lingerkptor.GoodsManager.Operator.DataAccess.DataObject;

import java.util.LinkedList;
import java.util.List;

public class Classification {

	private transient int CID;
	private String classificationName;
	private List<Classification> subClassificationList = null;
	private transient Integer PID = null;

	@SuppressWarnings("unused")

	private Classification() {

	}

	public Classification(int CID, String Name) {
		this.CID = CID;
		this.classificationName = Name;
	}

	public Classification(int CID, String Name, int PID) {
		this.CID = CID;
		this.classificationName = Name;
		if (PID != 0)
			this.PID = PID;
	}

	public String getClassificationName() {
		return this.classificationName;
	}

	public void addSubClassification(Classification subClass) {
		if (subClassificationList == null)
			subClassificationList = new LinkedList<Classification>();
		subClassificationList.add(subClass);
	}

	public int getCID() {
		return CID;
	}

	public Integer getPID() {
		return PID;
	}
}
