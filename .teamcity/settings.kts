import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    name = "Build and Test"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dockerCommand {
            name = "build"
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                platform = DockerCommandStep.ImagePlatform.Linux
                namesAndTags = "qt:01"
            }
        }
        dockerCommand {
            name = "run"
            commandType = other {
                subCommand = "run"
                commandArgs = "-d -it --name qtContainer qt:01"
            }
        }
        dockerCommand {
            name = "test: hello is exist"
            commandType = other {
                subCommand = "exec"
                commandArgs = "qtContainer test -f hello"
            }
        }
        dockerCommand {
            name = "stop"
            commandType = other {
                subCommand = "stop"
                commandArgs = "qtContainer"
            }
        }
        dockerCommand {
            name = "rm"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            commandType = other {
                subCommand = "rm"
                commandArgs = "-f qtContainer"
            }
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_CUSTOM
            quietPeriod = 15
        }
    }

    features {
        perfmon {
        }
        commitStatusPublisher {
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:e936fa91-da18-45ce-9b40-0b46476e3d57"
                }
            }
        }
    }
})
