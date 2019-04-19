package co.uk.domainmodelingmadefunktional

import android.util.Patterns
import androidx.core.text.isDigitsOnly
import arrow.core.Either
import arrow.core.Option
import java.util.*

class String50 private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String) =
            if (value.isBlank() || value.length > 50) {
                Either.left(ValidationError("Length can't be greater than 50"))
            } else {
                Either.right(Option.just(String50(value)))
            }
    }
}

inline class ValidationError(val value: String)

class EmailAddress private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String) =
            if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                Either.right(EmailAddress(value))
            } else {
                Either.left(ValidationError("Invalid email address"))
            }
    }
}

class ZipCode private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String) =
            if (value.isDigitsOnly() && value.length == 5) {
                Either.right(ZipCode(value))
            } else {
                Either.left(ValidationError("Only 5 digits allowed"))
            }
    }
}

class OrderId private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String) {
            UUID.fromString(value)
        }
    }
}

class OrderLineId private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String) =
            if (value.isBlank() || value.length > 50) {
                Either.right(OrderLineId(value))
            } else {
                Either.left(ValidationError("Length must be greather than 50"))
            }
    }
}

sealed class ProductCode {

    companion object {
        operator fun invoke(value: String) =
            when {
                value.isBlank() -> Either.left(ValidationError("ProductCode cannot be blank"))
                value.startsWith("W") -> WidgetCode(value).fold({ Either.left(it) }, { Either.right(it) })
                value.startsWith("G") -> GizmoCode(value).fold({ Either.left(it) }, { Either.right(it) })
                else -> Either.left(ValidationError("Format not recognised"))
            }
    }

    class WidgetCode private constructor(val value: String) : ProductCode() {
        companion object {
            operator fun invoke(value: String) =
                if (value.isDigitsOnly() && value.length == 4) {
                    Either.right(WidgetCode(value))
                } else {
                    Either.left(ValidationError("Only 4 digits"))
                }
        }
    }

    class GizmoCode private constructor(val value: String) : ProductCode() {
        companion object {
            operator fun invoke(value: String) =
                if (value.isDigitsOnly() && value.length == 3) {
                    Either.right(GizmoCode(value))
                } else {
                    Either.left(ValidationError("Only 4 digits"))
                }
        }
    }
}

sealed class OrderQuantity {

    class UnitQuantity private constructor(val value: Int) {
        companion object {
            operator fun invoke(value: Int) {
                if (value in (1..1000)) Either.right(UnitQuantity(value)) else Either.left(ValidationError("Must be between 1 and 1000"))
            }
        }
    }

    class KilogramQuantity(val value: Double) {
        companion object {
            operator fun invoke(value: Double) {
                if (value in (0.05..100.00)) Either.right(KilogramQuantity(value)) else Either.left(ValidationError("Must be between 0.05 and 100.00"))
            }
        }
    }
}

class Price private constructor(val value: Double) {
    companion object {
        operator fun invoke(value: Double) =
            if (value in (0.0..1000.00)) Either.right(Price(value)) else Either.left(ValidationError("Must be between 0 and 1000.00"))
    }
}

class BillingAmount private constructor(val value: Double) {
    companion object {
        operator fun invoke(value: Double) =
            if (value in (0.0..1000.00)) Either.right(BillingAmount(value)) else Either.left(ValidationError("Must be between 0 and 1000.00"))
    }
}

data class PdfAttachment(val name: String, val bytes: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PdfAttachment

        if (name != other.name) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}

