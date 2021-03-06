package jenkins.plugins.glip.webhook.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;


public class JsonResponse implements HttpResponse {
    private int status;

    private String json;

    public JsonResponse(Object obj, int status) {
        this.json = null;
        try {
            this.json = new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            this.json = new JSONObject().put("text", ex.getMessage()).toString();
        } 
        this.status = status;
    }

    @Override
    public void generateResponse(StaplerRequest req,
        StaplerResponse rsp,
        Object o) throws IOException, ServletException {

        rsp.setStatus(status);
        rsp.setContentType("application/json;charset=UTF-8");
        rsp.getWriter().println(json);
    }

}
