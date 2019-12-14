package yovi.putra;

/***
 * @author yoviekaputra
 */
public class QRISSegment {
	private String rootId;
	private String field;
	private int length;
	private Object data;
	
	public QRISSegment() {
		// TODO Auto-generated constructor stub
	}
	
	public QRISSegment(String rootId, int length, Object data) {
		super();
		this.rootId = rootId;
		this.length = length;
		this.data = data;
	}
	
	public QRISSegment(String rootId, String field, int length, Object data) {
		super();
		this.rootId = rootId;
		this.field = field;
		this.length = length;
		this.data = data;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "ID[" + rootId + "] F[" + field + "] DATA[" + length + "][" + data + "]";
	}
}
