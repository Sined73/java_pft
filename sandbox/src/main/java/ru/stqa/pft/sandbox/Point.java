package ru.stqa.pft.sandbox;

public class Point {

  public double p1;
  public double p2;

  public Point(double p1, double p2) { //Конструктор. Закомитить, если значения будут вписаны в тело
    this.p1 = p1;
    this.p2 = p2;
  }

  public double distance() {          // Метод. Закоммитить, если метот нужно отключить
    return Math.sqrt((this.p1 * this.p1) + (this.p2 * this.p2));
  }
}