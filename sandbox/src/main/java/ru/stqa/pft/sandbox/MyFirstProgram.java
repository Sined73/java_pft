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

    Point p = new Point(10, 11);  // Удалить параметры значений, если будет использоваться обычное вычисление расстояния без спользовании конструктора.
//    p.p1 = 7; // Для обычного вычисления расстояния. Снять коммит, без спользовании конструктора
//    p.p2 = 9;
    System.out.println("Расстояние между двумя точками равно " + p.distance() );

  }

  public static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }
//  public static double distance(Point p) {  //Для обычного вычисления расстояния без использования метода, прописанного в классе POINT
//    return Math.sqrt((p.p1 * p.p1) + (p.p2 * p.p2));
//  }
}