import io.ktor.auth.*
import io.ktor.http.*

/**
 * Keycloak configuration file
 *
 * @author Vitor Goncalves
 * @since 27.04.2021, ter, 15:18
 */

val keycloakAddress = "http://localhost:8180"

val keycloakProvider = OAuthServerSettings.OAuth2ServerSettings(
    name = "keycloak",
    authorizeUrl = "$keycloakAddress/auth/realms/Ktor/protocol/openid-connect/auth",
    accessTokenUrl = "$keycloakAddress/auth/realms/Ktor/protocol/openid-connect/token",
    clientId = "KtorApp",
    clientSecret = "fe028175-f68b-4fe3-b572-4a24e1f0ad63",
    accessTokenRequiresBasicAuth = false,
    requestMethod = HttpMethod.Post,
    defaultScopes = listOf("openid")
)

const val keycloakOAuth = "keycloakOAuth"