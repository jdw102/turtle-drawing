package oolala.Models;

/**
 * A class to hold the information of running status.
 * The RunningStatus instance is passed from AppModel to TurtleView, so TurtleView can know whether the commands are running.
 *
 * @author Luyao Wang
 */
public class RunningStatus {
    private boolean isRunning = false;

    public RunningStatus() {
    }

    /**
     * A method to set running status.
     *
     * @param isRunning, the running status
     * @author Luyao Wang
     */
    public void setRunningStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * A method to get the running status.
     *
     * @return the running status
     * @author Luyao Wang
     */
    public boolean isRunning() {
        return isRunning;
    }
}
