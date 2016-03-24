package max93n.chart.c3.model;

public class Axis {

    private X x;
    private Y y;
    private Y y2;

    private Boolean rotated;


    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

    public Y getY2() {
        return y2;
    }

    public void setY2(Y y2) {
        this.y2 = y2;
    }

    public Boolean isRotated() {
        return rotated;
    }

    public void setRotated(Boolean rotated) {
        this.rotated = rotated;
    }
}
