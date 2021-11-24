package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testLength1() {
    Point p1 = new Point(5, -16);
    Point p2 = new Point(0, 3);
    Assert.assertEquals(p1.distance(p2), 19.6468827043885);
  }

  @Test
  public void testLength2() {
    Point p1 = new Point(-3, 9);
    Point p2 = new Point(8, 7);
    Assert.assertEquals(Point.distance(p1, p2), 11.180339887498949);
  }

  @Test
  public void testLength3() {
    Point p1 = new Point(10, -11);
    Point p2 = new Point(4, -1);
    assert Point.distance(p1, p2) > 0;
    Assert.assertEquals(Point.distance(p1, p2), 11.661903789690601);
  }
}