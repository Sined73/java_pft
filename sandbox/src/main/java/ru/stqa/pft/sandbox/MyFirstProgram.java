package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
    hello("world");
    hello("user");
    hello("Denis");

    Square s = new Square(5);
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(4,6);
    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

    Point p1 = new Point(10, -11);
    Point p2 = new Point(4, -1);
    System.out.println("Расстояние между двумя точками равно " + p1.distance(p2) );
    System.out.println("Расстояние между двумя точками равно " + Point.distance(p1, p2) );

  }

  public static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }

}