package com.example.demo.aspects;

import com.example.demo.commonfunctions.SharedVariables;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CountIncrementAspect {
    @Before("@annotation(com.example.demo.aspects.CountIncrement)")
    public void incrementCount(){
        SharedVariables.setCount(SharedVariables.getCount() + 1);
    }
}
