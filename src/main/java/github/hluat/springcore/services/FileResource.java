package github.hluat.springcore.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;

public abstract class FileResource {
    protected Map<String, FileSystemResource> resourceProperties;

    public abstract void loadResource(Map<String, String> pathLoad);

    public void setResourceProperties(Map<String, FileSystemResource> resources ){
        this.resourceProperties = resources;
    }

    public Map<String, FileSystemResource> getResource(){
        if (resourceProperties == null) {
            return new HashMap<>();
        }
        return resourceProperties;
   }
}
