package top.homesoft.drools;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.homesoft.drools.domain.person.People;
import top.homesoft.drools.domain.person.Person;

import java.util.ArrayList;
import java.util.List;

public class Test3 {


    @BeforeClass
    public static  void setup(){

    }

    //@Test
    public void test() {
        Person person = new Person();
        person.setAge(10);
        person.setName("Test");

        Person person1 = new Person();
        person1.setAge(15);
        person1.setName("Test1");





        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("personSession");

        List list = new ArrayList<>();
      //  kieSession.setGlobal("myList", list);
        kieSession.insert(person);
        kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("man"));
        //kieSession.fireAllRules();
        kieSession.dispose();



    }
    @Test
    public void test2(){
        People people = new People();
        people.setSex(0);
        people.setName("小芳");
        people.setDrlType("people");

        Person person = new Person();
        person.setAge(16);
        person.setName("Test");
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("personSession");
        //传参
        kieSession.insert(people);
        kieSession.insert(person);
        //执行指定名称的规则
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("girlAndPerson"));
    }
}
