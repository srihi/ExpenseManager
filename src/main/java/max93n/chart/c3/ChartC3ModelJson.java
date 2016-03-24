package max93n.chart.c3;

import max93n.chart.c3.model.*;

import java.io.Serializable;

public class ChartC3ModelJson implements Serializable{

    private String bindto;

    private Data data;

    private Bar bar;

    private Axis axis;

    private Grid grid;

    private Legend legend;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getBindto() {
        return bindto;
    }

    public void setBindto(String bindto) {
        this.bindto = bindto;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }
}
