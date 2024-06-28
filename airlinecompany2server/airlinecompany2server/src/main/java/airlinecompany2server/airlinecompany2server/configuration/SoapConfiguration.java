package airlinecompany2server.airlinecompany2server.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapConfiguration {

    @Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(messageDispatcherServlet, "/ws/*");
	}

    @Bean(name = "airline-company-2")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema airlineCompanySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AirlineCompany2Port");
        wsdl11Definition.setTargetNamespace("http://localhost:8082/");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setSchema(airlineCompanySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema airlineCompanySchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/airline-company-2.xsd"));
    }
}
