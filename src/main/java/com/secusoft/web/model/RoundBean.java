package com.secusoft.web.model;

public class RoundBean {
    private PointBean pointBean;
    private Double radius;

    public RoundBean() {
    }

    public RoundBean(PointBean pointBean, Double radius) {
        this.pointBean = pointBean;
        this.radius = radius;
    }

    public PointBean getPointBean() {
        return pointBean;
    }

    public void setPointBean(PointBean pointBean) {
        this.pointBean = pointBean;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "RoundBean{" +
                "pointBean=" + pointBean +
                ", radius=" + radius +
                '}';
    }
}
