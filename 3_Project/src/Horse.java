import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Horse extends Canvas {	
	private GraphicsContext gc;
	private Thread animationThread;
	private double xPos;
	private HorseRace horseRace;
	private long startTime;
	private long stopTime;
	private int finishLine;
	public static AtomicBoolean animationRunning = new AtomicBoolean(true);
	
	private void DrawHorse() {
		gc.setFill(Color.BROWN);
		gc.fillRect(xPos + 10, 10, 100, 40);
		gc.setFill(Color.BLACK);
		gc.fillRect(xPos + 100,0, 50, 12);
	}
	
	private void ClearCanvas() {
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	private int getRandomNumber() {
		int maxValue = 30;
		int minValue = 1;
        Random random = new Random();
        return random.nextInt((maxValue - minValue) + 1) + minValue;
    }
	
	public Horse(HorseRace horseRace) {
		super(1000, 100);
		startTime = 0;
		stopTime = 0;
		xPos = 0;
		finishLine = 300;
		this.horseRace = horseRace;
		gc = this.getGraphicsContext2D();
		DrawHorse();
	}

	public synchronized void AnimateHorse() {
		startTime = System.currentTimeMillis();
		setTranslateX(xPos);
		DrawHorse();
		xPos += getRandomNumber();
		if (animationThread != null && animationThread.isAlive()) return; // if thread does exist, and, a thread is running somewhere in memory: do not start animation. This line keeps a lot of stuff from crashing.
		animationRunning.set(true);;
		animationThread = new Thread(() -> { // replace code in run() with lambda exp.
			while (animationRunning.get()) {
				if (xPos > finishLine + 2) HasWon();
				stopTime = System.currentTimeMillis();
				xPos += getRandomNumber(); // horse will move forward by a number 1 - 30
				ClearCanvas();
				DrawHorse(); // Draw the horse
				try {Thread.sleep(150);} catch (InterruptedException e) {Thread.currentThread().interrupt(); break;} 
			}
		});
		animationThread.start();
	}
	
	// Resets a horse to the starting position
	public void ResetHorse() {
		animationRunning.set(false);
		if (animationThread != null && animationThread.isAlive()) {
            animationThread.interrupt();
            animationThread = null;
        }
		xPos = 0;
		ClearCanvas();
		DrawHorse();
	}
	
	// Checks to see if a horse has crossed the finish line
	private boolean HasWon() {
	    if (xPos > finishLine && animationRunning.get()) {
	        animationRunning.set(false);
	        displayWinnerMessage();
	        return true;
	    } else {
	        return false;
	    }
	}

	// Dialog box was crashing the program. After multiple attempts to fix it I just defaulted with a System.out.println
	private void displayWinnerMessage() { 
	    int horseNumber = Arrays.asList(horseRace.GetHorses()).indexOf(this) + 1;
	    double elapsedTime = (((double)stopTime / 1000) - ((double)startTime / 1000));
	    elapsedTime = Math.round(elapsedTime * 1000);
	    elapsedTime /= 1000;
	    System.out.println("Horse " + horseNumber + " is the winner!\nTime: " + elapsedTime);
	}
}


