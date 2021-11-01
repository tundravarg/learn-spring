package tuman.learnspring.server.dto;

public class LocationDto {

	private int id;
	private String name;
	private String type;


	public int getId() {
		return this.id;
	}

	public LocationDto setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public LocationDto setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return this.type;
	}

	public LocationDto setType(String type) {
		this.type = type;
		return this;
	}

}
