package com.smashrating.auth.resolver;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.common.exception.CommonErrorCode;
import com.smashrating.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthUserDtoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthUserDto.class) != null
                && UserDto.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public UserDto resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new CustomException(CommonErrorCode.UNAUTHORIZED);
        }
        Object principal = authentication.getPrincipal();
        // anonymousUser 일 수 있으므로 instanceof로 체크 후 캐스팅
        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new CustomException(CommonErrorCode.UNAUTHORIZED);
        }
        return UserDto.of(
                userPrincipal.getRole(),
                userPrincipal.getId(),
                userPrincipal.getUsername(),
                ""
        );
    }
}
