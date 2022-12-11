package de.ostfalia.s3.control.commands.command;

import de.ostfalia.s1.lamp.AbstractLampController;
import lombok.Getter;

public abstract class AbstractThreadCommand extends AbstractCommand {

    @Getter
    private Thread thread;

    public AbstractThreadCommand(AbstractLampController controller, String name) {
        super(controller, name);
    }

    public Thread runThread(Thread thread) {
        this.thread = thread;
        thread.start();
        return thread;
    }

    public void stopThread() {
        if(thread != null) thread.stop();
    }

}
