package global.search.pretask.domain.model.search.transfer.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BlogSearchResponse(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("blogname")
    val blogname: String,
    @JsonProperty("datetime")
    val datetime: String, // yyyyMMdd
    @JsonProperty("summary")
    val summary: String,
)
