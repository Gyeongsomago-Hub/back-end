package com.gbsw.gbswhub.domain.global.util;

import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.project.model.Stack;

import java.util.List;
import java.util.stream.Collectors;

public class StackConverter {

    public static List<Stack> convertToStacks(List<String> stackNames, Project project) {
        return stackNames.stream()
                .map(stackName -> {
                    Stack stack = new Stack();
                    stack.setStack_name(stackName);
                    stack.setProject(project);
                    return stack;
                })
                .collect(Collectors.toList());
    }
}