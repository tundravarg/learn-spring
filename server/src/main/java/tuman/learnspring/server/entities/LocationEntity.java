package tuman.learnspring.server.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class LocationEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_id_generator")
	@SequenceGenerator(name = "locations_id_generator", sequenceName = "locations_is_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@ManyToOne(targetEntity = LocationEntity.class, fetch = FetchType.LAZY)
	private LocationEntity parent;

	// TODO LAZY
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private List<LocationEntity> children;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocationEntity getParent() {
		return parent;
	}

	public void setParent(LocationEntity parent) {
		this.parent = parent;
	}

	public List<LocationEntity> getChildren() {
		return children;
	}

	public void setChildren(List<LocationEntity> chidlren) {
		this.children = chidlren;
	}

}
