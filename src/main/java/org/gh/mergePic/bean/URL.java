package org.gh.mergePic.bean;

public class URL {

	private int id;
	private String name;
	private String url;
	private int tid;
	private int order;
	private String domain;
	private int ico;
	private int index_p;
	private int index_w;
	private int index_h;

	public URL() {

	}

	public URL(int id, String url) {
		this.id = id;
		this.url = url;
	}

	public URL(int id, String name, String url, int tid, int order, String domain, int ico) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.tid = tid;
		this.order = order;
		this.domain = domain;
		this.ico = ico;
	}

	public URL(int id, String name, String url, int tid, int order, String domain, int ico, int index_p, int index_w,
			int index_h) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.tid = tid;
		this.order = order;
		this.domain = domain;
		this.ico = ico;
		this.index_p = index_p;
		this.index_w = index_w;
		this.index_h = index_h;
	}

	public int getIndex_p() {
		return index_p;
	}

	public void setIndex_p(int index_p) {
		this.index_p = index_p;
	}

	public int getIndex_w() {
		return index_w;
	}

	public void setIndex_w(int index_w) {
		this.index_w = index_w;
	}

	public int getIndex_h() {
		return index_h;
	}

	public void setIndex_h(int index_h) {
		this.index_h = index_h;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public int getTid() {
		return tid;
	}

	public int getOrder() {
		return order;
	}

	public String getDomain() {
		return domain;
	}

	public int getIco() {
		return ico;
	}

	@Override
	public String toString() {
		return "URL [id=" + id + ", name=" + name + ", url=" + url + ", tid=" + tid + ", order=" + order + ", domain="
				+ domain + ", ico=" + ico + "]";
	}

}
