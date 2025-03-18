package dev.freya02.commandinator.api.utils

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.ANNOTATION_CLASS)
@AuthenticationPrincipal
annotation class DashboardUser

@Suppress("UNCHECKED_CAST")
operator fun <T> OAuth2AuthenticatedPrincipal.get(name: String): T? = attributes[name] as T?

@Suppress("UNCHECKED_CAST")
operator fun <T> OAuth2User.get(name: String): T? = attributes[name] as T?