
include(":paper", ":waterfall", "fatJar", "shared")
rootProject.name = "minecraft2FA"
project(":paper").name = "minecraft2FA-paper"
project(":waterfall").name = "minecraft2FA-waterfall"
project(":shared").name = "minecraft2FA-shared"
findProject(":fatJar:shared")?.name = "shared"
