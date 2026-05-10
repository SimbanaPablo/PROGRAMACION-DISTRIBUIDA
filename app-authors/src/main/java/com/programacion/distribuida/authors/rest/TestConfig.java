package com.programacion.distribuida.authors.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path("/config")
public class TestConfig {
    @Inject
    @ConfigProperty(name = "quarkus.http.portgod", defaultValue = "8080")
    Integer port;

    @GET
    public String test(){
        Config config = ConfigProvider.getConfig();
        // -- Listar fuentes de confiugracion
         config.getConfigSources().forEach(
                 it -> {
                     System.out.printf("[%d] \t %s\n",it.getOrdinal(),it.getName());
                 }
         );

         //-- Recuperar un valor de configuracion
        String url = config.getValue("quarkus.datasource.jdbc.url", String.class);
        Integer puerto = config.getValue("quarkus.http.port", Integer.class);
        Optional<String> titulo = config.getOptionalValue("app.title", String.class);


        System.out.println("/////////////////////////////////////////////////");
        System.out.println("URL: "+url);
        System.out.println("PUERTO: "+puerto);
        if(titulo.isPresent()){
            System.out.println("Titulo: "+titulo.get());
        }else{
            System.out.println("No existe");
        }

        // config + DI
        System.out.println("PUERTO (DI): "+port);

        return "tok";
    }
}
