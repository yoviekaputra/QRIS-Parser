package yovi.putra;

public class QRISMerchantInfo extends Object {
	private String globalId;
	private String mPAN;
	private String mCriteria;
	private String mId;
	
	public QRISMerchantInfo() {
		super();
	}

	public QRISMerchantInfo(String globalId, String mPAN, String mCriteria, String mId) {
		super();
		this.globalId = globalId;
		this.mPAN = mPAN;
		this.mCriteria = mCriteria;
		this.mId = mId;
	}

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public String getmPAN() {
		return mPAN;
	}

	public void setmPAN(String mPAN) {
		this.mPAN = mPAN;
	}

	public String getmCriteria() {
		return mCriteria;
	}

	public void setmCriteria(String mCriteria) {
		this.mCriteria = mCriteria;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	@Override
	public String toString() {
		return "{\n"
				+ "\tglobalId=" + globalId + ", \n"
				+ "\tmPAN=" + mPAN + ", \n"
				+ "\tmCriteria=" + mCriteria + ", \n"
				+ "\tmId=" + mId +"\n"
				+ "}";
	}
}
