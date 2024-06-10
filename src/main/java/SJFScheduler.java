import java.util.PriorityQueue;
import java.util.Properties;

/**
 * Shortest Job First Scheduler
 *
 * @version 2017
 */
public class SJFScheduler extends AbstractScheduler {

  double initialBurstEstimate;
  double alphaBurstEstimate;

  PriorityQueue<Process> readyQueue;

  @Override
  public void initialize(Properties parameters) {
    initialBurstEstimate = Double.parseDouble(parameters.getProperty("initialBurstEstimate"));
    alphaBurstEstimate = Double.parseDouble(parameters.getProperty("alphaBurstEstimate"));
    readyQueue = new PriorityQueue<>((Process p1, Process p2) -> (int) (nextBurst(p1) - nextBurst(p2)));
  }

  @Override
  public int getTimeQuantum() {
    return super.getTimeQuantum();
  }

  @Override
  public boolean isPreemptive() {
    return false;
  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  public void ready(Process process, boolean usedFullTimeQuantum) {
    readyQueue.add(process);
  }

  /**
   * Removes the next process to be run from the ready queue
   * and returns it.
   * Returns null if there is no process to run.
   */
  public Process schedule() {
    System.out.println("Scheduler selects process "+readyQueue.peek());
    return readyQueue.poll();
  }

  // Exponential Averaging: T(n+1) = a * T(n) + (1 - a) * T(n)
  // Predicts the next burst time for a process.
  // Prediction is calculated using exponential average.
  private double nextBurst(Process process) {
    double perdNextBurst;

    if (process.getRecentBurst() == -1) {
      perdNextBurst = initialBurstEstimate;
    } else {
      perdNextBurst = (alphaBurstEstimate * process.getRecentBurst()) + ((1 - alphaBurstEstimate) * process.getRecentBurst());
    }
    return perdNextBurst;
  }
}