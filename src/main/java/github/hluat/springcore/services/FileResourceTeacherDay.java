package github.hluat.springcore.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import github.hluat.springcore.utils.Constants;

@Component
@Lazy
public class FileResourceTeacherDay extends FileResource{

    @Override
    public void loadResource(Map<String, String> fileLoad) {
        Map<String, FileSystemResource> x = new HashMap<>();
        x.put(Constants.IMAGE_COVER, new FileSystemResource("src/main/resources/static/teacher-day/image-5.jpg"));
        x.put(Constants.IMAGE_FOOTER, new FileSystemResource("src/main/resources/static/teacher-day/image-5.jpg"));
        // for(var key : fileLoad.keySet()){
        //     if (x.get(key) == null){
        //         x.put(key, new FileSystemResource(fileLoad.get(key)));
        //     }
        // }
        // setResourceProperties(x);
        super.resourceProperties = x;
    }
    
}
