package global.search.pretask.domain.model.external.kakao.transfer.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Document(
    @JsonProperty("blogname")
    val blogname: String,
    @JsonProperty("contents")
    val contents: String,
    @JsonProperty("datetime")
    val datetime: String,
    @JsonProperty("thumbnail")
    val thumbnail: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("url")
    val url: String
)
