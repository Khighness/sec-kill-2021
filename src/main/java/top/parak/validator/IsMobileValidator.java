package top.parak.validator;

import org.apache.commons.lang3.StringUtils;
import top.parak.util.ValidateUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 手机号码格式校验器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required || StringUtils.isNotBlank(value)) {
            return ValidateUtil.isMobile(value);
        } else {
            return true;
        }
    }
}
