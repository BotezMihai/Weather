package com.example.demo.aspects;

import com.example.demo.commonfunctions.SharedVariables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CountIncrementAspect {
//    @Around(value = "@annotation(com.example.demo.aspects.CountIncrement)")
    @Before("execution(* com.example.demo.service.WeatherService.getWeatherNow(..))")
    public void countIncrement() {
        SharedVariables.setCount(SharedVariables.getCount() + 1);
    }
}
