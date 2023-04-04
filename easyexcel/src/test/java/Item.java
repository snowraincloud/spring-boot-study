import java.util.List;

public class Item {
	private String parentId;
	private String itemCode;
	private String itemName;
	private String cateName;
	private List<Item> children;

	public String getParentId(){
		return parentId;
	}

	public String getItemCode(){
		return itemCode;
	}

	public String getItemName(){
		return itemName;
	}

	public String getCateName(){
		return cateName;
	}

	public List<Item> getChildren(){
		return children;
	}
}
