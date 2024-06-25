package airlinecompany2server.airlinecompany2server.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlTemplate {
    private String baseHtml;
    private String htmlBuild;

    public HtmlTemplate(String filePath) {
        try {
            this.baseHtml = new String(Files.readAllBytes(Paths.get(filePath)));
            this.htmlBuild = baseHtml;
        } catch(IOException e) {
            this.baseHtml = "";
            this.htmlBuild = "";
        }
    }

    public void setBaseValue(String variable, String value) {
        String target = String.format("{%s}", variable);
        if (!this.baseHtml.contains(target)) {
            return ;
        }

        this.baseHtml = this.baseHtml.replace(target, value);
        setValue(variable, value);
    }

    public void setValue(String variable, String value) {
        String target = String.format("{%s}", variable);
        if (!this.htmlBuild.contains(target)) {
            return ;
        }

        this.htmlBuild = this.htmlBuild.replace(target, value);
    }

    public void reset() {
        this.htmlBuild = this.baseHtml;
    }

    public String getHtml() {
        return this.htmlBuild;
    }
    
}
