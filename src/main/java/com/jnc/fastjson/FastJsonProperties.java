package com.jnc.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 *
 * @author even
 */
@ConditionalOnClass({JSON.class})
@ConfigurationProperties(prefix = "spring.fastjson")
public class FastJsonProperties {
    /**
     * 默认Content-Type
     *
    /**
     * 是否生效
     */
    private boolean enabled;
    /**
     * 默认List<MediaType>
     */
    private List<MediaType> supportedMediaTypes = MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_UTF8_VALUE);
    /**
     * 实例化一个FastJsonConfig对象
     */
    private FastJsonConfig config = new FastJsonConfig();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<MediaType> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    public FastJsonConfig getConfig() {
        return config;
    }

    public void setConfig(FastJsonConfig config) {
        this.config = config;
    }

}
