package main.java.timer;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Modified implementation of Class created by KEV on:
 * http://stackoverflow.com/questions/9355303/javafx-stopwatch-timer.
 */

public class STimer extends Thread {
    private Thread thread = null;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss:S");
    private String[] split;
    private SimpleStringProperty min, sec, millis, sspTime;
    private long time;

    public STimer() {
        min = new SimpleStringProperty("00");
        sec = new SimpleStringProperty("00");
        millis = new SimpleStringProperty("00");
        sspTime = new SimpleStringProperty("00:00:00");
    }

    public void startTimer(long time) {
        this.time = time;
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void stopTimer(long time) {
        if (thread != null) {
            thread.interrupt();
        }
        this.time = time;

    }

    public void setTime(long time) {
        this.time = time;
        split = simpleDateFormat.format(new Date(time)).split(":");
        min.set(split[0]);
        sec.set(split[1]);

        if (split[2].length() == 1) {
            split[2] = "0" + split[2];
        }
        millis.set(split[2].substring(0, 2));

        sspTime.set(min.get() + ":" + sec.get() + ":" + millis.get());
    }

    public long getTime() {
        return time;
    }

    public SimpleStringProperty getSspTime() {
        return sspTime;
    }

    @Override
    public void run() {
        try {
            while (!thread.isInterrupted()) {
                setTime(time);
                sleep(10);
                time = time + 10;
            }
        } catch (Exception e) {
        }

    }
}
