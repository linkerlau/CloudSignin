package signin.model;

public class ChartData {

    private String label;
    private double data;

    public ChartData() {
    }

    @Override
    public String toString() {
        return "ChartData{" +
                "label='" + label + '\'' +
                ", data=" + data +
                '}';
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}