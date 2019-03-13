package at.fhwn.ma.serverapp.app.config.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;

/**
 * Created by milos on 12/03/2019.
 */

@Aspect
@Configuration
public class AroundLogging {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String PACKAGE_WEBSERVICE = "execution(* at.fhwn.ma.serverapp.controller.*.*(..)) ||";
    private static final String PACKAGE_SERVICE = "execution(* at.fhwn.ma.serverapp.service.*.*(..)) ||";;
    private static final String PACKAGE_REPOSITORY = "execution(* at.fhwn.ma.serverapp.repository.*.*(..))";


    @Around(PACKAGE_WEBSERVICE + PACKAGE_SERVICE +PACKAGE_REPOSITORY)
    public Object logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("BEGIN " + joinPoint.getSignature().getName());

        Object obj;
        try {

            obj = joinPoint.proceed();

        }  finally {
            //Do Something useful, If you have
        }
        logger.info("END " + joinPoint.getSignature().getName());

        return obj;

    }

}
