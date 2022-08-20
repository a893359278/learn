package org.csp.learn.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈少平
 * @date 2022-08-20 09:28
 */
public class QuickStart {

    public static void main(String[] args) {


        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        while (true) {
            try(Entry entry = SphU.entry("HelloWorld")) {
                System.out.println(System.currentTimeMillis() + " hello world");
            } catch (BlockException ex) {
//                ex.printStackTrace();
            }
        }
    }
}
