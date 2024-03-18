package io.github.dsvdev.letovo_os.config

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

class YamlPropertySourceFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> =
        PropertiesPropertySource(resource.resource.filename!!,
            YamlPropertiesFactoryBean().also { it.setResources(resource.resource) }.getObject()!!
        )
}