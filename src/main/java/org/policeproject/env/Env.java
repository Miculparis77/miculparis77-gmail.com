package org.policeproject.env;

import com.cucumber.utils.engineering.utils.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Env {
    private static Logger LOG = LogManager.getLogger();
    private static Properties props = ResourceUtils.readProps("api/api.properties");
    public static final String apiHost = props.getProperty("api.host").strip();


}