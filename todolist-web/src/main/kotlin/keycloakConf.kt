import io.ktor.auth.*
import io.ktor.http.*

/**
 * Keycloak configuration file
 *
 * @author Vitor Goncalves
 * @since 27.04.2021, ter, 15:18
 */

val keycloakAddress = "https://keycloak-development-instance.herokuapp.com"

val keycloakProvider = OAuthServerSettings.OAuth2ServerSettings(
    name = "keycloak",
    authorizeUrl = "$keycloakAddress/auth/realms/Ktor/protocol/openid-connect/auth",
    accessTokenUrl = "$keycloakAddress/auth/realms/Ktor/protocol/openid-connect/token",
    clientId = "KtorApp",
    clientSecret = "c2310b6c-daba-49f9-9947-9e36432e5a31",
    accessTokenRequiresBasicAuth = false,
    requestMethod = HttpMethod.Post,
    defaultScopes = listOf("openid")
)

const val keycloakOAuth = "keycloakOAuth"