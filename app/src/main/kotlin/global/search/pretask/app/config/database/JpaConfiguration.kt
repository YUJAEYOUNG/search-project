package global.search.pretask.app.config.database

import global.search.pretask.persistence.config.PersistenceConfig
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["global.search.pretask.domain"])
@EnableJpaRepositories(basePackages = ["global.search.pretask.persistence"])
class JpaConfiguration: PersistenceConfig() {}