package com.djawnstj.mvcframework.web.servlet.view;

import com.djawnstj.mvcframework.web.servlet.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HtmlView implements View {

    private static final Logger log = LoggerFactory.getLogger(HtmlView.class);

    private final String viewName;

    public HtmlView(final String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        setResponseMetaData(resp);

        final String viewPath = getViewPath(req);

        renderHtmlFile(resp, viewPath);
    }

    private void setResponseMetaData(final HttpServletResponse resp) {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
    }

    private String getViewPath(final HttpServletRequest req) {
        final ServletContext sc = req.getServletContext();
        return sc.getRealPath(viewName);
    }

    private void renderHtmlFile(final HttpServletResponse resp, final String viewPath) throws IOException {
        final PrintWriter out = resp.getWriter();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(viewPath), StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        } catch (Exception e) {
            log.error("`{}` render fail - {}", viewName, e.getMessage());
            throw e;
        }
    }

}
