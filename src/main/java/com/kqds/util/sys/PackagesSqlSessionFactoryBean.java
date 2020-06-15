package com.kqds.util.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

public class PackagesSqlSessionFactoryBean extends SqlSessionFactoryBean {
  static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
  
  private Logger logger = LoggerFactory.getLogger(PackagesSqlSessionFactoryBean.class);
  
  private String typeAliasesPackage;
  
  public void setTypeAliasesPackage(String typeAliasesPackages) {
    try {
      List<String> result = new ArrayList<>();
      String[] typeAliasesPackageArr = typeAliasesPackages.split(";");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = typeAliasesPackageArr).length, b = 0; b < i; ) {
        String typeAliasesPackage = arrayOfString1[b];
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory((ResourceLoader)pathMatchingResourcePatternResolver);
        typeAliasesPackage = "classpath*:" + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/" + 
          "**/*.class";
        Resource[] resources = pathMatchingResourcePatternResolver.getResources(typeAliasesPackage);
        if (resources != null && resources.length > 0) {
          MetadataReader metadataReader = null;
          byte b1;
          int j;
          Resource[] arrayOfResource;
          for (j = (arrayOfResource = resources).length, b1 = 0; b1 < j; ) {
            Resource resource = arrayOfResource[b1];
            if (resource.isReadable()) {
              metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
              try {
                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
              } catch (ClassNotFoundException e) {
                e.printStackTrace();
              } 
            } 
            b1++;
          } 
        } 
        b++;
      } 
      if (result.size() > 0) {
        super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
      } else {
        this.logger.warn("参数typeAliasesPackage:" + this.typeAliasesPackage + "，未找到任何包");
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
