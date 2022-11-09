package top.homesoft.drools.infrastructure.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import top.homesoft.drools.base.domain.DroolRules;
import top.homesoft.drools.infrastructure.ext.DroolRulesService;
import top.homesoft.drools.infrastructure.ext.DroolRulesServiceImpl;


import java.io.IOException;
import java.util.List;

@Configuration
public class DroolsConfig {
    private static final String RULES_PATH = "droolRules/";
    private final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public DroolRulesService droolRulesService() {
        return new DroolRulesServiceImpl();
    }

    /**
     * 指定规则路径
     *
     * @return
     * @throws IOException
     */
    @Bean
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] files = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*");
        String path = null;
        for (Resource file : files) {
            path = RULES_PATH + file.getFilename();
            kieFileSystem.write(ResourceFactory.newClassPathResource(path, "UTF-8"));
        }
        return kieFileSystem;
    }

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    public KieSession kieSession() throws IOException {
        return kieContainer().newKieSession();
    }

    @Bean("kieSessionByDB")
    public KieSession kieSessionByDB(DroolRulesService droolRulesService) throws IOException {
        List<DroolRules> rules = droolRulesService.list();
        KieHelper helper = new KieHelper();
        rules.forEach((item) -> {
            helper.addContent(item.getRuleItem(), ResourceType.DRL);
        });

        return helper.build().newKieSession();
    }

    @Bean
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}
