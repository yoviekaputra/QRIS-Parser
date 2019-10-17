package yovi.putra;

import java.util.HashMap;
import java.util.Map;

/***
 * @author yoviekaputra
 */
public class QRISSegment {
	private String rootId;
	private int length;
	private String data;
	private boolean hasChild;
	private Map<String, QRISSegment> childs;
	
	public QRISSegment(String rootId, boolean hasChild) {
		this.rootId = rootId;
		this.hasChild = hasChild;
		this.childs = new HashMap<String, QRISSegment>();
	}
	public QRISSegment(String rootId, boolean hasChild, Map<String, QRISSegment> childs) {
		this.rootId = rootId;
		this.hasChild = hasChild;
		this.childs = childs;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public Map<String, QRISSegment> getChilds() {
		return childs;
	}
	public void setChilds(Map<String, QRISSegment> childs) {
		this.childs = childs;
	}
	@Override
	public String toString() {
		return "Segment [rootId=" + rootId + ", length=" + length + ", data=" + data + ", hasChild=" + hasChild
				+ ", childs=" + childs + "]";
	}
}
