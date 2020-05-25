package javaJG.geometry;

public interface ChangeListener {
	
	public default void onDimensionChange(double oldWidth, double oldHeight, double width, double height) {
		
	}
	public default void onDimensionCall(double oldWidth, double oldHeight, double width, double height) {
		
	}
	public default void onPositionChange(double oldX, double oldY, double x, double y) {
		
	}
	public default void onPositionCall(double oldX, double oldY, double x, double y) {
		
	}
	
}
