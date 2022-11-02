package de.ostfalia.s2.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;
@Named
@RequestScoped
public class DebugOverview {
    @Inject
    HttpServletRequest request;

    Logger log = Logger.getLogger(DebugOverview.class.getSimpleName());

    public void breakpoint(){
        log.info("Breakpoint reached by" + request.getHeader("user-agent"));
    }

    public void info(String msg){
        log.info(msg);
    }
}
