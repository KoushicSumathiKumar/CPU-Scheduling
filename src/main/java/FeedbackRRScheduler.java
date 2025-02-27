import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Properties;

/**
 * Feedback Round Robin Scheduler
 *
 * @version 2017
 */
public class FeedbackRRScheduler extends AbstractScheduler {


  PriorityQueue<Process> readyQueue;

  int timeQuantum;

  @Override
  public void initialize(Properties parameters) {
    timeQuantum = Integer.parseInt(parameters.getProperty("timeQuantum"));
    readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
  }

  @Override
  public int getTimeQuantum() {
    return timeQuantum;
  }

  @Override
  public boolean isPreemptive() {
    return true;
  }

  private void demoteProcess(Process process) {
    process.setPriority(process.getPriority() + 1);
  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  public void ready(Process process, boolean usedFullTimeQuantum) {
    if (usedFullTimeQuantum) {
      demoteProcess(process);
      readyQueue.remove(process);
    }
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
}