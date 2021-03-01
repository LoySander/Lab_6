package com.content6;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
@SuppressWarnings("serial")
public class Main extends JFrame {
    // Константы, задающие размер окна приложения, если оно
    // не распахнуто на весь экран
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem resumeMenuItem1;
    private JMenuItem pauseMenuItem1;
    private BouncingFrame field2;
    // Поле, по которому прыгают мячи
    private Field field = new Field();
    private boolean paused = true;

    // Конструктор главного окна приложения
    public Main() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        paused = true;
        field2 = new BouncingFrame(this);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        // Установить начальное состояние окна развѐрнутым на весь экран
        //setExtendedState(MAXIMIZED_BOTH);
        // Создать меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");

        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
                    // Ни один из пунктов меню не являются
                    // доступными -сделать доступным "Паузу"
                    pauseMenuItem.setEnabled(true);
                }
            }
        };
    menuBar.add(ballMenu);
    ballMenu.add(addBallAction);
    JMenu fieldMenu = new JMenu("Теория относительности");
    menuBar.add(fieldMenu);
    Action ActivateAction = new AbstractAction("Активировать"){
            public void actionPerformed(ActionEvent event) {
               //bouncingframe.BouncingFrame
                resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };

        resumeMenuItem=fieldMenu.add(ActivateAction);
        resumeMenuItem.setEnabled(true);
        Action DeactivateAction = new AbstractAction("Деактивировать"){
            public void actionPerformed(ActionEvent event) {
               pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        menuBar.add(fieldMenu);
        pauseMenuItem=fieldMenu.add(DeactivateAction);
        pauseMenuItem.setEnabled(true);
    JMenu controlMenu = new JMenu("Управление");
    menuBar.add(controlMenu);
    Action pauseAction = new AbstractAction("Приостановить движение"){
        public void actionPerformed(ActionEvent event) {
            field.pause();
            pauseMenuItem.setEnabled(false);
            resumeMenuItem.setEnabled(true);
        }
    };
    pauseMenuItem=controlMenu.add(pauseAction);
    pauseMenuItem.setEnabled(true);
    Action resumeAction = new AbstractAction("Возобновить движение") {
        public void actionPerformed(ActionEvent event) {
            field.resume();
            pauseMenuItem.setEnabled(true);
            resumeMenuItem.setEnabled(false);
        }
    };
    resumeMenuItem= controlMenu.add(resumeAction);
    resumeMenuItem.setEnabled(false);
    // Добавить в центр граничной компоновки поле Field
        getContentPane().add(field, BorderLayout.CENTER);
    }
    public synchronized void pause() {
        // Включить режим паузы
        paused= true;
    }
    // Метод синхронизированный, т.е. только один поток может
    // одновременно быть внутри
    public synchronized void resume() {
        // Выключить режим паузы
        paused= false;
        // Будим все ожидающие продолжения потоки

        notifyAll();
    }
    public synchronized void canMove() throws InterruptedException {
        if(paused) {

            // Если режим паузы включен, то поток, зашедший // внутрь данного метода, засыпает
            wait();
        }
    }




    public static void main(String[] args) {
      // Создать и сделать видимым главное окно приложения
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}