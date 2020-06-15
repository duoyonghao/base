package com.kqds.util.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

public class PackagesSqlSessionFactoryBean
  extends SqlSessionFactoryBean
{
  static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
  private Logger logger = LoggerFactory.getLogger(PackagesSqlSessionFactoryBean.class);
  private String typeAliasesPackage;
  
  public void setTypeAliasesPackage(String typeAliasesPackages)
  {
    try
    {
      List<String> result = new ArrayList();
      String[] typeAliasesPackageArr = typeAliasesPackages.split(";");
      for (String typeAliasesPackage : typeAliasesPackageArr)
      {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        typeAliasesPackage = "classpath*:" + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/" + 
          "**/*.class";
        Resource[] resources = resolver.getResources(typeAliasesPackage);
        if ((resources != null) && (resources.length > 0))
        {
          MetadataReader metadataReader = null;
          for (Resource resource : resources) {
            if (resource.isReadable())
            {
              metadataReader = metadataReaderFactory.getMetadataReader(resource);
              try
              {
                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
              }
              catch (ClassNotFoundException e)
              {
                e.printStackTrace();
              }
            }
          }
        }
      }
      if (result.size() > 0) {
        super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
      } else {
        this.logger.warn("参数typeAliasesPackage:" + this.typeAliasesPackage + "，未找到任何包");
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
