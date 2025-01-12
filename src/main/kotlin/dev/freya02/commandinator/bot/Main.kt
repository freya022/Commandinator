package dev.freya02.commandinator.bot

import ch.qos.logback.classic.ClassicConstants
import dev.freya02.commandinator.bot.config.Environment
import dev.reformator.stacktracedecoroutinator.jvm.DecoroutinatorJvmApi
import io.github.freya022.botcommands.api.core.BotCommands
import io.github.freya022.botcommands.api.core.config.DevConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import java.lang.management.ManagementFactory
import kotlin.io.path.absolutePathString
import kotlin.system.exitProcess

private val logger by lazy { KotlinLogging.logger { } }

fun main(args: Array<String>) {
    try {
        System.setProperty(ClassicConstants.CONFIG_FILE_PROPERTY, Environment.logbackConfigPath.absolutePathString())
        logger.info { "Loading logback configuration at ${Environment.logbackConfigPath.absolutePathString()}" }

        // I use hotswap agent to update my code without restarting the bot
        // Of course this only supports modifying existing code
        // Refer to https://github.com/HotswapProjects/HotswapAgent#readme on how to use hotswap

        // stacktrace-decoroutinator has issues when reloading with hotswap agent
        if ("-XX:+AllowEnhancedClassRedefinition" in ManagementFactory.getRuntimeMXBean().inputArguments) {
            logger.info { "Skipping stacktrace-decoroutinator as enhanced hotswap is active" }
        } else if ("--no-decoroutinator" in args) {
            logger.info { "Skipping stacktrace-decoroutinator as --no-decoroutinator is specified" }
        } else {
            DecoroutinatorJvmApi.install()
        }

        BotCommands.create {
            disableExceptionsInDMs = Environment.isDev

            addSearchPath("dev.freya02.commandinator.bot")

            applicationCommands {
                fileCache {
                    @OptIn(DevConfig::class)
                    checkOnline = Environment.isDev
                }

                addLocalizations("commands")
            }

            components {
                enable = true
            }

            localization {
                addResponseBundle("responses")
            }

            appEmojis {
                enable = true
            }

            textCommands {
                enable = false
            }

            modals {
                enable = false
            }
        }

        logger.info { "Loaded bot" }
    } catch (e: Exception) {
        logger.error(e) { "Unable to start the bot" }
        exitProcess(1)
    }
}