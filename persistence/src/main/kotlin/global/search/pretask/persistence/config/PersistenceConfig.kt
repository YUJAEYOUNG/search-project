package global.search.pretask.persistence.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

open class PersistenceConfig {
    @PersistenceContext
    lateinit var em: EntityManager

    @Bean
    open fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(em)
    }

    @Bean
    open fun namedJdbcTemplate(ds: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(ds)
    }
}