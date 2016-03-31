package max93n.chart.c3.model;

import java.util.HashMap;
import java.util.Map;

public class Data {

    private String[][] columns;
    private String x;
    private String type;
    private Map<String, String> axes;
    private Map<String, String> colors;

    public Data() {
        axes = new HashMap<>();
        colors = new HashMap<>();
    }

    private Boolean labels;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[][] getColumns() {
        return columns;
    }

    public void setColumns(String[][] columns) {
        this.columns = columns;
    }

    public Map<String, String> getAxes() {
        return axes;
    }

    public void setAxes(Map<String, String> axes) {
        this.axes = axes;
    }

    public Map<String, String> getColors() {
        return colors;
    }

    public void setColors(Map<String, String> colors) {
        this.colors = colors;
    }

    public Boolean getLabels() {
        return labels;
    }

    public void setLabels(Boolean labels) {
        this.labels = labels;
    }
}
