package com.secusoft.web.model;

public class Round {
    private Point point;
    private Double radius;

    public Round() {
    }

    public Round(Point point, Double radius) {
        this.point = point;
        this.radius = radius;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Round{" +
                "point=" + point +
                ", radius=" + radius +
                '}';
    }
}
