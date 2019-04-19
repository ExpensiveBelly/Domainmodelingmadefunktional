package co.uk.domainmodelingmadefunktional

data class UnvalidatedCustomerInfo(val firstName: String, val lastName: String, val emailAdress: String)

data class UnvalidatedAddress(
    val addressLine1: String,
    val addressLine2: String,
    val addressLine3: String,
    val addressLine4: String,
    val city: String,
    val zipCode: String
)

data class UnvalidatedOrderLine(
    val orderLineId: String,
    val productCode: String,
    val quantity: Double
)

data class UnvalidatedOrder(
    val orderId: String,
    val customerInfo: UnvalidatedCustomerInfo,
    val shippingAddress: UnvalidatedAddress,
    val billingAddress: UnvalidatedAddress,
    val lines: List<UnvalidatedOrderLine>
)