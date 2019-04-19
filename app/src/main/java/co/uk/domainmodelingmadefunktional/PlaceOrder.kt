package co.uk.domainmodelingmadefunktional

interface PlaceOrder {

    fun checkProductCodeExists(productCode: ProductCode): Boolean

}

sealed class AddressValidationError {
    object InvalidFormat
    object AddressNotFound
}

class CheckedAddress private constructor()