package br.com.zup.autores

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Target(FIELD)
@Constraint(validatedBy = [CepValidator::class])
@Retention(RUNTIME)
annotation class Cep(
    val message: String = "CEP inválido"
)

@Singleton
class CepValidator : ConstraintValidator<Cep, String> {

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<Cep>,
        context: ConstraintValidatorContext,
    ): Boolean {
        if (value == null) {
            return true
        }
        return value.matches("[0-9]{5}-[0-9]{3}".toRegex())
    }

}
