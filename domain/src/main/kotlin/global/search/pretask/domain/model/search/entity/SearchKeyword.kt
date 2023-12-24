package global.search.pretask.domain.model.search.entity

import global.search.pretask.domain.model.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(
    name = "search_keyword",
    indexes = [
        Index(name = "uidx_sk_keyword", columnList = "keyword", unique = true),
    ]
)
@Comment("search keyword table")
class SearchKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name = "keyword", nullable = false)
    var keyword: String,

    @Column(name = "count", nullable = false)
    var count: Long,
): BaseEntity()