package com.sdzk.buss.web.quartz;

import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import org.quartz.Scheduler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Administrator on 17-9-20.
 */
public class InitQuartzServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = this.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        QrtzManagerServiceI qrtzManagerServiceI = (QrtzManagerServiceI) wac.getBean("qrtzManagerServiceImpl",QrtzManagerServiceI.class);
        Scheduler scheduler = (Scheduler) wac.getBean("quartzScheduler",Scheduler.class);
        qrtzManagerServiceI.resumeAll(scheduler);
    }
}
