package feature

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(
    scanBasePackages = [
        "global.search.pretask.app",
        "global.search.pretask.api",
        "global.search.pretask.domain",
        "global.search.pretask.service",
        "global.search.pretask.persistence",
        "feature"
    ],
)
class FeatureTestApplication
