package de.ostfalia.se.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Named
@RequestScoped
/**
 * Fire-And-Forget (RequestScoped) Bean for Debugging Purposes
 */
public class DebugOverview {

    @Inject
    HttpServletRequest request;

    // You may want to inject buggy beans here to do some testing, debugging or stack-trace gathering on them

    Logger log = Logger.getLogger(DebugOverview.class.getSimpleName());


    /**
     * Zum Mitloggen eines Aufrufs in der xhtml #{debugOverview.breakPoint()} nutzen.
     * Zum Debuggen des "Breakpoints" einfach die Methode in der IDE "Breakpointen".
     */
    public void breakPoint() {
        log.info("Breakpoint reached by " + request.getHeader("user-agent"));
    }

    public void info(String msg) {
        log.info(msg);
    }

}
