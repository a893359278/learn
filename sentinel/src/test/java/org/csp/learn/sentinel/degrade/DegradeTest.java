package org.csp.learn.sentinel.degrade;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.Sph;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.sun.org.apache.bcel.internal.generic.NEW;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-03-23 21:14
 */
public class DegradeTest {

    @Test
    public void test_1() {
        DegradeRule rule = new DegradeRule();
        rule.setCount(100);
        rule.setTimeWindow(10);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setResource("degradeMethodA");
        List<DegradeRule> list = new ArrayList<>();
        list.add(rule);

        DegradeRuleManager.loadRules(list);
        while (true) {
            try(Entry entry = SphU.entry("degradeMethodA")) {
                System.out.println("degradeMethodA");
            } catch (Exception e) {
            }
        }

    }

    private void methodA() {

    }
}
