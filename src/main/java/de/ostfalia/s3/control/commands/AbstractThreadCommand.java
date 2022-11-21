package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractThreadCommand extends AbstractCommand {

    @Getter
    private Thread thread;

    public AbstractThreadCommand(AbstractLampController controller, String name) {
        super(controller, name);
    }

    public Thread runThread(Thread thread) {
        this.thread = thread;
        System.out.println("Set thread to " + this.thread);
        thread.start();
        return thread;
    }


    public void stopThread() {
        System.out.println("Stopping thread " + this.thread);
        if (thread != null && !thread.isInterrupted()) thread.interrupt();
    }

}
