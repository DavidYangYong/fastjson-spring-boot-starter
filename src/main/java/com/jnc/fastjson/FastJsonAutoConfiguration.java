package com.jnc.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * Created by Administrator on 2018/12/20 0020.
 *
 * @author even
 */
@Configuration
@ConditionalOnClass({JSON.class,FastJsonHttpMessageConverter.class})
@EnableConfigurationProperties({FastJsonProperties.class})
@Slf4j
public class FastJsonAutoConfiguration {

	@Resource
	private FastJsonProperties fastJsonProperties;

	@Bean
	@ConditionalOnProperty(prefix = "spring.fastjson", name = "enabled", havingValue = "true", matchIfMissing = true)
	public HttpMessageConverters httpMessageConverter() {
		log.debug("FastJsonAutoConfiguration start");
		/**
		 * FastJsonHttpMessageConverter转换类
		 */
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		/*媒体类型*/
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastJsonProperties.getSupportedMediaTypes());
		/*配置*/
		FastJsonConfig config = fastJsonProperties.getConfig();
		if (config != null) {
			SerializerFeature[] serializerFeatures=	config.getSerializerFeatures();
			SerializerFeature[] serializerFeaturesDefault=new SerializerFeature[]{SerializerFeature.PrettyFormat, SerializerFeature.WriteNonStringKeyAsString,
					SerializerFeature.WriteNonStringValueAsString, SerializerFeature.WriteNullNumberAsZero};
			if (serializerFeatures!=null&&serializerFeatures.length>0){
				List<SerializerFeature> a = Arrays.asList(serializerFeatures);
				List<SerializerFeature> b = Arrays.asList(serializerFeaturesDefault);
				//并集
				Collection<SerializerFeature> union = CollectionUtils.union(a, b);
				SerializerFeature[] serializerFeaturesNew=new SerializerFeature[]{};
				serializerFeatures=union.toArray(serializerFeaturesNew);
			}else{
				serializerFeatures=serializerFeaturesDefault;
			}
			config.setSerializerFeatures(serializerFeatures);
		}
		fastJsonHttpMessageConverter.setFastJsonConfig(config);
		HttpMessageConverter<?> converter1 = fastJsonHttpMessageConverter;
		return new HttpMessageConverters(converter1);
	}
}
