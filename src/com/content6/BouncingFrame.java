package com.content6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JFrame;

public class BouncingFrame implements Runnable {
    // Вертикальная и горизонтальная компонента скорости
    private int speed = 1;
    private int speedX = 3;
    private int speedY = 3;
    private Main frame;
    private int WIDTH;
    private int HEIGHT ;
    private static final int MAX_SPEED = 15;
    Toolkit kit = Toolkit.getDefaultToolkit();

    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width =kit.getScreenSize().width;
    int height =kit.getScreenSize().height;
    private int x;
    private int y;


    public BouncingFrame(Main frame) {
        // через getWidth(), getHeight()
        this.frame = frame;
        // Начальное направление скорости тоже случайно,
        // угол в пределах от 0 до 2PI
        //double angle = Math.random() * 2 * Math.PI;
        // Вычисляются горизонтальная и вертикальная компоненты скорости
       // speedX = 3 * Math.cos(angle);
      //  speedY = 3 * Math.sin(angle);

        WIDTH = frame.getWidth();
        HEIGHT = frame.getHeight();
        x = (kit.getScreenSize().width -WIDTH)/2;
        y = (kit.getScreenSize().height - HEIGHT)/2;
        System.out.println(width);
        System.out.println(height);
        System.out.println(WIDTH);
        System.out.println(HEIGHT);
        System.out.println(x);
        System.out.println(y);

// Создаѐм новый экземпляр потока, передавая аргументом
// ссылку на класс, реализующий Runnable(т.е. на себя)
        Thread thisThread = new Thread(this);
        // Запускаем поток
        thisThread.start();
    }


    // Метод run() исполняется внутри потока. Когда он завершает работу, // то завершается и поток
    public void run() {
        try {

            // Крутим бесконечный цикл, т.е. пока нас не прервут,
            // мы не намерены завершаться
            while (true) {
                // Синхронизация потоков на самом объекте поля
                // Если движение разрешено -управление будет
                // возвращено в метод
                // В противном случае -активный поток заснѐт
                frame.canMove();
                if (x+speedX >= width-WIDTH) {
                    // Достигли левой стенки, отскакиваем право
                    System.out.println("x+speedX");
                    System.out.println(x+speedX);
                    speedX=-speedX;
                    x+=speedX;
                    frame.setLocation(x,y);

                } else if (x + speedX <= 0) {
                    // Достигли правой стенки, отскок влево
                    speedX=-speedX;
                    x-=speedX;
                    frame.setLocation(x,y);

                } else if (y + speedY >=height-HEIGHT) {
                    // Достигли верхней стенки
                   speedY = -speedY;
                    y-=speedY;
                    frame.setLocation(x,y);
                } else if (y + speedY <= 0) {
                    // Достигли нижней стенки
                    speedY = -speedY;
                    y-=speedY;
                    frame.setLocation(x,y);
                }
                else {
                    // Просто смещаемся
                    System.out.println(1);
                    x+=speedX;
                    y+=speedY;
                   frame.setLocation(x,y);
                }
                // Засыпаем на Xмиллисекунд, где Xопределяется
                // исходя из скорости
                // Скорость = 1 (медленно), засыпаем на 15 мс.// Скорость = 15 (быстро), засыпаем на 1 мс.
                Thread.sleep(30-speed);

            }


        }
        catch(InterruptedException ex) {
            // Если нас прервали, то ничего не делаем
            // и просто выходим (завершаемся)
        }
    }

}
