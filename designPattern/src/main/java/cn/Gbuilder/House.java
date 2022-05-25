package cn.Gbuilder;

public class House {
	
	private String wall;
	private String top;
	
	public String getWall() {
		return wall;
	}
	public void setWall(String wall) {
		this.wall = wall;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	@Override
	public String toString() {
		return "House [wall=" + wall + ", top=" + top + "]";
	}
	
}
