package aspectjtest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

@Aspect
public class ReplaceFooBarAspect {

    @Around("execution(* *.setValueTo*())")
    public Object getValue(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } finally {
            System.out.println(pjp.getThis().getClass().getDeclaredField("value").get(pjp.getThis()));
        }
    }

    @Around("execution(* *(.., @aspectjtest.ReplaceFooBar (*), ..))")
    public Object replaceFooBar(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[][] annotations;
        try {
            annotations = pjp.getTarget().getClass().
                    getMethod(methodName, parameterTypes).getParameterAnnotations();
        } catch (Exception e) {
            throw new SoftException(e);
        }

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation.annotationType() == ReplaceFooBar.class) {
                    Object raw = args[i];
                    if (raw instanceof String) {
                        args[i] = ((String) raw).replaceAll("foo", "bar");
                    }
                }
            }
        }

        return pjp.proceed(args);
    }
}
