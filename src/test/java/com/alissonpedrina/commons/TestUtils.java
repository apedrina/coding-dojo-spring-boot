package com.alissonpedrina.commons;

import lombok.experimental.UtilityClass;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
public class TestUtils {

    public static String getResourceFileFromJson(final String location) throws IOException {
        var file = ResourceUtils.getFile(location);
        return new String(Files.readAllBytes(file.toPath()));

    }

}