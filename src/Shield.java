import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shield extends Circle{
	private int heat;
	
	public Shield() {
		super(100, Main.HEIGHT/2, 55);
		this.setFill(Color.rgb(20, 20, 100, .3));
		heat = 0;
	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}
	
	public void activate() {
		if(heat > 0) {
			setVisible(true);
		}
		else {
			setVisible(false);
		}
	}

}
