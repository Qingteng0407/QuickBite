package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充逻辑
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点 joinPoint
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

        /**
         * 前置通知，在通知中进行公共字段的赋值
         */
        @Before("autoFillPointCut()")
        public void autoFill(JoinPoint joinPoint) {
            log.info("开始进行公共字段自动填充");


            // 获取当前被拦截数据库方法的操作类型
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
            OperationType operationType = autoFill.value();// 获取操作类型
            // 获取到当前操作的实体对象
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) {
                return;
            }
            Object entity = args[0];
            // 准备赋值的数据
            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();

            // 根据不同操作类型，为对应的属性进行赋值
            if (operationType == OperationType.INSERT) {
                // 为4个公共字段赋值
                try {
                    Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                    Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                    // 通过反射赋值
                    setCreateTime.invoke(entity, now);
                    setUpdateTime.invoke(entity, now);
                    setCreateUser.invoke(entity, currentId);
                    setUpdateUser.invoke(entity, currentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(operationType == OperationType.UPDATE){
                // 为2个公共字段赋值
                try {
                    Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                    // 通过反射赋值
                    setUpdateTime.invoke(entity, now);
                    setUpdateUser.invoke(entity, currentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
}
