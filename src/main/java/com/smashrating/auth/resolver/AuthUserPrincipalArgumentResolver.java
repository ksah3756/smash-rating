package com.smashrating.auth.resolver;

import com.smashrating.user.domain.User;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.exception.UserException;
import com.smashrating.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthUserPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserReader userReader;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @CurrentManager 애노테이션이 붙어 있고, 파라미터 타입이 Manager여야 함.
        return parameter.getParameterAnnotation(AuthUserPrincipal.class) != null
                && User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public com.smashrating.auth.dto.UserPrincipal resolveArgument(MethodParameter parameter,
                                                                  ModelAndViewContainer mavContainer,
                                                                  NativeWebRequest webRequest,
                                                                  WebDataBinderFactory binderFactory) throws Exception
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        return (com.smashrating.auth.dto.UserPrincipal) authentication.getPrincipal();
    }
}
